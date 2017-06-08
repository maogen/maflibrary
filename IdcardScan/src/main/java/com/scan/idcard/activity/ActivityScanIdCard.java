package com.scan.idcard.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.ShutterCallback;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.scan.idcard.R;
import exocr.exocrengine.EXIDCardResult;
import exocr.exocrengine.EXOCREngine;
import com.scan.idcard.idcard.CameraManager;
import com.scan.idcard.idcard.CaptureActivityHandler;
import com.scan.idcard.idcard.InactivityTimer;
import com.scan.idcard.idcard.ViewfinderView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 扫描身份证界面
 * 使用方法：
 * 1：在Manifest.xml中注册com.scan.idcard.activity.ActivityScanIdCard
 * 2：使用
 * ActivityScanIdCard.actionStart(Activity activity, int requestCode)
 * 启动扫描界面
 * 3：在onActivityResult中通过
 * IDCardResult result = (IDCardResult) data.getSerializableExtra(com.scan.idcard.activity.SysCode.SCAN_RESULT_ID_CARD);
 * 获取扫描之后的身份信息
 */
public class ActivityScanIdCard extends Activity implements Callback {
    /**
     * 启动扫描界面
     *
     * @param activity
     * @param requestCode
     */
    public static void actionStart(Activity activity, int requestCode) {
        Intent scanIntent = new Intent(activity, ActivityScanIdCard.class);
        scanIntent.putExtra(INTNET_FRONT, true); // true:正面； false背面
        activity.startActivityForResult(scanIntent, requestCode);
    }

    private static final String TAG = ActivityScanIdCard.class.getSimpleName();
    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private TextView txtResult;
    private ImageView imgView;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private int time;
    private byte dbpath[];
    //private Bitmap logo;
    private boolean bLight;

    private final int lastCardsLength = 5;
    //save last  time recognize result
    private EXIDCardResult[] lastCards = new EXIDCardResult[lastCardsLength];
    //private EXIDCardResult cardlast = null;
    //last index 0 ~ lastCardsLength -1
    private int lastCardsIndex = 0;

    private int compareCount = 0;
    //current time recognition result
    private EXIDCardResult cardRest = null;

    private static int uniqueOMatic = 10;
    private static final int REQUEST_DATA_ENTRY = uniqueOMatic++;

    private static final String FRONT_TIP = "请将身份证放在屏幕中央，正面朝上";
    private static final String BACK_TIP = "请将身份证放在屏幕中央，背面朝上";
    private static final String ERR_FRONT_TIP = "检测到身份证背面，请将正面朝上";
    private static final String ERR_BACK_TIP = "检测到身份证正面，请将背面朝上";
    private static final String INTNET_FRONT = "ShouldFront";
    private boolean bshouleFront;
    private boolean bLastWrong;
    private boolean bCamera;
    private PopupWindow popupWindow;
    private static final int MSG_POPUP = 1001;

    public static Bitmap IDCardFrontFullImage = null;
    public static Bitmap IDCardBackFullImage = null;

    public static Bitmap IDCardNameImage = null;
    public static Bitmap IDCardFaceImage = null;

    private final ShutterCallback shutterCallback = new ShutterCallback() {
        public void onShutter() {
            AudioManager mgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            mgr.playSoundEffect(AudioManager.FLAG_PLAY_SOUND);
        }
    };

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 检测摄像头权限
        bCamera = hardwareSupportCheck();
        // CameraManager
        CameraManager.init(getApplication());
        // 横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // FLAG_TRANSLUCENT_NAVIGATION
        if (Build.VERSION.SDK_INT >= 19) {
            //FIXME (yongming)换到api4.0时注释掉了此处
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
            //WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.idcardpreview);

        if (bCamera) {
            viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
            txtResult = (TextView) findViewById(R.id.txtResult);
            imgView = (ImageView) findViewById(R.id.FaceImg);
            hasSurface = false;
            inactivityTimer = new InactivityTimer(this);
            time = 0;
            //logo = BitmapFactory.decodeResource(this.getResources(), R.drawable.yidaoboshi);
            //viewfinderView.setLogo(logo);
            bshouleFront = getIntent().getBooleanExtra(INTNET_FRONT, true);
            Log.d(TAG, "bshouleFront:" + bshouleFront);
            if (bshouleFront) {
                viewfinderView.setTipText(FRONT_TIP);
                Log.d(TAG, "正面");
            } else {
                viewfinderView.setTipText(BACK_TIP);
                Log.d(TAG, "反面");
            }
            bLight = false;
//            InitDict(RESOURCEFILEPATH);
            String path = this.getApplicationContext().getFilesDir().getAbsolutePath();
            InitDict(path);
        } else { // 摄像头权限受限
        }
    }

    public static boolean hardwareSupportCheck() {
        // Camera needs to open
        Camera c = null;
        boolean ret = true;
        try {
            c = Camera.open();
        } catch (RuntimeException e) {
            // throw new RuntimeException();
            ret = false;
        }
        if (c == null) { // 没有背摄像头
            return false;
        }
        if (ret) {
            c.release();
            c = null;
        }
        return ret;
    }

    public boolean InitDict(String dictpath) {
        dbpath = new byte[256];
        // if the dict not exist, copy from the assets
        if (CheckExist(dictpath + "/zocr0.lib") == false) {
            File destDir = new File(dictpath);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }

            boolean a = copyFile("zocr0.lib", dictpath + "/zocr0.lib");
            if (a == false) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("exidcard dict Copy ERROR!\n");
                builder.setMessage(dictpath + " can not be found!");
                builder.setCancelable(true);
                builder.create().show();
                return false;
            }
        }

        String filepath = dictpath;

        // string to byte
        for (int i = 0; i < filepath.length(); i++)
            dbpath[i] = (byte) filepath.charAt(i);
        dbpath[filepath.length()] = 0;

        int nres = EXOCREngine.nativeInit(dbpath);

        if (nres < 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("exidcard dict Init ERROR!\n");
            builder.setMessage(filepath + " can not be found!");
            builder.setCancelable(true);
            builder.create().show();
            return false;
        } else {
            // just test
            // ExTranslator.nativeExTran(imgdata, width, height, pixebyte,
            // pitch, flft, ftop, frgt, fbtm, result, maxsize)
        }
        // sign ocr sdk
        EXOCREngine.nativeCheckSignature(this.getApplicationContext());
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bCamera) {
            //重置比对计数
            compareCount = 0;
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.IDpreview_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            if (hasSurface) {
                initCamera(surfaceHolder);
            } else {
                surfaceHolder.addCallback(this);
                surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            }

            playBeep = true;
            AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
            if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
                playBeep = false;
            }
            initBeepSound();
            vibrate = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        // inactivityTimer.shutdown();
        EXOCREngine.nativeDone();
        super.onDestroy();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    // show the decode result
    public void handleDecode(EXIDCardResult result) {
        // inactivityTimer.onActivity();
        if (result == null) {
            return;
        }
        // 将扫描结果返回到
        Intent intent = new Intent();
        IDCardResult idCardResult = new IDCardResult();
        idCardResult.setAddress(result.address);
        idCardResult.setBirth(result.birth);
        idCardResult.setCardNum(result.cardnum);
        idCardResult.setName(result.name);
        idCardResult.setNation(result.nation);
        idCardResult.setSex(result.sex);
        intent.putExtra(SysCode.SCAN_RESULT_ID_CARD, idCardResult);
        setResult(RESULT_OK, intent);
        finish();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (bCamera) {
            float x = event.getX();
            float y = event.getY();
            Point res = CameraManager.get().getResolution();

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (x > res.x * 8 / 10 && y < res.y / 4) {
                    return false;
                }

                handleDecode(null);

                // 点击重新聚焦
                handler.restartAutoFocus();
                return true;
            }
        }
        return false;
    }

    public boolean copyFile(String from, String to) {
        // 例：from:890.salid;
        // to:/mnt/sdcard/to/890.salid
        boolean ret = false;
        try {
            int bytesum = 0;
            int byteread = 0;

            InputStream inStream = getResources().getAssets().open(from);// 将assets中的内容以流的形式展示出来
            File file = new File(to);
            OutputStream fs = new FileOutputStream(file);// to为要写入sdcard中的文件名称
            byte[] buffer = new byte[1024];
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                fs.write(buffer, 0, byteread);
            }
            inStream.close();
            fs.close();
            ret = true;

        } catch (Exception e) {
            ret = false;
        }
        return ret;
    }

    // check one file
    public boolean CheckExist(String filepath) {
        int i;
        File file = new File(filepath);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    public void SetRecoResult(EXIDCardResult result) {
        cardRest = result;
    }

    //check is equal()
    public boolean CheckIsEqual(EXIDCardResult cardcur) {
        if (!(EXIDCardResult.DOUBLE_CHECK)) {
            return true;
        }
        if (compareCount++ > 50) {
            return true;
        }
        EXIDCardResult cardlast;
        for (int i = 0; i < lastCardsLength; i++) {
            if (lastCards[i] != null) {
                cardlast = lastCards[i];
                if (cardlast.type == 1 && cardcur.type == 1) {
                    if (cardlast.name.equals(cardcur.name) &&
                            cardlast.sex.equals(cardcur.sex) &&
                            cardlast.nation.equals(cardcur.nation) &&
                            cardlast.cardnum.equals(cardcur.cardnum) &&
                            cardlast.address.equals(cardcur.address)) {
                        //Log.e("比对成功",  String.valueOf(i));
                        return true;
                    }
                } else if (cardlast.type == 2 && cardcur.type == 2) {
                    if (cardlast.validdate.equals(cardcur.validdate) &&
                            cardlast.office.equals(cardcur.office)) {
                        //Log.e("比对成功",  String.valueOf(i));
                        return true;
                    }
                }
            }
        }

        lastCardsIndex++;
        if (lastCardsIndex + 1 > lastCardsLength) {
            lastCardsIndex = 0;
        }
        if (lastCards[lastCardsIndex] == null) {
            lastCards[lastCardsIndex] = new EXIDCardResult();
        }
        lastCards[lastCardsIndex].type = cardcur.type;
        if (cardcur.type == 1) {
            lastCards[lastCardsIndex].sex = cardcur.sex;
            lastCards[lastCardsIndex].nation = cardcur.nation;
            lastCards[lastCardsIndex].cardnum = cardcur.cardnum;
            lastCards[lastCardsIndex].address = cardcur.address;
            lastCards[lastCardsIndex].name = cardcur.name;
        } else if (cardcur.type == 2) {
            lastCards[lastCardsIndex].validdate = cardcur.validdate;
            lastCards[lastCardsIndex].office = cardcur.office;
        }
        //Log.e("比对失败",  String.valueOf(lastCardsIndex));
        return false;
    }

    public ShutterCallback getShutterCallback() {
        return shutterCallback;
    }

    public void OnFlashBtnClick(View view) {
        if (bLight) {
            CameraManager.get().disableFlashlight();
            bLight = false;
        } else {
            CameraManager.get().enableFlashlight();
            bLight = true;
        }
    }

    //////////////////////////////////////////////////////////////////////////
    public void OnShotBtnClick(View view) {
        // Toast.makeText(this, "Button clicked!", Toast.LENGTH_SHORT).show();
        handleDecode(null);
        // playBeepSoundAndVibrate();
        handler.takePicture();
    }
}
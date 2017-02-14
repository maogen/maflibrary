package com.maf.base.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.artifex.mupdf.MuPDFCore;
import com.artifex.mupdf.MuPDFPageAdapter;
import com.artifex.mupdf.ReaderView;
import com.artifex.mupdf.SavePdf;
import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnDrawListener;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.maf.activity.BaseTitleActivity;
import com.maf.utils.DialogUtil;
import com.maf.utils.Lg;
import com.maf.utils.SdcardUtils;

import java.io.File;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：浏览pdf的界面
 * 创建人：mzg
 * 创建时间：2017/2/9 16:03
 * 修改人：mzg
 * 修改时间：2017/2/9 16:03
 * 修改备注：
 */

public class ReadPdfActivity extends BaseTitleActivity implements OnPageChangeListener, OnLoadCompleteListener, OnDrawListener {
    // pdf文件路径
    private String pdfPath = SdcardUtils.getRootPath() + "MAF_DIR/pdf/123.pdf";
    private String pdfOutPath = SdcardUtils.getRootPath() + "MAF_DIR/pdf/123_Temp.pdf";
    // 浏览pdf控件
    private PDFView pdfView;

    // 使用mupdf浏览dpf
    private ReaderView readerView;
    private MuPDFCore pdfCore;
    // 显示签名的控件
    private ImageView imageView;

    @Override
    protected void initTitleView() {
        titleBarView.setTitle("浏览");
        titleBarView.setOnMenuClick(R.drawable.icon_write_paint, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReadPdfActivity.this, SignalActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_read_pdf;
    }

    @Override
    protected void initView() {
        imageView = (ImageView) findViewById(R.id.imageView);
        // 初始化pdfview浏览控件
        pdfView = (PDFView) findViewById(R.id.pdfView);
        pdfView.fromFile(new File(pdfPath))   //设置pdf文件地址
                .defaultPage(1)         //设置默认显示第1页
                .onPageChange(this)     //设置翻页监听
                .onLoad(this)           //设置加载监听
                .onDraw(this)            //绘图监听
                .showMinimap(false)     //pdf放大的时候，是否在屏幕的右上角生成小地图
                .swipeVertical(false)  //pdf文档翻页是否是垂直翻页，默认是左右滑动翻页
                .enableSwipe(true)   //是否允许翻页，默认是允许翻
                // .pages( 2 , 3 , 4 , 5  )  //把2 , 3 , 4 , 5 过滤掉
                .load();
        // 初始化mupdf浏览控件
        readerView = (ReaderView) findViewById(R.id.readerView);
        try {
            pdfCore = new MuPDFCore(pdfPath);
            readerView.setAdapter(new MuPDFPageAdapter(this, pdfCore));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void initValue() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        // 开始保存
                        DialogUtil.showProgressDialog(ReadPdfActivity.this);
                        break;
                    case 1:
                        // 保存结束
                        DialogUtil.dismissDialog();
                        try {
                            Lg.d("显示文件：" + pdfOutPath);
                            pdfCore = new MuPDFCore(pdfOutPath);
                            readerView.setAdapter(new MuPDFPageAdapter(ReadPdfActivity.this, pdfCore));
                            readerView.setmScale(1.0f);
                            readerView.setDisplayedViewIndex(readerView.getDisplayedViewIndex());
                            imageView.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void loadComplete(int nbPages) {
        Lg.d("加载结束：" + nbPages);
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        Lg.d("翻页：" + page + ";" + pageCount);
    }

    @Override
    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
        Lg.d("绘图：" + pageWidth + ";" + pageHeight);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1000:
                // 从签名界面回来
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = data.getParcelableExtra("bitmap");
                    Lg.d("签字图片大小Height：" + bitmap.getHeight() + ";Width：" + bitmap.getWidth());
                    imageView.setImageBitmap(bitmap);
                    imageView.setVisibility(View.VISIBLE);
                    savePdf(bitmap);
                }
                break;
            default:
                break;
        }
    }

    private Handler handler;

    /**
     * 保存签字
     *
     * @param bitmap 签名的bitmap
     */
    private void savePdf(Bitmap bitmap) {
        File outFile = new File(pdfOutPath);
        if (outFile.exists()) {
            outFile.deleteOnExit();
        }
        float scale = readerView.getmScale();///得到放大因子
        SavePdf savePdf = new SavePdf(pdfPath, pdfOutPath);
        savePdf.setScale(scale);

        // 设置签名在当前显示的页码
        savePdf.setPageNum(readerView.getDisplayedViewIndex() + 1);

        savePdf.setWidthScale(1.0f * readerView.scrollX / readerView.getDisplayedView().getWidth());//计算宽偏移的百分比
        savePdf.setHeightScale(1.0f * readerView.scrollY / readerView.getDisplayedView().getHeight());//计算长偏移的百分比
        Lg.d("readerView的大小" + readerView.getHeight() + "；" + readerView.getWidth());

        //计算分辨率密度
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        savePdf.setDensity(metric.density);

        // 创建一个原始的bitmap，改bitmap大小和readerView展示的大小一致
//        Bitmap backgroundBitmap = Bitmap.createBitmap(readerView.getWidth(), readerView.getHeight(),
//                Bitmap.Config.ARGB_8888);
//        Bitmap newBitmap = BitmapUtils.toConformBitmap(backgroundBitmap, bitmap);
//        Lg.d("bitmap大小" + bitmap.getHeight() + "；" + bitmap.getWidth());
//        Lg.d("newBitmap大小" + newBitmap.getHeight() + "；" + newBitmap.getWidth());
        savePdf.setBitmap(bitmap);
//        SavePdfTask saveTask = new SavePdfTask(savePdf);
//        saveTask.execute();


    }


    class SavePdfTask extends AsyncTask {
        private SavePdf savePdf;

        public SavePdfTask(SavePdf savePdf) {
            this.savePdf = savePdf;
        }

        @Override
        protected Object doInBackground(Object[] params) {
//            BaseToast.makeTextShort("开始保存");
            Lg.d("开始保存");
            handler.sendEmptyMessage(0);
            savePdf.addText();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
//            BaseToast.makeTextShort("存储完成");
            handler.sendEmptyMessage(1);
            Lg.d("存储完成");
        }
    }
}

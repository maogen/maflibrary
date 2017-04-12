package com.maf.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.maf.R;
import com.maf.utils.BitmapUtils;

/**
 * 项目名称：maflibrary
 * 类描述：自定义SwitchView
 * 创建人：mzg
 * 创建时间：2016/12/14 15:28
 * 修改人：mzg
 * 修改时间：2016/12/14 15:28
 * 修改备注：
 */

public class SlideSwitch extends View {
    public static final int SWITCH_OFF = 0;//关闭状态
    public static final int SWITCH_ON = 1;//打开状态
    public static final int SWITCH_SCROLING = 2;//滚动状态

    //用于显示的文本
    private String mOnText = "";
    private String mOffText = "";
    private float mTextSize = 18;

    private int mSwitchStatus = SWITCH_OFF;

    private boolean mHasScrolled = false;//表示是否发生过滚动

    private int mSrcX = 0, mDstX = 0;

    private int mBmpWidth = 0;
    private int mBmpHeight = 0;
    private int mThumbWidth = 0;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private OnSwitchChangedListener mOnSwitchChangedListener = null;

    //开关状态图
    Bitmap mSwitch_off, mSwitch_on;
//            , mSwitch_thumb;

    public SlideSwitch(Context context) {
        this(context, null);
    }

    public SlideSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlideSwitch(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    //初始化三幅图片
    private void init() {
        Resources res = getResources();
        mSwitch_off = BitmapFactory.decodeResource(res, R.drawable.icon_permission_select);
        mSwitch_on = BitmapFactory.decodeResource(res, R.drawable.icon_permission_unselect);
//        mSwitch_thumb = BitmapFactory.decodeResource(res, R.drawable.radio_unchecked);
        mBmpWidth = mSwitch_on.getWidth();
        mBmpHeight = mSwitch_on.getHeight();
//        mThumbWidth = mSwitch_thumb.getWidth();
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
//        params.width = mBmpWidth;
//        params.height = mBmpHeight;
        int width = params.width;// 自定义的宽度
        int height = params.height;// 自定义的高度
        // 按照布局中自定义的宽度和高度，将背景图片缩放
        mSwitch_on = BitmapUtils.scaleBitmap(mSwitch_on, width, height);
        mSwitch_off = BitmapUtils.scaleBitmap(mSwitch_off, width, height);
        mBmpWidth = mSwitch_on.getWidth();
        mBmpHeight = mSwitch_on.getHeight();
        super.setLayoutParams(params);
    }

    /**
     * 为开关控件设置状态改变监听函数
     *
     * @param onSwitchChangedListener 参见 {@link OnSwitchChangedListener}
     */
    public void setOnSwitchChangedListener(OnSwitchChangedListener onSwitchChangedListener) {
        mOnSwitchChangedListener = onSwitchChangedListener;
    }

    /**
     * 设置开关上面的文本
     *
     * @param onText  控件打开时要显示的文本
     * @param offText 控件关闭时要显示的文本
     */
    public void setText(String onText, String offText) {
        mOnText = onText;
        mOffText = offText;
        invalidate();
    }

    /**
     * 设置开关的状态
     *
     * @param on 是否打开开关
     */
    public void setStatus(boolean on) {
        mSwitchStatus = (on ? SWITCH_OFF : SWITCH_ON);
    }

    /**
     * 是否打开
     *
     * @return 是否打开
     */
    public boolean isStatus() {
        return mSwitchStatus == SWITCH_ON ? false : true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mSrcX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                mDstX = Math.max((int) event.getX(), 10);
                mDstX = Math.min(mDstX, 62);
                if (mSrcX == mDstX)
                    return true;
//                mHasScrolled = true;
//                AnimationTransRunnable aTransRunnable = new AnimationTransRunnable(mSrcX, mDstX, 0);
//                new Thread(aTransRunnable).start();
                mSrcX = mDstX;
                break;
            case MotionEvent.ACTION_UP:
                if (mHasScrolled == false) {//如果没有发生过滑动，就意味着这是一次单击过程
                    mSwitchStatus = Math.abs(mSwitchStatus - 1);
                    int xFrom = 10, xTo = 62;
                    if (mSwitchStatus == SWITCH_OFF) {
                        xFrom = 62;
                        xTo = 10;
                    }
                    AnimationTransRunnable runnable = new AnimationTransRunnable(xFrom, xTo, 1);
                    new Thread(runnable).start();
                } else {
                    invalidate();
                    mHasScrolled = false;
                }
                //状态改变的时候 回调事件函数
                if (mOnSwitchChangedListener != null) {
                    mOnSwitchChangedListener.onSwitchChanged(this, mSwitchStatus);
                }
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘图的时候 内部用到了一些数值的硬编码，其实不太好，
        //主要是考虑到图片的原因，图片周围有透明边界，所以要有一定的偏移
        //硬编码的数值只要看懂了代码，其实可以理解其含义，可以做相应改进。
        mPaint.setTextSize(mTextSize);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);

        if (mSwitchStatus == SWITCH_OFF) {
            drawBitmap(canvas, null, null, mSwitch_off);
            int count = canvas.save();
//            drawBitmap(canvas, null, null, mSwitch_thumb);
            canvas.restoreToCount(count);
//            canvas.translate(mSwitch_thumb.getWidth(), 0);
//            mPaint.setColor(Color.rgb(105, 105, 105));
//            canvas.drawText(mOffText, 0, 20, mPaint);
            drawText(canvas);
        } else if (mSwitchStatus == SWITCH_ON) {
            drawBitmap(canvas, null, null, mSwitch_on);
            int count = canvas.save();
//            canvas.translate(mSwitch_on.getWidth() - mSwitch_thumb.getWidth(), 0);
//            drawBitmap(canvas, null, null, mSwitch_thumb);
            canvas.restoreToCount(count);
//            mPaint.setColor(Color.WHITE);
//            canvas.drawText(mOnText, 17, 20, mPaint);
            drawText(canvas);
        } else {
            mSwitchStatus = mDstX > 35 ? SWITCH_ON : SWITCH_OFF;
            drawBitmap(canvas, new Rect(0, 0, mDstX, mBmpHeight), new Rect(0, 0, (int) mDstX, mBmpHeight), mSwitch_on);
//            mPaint.setColor(Color.WHITE);
//            canvas.drawText(mOnText, 17, 20, mPaint);

            int count = canvas.save();
            canvas.translate(mDstX, 0);
            drawBitmap(canvas, new Rect(mDstX, 0, mBmpWidth, mBmpHeight),
                    new Rect(0, 0, mBmpWidth - mDstX, mBmpHeight), mSwitch_off);
            canvas.restoreToCount(count);

            count = canvas.save();
            canvas.clipRect(mDstX, 0, mBmpWidth, mBmpHeight);
            canvas.translate(mThumbWidth, 0);
//            mPaint.setColor(Color.rgb(105, 105, 105));
//            canvas.drawText(mOffText, 0, 20, mPaint);
            canvas.restoreToCount(count);

            count = canvas.save();
            canvas.translate(mDstX - mThumbWidth / 2, 0);
//            drawBitmap(canvas, null, null, mSwitch_thumb);
            canvas.restoreToCount(count);
        }

    }

    /**
     * 绘制文字
     *
     * @param canvas canvas
     */
    private void drawText(Canvas canvas) {
        if (mSwitchStatus == SWITCH_OFF) {
            mPaint.setColor(Color.WHITE);
//            canvas.drawText(mOffText, 10, mBmpHeight / 2 - 20, mPaint);
            canvas.drawText(mOffText, 10, mBmpHeight / 2 + 6, mPaint);
        } else {
            mPaint.setColor(Color.rgb(105, 105, 105));
//            canvas.drawText(mOnText, mBmpWidth - mOnText.length() * 20, mBmpHeight / 2 - 20, mPaint);
            canvas.drawText(mOnText, mBmpWidth - 30, mBmpHeight / 2 + 6, mPaint);
        }
    }

    public void drawBitmap(Canvas canvas, Rect src, Rect dst, Bitmap bitmap) {
        dst = (dst == null ? new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()) : dst);
        Paint paint = new Paint();
        canvas.drawBitmap(bitmap, src, dst, paint);
    }

    /**
     * AnimationTransRunnable 做滑动动画所使用的线程
     */
    private class AnimationTransRunnable implements Runnable {
        private int srcX, dstX;
        private int duration;

        /**
         * 滑动动画
         *
         * @param srcX     滑动起始点
         * @param dstX     滑动终止点
         * @param duration 是否采用动画，1采用，0不采用
         */
        public AnimationTransRunnable(float srcX, float dstX, final int duration) {
            this.srcX = (int) srcX;
            this.dstX = (int) dstX;
            this.duration = duration;
        }

        @Override
        public void run() {
            final int patch = (dstX > srcX ? 5 : -5);
            if (duration == 0) {
                SlideSwitch.this.mSwitchStatus = SWITCH_SCROLING;
                SlideSwitch.this.postInvalidate();
            } else {
                int x = srcX + patch;
                while (Math.abs(x - dstX) > 5) {
                    mDstX = x;
                    SlideSwitch.this.mSwitchStatus = SWITCH_SCROLING;
                    SlideSwitch.this.postInvalidate();
                    x += patch;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mDstX = dstX;
                SlideSwitch.this.mSwitchStatus = mDstX > 35 ? SWITCH_ON : SWITCH_OFF;
                SlideSwitch.this.postInvalidate();
            }
        }

    }

    public interface OnSwitchChangedListener {
        /**
         * 状态改变 回调函数
         *
         * @param status SWITCH_ON表示打开 SWITCH_OFF表示关闭
         */
        void onSwitchChanged(SlideSwitch obj, int status);
    }

    /**
     * 设置不可选
     */
    public void setNotSwitch() {
        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }
}

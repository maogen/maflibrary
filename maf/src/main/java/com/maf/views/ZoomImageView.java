package com.maf.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.maf.R;
import com.maf.utils.Lg;

/**
 * 项目名称：maflibrary
 * 类描述：可以缩放和删除的Image
 * 创建人：mzg
 * 创建时间：2017/2/13 14:50
 * 修改人：mzg
 * 修改时间：2017/2/13 14:50
 * 修改备注：
 */

public class ZoomImageView extends RelativeLayout {
    private ImageView imageDelete;// 删除按钮
    private ImageView imageZoom;// 缩放按钮
    private ImageView imageContent;// 展示内容的控件

    public ZoomImageView(Context context) {
        super(context);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZoomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        imageDelete = (ImageView) findViewById(R.id.image_sign_delete);
        imageZoom = (ImageView) findViewById(R.id.image_sign_zoom);
        imageContent = (ImageView) findViewById(R.id.image_content);
        initZoomListener();
        super.onFinishInflate();
    }

    private float lastX, lastY;

    /**
     * 添加搜索监听器
     */
    private void initZoomListener() {
        imageZoom.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = event.getRawX();
                        lastY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Lg.d("lastX:" + lastX + ";lastY:" + lastY);
                        // 获取当前move的坐标
                        float rawX = event.getRawX();
                        float rawY = event.getRawY();
                        Lg.d("rawX:" + rawX + ";rawY:" + rawY);
                        // 获取当前控件的尺寸
                        ViewGroup.LayoutParams layoutParams = getLayoutParams();
//                        int viewWidth = layoutParams.width;
//                        int viewHeight = layoutParams.height;
//                        Lg.d("viewWidth:" + viewWidth + ";viewHeight:" + viewHeight);
//                        float viewX = getX();
//                        float viewY = getY();
//                        Lg.d("viewX:" + viewX + ";viewY:" + viewY);
                        float addX = rawX - lastX;
                        float addY = rawY - lastY;
                        Lg.d("addX:" + addX + ";addY:" + addY);
                        layoutParams.width = (int) (layoutParams.width + addX);
                        layoutParams.height = (int) (layoutParams.height + addY);
                        setLayoutParams(layoutParams);
                        lastX = rawX;
                        lastY = rawY;
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 未删除按钮添加监听器
     *
     * @param onDeleteListener
     */
    public void setOnDeleteListener(OnClickListener onDeleteListener) {
        imageDelete.setOnClickListener(onDeleteListener);
    }

    /**
     * 设置内容
     *
     * @param bitmap
     */
    public void setContent(Bitmap bitmap) {
        imageContent.setImageBitmap(bitmap);
    }

    /**
     * 设置内容
     *
     * @param resourceId
     */
    public void setContent(int resourceId) {
        imageContent.setImageResource(resourceId);
    }

}

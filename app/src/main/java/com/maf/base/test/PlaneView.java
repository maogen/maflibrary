package com.maf.base.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import maf.com.mafproject.R;

/**
 * Created by jihelife on 16/10/12.
 */
public class PlaneView extends View {

    public float currentX;
    public float currentY;
    Bitmap plane;

    public PlaneView(Context context) {
        super(context);
        plane = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.file4);
        plane = Bitmap.createScaledBitmap(plane, 40, 40, false);
        setFocusable(true);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        canvas.drawBitmap(plane, currentX, currentY, p);
    }
}

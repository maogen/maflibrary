package com.maf.base.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import maf.com.mafproject.R;

public class MainActivity extends AppCompatActivity {

//    private int speed = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        MyButton btn = (MyButton) findViewById(R.id.btn_id);
        btn.requestFocus();
        btn.setFocusableInTouchMode(true);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        final PlaneView planeView = new PlaneView(this);
//        setContentView(planeView);
//        planeView.setBackgroundResource(R.drawable.file1);
//        WindowManager windowManager = getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
//        DisplayMetrics metrics = new DisplayMetrics();
//        display.getMetrics(metrics);
//        planeView.currentX = metrics.widthPixels / 2;
//        planeView.currentY = metrics.heightPixels - 40;
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        planeView.setLayoutParams(params);
//        planeView.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                switch (event.getKeyCode()) {
//                    case KeyEvent.KEYCODE_S:
//                        planeView.currentY += speed;
//                        break;
//                    case KeyEvent.KEYCODE_W:
//                        planeView.currentY -= speed;
//                        break;
//                    case KeyEvent.KEYCODE_A:
//                        planeView.currentX -= speed;
//                        break;
//                    case KeyEvent.KEYCODE_D:
//                        planeView.currentX += speed;
//                        break;
//                }
//                planeView.invalidate();
//                return true;
//            }
//        });

    }
}

package com.example.android.planeshotter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Missile {

    int x, y;
    int mVelocity;
    Bitmap missle;
    int width, height;

    public Missile(Context context, int width, int height, int tankHeight) {
        this.width = width;
        this.height = height;
        missle = BitmapFactory.decodeResource(context.getResources(), R.drawable.missile);
        x = width/2 - getWidth()/2;
        y = height - tankHeight - getHeight()/2;
        mVelocity = 50;
    }

    public int getWidth(){
        return missle.getWidth();
    }

    public int getHeight(){
        return missle.getHeight();
    }

}

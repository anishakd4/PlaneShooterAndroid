package com.example.android.planeshotter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Plane2 {

    Bitmap plane[] = new Bitmap[10];
    int planex, planey, velocity, planeFrame, planeWidth;
    Random random;
    int width;

    public Plane2(Context context, int width) {
        this.width = width;
        plane[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.plane2_1);
        plane[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.plane2_2);
        plane[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.plane2_3);
        plane[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.plane2_4);
        plane[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.plane2_5);
        plane[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.plane2_6);
        plane[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.plane2_7);
        plane[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.plane2_8);
        plane[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.plane2_9);
        plane[9] = BitmapFactory.decodeResource(context.getResources(), R.drawable.plane2_10);
        random = new Random();
        setPosition();
        planeFrame = 0;
    }

    public Bitmap getBitmap(){
        return plane[planeFrame];
    }

    public int getWidth(){
        return plane[0].getWidth();
    }

    public int getHeight(){
        return plane[0].getHeight();
    }

    public void incrementPlanFrame(){
        planeFrame++;
        if(planeFrame >= plane.length){
            planeFrame = 0;
        }
    }

    public void updatePlanex(){
        planex = planex + velocity;
    }

    public void setPosition(){
        planex = -(200+random.nextInt(300));
        planey = random.nextInt(400);
        velocity = 5 + random.nextInt(21);
    }

    public void resetPosition(){
        if(planex > width + getWidth()){
            planex = -(200+random.nextInt(300));
            planey = random.nextInt(400);
            velocity = 5 + random.nextInt(21);
        }
    }
}

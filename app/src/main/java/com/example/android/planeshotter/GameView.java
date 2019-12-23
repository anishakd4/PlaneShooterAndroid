package com.example.android.planeshotter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends View {

    Bitmap background, tank;
    int height;
    int width;
    Rect rect;
    int tankWidth, tankHeight;
    ArrayList<Plane> planes;
    ArrayList<Plane2> planes2;
    ArrayList<Missile> missiles;
    ArrayList<Explosion> explosions;
    Handler handler;
    Runnable runnable;
    final long UPDATE_MILLIES = 30;
    Context context;
    int count = 0;
    SoundPool sp;
    int fire=0, point=0;
    Paint scorePaint, healthPaint;
    final int TEXT_SIZE = 60;
    int life = 5;

    public GameView(Context context) {
        super(context);
        this.context = context;
        getScreenWidthHeight(context);
        getBitmaps();
        initialValues(context);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }

    public void initialValues(Context context){
        rect = new Rect(0, 0, width, height);
        planes = new ArrayList<Plane>();
        planes2 = new ArrayList<Plane2>();
        missiles = new ArrayList<Missile>();
        explosions = new ArrayList<Explosion>();
        for(int i=0; i<2; i++){
            Plane plane = new Plane(context, width);
            planes.add(plane);
            Plane2 plane2 = new Plane2(context, width);
            planes2.add(plane2);
        }
        tankWidth = tank.getWidth();
        tankHeight = tank.getHeight();
        sp = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        fire = sp.load(context, R.raw.fire, 1);
        point = sp.load(context, R.raw.point, 1);
        scorePaint = new Paint();
        scorePaint.setColor(Color.RED);
        scorePaint.setTextSize(TEXT_SIZE);
        scorePaint.setTextAlign(Paint.Align.LEFT);
        healthPaint = new Paint();
        healthPaint.setColor(Color.GREEN);
    }

    public void getBitmaps(){
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        tank = BitmapFactory.decodeResource(getResources(), R.drawable.tank);
    }

    public void getScreenWidthHeight(Context context){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawBitmap(background, 0, 0, null);
        canvas.drawBitmap(background, null, rect, null);
        for(int i=0; i<planes.size(); i++){
            canvas.drawBitmap(planes.get(i).getBitmap(), planes.get(i).planex, planes.get(i).planey, null);
            planes.get(i).incrementPlanFrame();
            planes.get(i).updatePlanex();
            if(planes.get(i).planex < -planes.get(i).getWidth()){
                life--;
                if(life == 0){
                    Intent intent = new Intent(context, GameOver.class);
                    intent.putExtra("score", count * 10);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
            }
            planes.get(i).resetPosition();
        }

        for(int i=0; i<planes2.size(); i++){
            canvas.drawBitmap(planes2.get(i).getBitmap(), planes2.get(i).planex, planes2.get(i).planey, null);
            planes2.get(i).incrementPlanFrame();
            planes2.get(i).updatePlanex();
            if(planes2.get(i).planex > width + planes2.get(i).getWidth()){
                life--;
                if(life == 0){
                    Intent intent = new Intent(context, GameOver.class);
                    intent.putExtra("score", count * 10);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
            }
            planes2.get(i).resetPosition();
        }

        for(int i=0; i<missiles.size(); i++){
            if(missiles.get(i).y > -missiles.get(i).getHeight()){
                missiles.get(i).y = missiles.get(i).y - missiles.get(i).mVelocity;
                canvas.drawBitmap(missiles.get(i).missle, missiles.get(i).x, missiles.get(i).y, null);

                if(missiles.get(i).x >= planes.get(0).planex
                        && missiles.get(i).x + missiles.get(i).getWidth() <= planes.get(0).planex + planes.get(0).getWidth()
                        && missiles.get(i).y >= planes.get(0).planey
                        && missiles.get(i).y <= planes.get(0).planey + planes.get(0).getHeight()){
                    Explosion explosion = new Explosion(context);
                    explosion.explosionx = planes.get(0).planex + planes.get(0).getWidth()/2 - explosion.getExplosionWidth()/2;
                    explosion.explosiony = planes.get(0).planey + planes.get(0).getHeight()/2 - explosion.getExplosionHeight()/2;
                    explosions.add(explosion);
                    planes.get(0).setPosition();
                    missiles.remove(i);
                    count++;
                    if(point != 0){
                        sp.play(point, 1, 1, 0, 0, 1);
                    }
                }else if(missiles.get(i).x >= planes.get(1).planex
                        && missiles.get(i).x + missiles.get(i).getWidth() <= planes.get(1).planex + planes.get(1).getWidth()
                        && missiles.get(i).y >= planes.get(1).planey
                        && missiles.get(i).y <= planes.get(1).planey + planes.get(1).getHeight()){
                    Explosion explosion = new Explosion(context);
                    explosion.explosionx = planes.get(1).planex + planes.get(1).getWidth()/2 - explosion.getExplosionWidth()/2;
                    explosion.explosiony = planes.get(1).planey + planes.get(1).getHeight()/2 - explosion.getExplosionHeight()/2;
                    explosions.add(explosion);
                    planes.get(1).setPosition();
                    missiles.remove(i);
                    count++;
                    if(point != 0){
                        sp.play(point, 1, 1, 0, 0, 1);
                    }
                }else if(missiles.get(i).x >= planes2.get(0).planex
                        && missiles.get(i).x + missiles.get(i).getWidth() <= planes2.get(0).planex + planes2.get(0).getWidth()
                        && missiles.get(i).y >= planes2.get(0).planey
                        && missiles.get(i).y <= planes2.get(0).planey + planes2.get(0).getHeight()){
                    Explosion explosion = new Explosion(context);
                    explosion.explosionx = planes2.get(0).planex + planes2.get(0).getWidth()/2 - explosion.getExplosionWidth()/2;
                    explosion.explosiony = planes2.get(0).planey + planes2.get(0).getHeight()/2 - explosion.getExplosionHeight()/2;
                    explosions.add(explosion);
                    planes2.get(0).setPosition();
                    missiles.remove(i);
                    count++;
                    if(point != 0){
                        sp.play(point, 1, 1, 0, 0, 1);
                    }
                }else if(missiles.get(i).x >= planes2.get(1).planex
                        && missiles.get(i).x + missiles.get(i).getWidth() <= planes2.get(1).planex + planes2.get(1).getWidth()
                        && missiles.get(i).y >= planes2.get(1).planey
                        && missiles.get(i).y <= planes2.get(1).planey + planes2.get(1).getHeight()){
                    Explosion explosion = new Explosion(context);
                    explosion.explosionx = planes2.get(1).planex + planes2.get(1).getWidth()/2 - explosion.getExplosionWidth()/2;
                    explosion.explosiony = planes2.get(1).planey + planes2.get(1).getHeight()/2 - explosion.getExplosionHeight()/2;
                    explosions.add(explosion);
                    planes2.get(1).setPosition();
                    missiles.remove(i);
                    count++;
                    if(point != 0){
                        sp.play(point, 1, 1, 0, 0, 1);
                    }
                }

            }else{
                missiles.remove(i);
            }
        }

        for(int i=0; i<explosions.size(); i++){
            Log.i("Enter size: ", "" + explosions.size());
            canvas.drawBitmap(explosions.get(i).getExplosionBitmap(), explosions.get(i).explosionx, explosions.get(i).explosiony, null);
            explosions.get(i).explosionFrame++;
            if(explosions.get(i).explosionFrame > 8){
                explosions.remove(i);
            }
            Log.i("Enter exit: ", "" + explosions.size());
        }

        canvas.drawBitmap(tank, width/2 - tankWidth /2, height - tankHeight, null);

        canvas.drawText("Pt: "+String.valueOf(count), 0, TEXT_SIZE + 60, scorePaint);

        canvas.drawRect(width - 110, 120, width - 110 + 10*life, TEXT_SIZE, healthPaint);

        handler.postDelayed(runnable, UPDATE_MILLIES);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchx = event.getX();
        float touchy = event.getY();
        int action = event.getAction();

        if(action == MotionEvent.ACTION_DOWN){
            if(touchx >= (width / 2 - tankWidth / 2) && touchx <= (width / 2 + tankWidth / 2) && touchy >= height - tankHeight){
                //Log.i("Tank", "is tapped");
                if(missiles.size() < 3){
                    Missile m = new Missile(context, width, height, tankHeight);
                    missiles.add(m);
                    if(fire != 0){
                        sp.play(fire, 1, 1, 0, 0, 1);
                    }
                }
            }
        }
        return true;
    }
}

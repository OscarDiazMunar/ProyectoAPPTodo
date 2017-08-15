package com.oscar.proyectoapptodo.Presentations.MainTabActivity.GestureDetector;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;


/**
 * Created by Usuario on 21/07/2017.
 */

public class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    private SwipeGestureListener listener;

    public SwipeGestureDetector(SwipeGestureListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        listener.navigateToYotube();
        return super.onDoubleTap(e);

    }


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result = false;

        try {
            float diffX = e2.getX() - e1.getX();
            float diffY = e2.getY() - e1.getY();
            if (Math.abs(diffX) > Math.abs(diffY)){
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD){
                    if (diffX > 0){
                        listener.dismissYoutube(true);
                    }else{
                        listener.dismissYoutube(false);
                    }
                }
            }
            result = true;

        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
}

package com.flaviu.flickrbrowser;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "RecyclerItemClickListen";

    interface OnRecyclerClickListener {
        void onItemClick(View view , int position);
        void onItemLongClick(View  view , int position);
    }

        private final OnRecyclerClickListener mListener;
        private final GestureDetectorCompat gestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnRecyclerClickListener mListener) {
        this.mListener = mListener;
        gestureDetector = new GestureDetectorCompat(context , new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
                if(childView != null){
                    Log.d(TAG, "onSingleTapUp: calling listener.onitemclick");
                    mListener.onItemClick(childView, recyclerView.getChildAdapterPosition(childView));
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d(TAG, "onLongPress: starts");
                View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
                if (childView != null){
                    Log.d(TAG, "onLongPress: callinglistener on long press");
                    mListener.onItemLongClick(childView,recyclerView.getChildAdapterPosition(childView));
                }
            }
        });


    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv,MotionEvent e) {
        Log.d(TAG, "onInterceptTouchEvent: starts");
        if(gestureDetector != null ){
            boolean result = gestureDetector.onTouchEvent(e);
            return result;
        } else {
            Log.d(TAG, "onInterceptTouchEvent: returned false");
        }
        return false;

    }
}

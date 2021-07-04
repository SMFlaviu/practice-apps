package com.flaviu.flickrbrowser;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

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
                    mListener.onItemClick(childView, recyclerView.getChildAdapterPosition(childView));
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
                if (childView != null){
                    mListener.onItemLongClick(childView,recyclerView.getChildAdapterPosition(childView));
                }
            }
        });


    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv,MotionEvent e) {
        if(gestureDetector != null ){
            boolean result = gestureDetector.onTouchEvent(e);
            return result;
        } else {
        }
        return false;

    }
}

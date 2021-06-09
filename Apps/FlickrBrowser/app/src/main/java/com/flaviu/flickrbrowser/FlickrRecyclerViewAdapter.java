package com.flaviu.flickrbrowser;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrRecyclerViewAdapter.FlickrImageViewHolder> {
    private static final String TAG = "FlickrRecyclerViewAdapt";
    private List<Photo> mPhotoList;
    private Context mContext;

    public FlickrRecyclerViewAdapter(List<Photo> PhotoList, Context Context) {
        this.mPhotoList = PhotoList;
        this.mContext = Context;
    }

    @Override
    public FlickrImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // called by the layout manager when it needs a new view
        Log.d(TAG, "onCreateViewHolder: New view requested");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse,parent,false);
        return  new FlickrImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlickrRecyclerViewAdapter.FlickrImageViewHolder holder, int position) {
    // called by the layout manager when it wants new data in an existing row

        if((mPhotoList == null) || (mPhotoList.size()) == 0){
            holder.thumbnail.setImageResource(R.drawable.placeholder);
            holder.title.setText("No photo match your search\n\n Use search icon to search for photos");
        } else {
            Photo photoItem = mPhotoList.get(position);
            Log.d(TAG, "onBindViewHolder: " + photoItem.getTitle() + "-->"+position);
            Picasso.with(mContext).load(photoItem.getImage()).error(R.drawable.placeholder).placeholder(R.drawable.placeholder).into(holder.thumbnail);

            holder.title.setText(photoItem.getTitle());
        }



    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: called");

        return ((mPhotoList != null  && (mPhotoList.size() != 0) ? mPhotoList.size() : 1 ));
    }

    void loadNewData(List<Photo> newPhotos){
        mPhotoList = newPhotos;
        notifyDataSetChanged();
    }

    public Photo getPhoto(int position){
        return ((mPhotoList != null) && (mPhotoList.size() != 0) ? mPhotoList.get(position) : null);
    }

    static class FlickrImageViewHolder extends RecyclerView.ViewHolder{
        private static final String TAG = "FlickrImageViewHolder";
        ImageView thumbnail = null;
        TextView title;

        public FlickrImageViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "FlickrImageViewHolder: ");
            this.thumbnail = (ImageView) itemView.findViewById(R.id.thumbail);
            this.title = (TextView) itemView.findViewById(R.id.titlev);
        }
    }
}

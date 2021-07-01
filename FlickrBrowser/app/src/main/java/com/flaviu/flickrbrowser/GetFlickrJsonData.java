package com.flaviu.flickrbrowser;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetFlickrJsonData extends AsyncTask<String, Void, List<Photo>> implements GetRawData.OnDownloadComplete {
    private static final String TAG = "GetFlickrJsonData";

    private List<Photo> mPhotoList = null;
    private String mBasedURL;
    private String mLanguage;
    private boolean mMatchAll;

    private final OnDataAvailable mCallBack;
    private boolean runInSameThread = false;

    interface  OnDataAvailable{
        void onDataAvailable(List<Photo> data , DownloadStatus status);
    }

    public GetFlickrJsonData(OnDataAvailable callBack,String basedURL, String language, boolean matchAll) {
        Log.d(TAG, "GetFlickrJsonData: called");
        this.mBasedURL = basedURL;
        this.mCallBack = callBack;
        this.mLanguage = language;
        this.mMatchAll = matchAll;
        
    }

    void executeOnSameThread(String searchCriteria){
        Log.d(TAG, "executeOnSameThread: Starts ");
        runInSameThread = true;
        String destinationUri = createUri(searchCriteria, mLanguage, mMatchAll);
        
        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(destinationUri);
        Log.d(TAG, "executeOnSameThread: Thread ends");

    }

    @Override
    protected void onPostExecute(List<Photo> photos) {
        Log.d(TAG, "onPostExecute: Starts ");

        if(mCallBack != null){
            mCallBack.onDataAvailable(mPhotoList,DownloadStatus.OK);
        }
        Log.d(TAG, "onPostExecute: ends");
        
    }

    @Override
    protected List<Photo> doInBackground(String... params) {
        Log.d(TAG, "doInBackground: Starts");
        String destinationUri = createUri(params [0],mLanguage,mMatchAll);

        GetRawData getRawData = new GetRawData(this);
        getRawData.runInSameThread(destinationUri);
        Log.d(TAG, "doInBackground: ends");
        return mPhotoList;
    }

    private String createUri(String searchCriteria, String lang , boolean matchAll){
        Log.d(TAG, "createUri: starts");

        /*Uri uri = Uri.parse(mBasedURL);
        Uri.Builder builder = uri.buildUpon();
        builder = builder.appendQueryParameter("tags", searchCriteria);
        builder = builder.appendQueryParameter("tagmode", matchAll ? "ALL" : "ANY");
        builder = builder.appendQueryParameter("lang", lang);
        builder = builder.appendQueryParameter("format", "json");
        builder = builder.appendQueryParameter("nojsoncallback", "1");
        uri = builder.build(); */


        return Uri.parse(mBasedURL).buildUpon()
                .appendQueryParameter("tags",searchCriteria)
                .appendQueryParameter("tagmode",matchAll ? "ALL" : "ANY")
                .appendQueryParameter("lang",lang)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback","1")
                .build().toString();
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete: starts. Status = " + status);

        if(status == DownloadStatus.OK) {
            mPhotoList = new ArrayList<>();

            try{
                JSONObject jsonData = new JSONObject(data);
                JSONArray itemsArray = jsonData.getJSONArray("items");

                for (int i = 0; i < itemsArray.length() ; i++){
                    JSONObject jsonPhoto = itemsArray.getJSONObject(i);
                    String title = jsonPhoto.getString("title");
                    String author = jsonPhoto.getString("author");
                    String authorId = jsonPhoto.getString("author_id");
                    String tags = jsonPhoto.getString("tags");

                    JSONObject jsonMedia = jsonPhoto.getJSONObject("media");
                    String photoUrl = jsonMedia.getString("m");

                    String link = photoUrl.replaceFirst("_m.", "_b.");

                    Photo photoObject = new Photo(title, author, authorId , link ,tags , photoUrl);
                    mPhotoList.add(photoObject);

                    Log.d(TAG, "onDownloadComplete: " + photoObject.toString());

                }
            }catch (JSONException e){
                e.printStackTrace();
                Log.e(TAG, "onDownloadComplete: Error processing Json data " + e.getMessage() );
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
        if(runInSameThread && mCallBack != null){
            // now inform the caller that processing it's done = possibly returning null if there was an errror
            mCallBack.onDataAvailable(mPhotoList , status)
            ;
        }
        Log.d(TAG, "onDownloadComplete:  ends");
    }
}

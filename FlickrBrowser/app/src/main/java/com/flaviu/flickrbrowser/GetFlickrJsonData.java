package com.flaviu.flickrbrowser;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetFlickrJsonData extends AsyncTask<String, Void, List<Photo>> implements GetRawData.OnDownloadComplete {

    private final OnDataAvailable mCallBack;
    private List<Photo> mPhotoList = null;
    private String mBasedURL;
    private String mLanguage;
    private boolean mMatchAll;
    private boolean runInSameThread = false;

    public GetFlickrJsonData(OnDataAvailable callBack, String basedURL, String language, boolean matchAll) {
        this.mBasedURL = basedURL;
        this.mCallBack = callBack;
        this.mLanguage = language;
        this.mMatchAll = matchAll;
    }

    void executeOnSameThread(String searchCriteria) {
        runInSameThread = true;
        String destinationUri = createUri(searchCriteria, mLanguage, mMatchAll);

        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(destinationUri);
    }

    @Override
    protected void onPostExecute(List<Photo> photos) {

        if (mCallBack != null) {
            mCallBack.onDataAvailable(mPhotoList, DownloadStatus.OK);
        }

    }

    @Override
    protected List<Photo> doInBackground(String... params) {
        String destinationUri = createUri(params[0], mLanguage, mMatchAll);
        GetRawData getRawData = new GetRawData(this);
        getRawData.runInSameThread(destinationUri);
        return mPhotoList;
    }

    private String createUri(String searchCriteria, String lang, boolean matchAll) {
        return Uri.parse(mBasedURL).buildUpon()
                .appendQueryParameter("tags", searchCriteria)
                .appendQueryParameter("tagmode", matchAll ? "ALL" : "ANY")
                .appendQueryParameter("lang", lang)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1")
                .build().toString();
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        if (status == DownloadStatus.OK) {
            mPhotoList = new ArrayList<>();

            try {
                JSONObject jsonData = new JSONObject(data);
                JSONArray itemsArray = jsonData.getJSONArray("items");

                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject jsonPhoto = itemsArray.getJSONObject(i);
                    String title = jsonPhoto.getString("title");
                    String author = jsonPhoto.getString("author");
                    String authorId = jsonPhoto.getString("author_id");
                    String tags = jsonPhoto.getString("tags");

                    JSONObject jsonMedia = jsonPhoto.getJSONObject("media");
                    String photoUrl = jsonMedia.getString("m");

                    String link = photoUrl.replaceFirst("_m.", "_b.");

                    Photo photoObject = new Photo(title, author, authorId, link, tags, photoUrl);
                    mPhotoList.add(photoObject);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
        if (runInSameThread && mCallBack != null) {
            mCallBack.onDataAvailable(mPhotoList, status)
            ;
        }
    }

    interface OnDataAvailable {

        void onDataAvailable(List<Photo> data, DownloadStatus status);
    }
}

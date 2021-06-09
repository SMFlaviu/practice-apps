package com.flaviu.flickrbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

enum DownloadStatus { IDLE, PROCESSING, NOT_INITIALISED, FAILED_OR_EMPTY, OK}

class GetRawData extends AsyncTask<String,Void,String> {
    private static final String TAG = "GetRawData";

    private DownloadStatus mdownloadStatus;
    private final OnDownloadComplete mCallBack;

    interface OnDownloadComplete{
        void onDownloadComplete(String data,DownloadStatus status);
    }

    public GetRawData(OnDownloadComplete callback) {
        this.mdownloadStatus = DownloadStatus.IDLE;
        mCallBack = callback;
    }

    void runInSameThread (String s){
        Log.d(TAG, "runInSameThread: Starts");
       // onPostExecute(doInBackground(s));

        if ((mCallBack != null)){
            String result = doInBackground(s);
            mCallBack.onDownloadComplete(result,mdownloadStatus);
        }
        Log.d(TAG, "runInSameThread: ends");
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: parameter = "  + s);
        if (mCallBack != null){
            mCallBack.onDownloadComplete(s,mdownloadStatus);
        }
        Log.d(TAG, "onPostExecute: ends");
        


    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;

        if(strings == null){
            mdownloadStatus = DownloadStatus.NOT_INITIALISED;
            return null;
        }

        try{
            mdownloadStatus = DownloadStatus.PROCESSING;
            URL url = new URL(strings[0]);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int response = connection.getResponseCode();
            Log.d(TAG, "doInBackground: The response code was" + response);

            StringBuilder result = new StringBuilder();

            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

//            String line;
//            while(null != (line = reader.readLine())) {
            for(String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                result.append(line).append("\n");
            }

            mdownloadStatus = DownloadStatus.OK;
            return result.toString();


        } catch(MalformedURLException e) {
            Log.e(TAG, "doInBackground: Invalid URL " + e.getMessage() );
        } catch(IOException e) {
            Log.e(TAG, "doInBackground: IO Exception reading data: " + e.getMessage() );
        } catch(SecurityException e) {
            Log.e(TAG, "doInBackground: Security Exception. Needs permission? " + e.getMessage());
        } finally {
            if(connection != null){
                connection.disconnect();
            }
            if (bufferedReader != null){
                try {
                    bufferedReader.close();
                }catch (IOException e){
                    Log.e(TAG, "doInBackground: Error closing streams" + e.getMessage());
                }
            }
        }
        mdownloadStatus = DownloadStatus.FAILED_OR_EMPTY;

        return null;
    }

}

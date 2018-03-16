package com.anjum.chester.services;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by sanjum on 3/16/2018.
 */

public class DownloadImageServie extends IntentService {
    String imageUrl;

    public DownloadImageServie() {
        super("Fitoor");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        imageUrl = intent.getStringExtra("URL");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int counter = 0;
        int contentLength = -1;
        int progressUpdate = 0;
        HttpURLConnection urlConnection = null;
        URL url = null;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        File file = null;
        boolean successfull = false;
        try {
            url = new URL(imageUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            contentLength = urlConnection.getContentLength();
            inputStream = urlConnection.getInputStream();
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" +
                    Uri.parse(imageUrl).getLastPathSegment());
            outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int read = -1;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
                counter = counter + read;
            }
            successfull = true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

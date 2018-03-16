package com.anjum.chester.services;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
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
    protected void onHandleIntent(@Nullable Intent intent) {
        Bundle bundle = intent.getExtras();
        Messenger messenger = (Messenger) bundle.get("messenger");
        imageUrl = intent.getStringExtra("URL");
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
                if (bundle != null) {
                    progressUpdate = (int) (((double) counter / contentLength) * 100);
                    Message message = Message.obtain();
                    message.arg1 = progressUpdate;
                    messenger.send(message);
                }
            }
            successfull = true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
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

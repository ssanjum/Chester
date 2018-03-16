package com.anjum.chester.activity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.anjum.chester.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadImageWithAsynctask extends AppCompatActivity {
    private Button btnDownload;
    private ProgressBar progressBar;
    String urls = "https://s9.postimg.org/h3zlqc7hr/mypic1.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_image);
        btnDownload = findViewById(R.id.btnDownload);
        progressBar = findViewById(R.id.progree);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mytask mytask = new Mytask();
                mytask.execute(urls);
            }
        });
    }

    public class Mytask extends AsyncTask<String, Integer, Boolean> {
        private int counter = 0;
        private int contentLength = -1;
        private int progressUpdate = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            URL url = null;
            InputStream inputStream = null;
            FileOutputStream outputStream = null;
            File file = null;
            boolean successfull = false;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                contentLength = urlConnection.getContentLength();
                inputStream = urlConnection.getInputStream();
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" +
                        Uri.parse(strings[0]).getLastPathSegment());
                outputStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int read = -1;
                while ((read = inputStream.read(buffer)) != -1) {

                    outputStream.write(buffer, 0, read);
                    counter = counter + read;
                    publishProgress(counter);

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
            return successfull;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressUpdate = (int) (((double) values[0] / contentLength) * 100);
            progressBar.setProgress(progressUpdate);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressBar.setVisibility(View.GONE);
            if (aBoolean) {
                Toast.makeText(DownloadImageWithAsynctask.this, "Download Complete", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DownloadImageWithAsynctask.this, "Error Occured", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

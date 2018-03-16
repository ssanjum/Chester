package com.anjum.chester.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.anjum.chester.R;
import com.anjum.chester.services.DownloadImageServie;

public class DownloadImageWithIntentService extends AppCompatActivity {
    private Button btnDownload;
    private ProgressBar progressBar;
    String urls = "https://s9.postimg.org/h3zlqc7hr/mypic1.jpg";
    String url2 = "https://s9.postimg.org/vafclkq2n/mypic3.jpg";
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_image_with_intent_service);
        btnDownload = findViewById(R.id.btnDownloadIntent);
        progressBar = findViewById(R.id.progreeIntent);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(DownloadImageWithIntentService.this, DownloadImageServie.class);
                intent.putExtra("messenger", new Messenger(handler));
                intent.putExtra("URL", url2);
                startService(intent);
            }
        });
        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int value = msg.arg1;
                progressBar.setProgress(value);
            }
        };
    }
}

package com.example.nguyea3.snorelockholmes;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    TextView mStartStopButton;

    View.OnClickListener mStartStopListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.start_stop_button) {
                TextView tv = (TextView)v;
                String currentText = (tv.getText().toString());
                if(currentText.equalsIgnoreCase("start")){
                    tv.setText("stop");
                    startService(new Intent(MainActivity.this,RecordManager.class));
                } else{
                    tv.setText("start");
                    stopService(new Intent(MainActivity.this,RecordManager.class));
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File destDir = new File(Environment.getExternalStorageDirectory()+"/SnorelockHolmes/");
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        mStartStopButton = (TextView)findViewById(R.id.start_stop_button);
        mStartStopButton.setOnClickListener(mStartStopListener);

        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                    this,
                    "us-east-1:f0fcec25-5a5b-4ffd-8d10-aee79bad5c48", // Identity Pool ID
                    Regions.US_EAST_1 // Region
            );

        // add AWS code here
        AmazonS3 s3 = new AmazonS3Client(credentialsProvider);
        TransferUtility transferUtility = new TransferUtility(s3, getApplicationContext());

        File uploadfile = new File(Environment.getExternalStorageDirectory()+"/SnorelockHolmes/"+"snore.json");
        File mp3file = new File(Environment.getExternalStorageDirectory()+"/SnorelockHolmes/"+"snore1.mp3");

        if(uploadfile.exists()) {

            TransferObserver observer1 = transferUtility.upload(
                    "mobilefiletest",     /* The bucket to upload to */
                    "snore.json",    /* The key for the uploaded object */
                    uploadfile        /* The file where the data to upload exists */
            );
        }

        if(mp3file.exists())
        {
            TransferObserver observer2 = transferUtility.upload(
                    "mobilefiletest",     /* The bucket to upload to */
                    "snore1.mp3",    /* The key for the uploaded object */
                    mp3file        /* The file where the data to upload exists */
            );
        }
    }
}

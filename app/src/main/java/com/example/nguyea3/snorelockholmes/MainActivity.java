package com.example.nguyea3.snorelockholmes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
        View v = getLayoutInflater().inflate(R.layout.activity_main, null, false);
        mStartStopButton = (TextView)v.findViewById(R.id.start_stop_button);
        mStartStopButton.setOnClickListener(mStartStopListener);
    }
}

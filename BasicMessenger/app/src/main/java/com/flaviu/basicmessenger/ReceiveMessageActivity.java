package com.flaviu.basicmessenger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import static com.flaviu.basicmessenger.CreateMessageActivity.MESSAGE_INTENT;

public class ReceiveMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_message);

        TextView messageReceive = (TextView) findViewById(R.id.message);

        Intent intent = getIntent();
        String string = intent.getStringExtra(MESSAGE_INTENT);
        messageReceive.setText(string);
    }
}
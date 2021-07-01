package com.flaviu.basicmessenger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CreateMessageActivity extends AppCompatActivity {

    public static final String MESSAGE_INTENT = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);
    }

    public void onSendMessage(View view) {
        Intent intent = new Intent(this, ReceiveMessageActivity.class);
        EditText messageView = (EditText) findViewById(R.id.message);
        intent.putExtra(MESSAGE_INTENT, messageView.getText().toString());
        startActivity(intent);
    }
}
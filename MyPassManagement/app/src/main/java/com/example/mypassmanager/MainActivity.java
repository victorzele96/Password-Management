package com.example.mypassmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button continueButton, createTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.continueButton = findViewById(R.id.continueButton);
        this.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTopicActivity();
            }
        });

        this.createTopic = findViewById(R.id.createTopic);
        this.createTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewTopic();
            }
        });


    }

    public void openTopicActivity(){
        Intent intent = new Intent(this, Pass_table.class);
        startActivity(intent);
    }

    public void createNewTopic(){
        EditText editText = findViewById(R.id.editTopic);
        String topicName = editText.getText().toString();
    }
}
package com.example.mypassmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button continueButton, createTopic;
    private EditText editTopicValue;
    public static final String EXTRA_TEXT = "com.example.mypassmanager.topic_name";

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

        this.editTopicValue = findViewById(R.id.editTopic);
        this.editTopicValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetEditTopic();
            }
        });


    }

    public void openTopicActivity(){
        EditText editText = findViewById(R.id.editTopic);
        String topicName = editText.getText().toString();
        Intent intent = new Intent(this, Pass_table.class);
        intent.putExtra(EXTRA_TEXT, topicName);
        startActivity(intent);
    }

    public void createNewTopic(){
        EditText editText = findViewById(R.id.editTopic);
        String topicName = editText.getText().toString();


        Toast.makeText(MainActivity.this, "Topic created successfully.", Toast.LENGTH_SHORT).show();
    }
    public void resetEditTopic(){
        EditText editText = findViewById(R.id.editTopic);
        String topicName = editText.getText().toString();

        if(topicName.equals("Fill in topic"))
            editText.setText("");
    }
}
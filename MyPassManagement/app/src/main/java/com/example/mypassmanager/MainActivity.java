package com.example.mypassmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.File;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    private Button continueButton, createTopic;
    private EditText editTopicValue;
    public static final String EXTRA_TEXT = "com.example.mypassmanager.topic_name";
    private File fileDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.continueButton = (Button) findViewById(R.id.continueButton);
        this.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTopicActivity();
            }
        });

        this.createTopic = (Button) findViewById(R.id.createTopic);
        this.createTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewTopic();
            }
        });

        this.editTopicValue = (EditText) findViewById(R.id.editTopic);
        this.editTopicValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetEditTopic();
            }
        });


    }

    public void openTopicActivity(){
        String topicName = this.editTopicValue.getText().toString();
        if(topicName.equals("")) {
            topicError("");
            Toast.makeText(this, "Try another name topic!!! \n Fill topic please...", Toast.LENGTH_SHORT).show();
        }
        else {
            this.fileDir = new File(getFilesDir(), topicName);//get dir of file where he saved
            if(this.fileDir.exists()) {
                Intent intent = new Intent(this, Pass_table.class);
                intent.putExtra(EXTRA_TEXT, topicName);
                Toast.makeText(MainActivity.this, "Please wait...", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
            else
                Toast.makeText(MainActivity.this, "Wrong topic name... Try another." + "\nOr topic does not exist. ", Toast.LENGTH_LONG).show();
        }
    }

    public void createNewTopic(){
        String topicName = this.editTopicValue.getText().toString();
        if(topicName.length() == 0)
            topicError("Cannot create empty topic");
        else {// open new empty file called by editTopicValue
            try {
                FileOutputStream fOut = openFileOutput(topicName, MODE_PRIVATE);
                fOut.close();
                this.fileDir = new File(getFilesDir(), topicName);//get dir of file where he saved
                Toast.makeText(getBaseContext(), "File Saved at " + fileDir, Toast.LENGTH_LONG).show();
//                Toast.makeText(MainActivity.this, "Topic created successfully.", Toast.LENGTH_SHORT).show();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public void resetEditTopic(){
        String topicName = this.editTopicValue.getText().toString();

        if(topicName.equals("Fill in topic"))
            this.editTopicValue.setText("");
    }

    public void topicError(String errorType){
        this.editTopicValue.setError(errorType);
    }
}
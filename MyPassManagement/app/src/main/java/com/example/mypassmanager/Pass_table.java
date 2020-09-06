package com.example.mypassmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Pass_table extends AppCompatActivity {
    private ListView listView;
    private String topicName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pass_details);

        Intent intent = getIntent();
        this.topicName = intent.getStringExtra(MainActivity.EXTRA_TEXT);
        TextView textView = findViewById(R.id.topicName);
        textView.setText(this.topicName);




        this.listView = findViewById(R.id.dataView);

        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("victor");


        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        this.listView.setAdapter(arrayAdapter);

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Pass_table.this, "Clicked item " + (position +1) + "\nUser name: " + "\nPassword: " + "\nEmail: ", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void factoryMethod(String method){

    }
}
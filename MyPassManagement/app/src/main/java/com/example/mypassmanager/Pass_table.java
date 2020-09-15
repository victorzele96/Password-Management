package com.example.mypassmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Pass_table extends AppCompatActivity {
    private ListView listView;
    private String topicName;
    private Button deleteTopic, updatePass, removePass, addPass;
    private int choice;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pass_details);

        Intent intent = getIntent();
        this.topicName = intent.getStringExtra(MainActivity.EXTRA_TEXT);
        final TextView textView = (TextView) findViewById(R.id.topicName);
        textView.setText(this.topicName);

        this.addPass = findViewById(R.id.add);
        this.removePass = findViewById(R.id.remove);
        this.updatePass = findViewById(R.id.update);
        this.deleteTopic = findViewById(R.id.deleteTopic);

        this.listView = (ListView) findViewById(R.id.dataView);
        final ArrayList<String> subjectName = new ArrayList<>();
        final ArrayList<String> bodyOfSubject = new ArrayList<>();


        this.addPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Pass_table.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_add_pass, null);
                final EditText et_subject = mView.findViewById(R.id.subject);
                final EditText et_userName = mView.findViewById(R.id.user_name);
                final EditText et_password = mView.findViewById(R.id.password);
                final EditText et_email = mView.findViewById(R.id.e_mail);
                Button btn_save = mView.findViewById(R.id.save_pass);

                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subjectName.add(et_subject.getText().toString());
                        String str;
                        str = et_userName.getText().toString() + " " + et_password.getText().toString() + " " + et_email.getText().toString();
                        bodyOfSubject.add(str);

                        ArrayAdapter arrayAdapter = new ArrayAdapter(Pass_table.this, android.R.layout.simple_list_item_1, subjectName);
                        listView.setAdapter(arrayAdapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                choice = position;
                                String[] viewArray = bodyOfSubject.get(position).split(" ", 5);
                                Toast.makeText(Pass_table.this, "Clicked item " + (position +1) + "\nUser name: " + viewArray[0] +
                                        "\nPassword: " + viewArray[1] +
                                        "\nEmail: " + viewArray[2], Toast.LENGTH_LONG).show();
                            }
                        });
                        dialog.dismiss();
                        Toast.makeText(Pass_table.this, "Password Saved !", Toast.LENGTH_SHORT).show();
                    }
                });

                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();
            }
        });

        this.removePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        this.deleteTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFile(textView.getText().toString());
                Toast.makeText(Pass_table.this, "Deleting file completed !", Toast.LENGTH_LONG).show();
                finish();
                /*
                add the option custom dialog to check if sure want to delete the topic.
                 */
            }
        });


//        subjectName.add("victor");
//        choice = subjectName.indexOf("victor");
//        bodyOfSubject.add("vick96 123456 victorzele96@gmail.com");
    }

    public void factoryMethod(String method){

    }
}
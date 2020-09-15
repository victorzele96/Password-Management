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
    private AlertDialog add_dialog, remove_dialog;
    private ArrayAdapter arrayAdapter;

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
                        String str;
                        str = et_subject.getText().toString() + " " + et_userName.getText().toString() + " " + et_password.getText().toString() + " " + et_email.getText().toString();
                        subjectName.add(str);

                        arrayAdapter = new ArrayAdapter(Pass_table.this, android.R.layout.simple_list_item_1, extractName(subjectName));
                        listView.setAdapter(arrayAdapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                choice = position;
                                String[] viewArray = subjectName.get(position).split(" ", 5);
                                Toast.makeText(Pass_table.this, "Clicked item " + (position +1) + "\nUser name: " + viewArray[1] +
                                        "\nPassword: " + viewArray[2] +
                                        "\nEmail: " + viewArray[3], Toast.LENGTH_LONG).show();
                            }
                        });
                        add_dialog.dismiss();
                        Toast.makeText(Pass_table.this, "Password Saved !", Toast.LENGTH_SHORT).show();
                    }
                });

                mBuilder.setView(mView);
                add_dialog = mBuilder.create();
                add_dialog.show();

            }
        });

        this.removePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder_remove = new AlertDialog.Builder(Pass_table.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_remove_pass, null);
                final EditText et_item_id = mView.findViewById(R.id.item_id);
                Button btn_remove = mView.findViewById(R.id.remove_pass);

                btn_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int num = Integer.parseInt(et_item_id.getText().toString()) -1;
                        if(0 <= num && num < subjectName.size()) {

                            subjectName.remove(num);
                            arrayAdapter = new ArrayAdapter(Pass_table.this, android.R.layout.simple_list_item_1, extractName(subjectName));
                            listView.setAdapter(arrayAdapter);

                            remove_dialog.dismiss();
                            Toast.makeText(Pass_table.this, "Password successfully removed. ", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(Pass_table.this, "There is no such identification number - " + Integer.parseInt(et_item_id.getText().toString()), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mBuilder_remove.setView(mView);
                remove_dialog = mBuilder_remove.create();
                remove_dialog.show();
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
    }

    public ArrayList<String> extractName(ArrayList<String> array){
        ArrayList<String> str = new ArrayList<>();
        for(int i=0; i < array.size(); i++) {
            String[] viewArray = array.get(i).split(" ", 5);
            str.add(viewArray[0]);
        }
        return str;
    }

    public void factoryMethod(String method){

    }
}
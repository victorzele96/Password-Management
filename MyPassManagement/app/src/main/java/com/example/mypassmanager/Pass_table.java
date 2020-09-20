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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Pass_table extends AppCompatActivity {
    private ListView listView;
    private String topicName;
    private Button deleteTopic, updatePass, removePass, addPass;
    private int choice;
    private AlertDialog add_dialog, remove_dialog, update_dialog, delete_dialog;
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
        final ArrayList<String> subjectName = readFile(this.topicName);

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

                        writeFile(topicName, subjectName);
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

                            writeFile(topicName, subjectName);
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

        this.updatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder_update = new AlertDialog.Builder(Pass_table.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_update_pass, null);
                Button btn_update = mView.findViewById(R.id.update_pass);
                Button btn_ok = mView.findViewById(R.id.check_pass_id);
                final EditText et_item_id = mView.findViewById(R.id.choose);
                final EditText et_subject = mView.findViewById(R.id.subject);
                final EditText et_user_name = mView.findViewById(R.id.user_name);
                final EditText et_password = mView.findViewById(R.id.password);
                final EditText et_e_mail = mView.findViewById(R.id.e_mail);

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int num = Integer.parseInt(et_item_id.getText().toString()) -1;
                        if(0 <= num && num < subjectName.size()){
                            String[] viewArray = subjectName.get(num).split(" ", 5);
                            et_subject.setText(viewArray[0]);
                            et_user_name.setText(viewArray[1]);
                            et_password.setText(viewArray[2]);
                            et_e_mail.setText(viewArray[3]);
                        }
                        else{
                            Toast.makeText(Pass_table.this, "There is no such identification number - " + Integer.parseInt(et_item_id.getText().toString()), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int num = Integer.parseInt(et_item_id.getText().toString()) -1;
                        String str;
                        str = et_subject.getText().toString() + " " + et_user_name.getText().toString() + " " + et_password.getText().toString() + " " + et_e_mail.getText().toString();
                        subjectName.set(num, str);
                        arrayAdapter = new ArrayAdapter(Pass_table.this, android.R.layout.simple_list_item_1, extractName(subjectName));
                        listView.setAdapter(arrayAdapter);

                        writeFile(topicName, subjectName);
                        update_dialog.dismiss();
                        Toast.makeText(Pass_table.this, "Password updated ! ", Toast.LENGTH_SHORT).show();
                    }
                });
                mBuilder_update.setView(mView);
                update_dialog = mBuilder_update.create();
                update_dialog.show();
            }
        });

        this.deleteTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder_delete = new AlertDialog.Builder(Pass_table.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_delete_topic, null);
                Button btn_yes = mView.findViewById(R.id.yes_answer);
                Button btn_no = mView.findViewById(R.id.no_answer);

                btn_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteFile(textView.getText().toString());
                        Toast.makeText(Pass_table.this, "Deleting file completed !", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });

                btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete_dialog.dismiss();
                    }
                });

                mBuilder_delete.setView(mView);
                delete_dialog = mBuilder_delete.create();
                delete_dialog.show();
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

    public ArrayList<String> readFile(String nameFile){
        ArrayList<String> str = new ArrayList<>();
        FileInputStream fis = null;
        try {
            fis = openFileInput(nameFile);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
//            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
//                sb.append(text).append("\n");
                str.add(text);
            }
//            mEditText.setText(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return str;
    }


    public void writeFile(String nameFile, ArrayList<String> arr) {
        String text = "";
        for(int i = 0; i < arr.size(); i++)
            text += arr.get(i) + "\n";

        FileOutputStream fos = null;
        try {
            fos = openFileOutput(nameFile, MODE_PRIVATE);
            fos.write(text.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
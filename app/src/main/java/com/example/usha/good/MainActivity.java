package com.example.usha.good;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;



import android.app.AlertDialog;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.util.Locale;


public class Inserting_page extends AppCompatActivity implements View.OnClickListener {
    EditText name, address;
    Button btn, btn_display;
    SQLiteDatabase db;
    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserting_page);


        name = (EditText) findViewById(R.id.editText_name);
        address= (EditText) findViewById(R.id.editText2_address);
        btn = (Button) findViewById(R.id.button_insert);
        t1=(TextView)findViewById(R.id.textView);
        btn_display=(Button)findViewById(R.id.button_disp);

        btn.setOnClickListener(this);
        btn_display.setOnClickListener(this);

        db=openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student1(name VARCHAR,address VARCHAR);");
        db.setVersion(1);
        db.setLocale(Locale.getDefault());
        db.setLockingEnabled(true);


    }
    public void showMessage(String title,String message)
    {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText()
    {
        name.setText("");
        address.setText("");

    }

    public void onClick(View view) {
// Adding a record
        if (view == btn) {
            // Checking empty fields
            if (name.getText().toString().trim().length() == 0 ||
                    address.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please enter all values");
                return;
            }
            // Inserting record
            db.execSQL("INSERT INTO student1 VALUES('" + name.getText() +
                    "','" + address.getText() + "');");
            showMessage("Success", "Record added");
            clearText();
        }
        if (view == btn_display) {
            // Retrieving all records
            Cursor c = db.rawQuery("SELECT * FROM student1", null);
            // Checking if no records found
            if (c.getCount() == 0) {
                showMessage("Error", "No records found");
                return;
            }
            // Appending records to a string buffer
            StringBuffer buffer = new StringBuffer();
            while (c.moveToNext()) {
                buffer.append("Name: " + c.getString(0) + "\n");
                buffer.append("Address: " + c.getString(1) + "\n\n");
            }

            showMessage("Student Details", buffer.toString());

        }
    }
}


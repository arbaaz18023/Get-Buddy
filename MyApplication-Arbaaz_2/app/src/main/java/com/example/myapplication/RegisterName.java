package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class RegisterName extends AppCompatActivity {
    Button sub;


    private EditText name;
    private EditText id;
    private EditText password;
    private EditText confo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_name);
        sub = findViewById(R.id.submit);
        name = findViewById(R.id.name);
        id = findViewById(R.id.user_id);
        password = findViewById(R.id.user_password);
        confo = findViewById(R.id.confirm_password);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp_name = name.getText().toString();
                String temp_id = id.getText().toString();
                String temp_password = password.getText().toString();
                String temp_confo = confo.getText().toString();
                System.out.println("Register_Name");
                System.out.println(temp_confo);
                if(temp_confo.length()!=0 && temp_id.length()!=0 && temp_name.length()!=0 && temp_password.length()!=0){
                    if(temp_password.equals(temp_confo)){
                        FirebaseFirestore firebaseFirestore;
                        DocumentReference ref;
                        firebaseFirestore = FirebaseFirestore.getInstance();

                        ref = firebaseFirestore.collection("user").document();
                        Map<String, Object> mapper = new HashMap<>();

                        mapper.put("Name", temp_name);
                        mapper.put("id", temp_id);
                        mapper.put("password", temp_password);
                        mapper.put("event_id", "none");
                        firebaseFirestore.collection("user").add(mapper);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Confirm password is not matched",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
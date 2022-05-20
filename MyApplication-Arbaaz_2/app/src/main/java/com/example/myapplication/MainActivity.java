package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class MainActivity extends AppCompatActivity {

    private Button login;
    private Button sign_up;
    private TextView email;
    private TextView password;
    //    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("login_page", "Open");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login);
        sign_up = findViewById(R.id.signup);
        email = findViewById(R.id.user_email);
        password = findViewById(R.id.LoginPassword);

        FirebaseFirestore db= FirebaseFirestore.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("IN");
                db.collection("user")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()) {
                                    System.out.println("out");
                                    System.out.println(task.getResult().isEmpty());
                                    boolean check = false;
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String my_email = email.getText().toString();
                                        String my_password = password.getText().toString();
                                        String temp1 = document.getData().get("id").toString();
                                        String temp2 = document.getData().get("password").toString();
                                        System.out.println(temp1+" "+temp2+" "+my_email+" "+my_password);
                                        Log.d("data",document.getId() + " => " + document.getData());
                                        if(my_email.equals(temp1) && my_password.equals(temp2)){

                                            User.getInstance().setName(document.getData().get("Name").toString());
                                            User.getInstance().setEmail(document.getData().get("id").toString());
                                            User.getInstance().setPassword(document.getData().get("password").toString());
                                            User.getInstance().setEvent_id(document.getData().get("event_id").toString());
                                            User.getInstance().setUser_id(document.getId());


                                            check = true;
                                            email.setText("");
                                            password.setText("");
                                            Intent intent = new Intent(getApplicationContext(), MainScreen.class);
                                            startActivity(intent);

                                            break;
                                        }
                                    }
                                    if(!check){
                                        Toast.makeText(getApplicationContext(),"Wrong passowrd or ID",Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    System.out.println("out2");
                                    Log.d("data", "Error getting documents: ", task.getException());
                                }
                            }
                        });
                // check if username and password available and correct.

            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterName.class);
                startActivity(intent);
            }
        });

    }
}
package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Participant_progress extends AppCompatActivity {

    private boolean flag;
    private String event_id;
    private String chat_id;
    private TextView Name;
    private TextView Location;
    private TextView Total_slots;
    private TextView Slots_left;
    private TextView Zone;
    private Button Cancel;
    private Button chat;
//    private String

   // private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.participant_progress);



        event_id = getIntent().getStringExtra("event_id");
        chat_id = getIntent().getStringExtra("chat_id");

        Name = findViewById(R.id.search_event_name);
        Location = findViewById(R.id.search_location);
        Total_slots = findViewById(R.id.search_total_slots);
        Zone = findViewById(R.id.search_zone);
        Cancel = findViewById(R.id.search_cancel);
        Slots_left = findViewById(R.id.search_rest_slots);
        chat = findViewById(R.id.search_chat);

        Log.d("location lat lng12",MapLocation.getInstance().getLng()+" "+MapLocation.getInstance().getLat());


        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_details();
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=false;
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("chat_id", chat_id);
                startActivity(intent);
            }
        });

        show_data();
        start_download();

    }


    public void show_data(){
        FirebaseFirestore db= FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("event").document(event_id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("fetch data ff", "Name = " + document.getData().get("Name"));
                        Log.d("fetch data ff", "location = " + document.getData().get("location"));
                        Log.d("fetch data ff", "slots = " + document.getData().get("slots"));
                        Log.d("fetch data ff", "zone = " + document.getData().get("zone"));

                        Name.setText(document.getData().get("Name").toString());
                        Location.setText(document.getData().get("location").toString());
                        Zone.setText(document.getData().get("zone").toString());
                        Total_slots.setText(document.getData().get("team_size").toString());
                        Slots_left.setText(document.getData().get("slots").toString());


                    } else {
                        Log.d("fetch data", "No such document");
                    }
                } else {
                    Log.d("fetch data", "get failed with ", task.getException());
                }
            }
        });
    }

    public void start_download(){
        flag=true;
        Runnable runnable = new Runnable(){
            public void run() {
                while (flag) {
                    Log.d("Download Thread", "in run");
                    check_event();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

    }

    public void fetch_data() {

        FirebaseFirestore db= FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("event").document(event_id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("fetch data", "Name = " + document.getData().get("Name"));
                        Log.d("fetch data", "location = " + document.getData().get("location"));
                        Log.d("fetch data", "slots = " + document.getData().get("slots"));
                        Log.d("fetch data", "zone = " + document.getData().get("zone"));

                        Slots_left.setText(document.getData().get("slots").toString());

                    } else {
                        Log.d("fetch data", "No such document");
                    }
                } else {
                    Log.d("fetch data", "get failed with ", task.getException());
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This will cancel the event. Do you still wanna continue?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        update_details();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }



    public void update_details(){
        int total_slots = Integer.parseInt(Slots_left.getText().toString())+1;
        Slots_left.setText(Integer.toString(total_slots));
        Map<String, Object> mapper = new HashMap<>();
        mapper.put("slots", Integer.toString(total_slots));
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("event").document(event_id).update(mapper);


        Map<String, Object> mapper1 = new HashMap<>();
        User.getInstance().setEvent_id("none");
        mapper.put("event_id", "none");
        FirebaseFirestore db1= FirebaseFirestore.getInstance();
        db.collection("user").document(User.getInstance().getUser_id()).update(mapper);


        Intent intent = new Intent(getApplicationContext(), MainScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


        public void check_event() {
            boolean ans = false;
            FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
            DocumentReference docIdRef = rootRef.collection("event").document(event_id);
            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d("check", "Document exists!");
                            fetch_data();
                        } else {
                            flag = false;
                            User.getInstance().setEvent_id("none");
                            Map<String, Object> mapper = new HashMap<>();
                            mapper.put("event_id", "none");
                            FirebaseFirestore db= FirebaseFirestore.getInstance();
                            db.collection("user").document(User.getInstance().getUser_id()).update(mapper);



                            Intent intent = new Intent(getApplicationContext(), MainScreen.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    } else {
                        Log.d("check event", "Failed with: ", task.getException());
                    }
                }
            });
        }
    }

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class EventProgress extends AppCompatActivity {

    private Button Reached;
    private Button Cancel;
    private TextView Event_name;
    private TextView Event_Location;
    private TextView Event_Zone;
    private TextView Event_Total_slots;
    private TextView Event_Slots_left;
    private Button chat;
    private static EventData eventData;
    private String event_id;
    private String chat_id;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_progress);
        flag=true;
        eventData = (EventData)getIntent().getSerializableExtra("EventData");
        event_id = eventData.getId();
        chat_id = eventData.getChat_id();
        Log.d("getData from event data", "val "+eventData.getId());
        Reached = findViewById(R.id.create_reached);
        Cancel = findViewById(R.id.create_cancel);
        Event_name = findViewById(R.id.create_event_name);
        Event_Location = findViewById(R.id.create_location);
        Event_Zone = findViewById(R.id.create_zone);
        Event_Total_slots = findViewById(R.id.create_total_slots);
        Event_Slots_left = findViewById(R.id.create_rest_slots);
        chat = findViewById(R.id.create_chat);

        Event_name.setText(eventData.getName());
        Event_Zone.setText(eventData.getZone());
        Event_Location.setText(eventData.getLocation());
        Event_Total_slots.setText(Integer.toString(eventData.getTotal_slots()));
        Event_Slots_left.setText(Integer.toString(eventData.getTotal_slots()));

        Reached.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEvent(eventData.getId(), "event");
                deleteEvent(chat_id, "chat");
                Intent intent = new Intent(getApplicationContext(), MainScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("Get event ID: ", eventData.getId());
                Toast.makeText(getApplicationContext(),eventData.getId(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Are you sure you want to cancel the event?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteEvent(eventData.getId(), "event");
                                deleteEvent(chat_id, "chat");
                                Log.d("Getter from met", "bhadwa");
                                Intent intent = new Intent(getApplicationContext(), MainScreen.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
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
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=false;
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("chat_id", eventData.getChat_id());
                startActivity(intent);
            }
        });


        start_download();
        Log.d("EventProgress","Working");



    }

    static void deleteEvent(String id, String name){
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection(name).document(eventData.getId()).delete();


    }
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This will cancel the event. Do you still wanna continue?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        deleteEvent(eventData.getId(), "event");
                        deleteEvent(chat_id, "chat");
                        Intent intent = new Intent(getApplicationContext(), MainScreen.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
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

    public void start_download(){
        flag=true;
        Runnable runnable = new Runnable(){
            public void run() {
                while (flag) {
                    Log.d("Download Thread", "in run");
                    fetch_data();

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

                        Event_Slots_left.setText(document.getData().get("slots").toString());

                    } else {
                        Log.d("fetch data", "No such document");
                    }
                } else {
                    Log.d("fetch data", "get failed with ", task.getException());
                }
            }
        });

    }
}
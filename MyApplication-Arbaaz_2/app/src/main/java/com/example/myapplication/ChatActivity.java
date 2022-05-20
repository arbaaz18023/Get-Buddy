package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ChatActivity extends AppCompatActivity {

    private String chat_id;
    private Button send;
    private TextView message;
    private EditText enter_message;
    private boolean flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chat_id = getIntent().getStringExtra("chat_id");
        message = findViewById(R.id.chat_message);
        enter_message = findViewById(R.id.chat_enter_message);
        send = findViewById(R.id.chat_button);

        start_download();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp1 = enter_message.getText().toString();
                final String[] temp2 = {""};
                enter_message.setText("");
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Log.d("chat id",chat_id);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String field_name = User.getInstance().getName() +":"+String.valueOf(timestamp.getTime());
                DocumentReference docRef = db.collection("chat").document(chat_id);
                docRef.update(field_name, temp1);

//                Log.d("fetch data after", "Name = " + temp2[0]);
//                docRef = db.collection("chat").document(chat_id);
//                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//                            if (document.exists()) {
//                                Log.d("Input message: ", "DocumentSnapshot data: " + document.getData());
//                                ArrayList<String> arr = (ArrayList<String>) document.getData();
//                                String ans = "";
//                                for(String i:arr){
//                                    String[] temp = i.split(":");
//                                    ans+=temp[0]+" "+temp[temp.length-1]+"\n";
//                                }
//                                Log.d("answer",ans);
//                            } else {
//                                Log.d("Not found documents: ", "No such document");
//                            }
//                        } else {
//                            Log.d("Failed message", "get failed with ", task.getException());
//                        }
//                    }
//                });
//                temp2[0]+=temp1;
//                Log.d("fetch data after2", "Name = " + temp2[0]);
//                Map<String, Object> mapper = new HashMap<>();
//                mapper.put("Message", temp2[0]);
//                db= FirebaseFirestore.getInstance();
//                db.collection("event").document(chat_id).update(mapper);

            }
        });
    }


    public String update_map(Map<String,Object> arr){

        Map<String,Object> new_map = new HashMap<String,Object>();

        for(String i:arr.keySet()){
            if(i.equals("Message")){
                continue;
            }
            String[] temp = i.split(":");
            String key = temp[1];
            String value = temp[0]+" : "+arr.get(i);
            Log.d("chat activity"," = "+key+" "+value);
            new_map.put(key,value);
        }
        String ans = "";
        Map<String, Object> treeMap = new TreeMap<String, Object>(new_map);
        for (Map.Entry<String,Object> entry : treeMap.entrySet()) {
            System.out.println("Key : " + entry.getKey()
                    + " Value : " + entry.getValue());

            ans+=entry.getValue()+"\n";
        }
        return ans;

    }


    public void start_download(){
        flag=true;
        Runnable runnable = new Runnable(){
            public void run() {
                while (flag) {
                    Log.d("Download Thread chat", "in run");
                    fetch_data();

                    try {
                        Thread.sleep(1000);
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
        Log.d("chat id",chat_id);
        DocumentReference docRef = db.collection("chat").document(chat_id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        Log.d("Input message: ", "DocumentSnapshot data: " + document.getData());
                        Map<String, Object> arr = document.getData();
                        String ans = update_map(arr);
                        Log.d("answer",ans);
                        message.setText(ans);
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

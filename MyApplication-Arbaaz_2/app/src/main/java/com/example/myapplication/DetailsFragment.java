package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class DetailsFragment extends Fragment {
    private TextView Name;
    private TextView Location;
    private TextView Total_slots;
    private EventData Data;
    private Button Book_slots;
    private Button show_map;
    private TextView Zone;
    private String id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

//        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//        supportMapFragment.getMapAsync(this);


        Bundle bundle = getArguments();
        Data = (EventData) bundle.getSerializable("newsdata");

        Name = view.findViewById(R.id.fragment_name);
        Location = view.findViewById(R.id.fragment_location);
        Total_slots = view.findViewById(R.id.fragment_total_slots);
        Zone = view.findViewById(R.id.fragment_zone);
        Book_slots = view.findViewById(R.id.fragment_button);
        show_map = view.findViewById(R.id.fragment_showmap);


        id = Data.getId();
        Log.d("Details",id);
        show_data();
        Log.d("location lat lng3",MapLocation.getInstance().getLng()+" "+MapLocation.getInstance().getLat());





        Book_slots.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                

                Log.d("book","enter");

                Log.d("book"," enter "+User.getInstance().getEvent_id());

                if(User.getInstance().getEvent_id().equals("none")){
                    Log.d("book","inside");

                    int total_slots = Integer.parseInt(Total_slots.getText().toString())-1;

                    if(total_slots>=0){
                        Total_slots.setText(Integer.toString(total_slots));
                        Map<String, Object> mapper = new HashMap<>();
                        mapper.put("slots", Integer.toString(total_slots));
                        FirebaseFirestore db= FirebaseFirestore.getInstance();
                        db.collection("event").document(id).update(mapper);

                        User.getInstance().setEvent_id(id);
                        Map<String, Object> mapper1 = new HashMap<>();
                        mapper1.put("event_id", id);
                        FirebaseFirestore db1= FirebaseFirestore.getInstance();
                        db1.collection("user").document(User.getInstance().getUser_id()).update(mapper1);
                        Log.d("details fragment","event id = "+id+", chat id = "+Data.getChat_id());
                        Intent intent = new Intent(getActivity(), Participant_progress.class);
                        intent.putExtra("event_id",id);
                        intent.putExtra("chat_id",Data.getChat_id());
                        startActivity(intent);
                    }else{
                        Toast.makeText(getActivity(),"No slots left for the event",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getActivity(),"You have already Booked a slot",Toast.LENGTH_SHORT).show();
                }
            }
        });


        show_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ShowMap.class);
                startActivity(intent);
            }
        });


        return view;
    }

    public void show_data(){
        FirebaseFirestore db= FirebaseFirestore.getInstance();

        db.collection("event")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            System.out.println("out jjjjj");
                            boolean check = false;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String temp1 = document.getId().toString();
                                if( temp1.equals(id) ){
                                    Log.d("fetch data ff", "Name = " + document.getData().get("Name"));
                                    Log.d("fetch data ff", "location = " + document.getData().get("location"));
                                    Log.d("fetch data ff", "slots = " + document.getData().get("slots"));
                                    Log.d("fetch data ff", "zone = " + document.getData().get("zone"));

                                    Name.setText(document.getData().get("Name").toString());
                                    Location.setText(document.getData().get("location").toString());
                                    Zone.setText(document.getData().get("zone").toString());
                                    Total_slots.setText(document.getData().get("slots").toString());
                                    double lat = Double.parseDouble(document.getData().get("lati").toString());
                                    double lng = Double.parseDouble(document.getData().get("longi").toString());
                                    Log.d("location lat lng2",lat+" "+lng);
                                    MapLocation.getInstance().setLng(lng);
                                    MapLocation.getInstance().setLat(lat);
                                    break;
                                }
                            }
                            if(!check){
                                Toast.makeText(getContext(),"Wrong passowrd or ID",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            System.out.println("out2");
                            Log.d("data", "Error getting documents: ", task.getException());
                        }
                    }
                });


//
//        DocumentReference docRef = db.collection("event").document(id);
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d("fetch data ff", "Name = " + document.getData().get("Name"));
//                        Log.d("fetch data ff", "location = " + document.getData().get("location"));
//                        Log.d("fetch data ff", "slots = " + document.getData().get("slots"));
//                        Log.d("fetch data ff", "zone = " + document.getData().get("zone"));
//
//                        Name.setText(document.getData().get("Name").toString());
//                        Location.setText(document.getData().get("location").toString());
//                        Zone.setText(document.getData().get("zone").toString());
//                        Total_slots.setText(document.getData().get("slots").toString());
//                        double lat = Double.parseDouble(document.getData().get("lati").toString());
//                        double lng = Double.parseDouble(document.getData().get("longi").toString());
//                        Log.d("location lat lng2",lat+" "+lng);
//                        MapLocation.getInstance().setLng(lng);
//                        MapLocation.getInstance().setLog(lng);
//
//                    } else {
//                        Log.d("fetch data", "No such document");
//                    }
//                } else {
//                    Log.d("fetch data", "get failed with ", task.getException());
//                }
//            }
//        });
//
        Log.d("location lat lng4",MapLocation.getInstance().getLng()+" "+MapLocation.getInstance().getLat());

    }

}
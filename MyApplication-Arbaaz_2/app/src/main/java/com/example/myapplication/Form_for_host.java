package com.example.myapplication;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.Toast;


public class Form_for_host extends Fragment {
    private String zone;
    private Button host_submit;
    private TextView slot;
    private TextView sport_name;
    public String chat_id ;
    private String event_id;
    private String ss;



    final String[] ID = new String[1];

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_form_for_host, container, false);
        final String[] zone = new String[1];
        final String[] location = new String[1];
        slot = v.findViewById(R.id.slots);
        sport_name = v.findViewById(R.id.eventname);
        String[] value = new String[]{"North","East","West","South"};


        Spinner sp = v.findViewById(R.id.zone_spinner);
        sp.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,value));
        HashMap<String, String[]> map = new HashMap<String, String[]>();
        map.put("North", new String[]{"Bonta Park", "North Delhi Municipal Park", "Shalimar Lovers Park", "Kamla Nehru North", "Shubash Park"});
        map.put("South", new String[]{"Deer Park", "Maa Sarada Park", "Qila Rai Pithora Park", "Pancheel Park"});
        map.put("East", new String[]{"Green Belt Park", "Krishna Jayanti Park", "Shiv Park", "The Maharana Pratap Park"});
        map.put("West", new String[]{"Bheemrao Ambedkar Park", "Sanjay Park", "Bindra Park", "Jheel Wala Park"});
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String temp_zone = sp.getSelectedItem().toString();
                zone[0] = temp_zone;
                if(map.containsKey(temp_zone)){

                    // zone = adapterView.getItemAtPosition(i).toString();
                    Spinner sp2 = v.findViewById(R.id.location_spinner);
                    sp2.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,map.get(temp_zone)));
                    location[0] = sp2.getSelectedItem().toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        host_submit = v.findViewById(R.id.host_submit);
        host_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String slt = slot.getText().toString();
                String s_name = sport_name.getText().toString();
                System.out.println(slt);
                System.out.println(s_name);
                FirebaseFirestore firebaseFirestore;
                DocumentReference ref;
                firebaseFirestore = FirebaseFirestore.getInstance();
                ref = firebaseFirestore.collection("event").document();

                Map<String, Object> mapper = new HashMap<>();
                mapper.put("Message","Welcome Everyone" );
                Date date = new Date();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String chat_name = String.valueOf(timestamp.getTime())+User.getInstance().getUser_id();

                if(s_name.equals("")){
                    Toast.makeText(getContext(),"Name can not be empty",Toast.LENGTH_SHORT).show();
                }else if(slt.equals("0") || !isNumeric(slt)){
                    Toast.makeText(getContext(),"Slots are not valid",Toast.LENGTH_SHORT).show();
                }else {

                    HashMap<String, Double[]> map2 = new HashMap<>();
                    map2.put("Bonta Park", new Double[]{28.685000913807986, 77.21612485923772});
                    map2.put("North Delhi Municipal Park", new Double[]{28.704593815722575, 77.20584822861046});
                    map2.put("Shalimar Lovers Park", new Double[]{28.709353354559717, 77.1621650979265});
                    map2.put("Kamla Nehru North", new Double[]{28.692812895695795, 77.22005923654854});
                    map2.put("Shubash Park", new Double[]{28.681595514903503, 77.27641231380082});
                    map2.put("Deer Park", new Double[]{28.554884350466292, 77.19193224197473});
                    map2.put("Maa Sarada Park",new Double[]{28.55193904083096, 77.20621954432448});
                    map2.put("Qila Rai Pithora Park",new Double[]{28.530761136441814, 77.19880731455581});
                    map2.put("Pancheel Park", new Double[]{28.543243652655473, 77.21195467049523});
                    map2.put("Green Belt Park", new Double[]{28.69373682690657, 77.14358018697811});
                    map2.put("Krishna Jayanti Park",new Double[]{28.635767019973304, 77.28971476239495});
                    map2.put("Shiv Park", new Double[]{28.676393854718793, 77.07262525766672});
                    map2.put("The Maharana Pratap Park",new Double[]{28.657363696934443, 77.27783390134901});
                    map2.put("Bheemrao Ambedkar Park",new Double[]{28.656167644217287, 77.11244567457481});
                    map2.put("Sanjay Park",new Double[]{28.620644578458652, 77.29561089043828});
                    map2.put("Bindra Park",new Double[]{28.643054228811817, 77.1252877131955});
                    map2.put("Jheel Wala Park",new Double[]{28.67519095271176, 77.27573085129988});

                    firebaseFirestore.collection("chat").document(chat_name).set(mapper);

                    mapper = new HashMap<>();

                    mapper.put("Name", s_name);
                    mapper.put("zone", zone[0]);
                    mapper.put("team_size", slt);
                    mapper.put("location", location[0]);
                    mapper.put("slots", slt);
                    mapper.put("chat_id", chat_name);
                    mapper.put("lati",map2.get(location[0])[0]);
                    mapper.put("longi",map2.get(location[0])[1]);

                    event_id = s_name + String.valueOf(timestamp.getTime()) + User.getInstance().getUser_id();
                    firebaseFirestore.collection("event").document(event_id).set(mapper);
                    create_event(s_name, location[0], zone[0], slt, chat_name, event_id);
                }

            }
        });

        return v;

    }


    public void create_event(String Name,String Location,String Zone,String Slots,String chat_id,String event_id){
        Log.d("event_id","id "+event_id);
        EventData event_data = new EventData(Name,Location,Zone,Integer.parseInt(Slots),chat_id,event_id);
        Intent intent = new Intent(getActivity(), EventProgress.class);
        intent.putExtra("EventData", event_data);
        Log.d("fetch data abhinesh","id = "+" done");
        startActivity(intent);
    }


//    public void fetch_id(String Name,String Location,String Zone,String Slots){
//
//
//        FirebaseFirestore db= FirebaseFirestore.getInstance();
//        //System.out.println("IN");
//        db.collection("event")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                        if (task.isSuccessful()) {
//                            System.out.println("out");
//                            //System.out.println(task.getResult().isEmpty());
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                //System.out.println(document.getData());
//                                String name1 = (String) document.getData().get("Name");
//                                String location1 = (String) document.getData().get("location");
//                                String slots1 = (String) document.getData().get("slots");
//                                String zone1 = (String) document.getData().get("zone");
//                                String id = (String) document.getId();
//                                //System.out.println("Getting forloop ID: "+id);
//                                if(name1.length()!=0 && location1.length()!=0 && slots1.length()!=0 && zone1.length()!=0){
//                                    if(name1.equals(Name) && location1.equals(Location) && zone1.equals(Zone) && slots1.equals(Slots)){
//                                        //System.out.println("Getting inside ID: "+id);
//                                        ID[0] = id;
//                                        Log.d("fetch data abhinesh","id = "+ID[0]);
//                                        Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();
//                                        EventData event_data = new EventData(name1,location1,zone1,Integer.parseInt(slots1), "");
//                                        Intent intent = new Intent(getActivity(), EventProgress.class);
//                                        intent.putExtra("EventData", event_data);
//                                        Log.d("fetch data abhinesh","id = "+" done");
//                                        startActivity(intent);
//                                        break;
//                                    }
//                                }
//                                else{
//                                    Toast.makeText(getActivity(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                        } else {
//                            System.out.println("out2");
//                            Log.d("data", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//        // check if username and password available and correct.
//        System.out.println("getting id: "+ID[0]);
//        return ;
//    }

}
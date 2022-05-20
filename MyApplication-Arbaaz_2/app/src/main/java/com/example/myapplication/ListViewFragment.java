package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ListViewFragment extends Fragment implements BuddyAdapter.CommunicateRecycler {

    private RecyclerView recyclerView;
    private String TAG = "Fragment1";
    private  BuddyAdapter ba;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listview, container, false);
        Log.i("ListViewFragment", "open");

        EventList.getInstance().get_list().clear();
        fetch_data();

        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.d("fun1","size = "+EventList.getInstance().get_list_size());
        ba = new BuddyAdapter(this);
        recyclerView.setAdapter(ba);
        return view;

    }

    @Override
    public void replaceFragment(EventData nd) {
        Log.i("fragment", "all id well");

        DetailsFragment df = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("newsdata",nd);
        System.out.println("data entry: "+nd.getName()+" "+nd.getLocation()+" "+nd.getTotal_slots()+" "+nd.getId()+" "+nd.getChat_id());
        df.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.fragment,df).addToBackStack(null).commit();

    }


    public void fetch_data(){;
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        System.out.println("IN");
        db.collection("event")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            System.out.println("out");
                            System.out.println(task.getResult().isEmpty());
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getData());
                                String name = (String) document.getData().get("Name");
                                String location = (String) document.getData().get("location");
                                String slots = (String) document.getData().get("team_size");
                                String zone = (String) document.getData().get("zone");
                                String id = (String) document.getId();

                                String chat_id = (String)document.getData().get("chat_id");
                                fun(name,location,zone,Integer.parseInt(slots),id,chat_id);
                                Log.d("data",document.getId() + " => " + document.getData());
                            }

                        } else {
                            System.out.println("out2");
                            Log.d("data", "Error getting documents: ", task.getException());
                        }
                    }
                });
        // check if username and password available and correct.

    }



    public  void fun(String name,String location,String zone,int slots,String id,String chat_id){

        EventList.getInstance().add_data(new EventData(name, location, zone, slots,chat_id,id));
        EventList.getInstance().Add_to_set(id);
        Log.d("fun", "size = " + EventList.getInstance().get_list_size());
        ba.notifyDataSetChanged();
    }
}

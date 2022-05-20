package com.example.myapplication;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class SearchCreateFragment extends Fragment {

    private Button Search;
    private Button Create;
    private RecyclerView recyclerView;
    private String TAG = "Fragment1";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);

        Log.d("MainScreen","Open");
        Search = view.findViewById(R.id.Search_Event);
        Create = view.findViewById(R.id.Create_Event);

        Search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("MainScreen","Search clicked");
                getFragmentManager().beginTransaction().replace(R.id.fragment,new ListViewFragment()).addToBackStack(null).commit();
            }
        });

        Create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("MainScreen", "create clicked");
                getFragmentManager().beginTransaction().replace(R.id.fragment, new Form_for_host()).addToBackStack(null).commit();
            }
        });

        return view;
    }


}
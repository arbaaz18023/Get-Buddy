package com.example.myapplication;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BuddyAdapter extends RecyclerView.Adapter<BuddyAdapter.ViewHolder>  {

    private CommunicateRecycler Ilistener;

    public BuddyAdapter(CommunicateRecycler Ilistener)
    {
        this.Ilistener = Ilistener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_dataview, parent,false);
        ViewHolder holder=new ViewHolder(view,Ilistener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EventData nd = EventList.getInstance().get_data(position);
        holder.event_id.setText(String.valueOf(position+1));
        holder.event_name.setText(nd.getName());
        holder.news_location.setText(nd.getLocation());
    }

    @Override
    public int getItemCount() {
        return EventList.getInstance().get_list_size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView event_id;
        private TextView event_name;
        private TextView news_location;
        private View root_view;
        private CommunicateRecycler Ilistener;

        public ViewHolder(View itemView,CommunicateRecycler Recy)
        {
            super(itemView);
            Ilistener = Recy;
            event_id = itemView.findViewById(R.id.event_id);
            event_name = itemView.findViewById(R.id.name);
            news_location = itemView.findViewById(R.id.location);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

           // ((MainActivity)(root_view.getContext())).replaceFragment();
            Ilistener.replaceFragment(EventList.getInstance().get_data(getAdapterPosition()));
            Log.i("clicked: ", "" + EventList.getInstance().get_data(getAdapterPosition()).getName());
        }
    }

    interface CommunicateRecycler{
        void replaceFragment(EventData nd);
    }
}

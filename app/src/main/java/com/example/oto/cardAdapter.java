package com.example.oto;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class cardAdapter extends RecyclerView.Adapter<cardAdapter.viewHolder> {
private ArrayList<card_item> _card_list;

    public static class viewHolder extends RecyclerView.ViewHolder{
        public TextView driverName;
        public TextView origin;
        public TextView dest;
        public TextView time;
        public viewHolder(View itemView) {
            super(itemView);
            driverName = itemView.findViewById(R.id.driver_name);
            origin = itemView.findViewById(R.id.from_id);
            dest = itemView.findViewById(R.id.to_id);
            time = itemView.findViewById(R.id.time_id);

        }
    }

    public cardAdapter(ArrayList<card_item> cardList ){
        _card_list=cardList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_searchdrive,parent,false);
        viewHolder vh =new viewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder( viewHolder holder, int position) {
        card_item currentItem = _card_list.get(position);
       holder.driverName.setText(currentItem.get_name());
       holder.origin.setText((currentItem.get_origin()));
        holder.dest.setText((currentItem.get_dest()));
        holder.time.setText((currentItem.get_time()));
    }

    @Override
    public int getItemCount() {
        return _card_list.size();
    }
}



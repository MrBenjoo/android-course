package com.example.benjo.friendsintheworld.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.benjo.friendsintheworld.R;
import com.example.benjo.friendsintheworld.Interfaces.RecyclerViewClickListener;

import java.util.ArrayList;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.GroupViewHolder> {
    private ArrayList<String> groupList = new ArrayList<>();
    private RecyclerViewClickListener mListener;

    public ListAdapter(ArrayList<String> groupList, RecyclerViewClickListener mListener) {
        this.groupList = groupList;
        this.mListener = mListener;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new GroupViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        holder.tvGroup.setText(groupList.get(position));
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public void updateList(ArrayList<String> groupList) {
        this.groupList.clear();
        this.groupList.addAll(groupList);
        notifyDataSetChanged();
    }

    class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        RecyclerViewClickListener mListener;
        TextView tvGroup;

        private GroupViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            mListener = listener;
            tvGroup = (TextView) itemView.findViewById(R.id.tv_group);
            tvGroup.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            mListener.onLongClick(v, getAdapterPosition());
            return true;
        }
    }

}

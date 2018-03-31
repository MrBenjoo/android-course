package com.example.benjo.personal_finance_app.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benjo.personal_finance_app.Transaction;
import com.example.benjo.personal_finance_app.R;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.GroupViewHolder> {
    private ArrayList<Transaction> transactionList = new ArrayList<>();

    public ListAdapter(ArrayList<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        holder.tvTitle.setText(transactionList.get(position).getTitle());
        holder.tvDate.setText(transactionList.get(position).getDate());
        holder.tvCategory.setText(transactionList.get(position).getCategory());
        holder.tvAmount.setText(transactionList.get(position).getAmount());
        holder.imageView.setImageResource(transactionList.get(position).getImgRes());
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }


    public void updateList(ArrayList<Transaction> newList) {
        this.transactionList.clear();
        this.transactionList.addAll(newList);
        notifyDataSetChanged();
    }


    public class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvDate;
        TextView tvCategory;
        TextView tvAmount;
        ImageView imageView;


        private GroupViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.cv_tv_title);
            tvDate = (TextView) itemView.findViewById(R.id.cv_tv_date);
            tvCategory = (TextView) itemView.findViewById(R.id.cv_tv_category);
            tvAmount = (TextView) itemView.findViewById(R.id.cv_tv_amount);
            imageView = (ImageView) itemView.findViewById(R.id.cv_iv);
        }
    }

}

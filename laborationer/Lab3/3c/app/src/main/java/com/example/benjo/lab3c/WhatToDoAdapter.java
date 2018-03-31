package com.example.benjo.lab3c;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class WhatToDoAdapter extends ArrayAdapter<Instruction> {
    private LayoutInflater inflater;
    private ArrayList<Instruction> listInstructions;


    public WhatToDoAdapter(Context context, ArrayList<Instruction> listInstructions) {
        super(context, R.layout.list_row, listInstructions);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row, parent, false);
            holder = new ViewHolder();
            holder.tvListWhatToDo = (TextView) convertView.findViewById(R.id.tvListWhatToDo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvListWhatToDo.setText(this.getItem(position).getWhatToDo());


        return convertView;
    }

    class ViewHolder {
        TextView tvListWhatToDo;
    }
}

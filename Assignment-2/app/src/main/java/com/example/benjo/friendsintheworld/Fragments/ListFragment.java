package com.example.benjo.friendsintheworld.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.benjo.friendsintheworld.Controller.Controller;
import com.example.benjo.friendsintheworld.Adapters.ListAdapter;
import com.example.benjo.friendsintheworld.R;
import com.example.benjo.friendsintheworld.Interfaces.RecyclerViewClickListener;

import java.util.ArrayList;


public class ListFragment extends Fragment {
    private ListAdapter mAdapter;
    private ArrayList<String> groupList;
    private SwipeRefreshLayout refresh;
    private Controller controller;


    public ListFragment() { /* Default constructor */ }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        initRecyclerView(rootView);
        initRefresh(rootView);
        return rootView;
    }

    private void initRecyclerView(View rootView) {
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rc);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerViewClickListener mListener = new RecyclerViewListener();
        groupList = new ArrayList<>();
        mAdapter = new ListAdapter(groupList, mListener);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initRefresh(View rootView) {
        refresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefresh);
        refresh.setOnRefreshListener(new RefreshListener());
    }

    public void updateRecyclerView(ArrayList<String> currentGroups) {
        mAdapter.updateList(currentGroups);
    }

    private class RecyclerViewListener implements RecyclerViewClickListener {
        @Override
        public void onLongClick(View view, int position) {
            if (view.getId() == R.id.tv_group) {
                String currentGroup = groupList.get(position);
                controller.showDialogJoin(currentGroup);
            }
        }
    }

    private class RefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            if (controller != null) {
                controller.onRefreshListener();
            } else {
                Snackbar.make(getActivity().findViewById(R.id.main_activity_parent), "Controller == null", Snackbar.LENGTH_LONG).show();
            }
            refresh.setRefreshing(false);
        }
    }


}

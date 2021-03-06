package com.example.benjo.fragmentcontainer;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContainerFragment extends Fragment {
    private FragmentViewer viewer;

    public ContainerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_container, container, false);
        FragmentManager fm = getChildFragmentManager();
        viewer = new FragmentViewer(fm,R.id.fragment_container);
        if(savedInstanceState!=null) {
            viewer.setCurrentTag(savedInstanceState.getString("currentTag"));
        }
        return view;
    }

    public void onResume() {
        super.onResume();
        viewer.show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("currentTag",viewer.getCurrentTag());
        super.onSaveInstanceState(outState);
    }

    public void add(Fragment fragment, String tag) {
        viewer.add(fragment,tag);
    }

    public void setCurrentTag(String tag) {
        viewer.setCurrentTag(tag);
    }

    public String getCurrentTag() {
        return viewer.getCurrentTag();
    }

    public String show() {
        return viewer.show();
    }

    public String show(String tag) {
        return viewer.show(tag);
    }
}

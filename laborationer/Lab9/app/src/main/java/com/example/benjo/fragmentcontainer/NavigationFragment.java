package com.example.benjo.fragmentcontainer;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends Fragment {
    private Controller controller;
    private Button btnFrag1;
    private Button btnFrag2;
    private Button btnFrag3;

    public NavigationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Navigation-onCreateView", "Before");
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);
        Log.d("Navigation-onCreateView", "After");
        initializeComponents(view);
        registerListeners();
        return view;
    }

    private void initializeComponents(View view) {
        btnFrag1 = (Button)view.findViewById(R.id.btnFrag1);
        btnFrag2 = (Button)view.findViewById(R.id.btnFrag2);
        btnFrag3 = (Button)view.findViewById(R.id.btnFrag3);
    }

    private void registerListeners() {
        View.OnClickListener listener = new View.OnClickListener() {

            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.btnFrag1 : controller.showFragment("first"); break;
                    case R.id.btnFrag2 : controller.showFragment("second"); break;
                    case R.id.btnFrag3 : controller.showFragment("third"); break;
                }
            }
        };
        btnFrag1.setOnClickListener(listener);
        btnFrag2.setOnClickListener(listener);
        btnFrag3.setOnClickListener(listener);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}

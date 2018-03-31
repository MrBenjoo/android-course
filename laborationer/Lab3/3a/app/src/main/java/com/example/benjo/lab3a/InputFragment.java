package com.example.benjo.lab3a;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class InputFragment extends Fragment {
    private Controller controller;
    private Button btnColor;
    private String[] listItems = {"RÖD", "BLÅ", "GUL", "GRÖN"};

    public InputFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_input, container, false);
        initButton(rootView);
        initListView(rootView);
        return rootView;
    }

    private void initButton(View rootView) {
        btnColor = (Button)rootView.findViewById(R.id.btnColor);
        btnColor.setText("RÖD");
        btnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnColor.getText().equals("RÖD")) {
                    controller.changeTvColor("RÖD");
                } else if (btnColor.getText().equals("BLÅ")) {
                    controller.changeTvColor("BLÅ");
                } else if (btnColor.getText().equals("GUL")) {
                    controller.changeTvColor("GUL");
                } else if (btnColor.getText().equals("GRÖN")) {
                    controller.changeTvColor("GRÖN");
                } else {
                    Toast.makeText(getContext(), "NO COLOR", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initListView(View rootView) {
        ListView listView = (ListView) rootView.findViewById(R.id.listViewColors);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        controller.onItemClickListener("RÖD");
                        break;
                    case 1:
                        controller.onItemClickListener("BLÅ");
                        break;
                    case 2:
                        controller.onItemClickListener("GUL");
                        break;
                    case 3:
                        controller.onItemClickListener("GRÖN");
                        break;

                }
            }
        });
    }

    public void setBtnText(String text) {
        btnColor.setText(text);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

}

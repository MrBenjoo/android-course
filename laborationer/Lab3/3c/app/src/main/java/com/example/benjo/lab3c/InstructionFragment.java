package com.example.benjo.lab3c;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class InstructionFragment extends Fragment {
    private TextView tvWhatToDo;
    private TextView tvContent;
    private String whatToDo;
    private String content;

    public InstructionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_instructions, container, false);
        initTextViews(rootView);
        return rootView;
    }

    private void initTextViews(View rootView) {
        tvWhatToDo = (TextView) rootView.findViewById(R.id.tvContentWhatToDo);
        tvContent = (TextView) rootView.findViewById(R.id.tvContent);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setWhatToDo(whatToDo); // needs to be here so that the textView isn't null when setText is called
        setContent(content);
    }

    public void setWhatToDo(String whatToDo) {
        tvWhatToDo.setText(whatToDo);
    }

    public void setContent(String content) {
        tvContent.setText(content);
    }

    public void setInstruction(String whatToDo, String content) {
        this.whatToDo = whatToDo;
        this.content = content;
    }
}

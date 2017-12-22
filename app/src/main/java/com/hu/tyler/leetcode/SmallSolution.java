package com.hu.tyler.leetcode;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class SmallSolution extends Fragment {


    public SmallSolution() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //String solution = savedInstanceState.getString("solution");
        //Log.d("XXX", "solution text: " + solution);
       // TextView textView = (TextView)container.findViewById(R.id.smallSolutionText);
        //textView.setText(solution);
        return inflater.inflate(R.layout.fragment_small_solution, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);
       String solution = getArguments().getString("solution");
        String temp = getArguments().getString("title");
        getActivity().setTitle(temp + " Solution");
        TextView textView = (TextView)view.findViewById(R.id.smallSolutionText);
        textView.setText(solution);

    }
}

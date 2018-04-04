package com.xqlh.heartsmart.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xqlh.heartsmart.R;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppraisalFragment extends SupportFragment {


    public static AppraisalFragment newInstance() {
        // Required empty public constructor
        Bundle args = new Bundle();
        AppraisalFragment fragment = new AppraisalFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appraisal, container, false);
    }

}

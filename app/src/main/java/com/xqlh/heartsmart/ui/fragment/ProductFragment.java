package com.xqlh.heartsmart.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xqlh.heartsmart.R;

import me.yokeyword.fragmentation.SupportFragment;


public class ProductFragment extends SupportFragment {


    public static ProductFragment newInstance() {
        // Required empty public constructor
        Bundle args = new Bundle();
        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false);
    }


}

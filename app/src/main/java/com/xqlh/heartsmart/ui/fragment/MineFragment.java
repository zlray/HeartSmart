package com.xqlh.heartsmart.ui.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.ui.mine.MineActivity;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends SupportFragment {

    ImageView mine_iv_head;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("lz", "我的fragment");
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        mine_iv_head = view.findViewById(R.id.mine_iv_head);
        mine_iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MineActivity.class));
            }
        });
        return view;
    }

}

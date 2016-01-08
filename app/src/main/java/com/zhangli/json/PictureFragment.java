package com.zhangli.json;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class PictureFragment extends Fragment {


    public static PictureFragment newInstance() {
        PictureFragment fragment = new PictureFragment();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_picture_layout, container, false);
    }
}

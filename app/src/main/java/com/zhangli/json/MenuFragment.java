package com.zhangli.json;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MenuFragment extends Fragment {

    private Button picture_btn;
    private Button video_btn;
    private Button music_btn;
    private OnMenuFragmentInteractionListener mOnMenuFragmentInteractionListener;


    //给接口定义的方法。
    public interface OnMenuFragmentInteractionListener {
        void toPicture();
        void toVideo();
        void toMusic();
    }

    public static MenuFragment newInstance() {
        MenuFragment menuFragment = new MenuFragment();
        return menuFragment;
    }

    //加载fragment时调用
    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnMenuFragmentInteractionListener) {
            mOnMenuFragmentInteractionListener = (OnMenuFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMenuFragmentInteractionListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        picture_btn = (Button) getView().findViewById(R.id.fragment_merchant_picture_btn);
        video_btn = (Button) getView().findViewById(R.id.fragment_merchant_video_btn);
        music_btn = (Button) getView().findViewById(R.id.fragment_merchant_music_btn);

        picture_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnMenuFragmentInteractionListener.toPicture();

            }
        });

        video_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnMenuFragmentInteractionListener.toVideo();

            }
        });


        music_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnMenuFragmentInteractionListener.toMusic();
            }
        });


    }
}

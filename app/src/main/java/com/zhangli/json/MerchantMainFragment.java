package com.zhangli.json;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MerchantMainFragment extends Fragment implements View.OnClickListener{
    private ViewPager mViewPager;
    private TextView frist_news;
    private TextView pictrue_news;
    private TextView it_news;

    public static MerchantMainFragment newInstance() {
        MerchantMainFragment merchantMainFragment=new MerchantMainFragment();
        return  merchantMainFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_title_two_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewPager= (ViewPager) getView().findViewById(R.id.main_viewpager);

        frist_news= (TextView) getView().findViewById(R.id.frist_text);
        pictrue_news= (TextView) getView().findViewById(R.id.pictrue_text);
        it_news= (TextView) getView().findViewById(R.id.it_text);

        frist_news.setOnClickListener(this);
        pictrue_news.setOnClickListener(this);
        it_news.setOnClickListener(this);

        //给滑动的Fragment添加多个fragment。
        List<Fragment> list=new ArrayList<>();
        list.add(MerchantFragment.newInstance());
        list.add(PictureFragment.newInstance());
        list.add(MerchantFragment.newInstance());

        MerchantFragmentAdapter adapter=new MerchantFragmentAdapter(getFragmentManager(),list);
        mViewPager.setAdapter(adapter);

        //进去就让当前界面显示红色
        frist_news.setTextColor(Color.RED);

        //给滑动界面增加监听事件
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                clickView();
                switch (position){
                    case 0:
                        frist_news.setTextColor(Color.RED);
                        break;
                    case 1:
                        pictrue_news.setTextColor(Color.RED);
                        break;
                    case 2:
                        it_news.setTextColor(Color.RED);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }

    //点击上面的指示器时，调用方法将其他字体颜色还原成白色，并且切换viewpager。
    @Override
    public void onClick(View v) {
        clickView();
        switch (v.getId()){
            case R.id.frist_text:
                mViewPager.setCurrentItem(0);
                frist_news.setTextColor(Color.RED);
                break;
            case R.id.pictrue_text:
                mViewPager.setCurrentItem(1);
                pictrue_news.setTextColor(Color.RED);
                break;
            case R.id.it_text:
                mViewPager.setCurrentItem(2);
                it_news.setTextColor(Color.RED);
                break;
        }
    }

    public void clickView(){
        frist_news.setTextColor(Color.WHITE);
        pictrue_news.setTextColor(Color.WHITE);
        it_news.setTextColor(Color.WHITE);
    }

    public static class MerchantFragmentAdapter extends FragmentPagerAdapter{
        private List<Fragment> mList=new ArrayList<>();


        public MerchantFragmentAdapter(FragmentManager fm,List<Fragment> list) {
            super(fm);
            mList=list;
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }


        @Override
        public int getCount() {
            return mList.size();
        }
    }
}

package com.zhangli.json;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.warmtel.slidingmenu.lib.SlidingMenu;
import com.warmtel.slidingmenu.lib.app.SlidingActivity;

public class MerchantActivity extends SlidingActivity implements
        MenuFragment.OnMenuFragmentInteractionListener ,
        MerchantFragment.OnMerchantFragmentInterfaceListener

{

    @Override
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_merchant_layout);

        getSupportFragmentManager().beginTransaction().
                add(R.id.merchant_container_layout,MerchantMainFragment.newInstance()).commit();


        setBehindContentView(R.layout.sliding_menu_merchant_layout);
        getSupportFragmentManager().beginTransaction().
                add(R.id.sliding_merchant_layout,MenuFragment.newInstance()).commit();

        SlidingMenu slidingMenu=getSlidingMenu();
        // 设置滑动菜单视图的宽度
        slidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
        //从左边滑动
        slidingMenu.setMode(SlidingMenu.LEFT);
        // 设置渐入渐出效果的值
        slidingMenu.setFadeDegree(0.35f);
        //边缘触摸滑动
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
    }


    @Override
    public void toPicture(){
        //实现接口的方法，作用是用相应的Fragment替换掉当前的界面。
//        getSupportFragmentManager().beginTransaction().
//                replace(R.id.merchant_container_layout, PictureFragment.newInstance()).commit();
        Intent intent=new Intent(this,PictrueActivity.class);
        startActivity(intent);
        //如果当前状态是打开的就关闭，关闭就打开。
        toggle();
    }

    @Override
    public void toVideo() {
//        getSupportFragmentManager().beginTransaction().
//                replace(R.id.merchant_container_layout, MerchantMainFragment.newInstance()).commit();
        Intent intent=new Intent(this,VedioActivity.class);
        startActivity(intent);
        toggle();
    }

    @Override
    public void toMusic() {
        Intent intent=new Intent(this,MusicActivity.class);
        startActivity(intent);
        toggle();
    }

    //当点击返回按钮时
    @Override
    public void back(){
        toggle();
    }
}

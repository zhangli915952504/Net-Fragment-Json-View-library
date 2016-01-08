package com.zhangli.json;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.scxh.slider.library.SliderLayout;
import com.scxh.slider.library.SliderTypes.BaseSliderView;
import com.scxh.slider.library.SliderTypes.TextSliderView;
import com.squareup.picasso.Picasso;
import com.warmtel.android.xlistview.XListView;
import com.zhangli.json.map.Info;
import com.zhangli.json.map.MapResult;
import com.zhangli.json.map.MerchantKey;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends Activity {

    private XListView listView;
    private String mHttpUrl = "http://192.168.1.120/aa/around";
    private MyAdapter myAdapter;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SliderLayout mSliderLayout;
    private  RelativeLayout relativeLayout;
    private AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_layout);

        mProgressBar = (ProgressBar) findViewById(R.id.merchant_progressbar);
        listView = (XListView) findViewById(R.id.main_xlistview_id);


        //ListView上面的滑动图片
        View sliderHeaderView = LayoutInflater.from(this).inflate(R.layout.slider_image_layout, null);
        mSliderLayout = (SliderLayout) sliderHeaderView.findViewById(R.id.slider_imager);
        relativeLayout= (RelativeLayout) findViewById(R.id.main_relative_layout_id);
        //添加到ListView的头部
        listView.addHeaderView(sliderHeaderView);


        //取值 给滑动图片
        HashMap<String, String> silderList =getData();
        for (final String key : silderList.keySet()) {
            String url = silderList.get(key);
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView.description(key);
            textSliderView.image(url);
            //给滑动图片安上点击事件，点击就吐丝key.
            textSliderView.setScaleType(BaseSliderView.ScaleType.CenterCrop);
            textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView baseSliderView) {
                    Toast.makeText(MainActivity.this, key, Toast.LENGTH_SHORT).show();
                }
            });
            mSliderLayout.addSlider(textSliderView);
        }
        //将小圆球指示器放到右下角
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        mSliderLayout.setClickable(false);
        //自定义的小圆球
//        mSliderLayout.setCustomIndicator((PagerIndicator) findViewById(R.id.main_relative_layout_id));

        myAdapter = new MyAdapter(this);
        listView.setAdapter(myAdapter);
        listView.setEmptyView(mProgressBar);

        //调用添加数据到adapter的方法
        getAsyscDataList();



        //下拉刷新
        listView.setPullLoadEnable(true);
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        getAsyscDataList();
                    }
                }.execute();
            }

            @Override
            public void onLoadMore() {
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        getAsyscDataList();
                    }
                }.execute();
            }
        });


    }



    //滑动图片的数据源
    private  HashMap<String, String> getData(){
        HashMap<String, String> http_url_maps = new HashMap<String, String>();
        http_url_maps.put("习近平接受八国新任驻华大使递交国书", "http://img.my.csdn.net/uploads/201407/26/1406383291_6518.jpg");
        http_url_maps.put("天津港总裁出席发布会", "http://img.my.csdn.net/uploads/201407/26/1406383290_9329.jpg");
        http_url_maps.put("瑞海公司从消防鉴定到安评一路畅通无阻", "http://img.my.csdn.net/uploads/201407/26/1406383290_1042.jpg");
        http_url_maps.put("Airbnb高调入华 命运将如Uber一样吗？", "http://img.my.csdn.net/uploads/201407/26/1406383275_3977.jpg");

        return http_url_maps;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mSliderLayout!=null){
            mSliderLayout.stopAutoCycle();
            mSliderLayout=null;
        }
    }

    //加载网络将json数据加到adapter上
    public void getAsyscDataList() {
        asyncHttpClient.get(mHttpUrl, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Toast.makeText(MainActivity.this, "请稍等", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.e("tag", "s :" + s);
                Gson gson = new Gson();
                MapResult mapResult = gson.fromJson(s, MapResult.class);
                Info info = mapResult.getInfo();
                List<MerchantKey> merchantKey = info.getMerchantKey();

                myAdapter.setList(merchantKey);
                if(relativeLayout!=null){

                    relativeLayout.setVisibility(View.GONE);
                    relativeLayout=null;

                }
                listView.setRefreshTime(new SimpleDateFormat("hh:mm:ss").format(System.currentTimeMillis()));
                listView.stopRefresh();
                listView.stopLoadMore();

            }
        });
    }




    public static class MyAdapter extends BaseAdapter {
        private static final int TYPE_ONE = 0;
        private static final int TYPE_TWO = 1;

        private List<MerchantKey> list = new ArrayList<>();
        private LayoutInflater inflater;
        private Context context;

        public MyAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        public void setList(List<MerchantKey> merchantKeyList) {
            list = merchantKeyList;
            //通知刷新适配器数据源
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            MerchantKey merchantKey = (MerchantKey) getItem(position);
            if (merchantKey.getCardType().equalsIgnoreCase("YES")) {
                return TYPE_TWO;
            } else {
                return TYPE_ONE;
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(getItemViewType(position)==TYPE_TWO){
                return getView_two(position,convertView,parent);
            }else{
                return  getView_one(position,convertView,parent);
            }
        }


        public View getView_one(int position, View convertView, ViewGroup parent) {
            ViewHodler viewHodler = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item, null);
                viewHodler = new ViewHodler();
                viewHodler.img_pic = (ImageView) convertView.findViewById(R.id.imageView);
                viewHodler.textView_name = (TextView) convertView.findViewById(R.id.textView_name);
                viewHodler.textView_coupon = (TextView) convertView.findViewById(R.id.textView_coupon);
                viewHodler.textView_distance = (TextView) convertView.findViewById(R.id.textView_distance);
                viewHodler.textView_location = (TextView) convertView.findViewById(R.id.textView_location);
                viewHodler.image_group = (ImageView) convertView.findViewById(R.id.image_group);
                viewHodler.image_card = (ImageView) convertView.findViewById(R.id.image_card);
                viewHodler.image_ticket = (ImageView) convertView.findViewById(R.id.image_ticket);
                convertView.setTag(viewHodler);

            }
            viewHodler = (ViewHodler) convertView.getTag();
            MerchantKey merchantKey = (MerchantKey) getItem(position);
            Picasso.with(context).load(merchantKey.getPicUrl()).into(viewHodler.img_pic);
            viewHodler.textView_name.setText(merchantKey.getName());
            viewHodler.textView_coupon.setText(merchantKey.getCoupon());
            viewHodler.textView_distance.setText(merchantKey.getDistance());
            viewHodler.textView_location.setText(merchantKey.getLocation());

            if (merchantKey.getCouponType().equalsIgnoreCase("YES")) {
                viewHodler.image_ticket.setVisibility(View.VISIBLE);
            } else {
                viewHodler.image_ticket.setVisibility(View.GONE);
            }
            if (merchantKey.getCardType().equalsIgnoreCase("YES")) {
                viewHodler.image_card.setVisibility(View.VISIBLE);
            } else {
                viewHodler.image_card.setVisibility(View.GONE);
            }
            if (merchantKey.getGroupType().equalsIgnoreCase("YES")) {
                viewHodler.image_group.setVisibility(View.VISIBLE);
            } else {
                viewHodler.image_group.setVisibility(View.GONE);
            }

            return convertView;
        }

        public View getView_two(int position, View convertView, ViewGroup parent) {
            ViewHodler viewHodler = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_two_item, null);
                viewHodler = new ViewHodler();
                viewHodler.img_pic = (ImageView) convertView.findViewById(R.id.imageView);
                viewHodler.textView_name = (TextView) convertView.findViewById(R.id.textView_name);
                viewHodler.textView_coupon = (TextView) convertView.findViewById(R.id.textView_coupon);
                viewHodler.textView_distance = (TextView) convertView.findViewById(R.id.textView_distance);
                viewHodler.textView_location = (TextView) convertView.findViewById(R.id.textView_location);
                viewHodler.image_group = (ImageView) convertView.findViewById(R.id.image_group);
                viewHodler.image_card = (ImageView) convertView.findViewById(R.id.image_card);
                viewHodler.image_ticket = (ImageView) convertView.findViewById(R.id.image_ticket);
                convertView.setTag(viewHodler);

            }
            viewHodler = (ViewHodler) convertView.getTag();
            MerchantKey merchantKey = (MerchantKey) getItem(position);
            Picasso.with(context).load(merchantKey.getPicUrl()).into(viewHodler.img_pic);
            viewHodler.textView_name.setText(merchantKey.getName());
            viewHodler.textView_coupon.setText(merchantKey.getCoupon());
            viewHodler.textView_distance.setText(merchantKey.getDistance());
            viewHodler.textView_location.setText(merchantKey.getLocation());

            if (merchantKey.getCouponType().equalsIgnoreCase("YES")) {
                viewHodler.image_ticket.setVisibility(View.VISIBLE);
            } else {
                viewHodler.image_ticket.setVisibility(View.GONE);
            }
            if (merchantKey.getCardType().equalsIgnoreCase("YES")) {
                viewHodler.image_card.setVisibility(View.VISIBLE);
            } else {
                viewHodler.image_card.setVisibility(View.GONE);
            }
            if (merchantKey.getGroupType().equalsIgnoreCase("YES")) {
                viewHodler.image_group.setVisibility(View.VISIBLE);
            } else {
                viewHodler.image_group.setVisibility(View.GONE);
            }

            return convertView;
        }

        public class ViewHodler {
            ImageView img_pic;
            TextView textView_name;
            TextView textView_coupon;
            TextView textView_distance;
            TextView textView_location;
            ImageView image_group;
            ImageView image_card;
            ImageView image_ticket;
        }
    }
}

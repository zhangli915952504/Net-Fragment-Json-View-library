package com.zhangli.json;

import android.app.Application;
import android.content.res.AssetManager;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.google.gson.Gson;
import com.zhangli.json.map.Info;
import com.zhangli.json.map.MapResult;
import com.zhangli.json.map.MerchantKey;
import com.zhangli.json.map.PageInfo;
import com.zhangli.json.user.User;
import com.zhangli.json.user.UserInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    //------------------------2016.1.4---------------------------------------------------
    public void testUser() throws IOException {
        AssetManager assetManager = getContext().getAssets();
        InputStream inPutStream = assetManager.open("user.json");
        String jsonStr = readInPutStream(inPutStream);

        Log.e("tag", jsonStr);
        Gson gson = new Gson();
        UserInfo userInfo = gson.fromJson(jsonStr, UserInfo.class);

        String message = userInfo.getMessage();

        User user = userInfo.getUser();
        String email = user.getEmail();
        int id = user.getId();
        String userName = user.getUserName();
        String password = user.getPassWord();


        Log.e("tag", "id:" + id + ",username:" + userName + ",password:" + password + ",email:" + email + ",message:" + message);
    }

    public String readInPutStream(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }


//---------------------------------------------------------------------------------------



    //--------------------------map----------------------------------------
    public void testMap() throws IOException {
        AssetManager assetManager = getContext().getAssets();
        InputStream inputStream = assetManager.open("map.json");
        String mapStr=readInPutStream(inputStream);

        Gson gson= new Gson();
        MapResult mapResult=gson.fromJson(mapStr, MapResult.class);
        int resultCode =mapResult.getResultCode();

        String resultInfo=mapResult.getResultInfo();
        Log.e("tag","code:"+resultCode+",info:"+resultInfo);

        Info info=mapResult.getInfo();
        PageInfo pageInfo=info.getPageInfo();
        int total=pageInfo.getTotal();
        int pageSize=pageInfo.getPageSize();
        int lastPageNumber=pageInfo.getLastPageNumber();
        int nowPage=pageInfo.getNowPage();
        int currNum=pageInfo.getCurrNum();
        Log.e("tag","total:"+total+",pageSize:"+pageSize+"lastPageNumber:"+lastPageNumber+",nowPage:"+nowPage+
        ",currNum:"+currNum);

        List<MerchantKey> merchantKey=info.getMerchantKey();
        for(MerchantKey key:merchantKey){
            Log.e("tag","merchantID:"+key.getMerchantID()+"name:"+key.getName()+"coupon:"+key.getCoupon()+
            "location:"+key.getLocation()+"distance:"+key.getDistance()+"picUrl:"+key.getPicUrl()+
                    "couponType:"+key.getCouponType()+"cardType:"+key.getCardType()+"gpsX:"+key.getGpsX()+
                    "gpsY:"+key.getGpsY()+"goodSayNum:"+key.getGoodSayNum()+"midSayNum:"+key.getMidSayNum()+
            "badSayNum:"+key.getBadSayNum());
        }
    }

    //------------------------------------------------------------
    public void testGetHttpUrlConnection() {
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            URL url = new URL("http://192.168.1.144/json/around");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setDoInput(true);
            Log.e("tag", "3");
            if (httpURLConnection.getResponseCode() == 200) {
                Log.e("tag","123123");
                inputStream = httpURLConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                return stringBuilder.toString();
//                String responseJson = ;

                Log.e("tag","123"+stringBuilder.toString());
                //return stringBuilder.toString();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

    }



}
package com.mobileproto.lab2;

import android.util.Log;

/**
 * Created by evan on 9/25/13.
 */
public class FeedItem {

    public String text;
    public String userName;
    public String condition;

    public FeedItem(String userName, String text){
        this.userName = userName;
        this.text = text;
        Log.d("I made it here", "yipee");
    }

}

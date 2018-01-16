package com.mappr.gitgetter.Global;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mappr.gitgetter.Interfaces.ClientCallBack;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by pradumanpraduman on 14/01/18.
 */

public class Client {


    /**
     * ****************************************************************************************
     * *                                                                                  *
     * *  The client class contains all the functions to connect with the server,         *
     * *  It works with AutomationRestClient for its assistance.                          *
     * *  The Get calls, get use the information from the database with a relativeUrl     *
     * *  passed into the functions,                                                      *
     * *  There is call code in the parameter, which on return tells the                  *
     * *  functions of the activity from which call is made that,                         *
     * *  which call was made from the activity, so that the activity                     *
     * *  can decide what actions to be taken.                                            *
     * *  This has been done to keep the calls to server as minimum as possible           *
     * *                                                                                  *
     * **************************************************************************************
     */

    private final String TAG = "client";

    private ClientCallBack callBack;
    private Context context;
    private static AsyncHttpClient client;

    public Client(Context context, ClientCallBack callBack) {
        this.context = context;
        this.callBack = callBack;
        client = new AsyncHttpClient();
        String key = "User-Agent";
        String value = "Pradumann";
        client.addHeader(key, value);
    }

    public void get(final String url) {

        client.get( url, null, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // list of hubs must be an array, if its not an array its an error
                callBack.onSuccess(response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {

                callBack.onSuccess(timeline.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                callBack.onFailure("Failed to execute request");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                callBack.onFailure(responseString);
            }

        });
    }
}
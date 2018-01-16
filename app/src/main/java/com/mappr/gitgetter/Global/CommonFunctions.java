package com.mappr.gitgetter.Global;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mappr.gitgetter.Pojos.Repo;
import com.mappr.gitgetter.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by pradumanpraduman on 15/01/18.
 */

public class CommonFunctions {

    private Context context;
    private ProgressDialog progress;

    public CommonFunctions(Context context){
        this.context = context;
        progress = new ProgressDialog(context);
    }

    public ArrayList<Repo> getRepoListFromJson(JSONArray jsonArray){

            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Repo>>(){}.getType();
            return gson.fromJson(String.valueOf(jsonArray), type);

    }

    public boolean isNetworkAvailable(){

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if(activeNetwork != null && activeNetwork.isConnected()){
            return true;
        }
            return false;
    }

    public int getColor(){
        int [] colors = {R.color.colorCyan, R.color.colorIndigo,
                        R.color.colorRed, R.color.colorPink, R.color.colorTeal};

        Random r = new Random();
        int i = r.nextInt(4);

        return colors[i];
    }

    public void showProgress(String title, String msg){
        progress.setTitle(title);
        progress.setMessage(msg);
        progress.show();
    }

    public void dismissProgress(){
        progress.dismiss();
    }

    public void showToast(String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

}

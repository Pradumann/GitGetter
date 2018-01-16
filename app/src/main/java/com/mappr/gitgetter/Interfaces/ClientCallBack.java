package com.mappr.gitgetter.Interfaces;

/**
 * Created by pradumanpraduman on 14/01/18.
 */

public interface ClientCallBack {

    void onSuccess(String response);
    void onFailure(String response);
}

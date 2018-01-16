package com.mappr.gitgetter.Pojos;

/**
 * Created by pradumanpraduman on 15/01/18.
 */

public class License {

    private String key;
    private String name;
    private String spdx_id;
    private String url;

    public License(String key, String name, String spdx_id, String url) {
        this.key = key;
        this.name = name;
        this.spdx_id = spdx_id;
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpdx_id() {
        return spdx_id;
    }

    public void setSpdx_id(String spdx_id) {
        this.spdx_id = spdx_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

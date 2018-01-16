package com.mappr.gitgetter.Pojos;

/**
 * Created by pradumanpraduman on 15/01/18.
 */

public class Contributor {

    private String login;
    private int id;
    private String avatar_url;
    private String repos_url;
    private String html_url;
    private int contributions;

    public Contributor(String login, int id, String avatar_url, String repos_url, String html_url, int contributions) {
        this.login = login;
        this.id = id;
        this.avatar_url = avatar_url;
        this.repos_url = repos_url;
        this.html_url = html_url;
        this.contributions = contributions;
    }

    public int getContributions() {
        return contributions;
    }

    public void setContributions(int contributions) {
        this.contributions = contributions;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public void setRepos_url(String repos_url) {
        this.repos_url = repos_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }
}

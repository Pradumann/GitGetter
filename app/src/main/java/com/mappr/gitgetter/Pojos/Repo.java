package com.mappr.gitgetter.Pojos;

/**
 * Created by pradumanpraduman on 14/01/18.
 */

public class Repo {

    private String name;
    private String html_url;
    private String description;
    private String contributors_url;
    private int id;
    private Owner owner;
    private License license;
    private int watchers;
    private int forks_count;
    private int stargazers_count;
    private int size;
    private double score;
    private String full_name;
    private String language;


    public Repo(String name, String html_url, String description, String contributors_url,
                int id, Owner owner, License license, int watchers, int forks_count,
                int stargazers_count, int size, double score, String full_name, String language) {

        this.name = name;
        this.html_url = html_url;
        this.description = description;
        this.contributors_url = contributors_url;
        this.id = id;
        this.owner = owner;
        this.license = license;
        this.watchers = watchers;
        this.forks_count = forks_count;
        this.stargazers_count = stargazers_count;
        this.size = size;
        this.score = score;
        this.full_name = full_name;
        this.language = language;

    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getForks_count() {
        return forks_count;
    }

    public void setForks_count(int forks_count) {
        this.forks_count = forks_count;
    }

    public int getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(int stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContributors_url() {
        return contributors_url;
    }

    public void setContributors_url(String contributors_url) {
        this.contributors_url = contributors_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public int getWatchers() {
        return watchers;
    }

    public void setWatchers(int watchers) {
        this.watchers = watchers;
    }
}

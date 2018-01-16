package com.mappr.gitgetter.Global;

import com.mappr.gitgetter.Pojos.Contributor;
import com.mappr.gitgetter.Pojos.Repo;

/**
 * Created by pradumanpraduman on 15/01/18.
 */

public class GitGetterSingleton {
    private static final GitGetterSingleton ourInstance = new GitGetterSingleton();

    public static GitGetterSingleton getInstance() {
        return ourInstance;
    }

    private GitGetterSingleton() {
    }

    private Repo selectedRep;
    private Contributor selectedContributor;
    private final String URL_KEY = "URL";

    public Repo getSelectedRep() {
        return selectedRep;
    }

    public String getURL_KEY() {
        return URL_KEY;
    }

    public void setSelectedRep(Repo selectedRep) {
        this.selectedRep = selectedRep;
    }

    public Contributor getSelectedContributor() {
        return selectedContributor;
    }

    public void setSelectedContributor(Contributor selectedContributor) {
        this.selectedContributor = selectedContributor;
    }
}

package com.mappr.gitgetter.Global;

import com.mappr.gitgetter.Pojos.Repo;

import java.util.Comparator;

/**
 * Created by pradumanpraduman on 15/01/18.
 */

public class RepoSorter implements Comparator<Repo>{

    @Override
    public int compare(Repo repo1, Repo repo2) {

        int watchers1 = repo1.getWatchers();
        int watchers2 = repo2.getWatchers();

        if (watchers1 > watchers2) {
            return -1;
        } else if (watchers1 < watchers2) {
            return 1;
        } else {
            return 0;
        }
    }
}

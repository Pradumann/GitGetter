package com.mappr.gitgetter.Global;

import com.mappr.gitgetter.Interfaces.FilterCallBack;
import com.mappr.gitgetter.Pojos.Contributor;
import com.mappr.gitgetter.Pojos.Repo;

import java.util.HashMap;

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
    private FilterCallBack filterCallBack;
    // Filter Keys
    private final String F_K_LANGUAGE = "language";
    private final String F_K_WATCHERS = "watchers";
    private final String F_K_FORKS = "forksCount";
    private final String F_K_SCORE = "score";
    private final String F_K_STARGRAZERS = "grazerCount";

    private final String LANG_ASSMBLY = "Assembly";
    private final String LANG_JS = "JavaScript";
    private final String LANG_SWT = "Swift";
    private final String LANG_JAVA = "Java";
    private final String LANG_SHELL = "Shell";
    private final String LANG_HTML = "HTML";
    private final String LANG_GO = "Go";
    private final String LANG_C_PLS_PLS = "C++";
    private final String LANG_ELM = "ELM";


    private final String F_K_SIZE = "size";

    private HashMap<String, Object> filterMap = new HashMap<>();

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

    public String getF_K_LANGUAGE() {
        return F_K_LANGUAGE;
    }

    public String getF_K_WATCHERS() {
        return F_K_WATCHERS;
    }

    public String getF_K_FORKS() {
        return F_K_FORKS;
    }

    public String getF_K_SCORE() {
        return F_K_SCORE;
    }

    public String getF_K_STARGRAZERS() {
        return F_K_STARGRAZERS;
    }

    public String getF_K_SIZE() {
        return F_K_SIZE;
    }

    public FilterCallBack getFilterCallBack() {
        return filterCallBack;
    }

    public void setFilterCallBack(FilterCallBack filterCallBack) {
        this.filterCallBack = filterCallBack;
    }

    public String getLANG_ASSMBLY() {
        return LANG_ASSMBLY;
    }

    public String getLANG_JS() {
        return LANG_JS;
    }

    public String getLANG_SWT() {
        return LANG_SWT;
    }

    public String getLANG_JAVA() {
        return LANG_JAVA;
    }

    public String getLANG_SHELL() {
        return LANG_SHELL;
    }

    public String getLANG_HTML() {
        return LANG_HTML;
    }

    public String getLANG_GO() {
        return LANG_GO;
    }

    public String getLANG_C_PLS_PLS() {
        return LANG_C_PLS_PLS;
    }

    public String getLANG_ELM() {
        return LANG_ELM;
    }

    public HashMap<String, Object> getFilterMap() {
        return filterMap;
    }

    public void setFilterMap(HashMap<String, Object> filterMap) {
        this.filterMap = filterMap;
    }
}

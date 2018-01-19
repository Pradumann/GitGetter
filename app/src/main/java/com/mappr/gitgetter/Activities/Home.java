package com.mappr.gitgetter.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.mappr.gitgetter.Adapters.RepoAdapter;
import com.mappr.gitgetter.Global.Client;
import com.mappr.gitgetter.Global.CommonFunctions;
import com.mappr.gitgetter.Global.GitGetterSingleton;
import com.mappr.gitgetter.Global.RepoSorter;
import com.mappr.gitgetter.Interfaces.ClientCallBack;
import com.mappr.gitgetter.Interfaces.FilterCallBack;
import com.mappr.gitgetter.Pojos.Repo;
import com.mappr.gitgetter.Pojos.StarGrazer;
import com.mappr.gitgetter.R;
import com.mappr.gitgetter.Interfaces.RepoCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Home extends AppCompatActivity implements
        RepoCallBack,
        ClientCallBack,
        FilterCallBack{

    private RecyclerView repoRV;
    private List<Repo> repoList = new ArrayList<>();
    private List<Repo> filteredList = new ArrayList<>();
    private RepoAdapter adapter;
    private Client client;
    private GitGetterSingleton gitGetter;
    private CommonFunctions common;
    private final String BASE_URL = "https://api.github.com/";

    private EditText filterET;
    private ImageView filterIV;
    ProgressDialog progress;
    private String filterString = "";
    private HashMap<String, Object> filterMap  = new HashMap<>();

    //Home

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setInstances();
        setViews();
        setOnClickListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.menu_home, menu);

        MenuItem myActionMenuItem = menu.findItem( R.id.search_view);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(query.length()>0){
                    if(common.isNetworkAvailable()){
                        filterET.getText().clear();
                        searchGitRepo(query);
                    }else {
                        common.showToast(getString(R.string.no_internet_fail_to_load_repo_list));
                    }

                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                // uncomment this code to search git with every time text change
                //searchGitRepo(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search_view) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void searchGitRepo(String query){

        String url = BASE_URL + "search/repositories?q="+query;

        common.showProgress(
                getString(R.string.repo_list_loading_dialog_title),
                getString(R.string.repo_list_loading_dialog_msg));
        client.get(url);
    }

    private void setInstances(){
        client = new Client(this, this);
        common = new CommonFunctions(this);
        gitGetter = GitGetterSingleton.getInstance();

    }

    private void setViews() {

        filterET = (EditText) findViewById(R.id.filter_edit_text);
        filterIV = (ImageView) findViewById(R.id.filter_image_view);

        repoRV = (RecyclerView)findViewById(R.id.repo_recycle_view);
        repoRV.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false));

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
    }

    private void setOnClickListeners(){
        filterET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(repoList != null && repoList.size()>0){
                    filterString = editable.toString();
                    filterRepoByName(filterString, filteredList);
                }
            }
        });

        filterIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gitGetter.setFilterCallBack(Home.this);
                startActivity(new Intent(Home.this, Filter.class));
                overridePendingTransition(R.anim.slide_in_left , R.anim.slide_out_right);
            }
        });
    }

    // filters


    private void filterRepoByName(String filterString, List<Repo> list){
        List<Repo> temp = new ArrayList();

        for(Repo repo: list){

            if(repo.getName().contains(filterString)){
                temp.add(repo);
            }
        }

        updateAdapter(temp);
    }

    private List<Repo> getFilteredList(List<Repo> list, HashMap<String, Object> filterMap){

        List<Repo> newList = new ArrayList<>();

            if(filterMap.size() > 0){

                for(Repo repo: list){
                    if(shouldIncluded(repo, filterMap)){
                        newList.add(repo);
                    }
                }
            }else {
                return list;
            }


        return newList;
    }

    private boolean shouldIncluded(Repo repo, HashMap<String, Object> filterMap){

        Iterator iterator = filterMap.keySet().iterator();

        while(iterator.hasNext()){

            String key = (String) iterator.next();

            if(key == gitGetter.getF_K_LANGUAGE()){

                if(!didPassLanguage(repo, (List<String>) filterMap.get(key),  0)){
                    return false;
                }
            }

            if(key == gitGetter.getF_K_WATCHERS()){

                if(repo.getWatchers() <= (int) filterMap.get(key)){
                    return false;
                }
            }

            if(key == gitGetter.getF_K_FORKS()){

                if(repo.getForks_count() <= (int) filterMap.get(key)){
                    return false;
                }
            }

            if(key == gitGetter.getF_K_SCORE()){

                if(repo.getScore() <= (double) filterMap.get(key)){
                    return false;
                }
            }

            if(key == gitGetter.getF_K_SIZE()){

                if(repo.getSize() > (int) filterMap.get(key)){
                    return false;
                }

            }

            if(key == gitGetter.getF_K_STARGRAZERS()){

                if(!didPassStartGrazer(repo, (List<StarGrazer>) filterMap.get(key), 0)){
                    return false;
                }

            }


        }

        return true;
    }

    private boolean didPassLanguage(Repo repo, List<String> list, int counter){

        if(list.size() == counter){
            return false;
        }else {
            if(repo.getLanguage().equalsIgnoreCase(list.get(counter))){
                return true;
            }
            return didPassLanguage(repo, list, (counter+1));
        }

    }

    private boolean didPassStartGrazer(Repo repo, List<StarGrazer> list, int counter){

        if(list.size() == counter){
            return false;
        }else {

            StarGrazer stargrazer = list.get(counter);

            if(repo.getStargazers_count() >= stargrazer.getMin()
                    && repo.getStargazers_count() <= stargrazer.getMax()){

                return true;
            }
            return didPassStartGrazer(repo, list, (counter+1));
        }

    }

    private void updateAdapter(List<Repo> list){

        adapter = new RepoAdapter(list, this, common, Home.this);
        repoRV.setAdapter(adapter);
    }

    // callbacks
    @Override
    public void onRepoClick(Repo repo) {
        GitGetterSingleton.getInstance().setSelectedRep(repo);
        startActivity(new Intent(Home.this, RepoDetails.class));
    }

    @Override
    public void onSuccess(String response) {

        JSONObject jsonObj = null;
        try {

            jsonObj = new JSONObject(response);
            JSONArray items = jsonObj.getJSONArray("items");
            repoList = common.getRepoListFromJson(items);
            Collections.sort(repoList, new RepoSorter());

            if(repoList.size() > 10){
                repoList = repoList.subList(0,10);
            }
            filteredList = getFilteredList(repoList, filterMap);
            updateAdapter(filteredList);

        } catch (JSONException e) {
            e.printStackTrace();
            common.showToast(getString(R.string.repo_not_found_toast_msg));
        }
        common.dismissProgress();
    }

    @Override
    public void onFailure(String response) {

        common.showToast(getString(R.string.repo_loading_fail_toast_msg));
        common.dismissProgress();
    }

    @Override
    public void updateFilters(HashMap<String, Object> map) {

         filterMap = map;
         filteredList = getFilteredList(repoList, map);

         if(filterET.getText().toString().length() != 0){
             filterRepoByName(filterString, filteredList);
         }else {
             updateAdapter(filteredList);
         }

    }

}

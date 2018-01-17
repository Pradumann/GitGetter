package com.mappr.gitgetter.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.mappr.gitgetter.Adapters.RepoAdapter;
import com.mappr.gitgetter.Global.Client;
import com.mappr.gitgetter.Global.CommonFunctions;
import com.mappr.gitgetter.Global.GitGetterSingleton;
import com.mappr.gitgetter.Global.RepoSorter;
import com.mappr.gitgetter.Interfaces.ClientCallBack;
import com.mappr.gitgetter.Pojos.Repo;
import com.mappr.gitgetter.R;
import com.mappr.gitgetter.Interfaces.RepoCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Home extends AppCompatActivity implements
        RepoCallBack,
        ClientCallBack {

    private RecyclerView repoRV;
    private List<Repo> repoList;
    private RepoAdapter adapter;
    private Client client;
    private CommonFunctions common;
    private final String BASE_URL = "https://api.github.com/";
    private EditText filterET;
    ProgressDialog progress;

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
    }

    private void setViews() {

        filterET = (EditText) findViewById(R.id.filter_edit_text);

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
                    filterList(editable.toString());
                }
            }
        });
    }

    private void filterList(String filterString){
        List<Repo> temp = new ArrayList();

        for(Repo repo: repoList){

            if(repo.getName().contains(filterString)){
                temp.add(repo);
            }
        }

        updateAdapter(temp);
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
                repoList = repoList.subList(0,9);
            }

            updateAdapter(repoList);

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

}

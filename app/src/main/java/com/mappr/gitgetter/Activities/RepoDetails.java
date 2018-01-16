package com.mappr.gitgetter.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mappr.gitgetter.Adapters.ContributorAdapter;
import com.mappr.gitgetter.Global.Client;
import com.mappr.gitgetter.Global.CommonFunctions;
import com.mappr.gitgetter.Global.GitGetterSingleton;
import com.mappr.gitgetter.Interfaces.ClientCallBack;
import com.mappr.gitgetter.Interfaces.ContributorCallBack;
import com.mappr.gitgetter.Pojos.Contributor;
import com.mappr.gitgetter.Pojos.Repo;
import com.mappr.gitgetter.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RepoDetails extends AppCompatActivity implements
        ClientCallBack,
        ContributorCallBack{

    private TextView
            repoNameTV,
            repoIdTV,
            repoDescriptionTV,
            projectLinkTV,
            repoFullNameTV;

    private RecyclerView contributorsRV;
    private ArrayList<Contributor> contributorsArrayList;

    private Client client;
    private CommonFunctions common;
    private GitGetterSingleton gitGetter;
    private Repo selectedRepo;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setInstances();
        setViews();
        setOnClickListeners();

        if(common.isNetworkAvailable()){
            getContributorsFromGit();
        }else {
            common.showToast(getString(R.string.fail_to_load_contributor_list_toast_msg));
        }
    }

    private void setInstances(){
        client = new Client(this, this);
        gitGetter = GitGetterSingleton.getInstance();
        selectedRepo = gitGetter.getSelectedRep();
        common = new CommonFunctions(this);
    }

    private void setViews(){
        repoNameTV = (TextView) findViewById(R.id.repo_name_tv);
        repoIdTV = (TextView) findViewById(R.id.repo_id_tv);
        repoDescriptionTV = (TextView)findViewById(R.id.repo_description_tv);
        projectLinkTV = (TextView) findViewById(R.id.project_link_tv);
        repoFullNameTV = (TextView) findViewById(R.id.repo_full_name_tv);

        contributorsRV = (RecyclerView) findViewById(R.id.contributors_recycle_view);
        contributorsRV.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false));

        repoNameTV.setText(selectedRepo.getName());
        repoIdTV.setText(String.valueOf(selectedRepo.getId()));
        repoDescriptionTV.setText(selectedRepo.getDescription());
        repoFullNameTV.setText(selectedRepo.getFull_name());
        String text = "<a href=\""+ selectedRepo.getHtml_url() + "\">" + selectedRepo.getHtml_url() + "</a>";
        projectLinkTV.setText(Html.fromHtml(text));
        projectLinkTV.setClickable(true);

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
    }

    private void updateAdapter(){
        ContributorAdapter adapter = new ContributorAdapter(contributorsArrayList, this, RepoDetails.this);
        contributorsRV.setAdapter(adapter);
    }

    private void setOnClickListeners(){
        projectLinkTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(common.isNetworkAvailable()){
                    Intent intent = new Intent(RepoDetails.this, WebDetails.class);
                    intent.putExtra(gitGetter.getURL_KEY(), selectedRepo.getHtml_url());
                    startActivity(intent);
                }else {
                    common.showToast(getString(R.string.fail_to_open_link_toast_message));
                }
            }
        });
    }

    private void getContributorsFromGit(){
        common.showProgress(getString(R.string.loading_contributor_dialog_title),
                getString(R.string.loading_contributor_dialog_msg));
        String url = selectedRepo.getContributors_url();
        client.get(url);
    }

    private ArrayList<Contributor> getContributorArrayList(String jsonString){

        try {

            JSONArray array = new JSONArray(jsonString);
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Contributor>>(){}.getType();
            return gson.fromJson(String.valueOf(array), type);

        } catch (JSONException e) {
            e.printStackTrace();
            common.showToast(getString(R.string.contributor_not_found_toats_msg));
        }

        return new ArrayList<>();
    }


    // callbacks

    @Override
    public void onClick(Contributor contributor) {
        gitGetter.setSelectedContributor(contributor);
        startActivity(new Intent(RepoDetails.this, ContributorDetails.class));
    }

    @Override
    public void onSuccess(String response) {

        contributorsArrayList = getContributorArrayList(response);
        updateAdapter();
        common.dismissProgress();
    }

    @Override
    public void onFailure(String response) {

        common.dismissProgress();
        common.showToast(getString(R.string.contributor_loading_fail_toast_msg));
    }
}

package com.mappr.gitgetter.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mappr.gitgetter.Adapters.ContributorAdapter;
import com.mappr.gitgetter.Adapters.ContributorRepoAdapter;
import com.mappr.gitgetter.Adapters.RepoAdapter;
import com.mappr.gitgetter.Global.Client;
import com.mappr.gitgetter.Global.CommonFunctions;
import com.mappr.gitgetter.Global.GitGetterSingleton;
import com.mappr.gitgetter.Global.RepoSorter;
import com.mappr.gitgetter.Interfaces.ClientCallBack;
import com.mappr.gitgetter.Interfaces.RepoCallBack;
import com.mappr.gitgetter.Pojos.Contributor;
import com.mappr.gitgetter.Pojos.Repo;
import com.mappr.gitgetter.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;


public class ContributorDetails extends AppCompatActivity implements
        ClientCallBack,
        RepoCallBack{

    private TextView
            usernameTV,
            idTV,
            profileLinkTV;

    private ProgressDialog progress;

    private ImageView avatarIV;
    private RecyclerView contributorRV;
    private Contributor selectedContributor;

    private ArrayList<Repo> repoArrayList;

    private Client client;
    private GitGetterSingleton gitGetter;
    private CommonFunctions common;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributor_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setInstances();
        setViews();
        setOnClickListeners();

        if(common.isNetworkAvailable()){
            getRepoListFromGit();
        }else {
            common.showToast(getString(R.string.no_internet_fail_to_load_repo_list));
        }
    }

    private void setInstances(){
        client = new Client(this, this);
        gitGetter = GitGetterSingleton.getInstance();
        selectedContributor = gitGetter.getSelectedContributor();
        common = new CommonFunctions(this);
    }

    private void setViews(){
        usernameTV = (TextView) findViewById(R.id.contributor_user_name_tv);
        idTV = (TextView) findViewById(R.id.contributor_id_tv);
        avatarIV = (ImageView) findViewById(R.id.avatar_iv);
        profileLinkTV = (TextView) findViewById(R.id.contributor_profile_link_tv);
        contributorRV = (RecyclerView) findViewById(R.id.contributor_repo_list_recycle_view);
        contributorRV.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false));


        usernameTV.setText(selectedContributor.getLogin());
        idTV.setText(String.valueOf(selectedContributor.getId()));
        String text = "<a href=\""+ selectedContributor.getHtml_url() + "\">" + selectedContributor.getHtml_url() + "</a>";
        profileLinkTV.setText(Html.fromHtml(text));
        profileLinkTV.setClickable(true);

        progress = new ProgressDialog(this);
        progress.setCancelable(false);

        if(common.isNetworkAvailable()){
            Glide.with(ContributorDetails.this).load(selectedContributor.getAvatar_url()).placeholder(R.drawable.placeholder).into(avatarIV);
        }
    }

    private void setOnClickListeners(){
        profileLinkTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(common.isNetworkAvailable()){
                    Intent intent = new Intent(ContributorDetails.this, WebDetails.class);
                    intent.putExtra(gitGetter.getURL_KEY(), selectedContributor.getHtml_url());
                    startActivity(intent);
                }else {
                    common.showToast(getString(R.string.fail_to_open_link_toast_message));
                }
            }
        });
    }

    private void updateAdapter(){
        Collections.sort(repoArrayList, new RepoSorter());
        ContributorRepoAdapter adapter = new ContributorRepoAdapter(repoArrayList, this, ContributorDetails.this);
        contributorRV.setAdapter(adapter);
    }

    private void getRepoListFromGit(){
        common.showProgress(
                getString(R.string.repo_list_loading_dialog_title),
                getString(R.string.repo_list_loading_dialog_msg));
        String url = selectedContributor.getRepos_url();
        client.get(url);
    }

    @Override
    public void onSuccess(String response) {

        JSONArray array = null;
        try {

            array = new JSONArray(response);
            repoArrayList = common.getRepoListFromJson(array);
            updateAdapter();

        } catch (JSONException e) {
            e.printStackTrace();
            common.showToast(getString(R.string.repo_not_found_toast_msg));
        }

        common.dismissProgress();

    }

    @Override
    public void onFailure(String response) {
        common.dismissProgress();
        common.showToast(getString(R.string.repo_loading_fail_toast_msg));
    }

    @Override
    public void onRepoClick(Repo repo) {
        GitGetterSingleton.getInstance().setSelectedRep(repo);
        startActivity(new Intent(ContributorDetails.this, RepoDetails.class));
    }

}

package com.mappr.gitgetter.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mappr.gitgetter.Global.CommonFunctions;
import com.mappr.gitgetter.Interfaces.RepoCallBack;
import com.mappr.gitgetter.Pojos.Repo;
import com.mappr.gitgetter.R;

import java.util.List;

/**
 * Created by pradumanpraduman on 16/01/18.
 */

public class ContributorRepoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

private List<Repo> repoList;
private RepoCallBack callBack;
private CommonFunctions common;
private Context context;

public ContributorRepoAdapter(List<Repo> repoList, RepoCallBack callBack, Context context){

        this.repoList = repoList;
        this.callBack = callBack;
        this.common = new CommonFunctions(context);
        this.context = context;
        }

private class RepoViewHolder extends RecyclerView.ViewHolder {

    private TextView
            repoNameTV;

    private CardView cardView;

    public RepoViewHolder(View itemView) {
        super(itemView);
        repoNameTV = (TextView) itemView.findViewById(R.id.contributor_repo_list_item_title_tv);
        cardView = (CardView) itemView.findViewById(R.id.contributor_repo_list_card_view);
        cardView.setCardBackgroundColor(context.getResources().getColor(common.getColor()));
    }

    public void bindData(final int position) {

        Repo repo = repoList.get(position);

        repoNameTV.setText(repo.getName());
        itemView.setOnClickListener(new OnRepoClickListener(position ));
    }
}

private class OnRepoClickListener implements View.OnClickListener{

    private int position;

    public OnRepoClickListener(int position){

        this.position = position;
    }

    @Override
    public void onClick(View v) {

        callBack.onRepoClick(repoList.get(position));
    }
}

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.contributro_repo_list_item, parent, false);
        return new RepoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((RepoViewHolder)holder).bindData(position);
    }


    @Override
    public int getItemCount() {
        return repoList.size();
    }
}
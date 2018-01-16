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
import com.mappr.gitgetter.Pojos.Repo;
import com.mappr.gitgetter.R;
import com.mappr.gitgetter.Interfaces.RepoCallBack;

import java.util.List;

/**
 * Created by pradumanpraduman on 14/01/18.
 */

public class RepoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Repo> repoList;
    private RepoCallBack callBack;
    private CommonFunctions common;
    private Context context;

    public RepoAdapter(List<Repo> repoList, RepoCallBack callBack, CommonFunctions common, Context context){

        this.repoList = repoList;
        this.callBack = callBack;
        this.common = common;
        this.context = context;
    }

    private class RepoViewHolder extends RecyclerView.ViewHolder {

        private TextView
                repoNameTV,
                watchersTV,
                sizeTV,
                scoreTV;

        private ImageView avatarIV;

        private CardView cardView;

        public RepoViewHolder(View itemView) {
            super(itemView);
            repoNameTV = (TextView) itemView.findViewById(R.id.list_item_title_tv);
            watchersTV = (TextView) itemView.findViewById(R.id.list_item_watchers_tv);
            sizeTV = (TextView) itemView.findViewById(R.id.list_item_size_tv);
            scoreTV = (TextView) itemView.findViewById(R.id.list_item_score_tv);
            cardView = (CardView) itemView.findViewById(R.id.repo_list_card_view);
            avatarIV = (ImageView) itemView.findViewById(R.id.repo_avatar_image_view);
            cardView.setCardBackgroundColor(context.getResources().getColor(common.getColor()));
        }

        public void bindData(final int position) {

            Repo repo = repoList.get(position);

            repoNameTV.setText(repo.getName());
            watchersTV.setText(String.valueOf(repo.getWatchers()));
            sizeTV.setText(String.valueOf(repo.getSize()));
            scoreTV.setText(String.valueOf(repo.getScore()));
            itemView.setOnClickListener(new OnRepoClickListener(position ));
            Glide.with(context).load(repo.getOwner().getAvatar_url()).placeholder(R.drawable.placeholder).into(avatarIV);
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
                .inflate(R.layout.repo_list_item, parent, false);
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
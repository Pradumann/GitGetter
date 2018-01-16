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
import com.mappr.gitgetter.Interfaces.ContributorCallBack;
import com.mappr.gitgetter.Pojos.Contributor;
import com.mappr.gitgetter.R;

import java.util.ArrayList;

/**
 * Created by pradumanpraduman on 15/01/18.
 */

public class ContributorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Contributor> contributorsArrayList;
    private ContributorCallBack callBack;
    private Context context;
    private CommonFunctions common;
    public ContributorAdapter(ArrayList<Contributor> repoArrayList,  ContributorCallBack callBack,Context context){

        this.contributorsArrayList = repoArrayList;
        this.callBack = callBack;
        this.context = context;
        this.common = new CommonFunctions(context);
    }

    private class RepoViewHolder extends RecyclerView.ViewHolder {

        private TextView
                contributorNameTV,
                contributionsTV;

        private CardView cardView;

        private ImageView contributorAvatarIV;

        public RepoViewHolder(View itemView) {
            super(itemView);
            contributorNameTV = (TextView) itemView.findViewById(R.id.contributor_list_item_title_tv);
            contributionsTV = (TextView) itemView.findViewById(R.id.list_item_contributions_tv);
            contributorAvatarIV  = (ImageView) itemView.findViewById(R.id.contributor_avatar_image_view);
            cardView = (CardView) itemView.findViewById(R.id.contributor_list_card_view);
            cardView.setCardBackgroundColor(context.getResources().getColor(common.getColor()));

        }

        public void bindData(final int position) {

            Contributor contributor  = contributorsArrayList.get(position);
            contributorNameTV.setText(contributor.getLogin());
            contributionsTV.setText(String.valueOf(contributor.getContributions()));
            Glide.with(context).load(contributor.getAvatar_url()).placeholder(R.drawable.placeholder).into(contributorAvatarIV);
            itemView.setOnClickListener(new OnContributorClickListener(position));
        }
    }

    private class OnContributorClickListener implements View.OnClickListener{


        private int position;

        public OnContributorClickListener(int position){

            this.position = position;
        }

        @Override
        public void onClick(View v) {

            callBack.onClick(contributorsArrayList.get(position));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.contributor_list_item, parent, false);
        return new RepoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((RepoViewHolder)holder).bindData(position);
    }


    @Override
    public int getItemCount() {
        return contributorsArrayList.size();
    }
}

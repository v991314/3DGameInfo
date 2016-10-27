package com.gavin.your3dmgame.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gavin.your3dmgame.R;
import com.gavin.your3dmgame.entity.Comment_item;
import com.gavin.your3dmgame.utils.DateUtils;

import java.util.List;

/**
 * Created by GaVin on 2016/10/11 0011.
 */

public class Comment_adapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<Comment_item.DescriptionBean.DataBean> mData;

    public Comment_adapter(Context context, List<Comment_item.DescriptionBean.DataBean> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_comment, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.tv_username.setText(mData.get(position).getUsername());
        viewHolder.tv_date.setText(DateUtils.dateFromat(mData.get(position).getDtime()));
        viewHolder.tv_floor.setText(mData.get(position).getFloor());
        viewHolder.tv_comment.setText(mData.get(position).getMsg());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_username, tv_date, tv_floor, tv_comment;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_username = (TextView) itemView.findViewById(R.id.tv_username);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_floor = (TextView) itemView.findViewById(R.id.tv_floor);
            tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
        }
    }

    ;
}

package com.gavin.your3dmgame.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gavin.your3dmgame.R;
import com.gavin.your3dmgame.entity.GameListItem;
import com.gavin.your3dmgame.utils.API;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by GaVin on 2016/10/12 0012.
 */

public class Game_adapter extends RecyclerView.Adapter implements View.OnClickListener {
    private final Context mContext;
    private final List<GameListItem> mList;

    public Game_adapter(Context context, List<GameListItem> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_game, parent, false);
        //设置点击事件
        view.setOnClickListener(this);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GameViewHolder viewHolder = (GameViewHolder) holder;
        viewHolder.itemView.setTag(position);
        viewHolder.tv_game_name.setText(mList.get(position).getTitle());
        viewHolder.tv_game_type.setText(mList.get(position).getTypename());
        //获取到图片地址
        String litpic = mList.get(position).getLitpic();
        if (litpic == null) {
            viewHolder.ic_game_icon.setImageResource(R.drawable.product_default);
            return;
        }
        //地址拼接
        String imageUrl = API.DMGEAME_URL + litpic;
        Picasso.with(mContext).load(imageUrl).error(R.drawable.product_default).into(viewHolder.ic_game_icon);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick((Integer) v.getTag(), v);
        }
    }

    public class GameViewHolder extends RecyclerView.ViewHolder {
        private ImageView ic_game_icon;
        private TextView tv_game_name, tv_game_type;

        public GameViewHolder(View itemView) {
            super(itemView);
            ic_game_icon=(ImageView)itemView.findViewById(R.id.ic_game_icon);
            tv_game_name=(TextView)itemView.findViewById(R.id.tv_game_name);
            tv_game_type=(TextView)itemView.findViewById(R.id.tv_game_type);
        }
    }
    public  interface  OnItemClickListener{
        void onItemClick(int position, View view);
    }
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

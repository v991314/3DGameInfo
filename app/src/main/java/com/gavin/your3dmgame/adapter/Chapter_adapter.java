package com.gavin.your3dmgame.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gavin.your3dmgame.R;
import com.gavin.your3dmgame.entity.chapter_item;
import com.gavin.your3dmgame.utils.API;
import com.gavin.your3dmgame.utils.DateUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by GaVin on 2016/10/9 0009.
 */

public class Chapter_adapter extends RecyclerView.Adapter  {
    private static final int TYPE_FOOTER = 2;
    private static final int TYPE_NORMAL = 1;
    private Context mContext;
    private List<chapter_item> mData;

    public Chapter_adapter(Context context, List<chapter_item> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
            view = LayoutInflater.from(mContext).inflate(R.layout.item_chapter, parent, false);
//            //设置点击事件
//            view.setOnClickListener(this);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder vh = (MyViewHolder) holder;
//          vh.itemView.setTag(position);
            TextView comment_textView = vh.comment_TextView;
            TextView date_textView = vh.date_TextView;
            TextView title_textView = vh.title_TextView;
            ImageView pic_imageView = vh.pic_ImageView;

                comment_textView.setText(mData.get(position).getFeedback());
                date_textView.setText(DateUtils.dateFromat(mData.get(position).getSenddate()));
                title_textView.setText(mData.get(position).getTitle());
                //获取到图片地址
                String litpic = mData.get(position).getLitpic();
                Log.e("Chapter_adapter_banner", litpic);
                if (litpic == null) {
                    pic_imageView.setImageResource(R.drawable.product_default);
                    return;
                }
                //地址拼接
                String imageUrl = API.DMGEAME_URL + litpic;
                Picasso.with(mContext).load(imageUrl).error(R.drawable.product_default).into(pic_imageView);



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



   /* @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick((Integer) v.getTag(), v);
        }
    }*/

    class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView pic_ImageView;
        public TextView title_TextView, date_TextView, comment_TextView;
        public MyViewHolder(View itemView) {
            super(itemView);

                pic_ImageView = (ImageView) itemView.findViewById(R.id.pic_ImageView);
                title_TextView = (TextView) itemView.findViewById(R.id.title_TextView);
                date_TextView = (TextView) itemView.findViewById(R.id.date_TextView);
                comment_TextView = (TextView) itemView.findViewById(R.id.comment_TextView);

        }
    }

    /*public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }*/
}

package com.gavin.your3dmgame.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.gavin.your3dmgame.R;
import com.gavin.your3dmgame.entity.chapter_item;
import com.gavin.your3dmgame.utils.API;
import com.gavin.your3dmgame.utils.DateUtils;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by GaVin on 2016/10/9 0009.
 */

public class Chapter_adapter_banner extends RecyclerView.Adapter  {
    private Context mContext;
    private ArrayList<Integer> mLocalImages;
    private List<chapter_item> mData;
    private static final int TYPE_BANNER = 1;
    private static final int TYPE_NORMAL = 2;


    public Chapter_adapter_banner(Context context, ArrayList<Integer> localImages, List<chapter_item> data) {
        mContext = context;
        mLocalImages = localImages;
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == TYPE_NORMAL) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_chapter, parent, false);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_banner, parent, false);
        }
        //先缩小view
        ViewHelper.setScaleX(view, 0.9f);
        ViewHelper.setScaleY(view, 0.9f);
        return new MyViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        if (getItemViewType(position) == TYPE_NORMAL) {
//            myViewHolder.itemView.setTag(position);
            setTypeNomal(myViewHolder, position);
        } else {
            setTypeBanner(myViewHolder);
        }

    }

    private void setTypeBanner(MyViewHolder myViewHolder) {
        //以属性动画的形式放大
        ViewPropertyAnimator.animate(myViewHolder.itemView).scaleX(1).setDuration(350).start();
        ViewPropertyAnimator.animate(myViewHolder.itemView).scaleY(1).setDuration(350).start();
        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        myViewHolder.mConvenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, mLocalImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.page_switch_normal, R.drawable.page_switch_checked})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
    }


    public class LocalImageHolderView implements Holder<Integer> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, Integer data) {
            imageView.setImageResource(data);
        }
    }

    private void setTypeNomal(MyViewHolder myViewHolder, int position) {
//        Random r = new Random();
//        int colr = Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256));
//        myViewHolder.mCardView.setCardBackgroundColor(colr);
        //以属性动画的形式放大
        ViewPropertyAnimator.animate(myViewHolder.itemView).scaleX(1).setDuration(350).start();
        ViewPropertyAnimator.animate(myViewHolder.itemView).scaleY(1).setDuration(350).start();
        TextView comment_textView = myViewHolder.comment_TextView;
        TextView date_textView = myViewHolder.date_TextView;
        TextView title_textView = myViewHolder.title_TextView;
        ImageView pic_imageView = myViewHolder.pic_ImageView;
        comment_textView.setText(mData.get(position - 1).getFeedback());
        date_textView.setText(DateUtils.dateFromat(mData.get(position - 1).getSenddate()));
        title_textView.setText(mData.get(position - 1).getTitle());
        //获取到图片地址
        String litpic = mData.get(position - 1).getLitpic();
        if (litpic == null) {
            pic_imageView.setImageResource(R.drawable.product_default);
            return;
        }
        //地址拼接
        String imageUrl = API.DMGEAME_URL + litpic;
        Picasso.with(mContext).load(imageUrl).error(R.drawable.product_default).into(pic_imageView);

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BANNER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public ConvenientBanner mConvenientBanner;
        public ImageView pic_ImageView;
        public TextView title_TextView, date_TextView, comment_TextView;

        public MyViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == TYPE_NORMAL) {
                pic_ImageView = (ImageView) itemView.findViewById(R.id.pic_ImageView);
                title_TextView = (TextView) itemView.findViewById(R.id.title_TextView);
                date_TextView = (TextView) itemView.findViewById(R.id.date_TextView);
                comment_TextView = (TextView) itemView.findViewById(R.id.comment_TextView);
            } else {
                mConvenientBanner = (ConvenientBanner) itemView.findViewById(R.id.convenientBanner);
                if (onItemClickListener != null) {
                    onItemClickListener.onFindBannerFinshed(mConvenientBanner);
                }
            }
        }
    }

    public interface OnItemClickListener {
        void onFindBannerFinshed(ConvenientBanner convenientBanner);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnFindBannerFinshedListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

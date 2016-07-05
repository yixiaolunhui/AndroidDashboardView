package com.ihat.pihat.circleprogressviewdemo1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;
import com.ihat.pihat.circleprogress.CircleProgress;

import java.util.List;

/**
 * Project: CircleProgressViewDemo1
 * Package: com.ihat.pihat.circleprogressviewdemo1
 * Author: Guoger
 * Mail: 505917699@qq.com
 * Created time: 2016/4/17/16 20:44.
 */
public class FinanceAdapter extends RecyclerView.Adapter<FinanceAdapter.FinanceViewHolder>{
    private Context mContext;
    private List<FinanceBean> beanList;

    public FinanceAdapter(Context mContext, List<FinanceBean> beanList) {
        this.mContext = mContext;
        this.beanList = beanList;
    }

    @Override
    public FinanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_finance, parent, false);
        return new FinanceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FinanceViewHolder holder, int position) {
        holder.tvName.setText(beanList.get(position).getName());
        int value = (int) (1 + Math.random() * (100 - 1 + 1));
        holder.cpv.setAnimDuration(2000);
        holder.cpv.setInterpolator(new AccelerateDecelerateInterpolator());
        holder.cpv.setSweepValue(value);
        holder.cpv.setValueText(beanList.get(position).getNum() + "万");
        holder.cpv.anim();
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    class FinanceViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        CircleProgress cpv;
        public FinanceViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_finance_name);
            cpv = (CircleProgress) itemView.findViewById(R.id.cpv_finance);
        }
    }
}

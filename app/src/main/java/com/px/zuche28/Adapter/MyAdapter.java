package com.px.zuche28.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.px.zuche28.Model.ReservationFormCustom;
import com.px.zuche28.R;
import com.px.zuche28.DateTimeActivity;
import com.px.zuche28.Utils.Tools;
import com.px.zuche28.global.GlobalConstants;

import org.xutils.x;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {



    public void setmList(List<ReservationFormCustom> mList) {
        this.mList = mList;
    }

    //数据源
    private List<ReservationFormCustom> mList ;
    private Context context;

    public MyAdapter(List<ReservationFormCustom> list, Context context) {
        mList = list;
        this.context = context;
    }

    //返回item个数
    @Override
    public int getItemCount() {
        System.out.println(mList.size()+"Myadadad");
        return mList.size();

    }

    //创建ViewHolder,引入xml传送给viewholder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //填充视图,操作item的地方
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        x.image().bind(holder.mView, GlobalConstants.IMAGE_URL+mList.get(position).getHolderId()+"?s=256&d=retro");
        holder.textView6.setText(mList.get(position).getUserName());
        holder.chexing.setText(mList.get(position).getModelName());
        holder.chepai.setText(mList.get(position).getLicensePlate());
        holder.freeStartTime.setText(Tools.getStringDate(mList.get(position).getFreeStartTime()));
        holder.freeEndTime.setText(Tools.getStringDate(mList.get(position).getFreeEndTime()));
        holder.button3.setText("预约");

        holder.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DateTimeActivity.class);
                intent.putExtra("ReservationFormId",mList.get(position).getReservationFormId());
//                intent.putExtra("ReservationId",mList.get(position).getReservationId());
                context.startActivity(intent);
            }
        });
    }


    //初始化控件
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mView;
        TextView textView6;
        TextView freeStartTime;
        TextView freeEndTime;
        TextView chexing;
        TextView chepai;
        Button button3;

        ViewHolder(View itemView) {
            super(itemView);
            mView = itemView.findViewById(R.id.iv_icon);
            textView6 = itemView.findViewById(R.id.tv_title);
            chexing = itemView.findViewById(R.id.chexing);
            chepai = itemView.findViewById(R.id.chepai);
            freeStartTime = itemView.findViewById(R.id.freeStartTime);
            freeEndTime = itemView.findViewById(R.id.freeEndTime);
            button3 = itemView.findViewById(R.id.yuyue);
        }
    }
}

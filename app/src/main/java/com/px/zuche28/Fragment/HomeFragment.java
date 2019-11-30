package com.px.zuche28.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.px.zuche28.Adapter.MyAdapter;
import com.px.zuche28.MainActivity;
import com.px.zuche28.Model.Message;
import com.px.zuche28.Model.ReservationForm;
import com.px.zuche28.Model.ReservationFormCustom;
import com.px.zuche28.R;
import com.px.zuche28.global.GlobalConstants;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MyAdapter mMyAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<ReservationFormCustom> data;
    private RefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_all_page, container, false);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        initData();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getDate();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });


        mRecyclerView = view.findViewById(R.id.recyclerView);
        mMyAdapter = new MyAdapter(data, getActivity());
        getDate();
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mMyAdapter);

        return view; //设置RecycleView
    }

    public void getDate() {
        RequestParams params = new RequestParams(GlobalConstants.READFREEORDER_URL);
        x.http().get(params, new Callback.CacheCallback<Message<List<ReservationFormCustom>>>() {

            @Override
            public boolean onCache(Message<List<ReservationFormCustom>> result) {
                return false;
            }

            @Override
            public void onSuccess(Message<List<ReservationFormCustom>> result) {
                if (result.isSuccess()) {
                    data = result.getData();
                    refreshLayout.finishRefresh(2000);
                    mMyAdapter.setmList(data);
                    mMyAdapter.notifyDataSetChanged();
//                    System.out.println(data.size()+"===============================");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

        });
    }

    private void initData() {
        data = new ArrayList<>();
    }
}

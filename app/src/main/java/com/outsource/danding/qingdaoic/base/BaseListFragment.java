package com.outsource.danding.qingdaoic.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.outsource.danding.qingdaoic.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseListFragment<T> extends BaseFragment implements FNAdapter.ViewProvider {
    @BindView(R.id.recycler_view)
    public RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    protected List<T> mList;
    protected FNAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    protected int mPage, mCount = 5;
    protected int maxId, minId = 0;
    private boolean isViewCreated = false;
    private boolean isFirst = true;

    public BaseListFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!enableLazyLoad()) return;
        if (isViewCreated) lazyLoad();
    }

    public void lazyLoad() {
        //只有第一次进入才进行懒加载
        if (isFirst) {
            loadData();
            isFirst = false;
        }
    }

    //是否开启懒加载
    public abstract boolean enableLazyLoad();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewCreated = true;
        initView();
    }

    //刷新控件开关
    public void enableRefresh(boolean status) {
        //禁止下拉刷新
        swipeRefreshLayout.setEnabled(status);
    }

    //是否显示refreshBar
    public void showRefreshBar(boolean isShow) {
        swipeRefreshLayout.setRefreshing(isShow);
    }

    public void setListAdapter() {
        if (getActivity() == null) return;

        if (mAdapter == null) {
            initAdapter();
            mRecyclerView.setAdapter(mAdapter);
        } else
            notifyDataSetChanged();

        if (swipeRefreshLayout.isRefreshing())
            showRefreshBar(false);

        mAdapter.setLoadMoreStatus(false);
    }

    public abstract void loadData();

    @Override
    public void loadMore() {
        mPage++;
        loadData();
    }

    private void initView() {
        mList = new ArrayList<>();
        showRefreshBar(true);
        initAdapter();
        mLayoutManager = onCreateLayoutManager();
        if (mLayoutManager == null)
            mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                BaseListFragment.this.onRefresh();
            }
        });
        afterCreateView();
        if (!enableLazyLoad()) loadData();
    }

    public void afterCreateView() {

    }

    public void onRefresh() {
        maxId = 0;
        minId = 0;
        mPage = 0;
        mList.clear();
        notifyDataSetChanged();
        loadData();
    }

    public void initAdapter() {
        mAdapter = new FNAdapter<T>(getContext(), mList);
        mAdapter.setViewProvider(this);
    }

    public void moveToPosition(int n) {
        if (mLayoutManager == null) return;
        if (mLayoutManager instanceof LinearLayoutManager) {
            int firstItem = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
            int lastItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
            if (n <= firstItem) {
                mRecyclerView.scrollToPosition(n);
            } else if (n <= lastItem) {
                int top = mRecyclerView.getChildAt(n - firstItem).getTop();
                mRecyclerView.scrollBy(0, top);
            } else {
                mRecyclerView.scrollToPosition(n);
            }
        }
    }


    public void moveToTop() {
        if (mList.isEmpty()) return;
        mRecyclerView.scrollToPosition(0);
    }


    public void enableLoadMore(boolean isLoadMore) {
        mAdapter.enableLoadMore(isLoadMore);
    }

    public RecyclerView.LayoutManager onCreateLayoutManager() {
        return null;
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mLayoutManager = manager;
    }

    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    public void notifyItemChanged(int position) {
        mAdapter.notifyItemChanged(position);
    }

    public void notifyItemRangeChanged(int positionStart, int itemCount) {
        mAdapter.notifyItemRangeChanged(positionStart, itemCount);
    }


    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

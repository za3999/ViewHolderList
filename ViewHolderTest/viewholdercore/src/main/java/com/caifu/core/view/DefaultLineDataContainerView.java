package com.caifu.core.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.caifu.core.R;
import com.caifu.core.adapter.BaseLineDataAdapter;
import com.caifu.core.linedata.ILineData;
import com.caifu.core.util.NetWorkUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * ListView功能的封装View
 * 使用该View解除数据和List与adapter的耦合关系，封装上拉和下拉刷新以及网络请求错误的相关逻辑，让使用者只需要关注数据和相应的Holder呈现
 * Created by zhengcaifu on 2016/9/22.
 * mail：zhengcaifu@bravowhale.com
 */
public class DefaultLineDataContainerView extends RelativeLayout implements SwipeRefreshLayout.OnRefreshListener {

    private BaseLineDataAdapter dataAdapter;
    private ListView listView;
    private EmptyView emptyView;
    private SwipeRefreshLayout refreshLayout;
    private LoadDataListener loadDataListener;
    private volatile boolean isDataFinished = false;
    private volatile boolean isLoading = false;
    private volatile boolean scrollLoadingEnable = false;
    private View footerView;
    private int pageSize = 10;
    private int pageNow = 0;
    private IMergeStrategy mergeStrategy = new DefaultMergeStrategy();

    public DefaultLineDataContainerView(Context context) {
        super(context);
        init(context);
    }

    public DefaultLineDataContainerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DefaultLineDataContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.defult_list_layout, this, true);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        listView = (ListView) view.findViewById(R.id.list_view);
        emptyView = (EmptyView) view.findViewById(R.id.empty_view);
        emptyView.setOnClickListener(v -> {
            loadDataListener.refreshData(pageSize);

        });
        footerView = inflater.inflate(R.layout.load_more_view, null);
        listView.addFooterView(footerView);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_list);
        refreshLayout.setColorSchemeResources(R.color.red_c33146, R.color.red_brick,
                R.color.red_brick, R.color.red_db3850);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setEnabled(false);
        dataAdapter = new BaseLineDataAdapter(getContext());
        listView.setAdapter(dataAdapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (scrollLoadingEnable) {
                    int endVisible = firstVisibleItem + visibleItemCount;
                    if (endVisible < totalItemCount || isLoading) {
                        return;
                    }

                    if (!isDataFinished) {
                        loadData();
                    }
                }
            }
        });
    }

    /**
     * 设置合并数据的策略
     *
     * @param mergeStrategy 合并策略
     */
    public void setMergeStrategy(IMergeStrategy mergeStrategy) {
        this.mergeStrategy = mergeStrategy;
    }

    /**
     * 设置是否支持下拉刷新
     *
     * @param refLoadingEnable
     */
    public void setRefLoadingEnable(boolean refLoadingEnable) {
        refreshLayout.setEnabled(refLoadingEnable);
    }

    /**
     * 设置是否支持滚动加载更多
     *
     * @param scrollLoadingEnable
     */
    public void setScrollLoadingEnable(boolean scrollLoadingEnable) {
        this.scrollLoadingEnable = scrollLoadingEnable;
        if (scrollLoadingEnable && !isDataFinished) {
            footerView.setVisibility(View.VISIBLE);
        } else {
            footerView.setVisibility(View.GONE);
        }
    }

    /**
     * 每一次请求加载的数据条数
     *
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    /**
     * 为ListView添加顶部View
     *
     * @param view
     */
    public void addListViewHeader(View view) {
        listView.addHeaderView(view);
    }

    /**
     * 移除ListView的Header
     *
     * @param view
     */
    public void removeListViewHeader(View view) {
        listView.removeHeaderView(view);
    }


    @Override
    public void onRefresh() {
        if (NetWorkUtil.isNetworkConnected(getContext())) {
            isLoading = true;
            loadDataListener.refreshData(pageSize);
            refreshLayout.setRefreshing(false);
        }
    }

    /**
     * 设置加载数据的Listener
     *
     * @param loadDataListener
     */
    public void setLoadDataListener(LoadDataListener loadDataListener) {
        this.loadDataListener = loadDataListener;
    }

    /**
     * 初始化数据
     */
    public void initData() {
        if (!isLoading) {
            emptyView.setVisibility(View.VISIBLE);
            if (NetWorkUtil.isNetworkConnected(getContext())) {
                isLoading = true;
                emptyView.setViewType(EmptyView.NETWORK_LOADING);
                loadDataListener.refreshData(pageSize);
            } else {
                emptyView.setViewType(EmptyView.NETWORK_ERROR);
            }
        }
    }

    /**
     * 设置数据
     *
     * @param dataList 设置到adapter的data中的数据
     * @param finish   数据是否全部加载完成
     */
    public void setData(List<ILineData> dataList, boolean finish) {
        pageNow = 0;
        if (dataList != null && dataList.size() > 0) {
            dataAdapter.setData(dataList);
            dataAdapter.notifyDataSetChanged();
            isDataFinished = finish;
            emptyView.setVisibility(View.GONE);
            pageNow++;
        } else {
            emptyView.setVisibility(View.VISIBLE);
            emptyView.setViewType(EmptyView.NO_DATA);
        }
        ((TextView) footerView.findViewById(R.id.tv_more)).setText(R.string.ms_load_more);
        if (!scrollLoadingEnable || isDataFinished) {
            listView.removeFooterView(footerView);
        }
        isLoading = false;
    }

    /**
     * 添加数据
     *
     * @param dataList 添加到adapter的data中的数据
     * @param finish   数据是否全部加载完成
     */
    public void addData(List<ILineData> dataList, boolean finish) {
        if (dataList != null && dataList.size() > 0) {
            pageNow++;
            if (dataAdapter.getLinesData().size() > 0) {
                dataAdapter.setData(mergeStrategy.merge(dataList, dataAdapter.getLinesData()));
            } else {
                dataAdapter.setData(dataList);
            }
            dataAdapter.notifyDataSetChanged();
            ((TextView) footerView.findViewById(R.id.tv_more)).setText(R.string.ms_load_more);
        } else {
            ((TextView) footerView.findViewById(R.id.tv_more)).setText(R.string.ms_load_more_error);
        }
        isDataFinished = finish;
        if (isDataFinished) {
            listView.removeFooterView(footerView);
        }
        isLoading = false;
    }

    /**
     * 加载数据
     */
    private void loadData() {
        isLoading = true;
        loadDataListener.loadMoreData(pageNow, pageSize);
    }

    /**
     * 获取加载数据的起始位置
     *
     * @return
     */
    protected int getLoadingStartIndex() {
        int start = 0;
        for (ILineData lineData : dataAdapter.getLinesData()) {
            start = start + lineData.getMemberSize();
        }
        return start;
    }

    public interface LoadDataListener {

        void loadMoreData(int start, int size);

        void refreshData(int size);
    }

    public interface IMergeStrategy {
        /**
         * @param addDataList 增加到list中的data数据
         * @param dataList    List中已有的data数据
         * @return
         */
        List<ILineData> merge(List<ILineData> addDataList, List<ILineData> dataList);
    }

    /**
     * 全部是单列的ViewHolder元素的 Strategy
     */
    public static class SingleColMergeStrategy implements IMergeStrategy {
        @Override
        public List<ILineData> merge(List<ILineData> addDataList, List<ILineData> dataList) {
            List<ILineData> resultList = new ArrayList();
            resultList.addAll(dataList);
            resultList.addAll(addDataList);
            return resultList;
        }
    }

    /**
     * 默认的Strategy
     * 支持多个类型和列数的ViewHolder
     * 如果存在相同ViewHolder中间间隔其他ViewHolder，会将后面的同类型的提到第一个的位置
     */
    public static class DefaultMergeStrategy implements IMergeStrategy {

        @Override
        public List<ILineData> merge(List<ILineData> addDataList, List<ILineData> dataList) {
            List<ILineData> resultList = new ArrayList();
            LinkedHashMap<Class, List> map = new LinkedHashMap();
            if (dataList != null && dataList.size() > 1) {
                ILineData lineData = dataList.get(dataList.size() - 1);
                if (lineData.getMemberSize() < lineData.getColumnSize()) {
                    dataList.remove(lineData);
                    fillLineDataMap(map, lineData);
                }
                for (ILineData data : addDataList) {
                    fillLineDataMap(map, data);
                }
                resultList.addAll(dataList);
                resultList.addAll(map2LineDataList(map));
            } else {
                resultList.addAll(addDataList);
            }
            return resultList;
        }

        /**
         * map转化为LineDataList
         *
         * @param map
         * @return
         */
        private List<ILineData> map2LineDataList(LinkedHashMap<Class, List> map) {
            List<ILineData> resultList = new ArrayList();
            for (Class clazz : map.keySet()) {
                try {
                    ILineData mapLineData = (ILineData) clazz.newInstance();
                    resultList.addAll(mapLineData.list2LineData(map.get(clazz)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return resultList;
        }

        /**
         * 将 lineData的member放到对应的map中去
         *
         * @param map
         * @param lineData
         */
        private void fillLineDataMap(LinkedHashMap<Class, List> map, ILineData lineData) {
            List<ILineData> list = null;
            if (map.containsKey(lineData.getClass())) {
                list = map.get(lineData.getClass());
            } else {
                list = new ArrayList<>();
                map.put(lineData.getClass(), list);
            }
            list.addAll(lineData.getMembers());
        }
    }
}

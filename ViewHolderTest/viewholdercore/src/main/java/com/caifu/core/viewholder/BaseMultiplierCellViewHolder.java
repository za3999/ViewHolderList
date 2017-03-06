package com.caifu.core.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.caifu.core.R;
import com.caifu.core.linedata.ILineData;

import java.util.HashMap;

/**
 * 一行中有多列的数据的Holder基类
 * Created by lenovo on 2017/1/3.
 * mail：zhengcaifu@bravowhale.com
 */

public abstract class BaseMultiplierCellViewHolder extends BaseViewHolder {

    private HashMap<Integer, CellViewHolder> cellMap = new HashMap<>();

    private int interval;

    public final void init(Context context, ILineData lineData) {
        mContext = context;
        this.lineData = lineData;
        root = LayoutInflater.from(mContext).inflate(R.layout.base_holder_item, null, false);
        findViews(root);
    }

    @Override
    protected final void findViews(View rootView) {
        ViewGroup groupView = (ViewGroup) rootView;
        for (int i = 0; i < lineData.getColumnSize(); i++) {
            CellViewHolder holder = getCellHolder();
            View cellView = holder.getCellView(layoutResId());
            if (i == lineData.getColumnSize() - 1) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) cellView.getLayoutParams();
                params.rightMargin = interval;
            }
            groupView.addView(cellView);
            cellMap.put(i, holder);
        }
    }


    @Override
    protected final void bindDataToView(ILineData lineData) {
        for (int i = 0; i < lineData.getColumnSize(); i++) {
            CellViewHolder holder = cellMap.get(i);
            if (i < lineData.getMemberSize()) {
                holder.getCellRootView().setVisibility(View.VISIBLE);
                holder.bindDataToView(lineData.getMembers().get(i));
            } else {
                holder.getCellRootView().setVisibility(View.INVISIBLE);
            }
        }

    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    /**
     * 返回Item的一个单元Holder
     *
     * @return
     */
    public abstract CellViewHolder getCellHolder();


    protected abstract class CellViewHolder {

        View cellRootView;

        public final View getCellRootView() {
            return cellRootView;
        }

        public final View getCellView(int cellResId) {
            cellRootView = LayoutInflater.from(mContext).inflate(cellResId, null, false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            params.leftMargin = interval;
            cellRootView.setLayoutParams(params);
            findViews(cellRootView);
            return cellRootView;
        }

        /**
         * 初始化布局
         *
         * @param cellRootView
         * @return
         */
        public abstract void findViews(View cellRootView);

        /**
         * 绑定Cell数据
         *
         * @param cellData 数据
         */
        public abstract void bindDataToView(Object cellData);
    }
}

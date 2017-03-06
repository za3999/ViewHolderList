package com.caifu.core.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.caifu.core.viewholder.BaseViewHolder;
import com.caifu.core.linedata.ILineData;
import com.caifu.core.viewholder.ViewHolderHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengcaifu on 2016/9/21.
 * mail：zhengcaifu@bravowhale.com
 */
public class BaseLineDataAdapter extends BaseAdapter {

    protected Context mContext;
    protected List<ILineData> lineDatas = new ArrayList<ILineData>();

    public BaseLineDataAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<ILineData> data) {
        lineDatas.clear();
        lineDatas.addAll(data);
    }

    @Override
    public int getCount() {
        return lineDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return lineDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View returnView = convertView;
        ILineData lineData = lineDatas.get(position);
        if (lineData != null) {
            BaseViewHolder holder;
            if (returnView == null) {
                holder = lineData.getViewHolder();
                holder.init(mContext, lineData);
                returnView = holder.getRoot();
                returnView.setTag(holder);
            } else {
                holder = (BaseViewHolder) returnView.getTag();
            }
            holder.bindLineData(lineData);
        }
        return returnView;
    }

    @Override
    public int getItemViewType(int position) {
        return ViewHolderHelper.getViewHolderType(lineDatas.get(position).getClass());
    }

    @Override
    public int getViewTypeCount() {
        return ViewHolderHelper.getViewHolderTypesCount();
    }

    public List<ILineData> getLineDatas() {
        return lineDatas;
    }
}

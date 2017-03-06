package com.caifu.core.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.caifu.core.linedata.ILineData;

/**
 * ViewHolder的基类，用于指定Holder的加载流程
 */
public abstract class BaseViewHolder {
    protected View root;
    public Context mContext;
    ILineData lineData;

    public void init(Context context, ILineData lineData) {
        mContext = context;
        root =  LayoutInflater.from(mContext).inflate(layoutResId(), null, false);
        findViews(root);
    }

    public final void bindLineData(ILineData lineData) {
        this.lineData = lineData;
        bindDataToView(lineData);
    }

    /**
     * Get the root view
     *
     * @return root view.
     */
    public final View getRoot() {
        return root;
    }

    /**
     * Layout resource id.
     *
     * @return related resource
     */
    protected abstract int layoutResId();

    /**
     * @param rootView The root view of
     */
    protected abstract void findViews(View rootView);

    /**
     * Bind a line data to view
     *
     * @param lineData related line data
     */
    protected abstract void bindDataToView(ILineData lineData);

}

package com.caifu.test.linedata;

import com.caifu.core.linedata.AbstractLineData;
import com.caifu.core.viewholder.BaseViewHolder;
import com.caifu.test.viewholder.MultiplierTestViewHolder;

/**
 * Created by zhengcaifu on 2017/3/6.
 * mail：zhengcaifu@bravowhale.com
 */

public class DoubleTestLineData extends AbstractLineData {

    public DoubleTestLineData() {
        setColumns(2);
    }

    @Override
    public BaseViewHolder getViewHolder() {
        return new MultiplierTestViewHolder();
    }
}

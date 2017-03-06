package com.caifu.test.linedata;

import com.caifu.core.linedata.AbstractLineData;
import com.caifu.core.viewholder.BaseViewHolder;
import com.caifu.test.viewholder.SingleTestViewHolder;

/**
 * Created by lenovo on 2017/3/6.
 * mail：zhengcaifu@bravowhale.com
 */

public class SingleTestLineData extends AbstractLineData {
    @Override
    public BaseViewHolder getViewHolder() {
        return new SingleTestViewHolder();
    }
}

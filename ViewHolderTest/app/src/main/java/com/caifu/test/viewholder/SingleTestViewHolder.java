package com.caifu.test.viewholder;

import android.view.View;
import android.widget.TextView;

import com.caifu.core.linedata.ILineData;
import com.caifu.core.viewholder.BaseViewHolder;
import com.caifu.test.R;
import com.caifu.test.bean.TestBean;
import com.caifu.test.linedata.SingleTestLineData;

/**
 * Created by lenovo on 2017/3/6.
 * mail：zhengcaifu@bravowhale.com
 */

public class SingleTestViewHolder extends BaseViewHolder {

    TextView nameTv, sexTv;

    @Override
    protected int layoutResId() {
        return R.layout.test_item;
    }

    @Override
    protected void findViews(View rootView) {
        nameTv = (TextView) rootView.findViewById(R.id.name);
        sexTv = (TextView) rootView.findViewById(R.id.sex);
    }

    @Override
    protected void bindDataToView(ILineData lineData) {
        SingleTestLineData singleTestLineData = ((SingleTestLineData) lineData);
        TestBean bean = (TestBean) singleTestLineData.getMembers().get(0);
        nameTv.setText(bean.getName());
        sexTv.setText(bean.getSex());
    }
}

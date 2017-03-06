package com.caifu.test.viewholder;

import android.view.View;
import android.widget.TextView;

import com.caifu.core.viewholder.BaseMultiplierCellViewHolder;
import com.caifu.test.R;
import com.caifu.test.bean.TestBean;

/**
 * Created by zhengcaifu on 2017/3/6.
 * mail：zhengcaifu@bravowhale.com
 */

public class MultiplierTestViewHolder extends BaseMultiplierCellViewHolder {

    public MultiplierTestViewHolder() {
        setInterval(20);
    }

    @Override
    public CellViewHolder getCellHolder() {
        return new TestCellHolder();
    }

    @Override
    protected int layoutResId() {
        return R.layout.test_item;
    }

    private class TestCellHolder extends CellViewHolder {
        TextView nameTv, sexTv;


        @Override
        public void findViews(View cellRootView) {
            nameTv = (TextView) cellRootView.findViewById(R.id.name);
            sexTv = (TextView) cellRootView.findViewById(R.id.sex);
        }

        @Override
        public void bindDataToView(Object cellData) {
            TestBean bean = (TestBean) cellData;
            nameTv.setText(bean.getName());
            sexTv.setText(bean.getSex());
        }
    }
}

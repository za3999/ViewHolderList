package com.caifu.test;

import android.app.Application;

import com.caifu.core.viewholder.ViewHolderHelper;
import com.caifu.test.viewholder.MultiplierTestViewHolder;
import com.caifu.test.viewholder.SingleTestViewHolder;

/**
 * Created by zhengcaifu on 2017/3/6.
 * mail：zhengcaifu@bravowhale.com
 */

public class TestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ViewHolderHelper.addHolderType(MultiplierTestViewHolder.class);
        ViewHolderHelper.addHolderType(SingleTestViewHolder.class);
    }
}

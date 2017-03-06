package com.caifu.test.viewholder;

import com.caifu.core.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengcaifu on 2016/9/21.
 * mail：zhengcaifu@bravowhale.com
 */
public class ViewHolderHelper {

    private static List<Class<? extends BaseViewHolder>> viewHolderTypes;

    static {
        viewHolderTypes = new ArrayList();
    }

    public static int getViewHolderTypesCount() {
        return viewHolderTypes.size();
    }

    public static int getViewHolderType(Class clazz) {
        return viewHolderTypes.indexOf(clazz);
    }
}

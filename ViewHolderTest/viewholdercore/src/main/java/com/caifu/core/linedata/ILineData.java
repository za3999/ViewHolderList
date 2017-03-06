/**
 *
 */
package com.caifu.core.linedata;


import com.caifu.core.viewholder.BaseViewHolder;

import java.util.List;

/**
 * Adapter的LineData 的声明接口
 */
public interface ILineData<T> {

    BaseViewHolder getViewHolder();

    void addMember(Object member);

    int getMemberSize();

    int getColumnSize();

    List<T> getMembers();

    List<ILineData> list2LineData(List list);
}

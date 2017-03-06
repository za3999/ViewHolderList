package com.caifu.core.linedata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * lineData基类
 * Created by zhengcaifu on 2016/9/23.
 * mail：zhengcaifu@bravowhale.com
 */
public abstract class AbstractLineData implements ILineData {

    int columns = 1;

    protected List members = new ArrayList<>();

    public int getColumnSize() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    @Override
    public List getMembers() {
        return members;
    }

    @Override
    public void addMember(Object member) {
        if (members.size() < columns) {
            members.add(member);
        } else {
            throw new RuntimeException("line members is full.");
        }
    }

    @Override
    public int getMemberSize() {
        return members.size();
    }

    @Override
    public List<ILineData> list2LineData(List list) {
        list.removeAll(Collections.singleton(null));
        ArrayList<ILineData> resultList = new ArrayList();
        if (list != null) {
            int size = list.size();
            ILineData tempData = null;
            for (int i = 0; i < size; i++) {
                if (i % columns == 0) {
                    try {
                        tempData = getClass().newInstance();
                        resultList.add(tempData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                tempData.addMember(list.get(i));
            }
        }
        return resultList;
    }
}

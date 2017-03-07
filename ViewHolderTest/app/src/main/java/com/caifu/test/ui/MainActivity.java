package com.caifu.test.ui;

import android.app.Activity;
import android.os.Bundle;

import com.caifu.core.linedata.AbstractLineData;
import com.caifu.core.view.DefaultLineDataContainerView;
import com.caifu.test.R;
import com.caifu.test.bean.TestBean;
import com.caifu.test.linedata.DoubleTestLineData;
import com.caifu.test.linedata.SingleTestLineData;
import com.caifu.test.linedata.ThreeTestLineData;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by zhengcaifu on 2017/3/6.
 * mail：zhengcaifu@bravowhale.com
 */

public class MainActivity extends Activity {


    DefaultLineDataContainerView containerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_list_layout);
        containerView = (DefaultLineDataContainerView) findViewById(R.id.container_view);
        containerView.setScrollLoadingEnable(true);
        containerView.setRefLoadingEnable(true);
        containerView.setPageSize(15);
        containerView.setLoadDataListener(new DefaultLineDataContainerView.LoadDataListener() {

            @Override
            public void loadMoreData(int pageNow, int pageSize) {
                //延时一秒，模拟网络加载时间
                Observable timerObservable = Observable.timer(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread());
                timerObservable.subscribe(v -> {
                    AbstractLineData lineData = null;
                    ArrayList<TestBean> list = new ArrayList<TestBean>();
                    if (pageNow % 3 == 0) {
                        lineData = new ThreeTestLineData();
                        for (int i = 0; i < pageSize; i++) {
                            list.add(new TestBean(("3列同学" + (pageNow * pageSize + i)), "女"));
                        }
                    } else if ((pageNow % 3 == 1)) {
                        lineData = new DoubleTestLineData();
                        for (int i = 0; i < pageSize; i++) {
                            list.add(new TestBean(("双列同学" + (pageNow * pageSize + i)), "女"));
                        }
                    } else {
                        lineData = new SingleTestLineData();
                        for (int i = 0; i < pageSize; i++) {
                            list.add(new TestBean(("单列同学" + (pageNow * pageSize + i)), "女"));
                        }
                    }
                    containerView.addData(lineData.list2LineData(list), false);
                });
            }

            @Override
            public void refreshData(int pageSize) {
                //延时一秒，模拟网络加载时间
                Observable timerObservable = Observable.timer(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread());
                timerObservable.subscribe(v -> {
                    SingleTestLineData lineData = new SingleTestLineData();
                    ArrayList<TestBean> list = new ArrayList<TestBean>();
                    for (int i = 0; i < pageSize; i++) {
                        list.add(new TestBean(("单列同学" + i), "男"));
                    }
                    containerView.setData(lineData.list2LineData(list), false);
                });
            }
        });
        containerView.initData();
    }

}

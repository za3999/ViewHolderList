# ViewHolderList


一种基于ViewHolder编码的框架

1、让开发者只关注布局和数据绑定，省去其他的环节(封装List和Adapter的创建过程，省去网络处理和上拉下拉处理)，简化开发过程，提高工作效率。
2、已有ViewHolder可以在项目的任务布局中使用，提高复用性，省去重复编码工作。
3、代码更简洁，逻辑更清楚，增加项目的可维护性。

具体实现如下：

1、Bean对象：

public class TestBean {

    String name;
    String sex;

    public TestBean(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }
}

2、LineData对象

public class SingleTestLineData extends AbstractLineData {
    @Override
    public BaseViewHolder getViewHolder() {
        return new SingleTestViewHolder();
    }
}

省去多列的LineData实现具体看代码内部

3、ViewHolder 对象

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

4、Activity对象

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

                AbstractLineData lineData = null;
                ArrayList<TestBean> list = new ArrayList<TestBean>();
                String name ="";
                if (pageNow % 3 == 0) {
                    lineData = new ThreeTestLineData();
                    name = "3列同学";
                } else if ((pageNow % 3 == 1)) {
                    lineData = new SingleTestLineData();
                    name = "单列同学";
                } else {
                    lineData = new DoubleTestLineData();
                    name = "双列同学";
                }

                for (int i = 0; i < pageSize; i++) {
                    list.add(new TestBean(name + (pageNow * pageSize + i), "女"));
                }
                containerView.addData(lineData.list2LineData(list), false);
            }

            @Override
            public void refreshData(int pageSize) {
                SingleTestLineData lineData = new SingleTestLineData();
                ArrayList<TestBean> list = new ArrayList<TestBean>();
                for (int i = 0; i < pageSize; i++) {
                    list.add(new TestBean(("单列同学" + i), "男"));
                }
                containerView.setData(lineData.list2LineData(list), false);
            }

        });
        containerView.initData();
    }

}

通过上面四步，一个支持多列以及上拉下面的ListView就完成了。

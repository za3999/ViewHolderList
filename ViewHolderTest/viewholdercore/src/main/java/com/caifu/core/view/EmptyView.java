package com.caifu.core.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.caifu.core.R;
import com.caifu.core.util.NetWorkUtil;

/**
 * Created by zhengcaifu on 2017/3/6.
 * mail：zhengcaifu@bravowhale.com
 */

public class EmptyView extends RelativeLayout {

    public static final int HIDE_LAYOUT = 4;
    public static final int NETWORK_ERROR = 1;
    public static final int NETWORK_LOADING = 2;
    public static final int NO_DATA = 3;

    private boolean clickEnable = true;
    private final Context context;
    public ImageView topImage;
    private ProgressBar loadingProgress;
    private int viewState;
    private TextView messageTv;

    public EmptyView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    /**
     * 返回当前View状态
     *
     * @return
     */
    public int getViewState() {
        return viewState;
    }


    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        if (clickEnable) {
            super.setOnClickListener(l);
        }
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_error_layout, this, true);
        topImage = (ImageView) view.findViewById(R.id.top_image);
        messageTv = (TextView) view.findViewById(R.id.message_tv);
        loadingProgress = (ProgressBar) view.findViewById(R.id.loading_progress);
    }

    public void setViewType(int type) {
        setVisibility(View.VISIBLE);
        if (type == NETWORK_ERROR) {
            viewState = NETWORK_ERROR;
            if (NetWorkUtil.isNetworkConnected(getContext())) {
                showImageMessage(R.mipmap.page_failed_bg,
                        getContext().getString(R.string.error_view_load_error_click_to_refresh));
            } else {
                showImageMessage(R.mipmap.page_icon_network,
                        getContext().getString(R.string.error_view_network_error_click_to_refresh));
            }
            clickEnable = true;
        } else if (type == NETWORK_LOADING) {
            viewState = NETWORK_LOADING;
            showLoadingMessage(getContext().getString(R.string.error_view_loading));
            clickEnable = false;
        } else if (type == NO_DATA) {
            viewState = NO_DATA;
            showImageMessage(R.mipmap.page_icon_empty, getContext().getString(R.string.error_view_no_data));
            clickEnable = true;
        } else if (type == HIDE_LAYOUT) {
            viewState = HIDE_LAYOUT;
            setVisibility(View.GONE);

        }
    }

    /**
     * 显示图片和文字
     *
     * @param imageRes
     * @param message
     */
    private void showImageMessage(int imageRes, String message) {
        topImage.setVisibility(View.VISIBLE);
        loadingProgress.setVisibility(View.GONE);
        topImage.setBackgroundResource(imageRes);
        messageTv.setText(message);
    }

    /**
     * 显示加载加的信息
     *
     * @param message
     */
    private void showLoadingMessage(String message) {
        topImage.setVisibility(View.GONE);
        loadingProgress.setVisibility(View.VISIBLE);
        messageTv.setText(message);
    }
}

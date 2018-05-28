package com.xqlh.heartsmart.ui.product.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.alipay.android.phone.mrpc.core.NetworkUtils;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.RetrofitHelper;
import com.xqlh.heartsmart.api.base.BaseObserval;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.bean.EntityProductDetail;
import com.xqlh.heartsmart.utils.ProgressUtils;
import com.xqlh.heartsmart.widget.HProgressBarLoading;
import com.xqlh.heartsmart.widget.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProductDetailActivity extends BaseActivity {

    @BindView(R.id.product_detail_titleBar)
    TitleBar product_detail_titleBar;

    @BindView(R.id.product_detail_wb)
    WebView product_detail_wb;

    @BindView(R.id.top_progress)
    HProgressBarLoading top_progress;

    @BindView(R.id.rl_error)
    RelativeLayout rl_error;

    @BindView(R.id.bt_refresh)
    Button bt_refresh;
    @BindView(R.id.bt_check_network)
    Button bt_check_network;

    String id;
    String name;

    private boolean isContinue = false;


    @Override
    public int setContent() {
        return R.layout.activity_product_detail;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        initTtileBar();
        setWebView();
        if (!com.xqlh.heartsmart.utils.NetworkUtils.isConnected()) {
            rl_error.setVisibility(View.VISIBLE);
        } else {
            getData(id);
            rl_error.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.bt_refresh, R.id.bt_check_network})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.bt_refresh:
                getData(id);
                rl_error.setVisibility(View.GONE);
                break;
            case R.id.bt_check_network:
                Intent intent = null;
                if (android.os.Build.VERSION.SDK_INT > 10) {
                    intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                } else {
                    intent = new Intent();
                    ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
                    intent.setComponent(component);
                    intent.setAction("android.intent.action.VIEW");
                }
                startActivityForResult(intent, 0);
                break;
        }

    }

    public void getData(String id) {
        RetrofitHelper.getApiService()
                .getProductDetail(id)
                .subscribeOn(Schedulers.io())
                .compose(ProgressUtils.<EntityProductDetail>applyProgressBar(this))
                .compose(this.<EntityProductDetail>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityProductDetail>() {
                    @Override
                    public void onSuccess(EntityProductDetail response) {
                        if (response.getCode() == 1) {
                            name = response.getResult().getName();
//                            product_detail_wb.loadData(response.getResult().getDescribeStr(), "text/html", "UTF-8");

                            product_detail_wb.loadDataWithBaseURL(null, response.getResult().getDescribeStr(), "text/html", "UTF-8", null);


                            Log.i(TAG, "H5的内容: " + response.getResult().getDescribeStr());
                            product_detail_titleBar.setTitle(name);
                        }
                    }
                });
    }


    public void initTtileBar() {
        product_detail_titleBar.setLeftImageResource(R.drawable.return_button);
        product_detail_titleBar.setTitleColor(Color.WHITE);
        product_detail_titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setWebView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            product_detail_wb.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//软件解码
        }
        product_detail_wb.setLayerType(View.LAYER_TYPE_HARDWARE, null);//硬件解码

        final WebSettings webSettings = product_detail_wb.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(false);
        webSettings.setDefaultTextEncodingName("utf-8");

        product_detail_wb.setWebChromeClient(webChromeClient);
        product_detail_wb.setWebViewClient(webViewClient);
    }

    WebViewClient webViewClient = new WebViewClient() {
        //https的处理方式
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        //错误页面的逻辑处理
        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            errorOperation();
        }
    };

    WebChromeClient webChromeClient = new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (!NetworkUtils.isNetworkAvailable(ProductDetailActivity.this)) {
                return;
            }
            //如果进度条隐藏则让它显示
            if (View.INVISIBLE == top_progress.getVisibility()) {
                top_progress.setVisibility(View.VISIBLE);
            }
            //大于80的进度的时候,放慢速度加载,否则交给自己加载
            if (newProgress >= 80) {
                //拦截webView自己的处理方式
                if (isContinue) {
                    return;
                }
                top_progress.setCurProgress(100, 3000, new HProgressBarLoading.OnEndListener() {
                    @Override
                    public void onEnd() {
                        finishOperation(true);
                        isContinue = false;
                    }
                });
                isContinue = true;
            } else {
                top_progress.setNormalProgress(newProgress);
            }
        }
    };

    /**
     * 错误的时候进行的操作
     */
    private void errorOperation() {
        //隐藏webview
        product_detail_wb.setVisibility(View.INVISIBLE);

        if (View.INVISIBLE == top_progress.getVisibility()) {
            top_progress.setVisibility(View.VISIBLE);
        }
        //3.5s 加载 0->80 进度的加载 为了实现,特意调节长了事件
        top_progress.setCurProgress(80, 3500, new HProgressBarLoading.OnEndListener() {
            @Override
            public void onEnd() {
                //3.5s 加载 80->100 进度的加载
                top_progress.setCurProgress(100, 3500, new HProgressBarLoading.OnEndListener() {
                    @Override
                    public void onEnd() {
                        finishOperation(false);
                    }
                });
            }
        });
    }

    /**
     * 结束进行的操作
     */
    private void finishOperation(boolean flag) {
        //最后加载设置100进度
        top_progress.setNormalProgress(100);
        //显示网络异常布局
        rl_error.setVisibility(flag ? View.INVISIBLE : View.VISIBLE);
        //点击重新连接网络
        rl_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_error.setVisibility(View.INVISIBLE);
                product_detail_wb.setVisibility(View.VISIBLE);
                getData(id);
            }
        });
        hideProgressWithAnim();
    }

    /**
     * 隐藏加载对话框
     */
    private void hideProgressWithAnim() {
        AnimationSet animation = getDismissAnim(ProductDetailActivity.this);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                top_progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        top_progress.startAnimation(animation);
    }

    /**
     * 获取消失的动画
     *
     * @param context
     * @return
     */
    private AnimationSet getDismissAnim(Context context) {
        AnimationSet dismiss = new AnimationSet(context, null);
        AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.0f);
        alpha.setDuration(1000);
        dismiss.addAnimation(alpha);
        return dismiss;
    }
}

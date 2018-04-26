package com.xqlh.heartsmart.ui.home.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.http.SslError;
import android.text.Html;
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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alipay.android.phone.mrpc.core.NetworkUtils;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.RetrofitHelper;
import com.xqlh.heartsmart.api.base.BaseObserval;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.bean.EntityArticleDetail;
import com.xqlh.heartsmart.widget.HProgressBarLoading;
import com.xqlh.heartsmart.widget.TitleBar;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ArticleDetailActivity extends BaseActivity {

    @BindView(R.id.article_detail_titleBar)
    TitleBar article_detail_titleBar;

    @BindView(R.id.article_detail_wb)
    WebView article_detail_wb;
    @BindView(R.id.top_progress)
    HProgressBarLoading top_progress;
    @BindView(R.id.rl_retory)
    RelativeLayout rl_retory;

    private String id;

    private boolean isContinue = false;

    @Override
    public int setContent() {
        return R.layout.activity_article_detail;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        Intent intent = getIntent();

        id = intent.getStringExtra("id");

        Log.i(TAG, "文章的id" + id);

        initTtileBar();


        setWebView();

//        getData(id);
    }

    public void initTtileBar() {
        article_detail_titleBar.setLeftImageResource(R.drawable.return_button);
        article_detail_titleBar.setTitleColor(Color.WHITE);
        article_detail_titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getData(String id) {
        RetrofitHelper.getApiService()
                .getArticleDetail(id)
                .subscribeOn(Schedulers.io())
                .compose(this.<EntityArticleDetail>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityArticleDetail>() {
                    @Override
                    public void onSuccess(EntityArticleDetail response) {
                        if (response.getCode() == 1) {
                            Log.i(TAG, "标题: " + response.getResult().getArticleTitle());
                            Log.i(TAG, "H5的内容: " + response.getResult().getContent());
                            article_detail_wb.loadDataWithBaseURL(null,
                                    Html.fromHtml(response.getResult().getContent()) + "",
                                    "text/html", "UTF-8", null);

//                            article_detail_wb.loadData(response.getResult().getContent(), "text/html; charset=UTF-8", null);

                            article_detail_titleBar.setTitle(response.getResult().getArticleTitle());
                        } else {
                            Toasty.warning(ArticleDetailActivity.this, "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }


    private void setWebView() {

        WebSettings webSettings = article_detail_wb.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(false);
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);
        //扩大比例的缩放
        webSettings.setUseWideViewPort(true);
        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);

        article_detail_wb.setWebChromeClient(webChromeClient);

        article_detail_wb.setWebViewClient(webViewClient);

        //
        article_detail_wb.loadUrl("https://www.baidu.com");
    }

    WebViewClient webViewClient = new WebViewClient(){
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
            if (!NetworkUtils.isNetworkAvailable(ArticleDetailActivity.this)) {
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
        article_detail_wb.setVisibility(View.INVISIBLE);

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
        rl_retory.setVisibility(flag ? View.INVISIBLE : View.VISIBLE);
        //点击重新连接网络
        rl_retory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_retory.setVisibility(View.INVISIBLE);
                //重新加载网页
                article_detail_wb.reload();
            }
        });
        hideProgressWithAnim();
    }
    /**
     * 隐藏加载对话框
     */
    private void hideProgressWithAnim() {
        AnimationSet animation = getDismissAnim(ArticleDetailActivity.this);
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

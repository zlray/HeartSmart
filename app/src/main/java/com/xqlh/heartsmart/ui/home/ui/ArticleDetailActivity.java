package com.xqlh.heartsmart.ui.home.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import android.widget.Toast;

import com.alipay.android.phone.mrpc.core.NetworkUtils;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.RetrofitHelper;
import com.xqlh.heartsmart.api.base.BaseObserval;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.bean.EntityArticleDetail;
import com.xqlh.heartsmart.bean.EntityCollect;
import com.xqlh.heartsmart.utils.Constants;
import com.xqlh.heartsmart.utils.ContextUtils;
import com.xqlh.heartsmart.utils.SharedPreferencesHelper;
import com.xqlh.heartsmart.widget.HProgressBarLoading;
import com.xqlh.heartsmart.widget.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;
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
    @BindView(R.id.bt_collect)
    Button bt_collect;


    SharedPreferencesHelper sp;

    private String id;

    private boolean isContinue = false;
    private String token;
    private String str;

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
        sp = new SharedPreferencesHelper(ContextUtils.getContext(), Constants.CHECKINFOR);
        token = sp.getSharedPreference(Constants.LOGIN_TOKEN, "").toString();
        if (!"".equals(sp.getSharedPreference(id, "").toString())) {
            bt_collect.setText(sp.getSharedPreference(id, "").toString());
        }

        initTtileBar();

        setWebView();

        getData(id);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_all_down:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.bt_collect)
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.bt_collect:
                str = sp.getSharedPreference(id, "收藏").toString();
                if ("收藏".equals(str)) {
                    collect(str, id, 1);
                    bt_collect.setText("取消收藏");
                    sp.put(id, "取消收藏");
                } else if ("取消收藏".equals(str)) {
                    collect(str, id, 0);
                    bt_collect.setText("收藏");
                    sp.put(id, "收藏");
                }
                break;
        }
    }

    public void collect(final String str, String id, int collect) {
        RetrofitHelper.getApiService()
                .collect(token, id, collect)
                .subscribeOn(Schedulers.io())
                .compose(this.<EntityCollect>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityCollect>() {
                    @Override
                    public void onSuccess(EntityCollect response) {
                        if (response.getCode() == 1) {
                            if ("收藏".equals(str)) {
                                Toasty.success(ContextUtils.getContext(), "收藏成功", Toast.LENGTH_SHORT, true).show();
                            } else if ("取消收藏".equals(str)) {
                                Toasty.success(ContextUtils.getContext(), "取消成功", Toast.LENGTH_SHORT, true).show();
                            }

                        } else {
                            Toasty.warning(ArticleDetailActivity.this, "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
//
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
                            article_detail_wb.loadDataWithBaseURL(null,
                                    response.getResult().getContent(),
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            article_detail_wb.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//软件解码
        }
        article_detail_wb.setLayerType(View.LAYER_TYPE_HARDWARE, null);//硬件解码

        final WebSettings webSettings = article_detail_wb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(false);

        article_detail_wb.setWebChromeClient(webChromeClient);
        article_detail_wb.setWebViewClient(webViewClient);

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
                article_detail_wb.setVisibility(View.VISIBLE);
                //重新加载网页
                Log.i(TAG, "重新加载");
                getData(id);
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

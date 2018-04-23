package com.xqlh.heartsmart.ui.home.ui;

import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.RetrofitHelper;
import com.xqlh.heartsmart.api.base.BaseObserval;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.bean.EntityArticleDetail;
import com.xqlh.heartsmart.utils.ProgressUtils;
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

    private String id;

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
        setWebView();
        initTtileBar();
        getData(id);
    }

    //    03c1e7295f9648ebad4ea9ed1f701426
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
                .compose(ProgressUtils.<EntityArticleDetail>applyProgressBar(this))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityArticleDetail>() {
                    @Override
                    public void onSuccess(EntityArticleDetail response) {
                        Log.i(TAG, "标题: " + response.getResult().getArticleTitle());

                        Log.i(TAG, "H5的内容: " + response.getResult().getContent());
                        if (response.getCode() == 1) {
                                article_detail_wb.loadDataWithBaseURL(null,
                                        Html.fromHtml(response.getResult().getContent())+"",
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
        final WebSettings webSettings = article_detail_wb.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");

//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setDomStorageEnabled(false);
//        // 打开屏幕时自适应
//        webSettings.setUseWideViewPort(true);
//        //设置此属性，可任意比例缩放
//        webSettings.setLoadWithOverviewMode(true);
//        // 支持页面缩放
//        webSettings.setBuiltInZoomControls(true);
//
//        webSettings.setSupportZoom(true);
//
//        webSettings.setDomStorageEnabled(true);
//
//        webSettings.setAppCacheEnabled(true);
//
//        webSettings.setDatabaseEnabled(true);
//
//
//        // 设置可以支持缩放
//        article_detail_wb.getSettings().setSupportZoom(true);
//        // 设置出现缩放工具
//        article_detail_wb.getSettings().setBuiltInZoomControls(true);
//        //扩大比例的缩放
//        article_detail_wb.getSettings().setUseWideViewPort(true);
//        //自适应屏幕
//        article_detail_wb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        article_detail_wb.getSettings().setLoadWithOverviewMode(true);
    }
}

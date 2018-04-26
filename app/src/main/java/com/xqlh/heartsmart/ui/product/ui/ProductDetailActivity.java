package com.xqlh.heartsmart.ui.product.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.RetrofitHelper;
import com.xqlh.heartsmart.api.base.BaseObserval;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.bean.EntityProductDetail;
import com.xqlh.heartsmart.utils.ProgressUtils;
import com.xqlh.heartsmart.widget.TitleBar;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProductDetailActivity extends BaseActivity {

    @BindView(R.id.product_detail_titleBar)
    TitleBar product_detail_titleBar;

    @BindView(R.id.product_detail_wb)
    WebView product_detail_wb;
    String id;
    String name;
    String pic;
    String description;


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
        getData(id);

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
        // 打开屏幕时自适应
        webSettings.setUseWideViewPort(true);
        //设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        // 支持页面缩放
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        // 设置可以支持缩放
        product_detail_wb.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        product_detail_wb.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        product_detail_wb.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        product_detail_wb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        product_detail_wb.getSettings().setLoadWithOverviewMode(true);
    }
}

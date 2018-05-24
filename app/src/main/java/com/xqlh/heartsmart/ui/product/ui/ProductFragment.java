package com.xqlh.heartsmart.ui.product.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.RetrofitHelper;
import com.xqlh.heartsmart.api.base.BaseObserval;
import com.xqlh.heartsmart.base.BaseLazyFragment;
import com.xqlh.heartsmart.base.RvListener;
import com.xqlh.heartsmart.bean.EntityCategory;
import com.xqlh.heartsmart.bean.EntityProductCategory;
import com.xqlh.heartsmart.ui.product.adapter.AdapterLeft;
import com.xqlh.heartsmart.ui.product.adapter.AdapterRight;
import com.xqlh.heartsmart.utils.ProgressUtils;
import com.xqlh.heartsmart.utils.ContextUtils;
import com.xqlh.heartsmart.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class ProductFragment extends BaseLazyFragment {
    @BindView(R.id.product_fragmet_titlebar)
    TitleBar product_fragmet_titlebar;

    @BindView(R.id.bt_hardware)
    Button bt_hardware;
    @BindView(R.id.bt_software)
    Button bt_software;

    @BindView(R.id.rv_right)
    RecyclerView rv_right;

    private AdapterLeft adapterLeft;
    private AdapterRight adapterRight;

    List<EntityCategory> listCategory = new ArrayList<>();

    List<EntityProductCategory.ResultBean> listProduct = new ArrayList<>();


    @Override
    protected int setContentView() {
        Log.i("lz", "产品fragment");
        return R.layout.fragment_product;
    }

    @Override
    protected void init() {
        initTtileBar();
        rv_right.setLayoutManager(new LinearLayoutManager(getActivity()));
        bt_hardware.setBackgroundColor(getResources().getColor(R.color.lightgrey));
        bt_software.setBackgroundColor(Color.WHITE);
    }

    public void initTtileBar() {
        product_fragmet_titlebar.setTitle("产品中心");
        product_fragmet_titlebar.setTitleColor(Color.WHITE);
        product_fragmet_titlebar.setLeftTextColor(Color.WHITE);
    }

    @Override
    protected void lazyLoad() {
        initHardData();

    }

    @OnClick({R.id.bt_hardware, R.id.bt_software})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.bt_hardware:
                bt_hardware.setBackgroundColor(getResources().getColor(R.color.lightgrey));
                bt_software.setBackgroundColor(Color.WHITE);
                initHardData();
                break;
            case R.id.bt_software:
                bt_hardware.setBackgroundColor(Color.WHITE);
                bt_software.setBackgroundColor(getResources().getColor(R.color.lightgrey));
                initSoftdData();
                break;
        }
    }

    public void initHardData() {
        RetrofitHelper.getApiService()
                .getProductCategory(0, 1, 100)
                .subscribeOn(Schedulers.io())
                .compose(ProgressUtils.<EntityProductCategory>applyProgressBar(getActivity()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityProductCategory>() {
                    @Override
                    public void onSuccess(final EntityProductCategory response) {
                        if (response.getCode() == 1) {
                            listProduct = response.getResult();
                            adapterRight = new AdapterRight(getActivity(), listProduct, new RvListener() {
                                @Override
                                public void onItemClick(int id, int position) {
                                    Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                                    intent.putExtra("id", response.getResult().get(position).getID());
                                    getActivity().startActivity(intent);
                                }
                            });
                            rv_right.setAdapter(adapterRight);
                            Log.i("lz", "aaaa" + listProduct.size());
                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }

    public void initSoftdData() {

        RetrofitHelper.getApiService()
                .getProductCategory(1, 1, 100)
                .subscribeOn(Schedulers.io())
                .compose(ProgressUtils.<EntityProductCategory>applyProgressBar(getActivity()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityProductCategory>() {
                    @Override
                    public void onSuccess(final EntityProductCategory response) {
                        if (response.getCode() == 1) {
                            listProduct = response.getResult();
                            adapterRight = new AdapterRight(getActivity(), listProduct, new RvListener() {
                                @Override
                                public void onItemClick(int id, int position) {
                                    Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                                    intent.putExtra("id", response.getResult().get(position).getID());
                                    getActivity().startActivity(intent);
                                }
                            });
                            rv_right.setAdapter(adapterRight);
                            Log.i("lz", "aaaa" + listProduct.size());
                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }


}

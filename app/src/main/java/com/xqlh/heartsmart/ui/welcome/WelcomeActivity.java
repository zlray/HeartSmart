package com.xqlh.heartsmart.ui.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.ui.login.LoginActivity;
import com.xqlh.heartsmart.utils.ImageLoaderUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;

public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.welcome_iv)
    ImageView welcome_iv;
    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override

    public int setContent() {
        return R.layout.activity_welcome;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        final int countTime = 2;
//        getWelcome();
        ImageLoaderUtil.LoadImage(WelcomeActivity.this, "http://resources.bnuxq.com:8082/201803271144278180DO8UXQ.jpg", welcome_iv);
        mCompositeDisposable.add(Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(Long aLong) throws Exception {
                        return countTime - aLong.intValue();
                    }
                }).take(countTime + 1) //设置循环的次数
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                }).subscribeWith(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if (mCompositeDisposable != null) {
                            mCompositeDisposable.dispose();
                        }
                        Intent intent = new Intent();
                        intent.setClass(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
        );
    }

//    public void getWelcome() {
//        RetrofitHelper.getApiService()
//                .getWelcome(1)
//                .subscribeOn(Schedulers.io())
//                .compose(this.<EntityWelcome>bindToLifecycle())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserval<EntityWelcome>() {
//                    @Override
//                    public void onSuccess(EntityWelcome response) {
//
//
//                        Glide.with(WelcomeActivity.this)
//                                .load(response.getPicURL())
//                                .listener(new RequestListener<String, GlideDrawable>() {
//                                    @Override
//                                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                                        Log.d(TAG, "onException: " + e.toString() + "  model:" + model + " isFirstResource: " + isFirstResource);
//                                        return false;
//                                    }
//
//                                    @Override
//                                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                                        return false;
//                                    }
//                                })
//                                .into(welcome_iv);
//                    }
//                });
//    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }


    @Override
    protected void onDestroy() {
        Glide.clear(welcome_iv);
        super.onDestroy();
    }
}

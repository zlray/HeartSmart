package com.xqlh.heartsmart.ui.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xqlh.heartsmart.MainActivity;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.BaseActivity;
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
        final int countTime = 1;

        ImageLoaderUtil.LoadImage(WelcomeActivity.this, "http://api.dujin.org/bing/1920.php", welcome_iv);
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
                        intent.setClass(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
        );

    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }


    @Override
    protected void onDestroy() {
        Glide.clear(welcome_iv);
        super.onDestroy();
    }
}

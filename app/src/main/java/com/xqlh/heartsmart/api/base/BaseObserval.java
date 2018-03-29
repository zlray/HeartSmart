package com.xqlh.heartsmart.api.base;

import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.exception.ServerResponseException;
import com.xqlh.heartsmart.utils.LogUtils;
import com.xqlh.heartsmart.utils.Utils;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by Administrator on 2018/3/29.
 */

public abstract class BaseObserval<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T response) {
        onSuccess(response);
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.e("Retrofit", e.getMessage());
        if (e instanceof HttpException) {     //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            onException(ExceptionReason.CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            onException(ExceptionReason.PARSE_ERROR);
        } else if (e instanceof ServerResponseException) {
            onFail(e.getMessage());
        } else {
            onException(ExceptionReason.UNKNOWN_ERROR);
        }
    }

    @Override
    public void onComplete() {
    }

    /**
     * 请求成功
     *
     * @param response 服务器返回的数据
     */
    abstract public void onSuccess(T response);

    /**
     * 服务器返回数据，但响应码不为200
     *
     */
    /**
     * 服务器返回数据，但响应码不为1000
     */
    public void onFail(String message) {
        //
        Toasty.info(Utils.getContext(), message, Toast.LENGTH_SHORT, true).show();
    }


    /**
     * 请求异常
     *
     * @param reason
     */
    public void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                Toasty.info(Utils.getContext(), Utils.getContext().getResources().getText(R.string.connect_error), Toast.LENGTH_SHORT, true).show();
                break;
            case CONNECT_TIMEOUT:
                Toasty.info(Utils.getContext(), Utils.getContext().getResources().getText(R.string.connect_timeout), Toast.LENGTH_SHORT, true).show();
                break;
            case BAD_NETWORK:
                Toasty.info(Utils.getContext(), Utils.getContext().getResources().getText(R.string.bad_network), Toast.LENGTH_SHORT, true).show();
                break;
            case PARSE_ERROR:
                Toasty.info(Utils.getContext(), Utils.getContext().getResources().getText(R.string.parse_error), Toast.LENGTH_SHORT, true).show();
                break;
            case UNKNOWN_ERROR:
            default:
                Toasty.info(Utils.getContext(), Utils.getContext().getResources().getText(R.string.unknown_error), Toast.LENGTH_SHORT, true).show();
                break;
        }
    }

    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }
}

package com.xqlh.heartsmart.ui.mine.ui;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vondear.rxtools.RxPhotoTool;
import com.vondear.rxtools.RxSPTool;
import com.vondear.rxtools.view.dialog.RxDialogChooseImage;
import com.xqlh.heartsmart.Event.EventUpdateUserInfor;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.RetrofitHelper;
import com.xqlh.heartsmart.api.base.BaseObserval;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.bean.EntityUserInfor;
import com.xqlh.heartsmart.utils.CommonUtil;
import com.xqlh.heartsmart.utils.Constants;
import com.xqlh.heartsmart.utils.ProgressUtils;
import com.xqlh.heartsmart.utils.SharedPreferencesHelper;
import com.xqlh.heartsmart.utils.ContextUtils;
import com.xqlh.heartsmart.widget.TitleBar;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.vondear.rxtools.view.dialog.RxDialogChooseImage.LayoutType.TITLE;

public class MineActivity extends BaseActivity {

    @BindView(R.id.mine_iv_head)
    ImageView mine_iv_head;
    @BindView(R.id.mine_titleBar)
    TitleBar mine_titleBar;
    @BindView(R.id.mine_tv_nickname)
    TextView mine_tv_nickname;

    @BindView(R.id.mine_tv_sex)
    TextView mine_tv_sex;

    @BindView(R.id.mine_tv_birthday)
    TextView mine_tv_birthday;

    @BindView(R.id.mine_tv_phone)
    TextView mine_tv_phone;

    @BindView(R.id.mine_update_infor)
    Button mine_update_infor;

    private Uri resultUri;
    private SharedPreferencesHelper sp_login_token;

    @Override
    public int setContent() {
        return R.layout.activity_mine;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        initTtileBar();

        EventBus.getDefault().register(this);

        sp_login_token = new SharedPreferencesHelper(
                this, Constants.CHECKINFOR);
        getUserInfor();
        initView();
    }

    public void initTtileBar() {
        mine_titleBar.setTitle("我的信息");
        mine_titleBar.setTitleColor(Color.WHITE);
        mine_titleBar.setLeftText("返回");
        mine_titleBar.setLeftTextColor(Color.WHITE);
        mine_titleBar.setLeftImageResource(R.drawable.return_button);
        mine_titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void updatetCheck(EventUpdateUserInfor eventUpdateUserInfor) {
        switch (eventUpdateUserInfor.getMsg()) {
            case "updateUserInfor":
                getUserInfor();
                break;
        }
    }

    public void getUserInfor() {
        RetrofitHelper.getApiService()
                .getUserInfor(sp_login_token.getSharedPreference(Constants.LOGIN_TOKEN, "").toString().trim())
                .subscribeOn(Schedulers.io())
                .compose(this.<EntityUserInfor>bindToLifecycle())
                .compose(ProgressUtils.<EntityUserInfor>applyProgressBar(this))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityUserInfor>() {
                    @Override
                    public void onSuccess(EntityUserInfor response) {
                        if (response.getCode() == 1) {
                            mine_tv_nickname.setText(response.getResult().getName());
                            Glide.with(MineActivity.this)
                                    .load(response.getResult().getHeadimgurl())
                                    .error(R.drawable.head_default)
                                    .into(mine_iv_head);
                            if ("0".equals(response.getResult().getSex())) {
                                mine_tv_sex.setText("请设置");
                            } else if ("1".equals(response.getResult().getSex())) {
                                mine_tv_sex.setText("男");
                            } else if ("2".equals(response.getResult().getSex())) {
                                mine_tv_sex.setText("女");
                            }
                            mine_tv_birthday.setText(response.getResult().getBirthDate());
                            mine_tv_phone.setText(CommonUtil.hidePhone(response.getResult().getTelephone()));
                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }

    @OnClick({R.id.mine_update_infor})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.mine_update_infor:
                Intent intent = new Intent(MineActivity.this, UpdateUserInforActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void initView() {

        mine_iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDialogChooseImage();
            }
        });
    }

    private void initDialogChooseImage() {
        RxDialogChooseImage dialogChooseImage = new RxDialogChooseImage(this, TITLE);
        dialogChooseImage.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RxPhotoTool.GET_IMAGE_FROM_PHONE://选择相册之后的处理
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "" +
                            "选择相册data为:" + data.getData());
                    initUCrop(data.getData());
                }
                break;
            case RxPhotoTool.GET_IMAGE_BY_CAMERA://选择照相机之后的处理
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "选择相机为" + RxPhotoTool.imageUriFromCamera);
                    initUCrop(RxPhotoTool.imageUriFromCamera);
                }
                break;
            case RxPhotoTool.CROP_IMAGE://普通裁剪后的处理
                Log.i(TAG, "普通裁剪" + RxPhotoTool.cropImageUri);

                Glide.with(this).
                        load(RxPhotoTool.cropImageUri).
                        diskCacheStrategy(DiskCacheStrategy.ALL). //缓存的尺寸
                        bitmapTransform(new CropCircleTransformation(this)). //裁剪圆角
                        thumbnail(0.5f).
                        placeholder(mine_iv_head.getDrawable()).//预加载
                        dontAnimate().
                        priority(Priority.LOW).
                        error(R.drawable.head_default).//错误图片显示
                        fallback(R.drawable.head_default).//url为空的显示的 图片
                        into(mine_iv_head);
                break;
            case UCrop.REQUEST_CROP://UCrop裁剪之后的处理
                if (resultCode == RESULT_OK) {

                    resultUri = UCrop.getOutput(data);

                    Log.i(TAG, "UCrop裁剪之后的处理" + resultUri);

                    roadImageView(resultUri, mine_iv_head);

                    RxSPTool.putContent(this, "AVATAR", resultUri.toString());

                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                }
                break;
            case UCrop.RESULT_ERROR://UCrop裁剪错误之后的处理
                final Throwable cropError = UCrop.getError(data);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //从Uri中加载图片 并将其转化成File文件返回
    private File roadImageView(Uri uri, ImageView imageView) {

        Log.i(TAG, "加载图片的uri为" + uri);

        Glide.with(this).
                load(uri).
                diskCacheStrategy(DiskCacheStrategy.RESULT).
                bitmapTransform(new CropCircleTransformation(this)).
                thumbnail(0.5f).
                placeholder(mine_iv_head.getDrawable()). //待加载
                dontAnimate().
                priority(Priority.LOW).
                error(R.drawable.head_default). //错误加载
                fallback(R.drawable.head_default). // uri为空时加载的图片
                into(imageView);

        Log.i(TAG, "返回的文件名称" + new File(RxPhotoTool.getImageAbsolutePath(this, uri)));
        return (new File(RxPhotoTool.getImageAbsolutePath(this, uri)));
    }

    private void initUCrop(Uri uri) {

        //设置图片的名称
        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));

        Log.i(TAG, "图片名称" + imageName);

        Uri destinationUri = Uri.fromFile(new File(this.getCacheDir(), imageName + ".jpeg"));

        Log.i(TAG, "目的地的uri为" + destinationUri);


        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(this, R.color.color_title_bar));
        //设置状态栏颜色
//        options.setStatusBarColor(ActivityCompat.getColor(this, R.color.colorPrimaryDark));

        //开始设置
        //设置最大缩放比例
        options.setMaxScaleMultiplier(5);
        //设置图片在切换比例时的动画
        options.setImageToCropBoundsAnimDuration(666);

        //图片来源uri 设置到目的地
        UCrop.of(uri, destinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(1000, 1000)
                .withOptions(options)
                .start(this);
    }

    public void uploadHead() {

    }
}

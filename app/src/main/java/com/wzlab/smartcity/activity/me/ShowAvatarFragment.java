package com.wzlab.smartcity.activity.me;


import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.wzlab.smartcity.R;
import com.wzlab.smartcity.activity.account.Config;
import com.wzlab.smartcity.net.main.ImageUpload;
import com.wzlab.smartcity.utils.GraphProcess;

import java.io.File;
import java.util.List;

import io.reactivex.disposables.Disposable;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowAvatarFragment extends Fragment {
    private static final String TAG = "ShowAvatarFragment";

    private ImageView mIvAvatar;
    private String phone;
    private Bitmap bitmapAvatar;

    private int KEY_IMAGE_UPLOAD_SUCCESS = 7;
    private int KEY_IMAGE_DOWNLOAD_SUCCESS = 8;

    private String testPath = null;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == KEY_IMAGE_UPLOAD_SUCCESS){
                showImage();

            }else if(msg.what == KEY_IMAGE_DOWNLOAD_SUCCESS){
                showImage();
            }
        }
    };

    public ShowAvatarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_avatar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        phone = Config.getCachedPhone(getContext());

        Toolbar toolbar = view.findViewById(R.id.toolbar_avatar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        mIvAvatar = view.findViewById(R.id.iv_avatar_show);
        showImage();


        Button mBtnChangeAvatat = view.findViewById(R.id.btn_change_avatar);
        mBtnChangeAvatat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED
                ) {
                    //拥有权限，执行操作
                    initPictureSelector();
                }else{
                    //没有权限，向用户请求权限
                    ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }
            }
        });


    }

    // 显示头像及背景
    private void showImage() {

        WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;


        String avatarPath = getContext().getExternalFilesDir(null).getPath()+"/avatar/"+phone+".png";
        File file = new File(avatarPath);
        // 预设图片

        if(file.exists()){//如果有缓存，直接显示
            bitmapAvatar = BitmapFactory.decodeFile(avatarPath);

        }else{
            bitmapAvatar = BitmapFactory.decodeResource(getResources(), R.drawable.ic_avatar);

        }



        mIvAvatar.setImageBitmap(bitmapAvatar);
    //    mIvAvatar.setAdjustViewBounds(true);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mIvAvatar.getLayoutParams();
        params.width = screenWidth;
        params.height = screenWidth;
        mIvAvatar.setLayoutParams(params);
        mIvAvatar.setScaleType(ImageView.ScaleType.FIT_XY);


    }


    // PictureSelector的回调

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureConfig.CHOOSE_REQUEST && resultCode == Activity.RESULT_OK) {

            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            LocalMedia localMedia = selectList.get(0);
            String filePath = null;
            testPath = localMedia.getPath();
            Log.e(TAG, testPath);
            if(localMedia.isCut()){
                filePath  = localMedia.getCutPath();
                Log.e(TAG, filePath );
            }else if(localMedia.isCompressed()){
                filePath = localMedia.getCompressPath();
            }else {
                filePath = localMedia.getPath();

            }
            File file = new File(filePath);
            if(file.exists()){
                final Bitmap bitmapForUpload = BitmapFactory.decodeFile(filePath);
              String avatarToBase64Str = GraphProcess.bitmapToBase64(bitmapForUpload);
             //  String avatarToBase64Str = GraphProcess.ImageToBase64ByLocal(filePath);
               // String avatarToBase64Str = GraphProcess.imageToBase64(testPath);

                Log.e(TAG, avatarToBase64Str);
                ImageUpload.singleImageUpload(phone, avatarToBase64Str, new ImageUpload.SuccessCallback() {
                    @Override
                    public void onSuccess(String msg) {

                        // 图片上传成功后将cache中的图片保存到file中
                        GraphProcess.savaImage(bitmapForUpload,getContext().getExternalFilesDir(null).getPath()+"/avatar",phone);
                        clearCache();
                        Message message = new Message();
                        message.what = KEY_IMAGE_UPLOAD_SUCCESS;
                        handler.sendMessage(message);
                    }
                }, new ImageUpload.FailCallback() {
                    @Override
                    public void onFail(String msg) {
                        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                    }
                });



            }

        }
    }

    public void initPictureSelector(){
        PictureSelector.create(ShowAvatarFragment.this)
                .openGallery(PictureMimeType.ofImage())
                .minSelectNum(1)
                .imageSpanCount(4)
                .selectionMode(PictureConfig.SINGLE)
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.9f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/smartSecurityPic")// 自定义拍照保存路径,可不填
                .enableCrop(true)// 是否裁剪 true or false
                .compress(false)// 是否压缩 true or false
                .withAspectRatio(1,1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .isGif(false)// 是否显示gif图片 true or false
                .compressSavePath("/smartSecurityPic")//压缩图片保存地址
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .cropWH(400,400)// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(false) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    public void clearCache(){
        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions(getActivity());
        permissions.request(android.Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe();

    }
}

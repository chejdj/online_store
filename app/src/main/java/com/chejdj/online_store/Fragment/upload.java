package com.chejdj.online_store.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.Toolbar;

import com.chejdj.online_store.R;
import com.chejdj.online_store.tools.Goods;
import com.chejdj.online_store.tools.ImageTools;

import java.io.File;
import java.io.IOException;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

import static android.app.Activity.RESULT_CANCELED;


public class upload extends Fragment implements View.OnClickListener {
    private EditText price;
    private EditText name;
    private EditText discr;
    private PopupWindow mPopwindow;
    private Button photograph;//照相
    private Button album;//相册
    private Button log_cancel;//取消
    private ImageView img;
    private static final int CODE_GALLERY_REQUEST = 0xa0;//本地
    private static final int CODE_CAMERA_REQUEST = 0xa1;//拍照
    private String contacts;
    private File photodata;
    private Bitmap photo;
    private Uri imagePhotoUri = null;
    private static final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/com.Bmob";

    public upload() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        imagePhotoUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/com.Bmob", "image.png"));
        View view = inflater.inflate(R.layout.upload_fragment, container, false);
        Button upload = (Button) view.findViewById(R.id.upload);
        upload.setOnClickListener(this);
        Button save = (Button) view.findViewById(R.id.save);
        save.setOnClickListener(this);
        name = (EditText) view.findViewById(R.id.name);
        price = (EditText) view.findViewById(R.id.price);
        discr = (EditText) view.findViewById(R.id.content);
        img = (ImageView) view.findViewById(R.id.picture);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        name.setText("");
        price.setText("");
        discr.setText("");
        SharedPreferences preferences = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        contacts = preferences.getString("email", "DLUT");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upload:
                showpopwindow();
                break;
            case R.id.save:
                //把东西上传
                final String goods_price = price.getText().toString();
                final String goods_name = name.getText().toString();
                final String goods_discr = discr.getText().toString();
                photodata = new File(path, "image.png");
                if (goods_discr.equals("") || goods_name.equals("")
                        || goods_price.equals("") || photodata == null) {
                    Toast.makeText(getActivity(), "请填写数据完整", Toast.LENGTH_SHORT).show();
                } else {
                    final BmobFile file = new BmobFile(photodata);
                    file.upload(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            Goods goods = new Goods();
                            goods.setPicture(file);
                            goods.setPrice(goods_price);
                            goods.setName(goods_name);
                            goods.setDiscrible(goods_discr);
                            goods.setCategory("无分类");
                            goods.setContacts(contacts);
                            goods.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(getActivity(), "添加数据成功", Toast.LENGTH_SHORT).show();
                                        photodata.delete();
                                    } else {
                                        Toast.makeText(getActivity(), "添加数据失败" + e.getErrorCode() + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }
                break;
        }
    }

    private void showpopwindow() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.own_popwindow, null);
        mPopwindow = new PopupWindow(contentView, Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT, true);
        mPopwindow.setContentView(contentView);
        photograph = (Button) contentView.findViewById(R.id.photograph);
        album = (Button) contentView.findViewById(R.id.album);
        log_cancel = (Button) contentView.findViewById(R.id.own_cancel);
        photograph.setOnClickListener(new choicelistener());
        album.setOnClickListener(new choicelistener());
        log_cancel.setOnClickListener(new choicelistener());
        mPopwindow.setBackgroundDrawable(new BitmapDrawable());//设置一个空的Bitmap
        View root_view = LayoutInflater.from(getActivity()).inflate(R.layout.upload_fragment, null);
        mPopwindow.setOutsideTouchable(true);
        mPopwindow.showAtLocation(root_view, Gravity.TOP, 0, 0);
    }

    class choicelistener implements View.OnClickListener {
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.album: {
                    mPopwindow.dismiss();
                    choseHeadImageFromGallery();
                }
                break;
                case R.id.photograph: {
                    mPopwindow.dismiss();
                    choseHeadImageFromCameraCapture();
                }
                break;
                case R.id.own_cancel: {
                    mPopwindow.dismiss();
                }
                break;
            }
        }
    }

    //从相册选择
    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent();
        intentFromGallery.setType("image/*");//选择图片
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }

    //打开相机选择
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, photo);
        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent intent) {
        if (resultCode == RESULT_CANCELED) {//取消
            Toast.makeText(getActivity(), "取消", Toast.LENGTH_LONG).show();
            return;
        }
        switch (requestCode) {
            case CODE_GALLERY_REQUEST:  //图库
                if (intent != null) {
                    Log.e("upload", "图库中的图片开始上传");
                    Uri uri = intent.getData();
                    //String path = uri.getPath()
                    try {
                        final Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                        if (bitmap != null)
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    ImageTools.savePhotoToSDCard(bitmap, Environment.getExternalStorageDirectory().getAbsolutePath(), "image");
                                }
                            }).start();
                        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        img.setImageURI(uri);
                        Log.e("upload", "图库上传");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CODE_CAMERA_REQUEST:  //相机
                if (intent != null) {
                    final Bitmap bitmap = intent.getParcelableExtra("data");
                    img.setImageBitmap(bitmap);
                    img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ImageTools.savePhotoToSDCard(bitmap, Environment.getExternalStorageDirectory().getAbsolutePath(), "image");
                        }
                    }).start();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }
}

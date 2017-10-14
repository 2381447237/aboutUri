package com.okhttp.zhengbin.abouturi;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

/**
 * 作者: zhengbin on 2017/9/26.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 */

public class IvActivity extends Activity{

    private ImageView iv;
    private Button btn;
    private String  path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iv);
        verifyStoragePermissions(IvActivity.this);
        path=SharedPreferencesUtils.getString(this,"path");

        iv= (ImageView) findViewById(R.id.iv);

        if(!TextUtils.equals("",path)){
           iv.setImageURI(Uri.parse(path));
        }

        btn= (Button) findViewById(R.id.chooseBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK) {

            if (requestCode == 1) {

                Uri uri = data.getData();

                iv.setImageURI(uri);
                String path=GetPathFromUri4kitkat.getPath(this,uri);
                SharedPreferencesUtils.putString(IvActivity.this,"path",path);

                }

            }
        }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {



        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };

    public static void verifyStoragePermissions(IvActivity activity) {
        // Check if we have write permission

        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
}

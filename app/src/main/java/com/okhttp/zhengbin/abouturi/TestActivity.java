package com.okhttp.zhengbin.abouturi;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;

/**
 * Created by ZHengBin on 2017/9/25.
 */

public class TestActivity extends Activity {

    private Button btn;
    private VideoView vv;
    private String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        verifyStoragePermissions(TestActivity.this);
        path=SharedPreferencesUtils.getString(this,"path");

        btn= (Button) findViewById(R.id.chooseBtn);
        vv= (VideoView) findViewById(R.id.vv);

        if(!TextUtils.equals("",path)){
            bofang(Uri.parse(path));
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();

                bofang(uri);

        String path=GetPathFromUri4kitkat.getPath(this,uri);
                SharedPreferencesUtils.putString(TestActivity.this,"path",path);
            }

        }

    }


   private void bofang(Uri uri){

       MediaController mc=new MediaController(TestActivity.this);
       mc.setVisibility(View.GONE);
       vv.setMediaController(mc);
       vv.setVideoURI(uri);

       vv.requestFocus();

       vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
           @Override
           public void onPrepared(MediaPlayer mp) {
               vv.start();
               mp.setLooping(true);
               mp.setVolume(0,0);
           }
       });

   }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {



        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };

    public static void verifyStoragePermissions(TestActivity activity) {
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

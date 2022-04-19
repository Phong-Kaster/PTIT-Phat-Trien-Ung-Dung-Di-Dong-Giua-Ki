package com.example.stdmanager.Classroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.example.stdmanager.R;
import com.squareup.picasso.Picasso;

import java.io.File;

public class ClassroomExportPreviewActivity extends AppCompatActivity {

    /**
     * this activity - ClassroomExportPreviewActivity is used to show the JPEG file which is stored in sd card
     */

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_export_preview);

        /*Pop up a notice that user must accept request READ | WRITE EXTERNAL STORAGE*/
        ActivityCompat.requestPermissions(ClassroomExportPreviewActivity.this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE
        }, PackageManager.PERMISSION_GRANTED);


        setControl();
        setEvent();
    }

    private void setControl()
    {
        imageView = findViewById(R.id.imageView);
    }

    private void setEvent()
    {
        String path = Environment.getExternalStorageDirectory() + "/DanhSachSinhVien.jpeg";
        Log.d("path", path);
        File file = new File(path);
        Uri uri =  Uri.fromFile(file);

        Picasso.get().load(uri).into(imageView);
    }
}
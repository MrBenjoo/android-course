package com.example.ag6505.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_TAKE_THUMBNAIL = 1;
    static final int REQUEST_TAKE_PICTURE = 2;
    private Button btnThumbnail;
    private Button btnPicture;
    private ImageView ivThumbnail;
    private ImageView ivPicture;
    private Uri pictureUri;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        if(savedInstanceState!=null) {
            pictureUri = savedInstanceState.getParcelable("Uri");
        }
    }

    private void initComponents() {
        btnThumbnail = (Button)findViewById(R.id.btnThumbnail);
        btnPicture = (Button)findViewById(R.id.btnPicture);
        ivThumbnail = (ImageView)findViewById(R.id.ivThumbnail);
        ivPicture = (ImageView)findViewById(R.id.ivPicture);
        btnThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_TAKE_THUMBNAIL);
                }
            }
        });
        btnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES); // Allmänt tillgängligt
                    pictureUri = Uri.fromFile(new File(dir, "test.jpg"));
                    Log.d("picture", pictureUri.getPath());
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
                    startActivityForResult(intent, REQUEST_TAKE_PICTURE);
                }
            }
        });
    }

    public void onResume() {
        super.onResume();
        if(pictureUri!=null) {
            String pathToPicture = pictureUri.getPath();
            ivPicture.setImageBitmap(getScaled(pathToPicture, 500, 500));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("Uri", pictureUri);
    }

    private Bitmap getScaled(String pathToPicture, int targetW, int targetH) {
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathToPicture, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(pathToPicture, bmOptions);
        return bitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_TAKE_THUMBNAIL && resultCode== Activity.RESULT_OK) {
            Bitmap thumbnail = data.getParcelableExtra("data");
            ivThumbnail.setImageBitmap(thumbnail);
        } else if(requestCode==REQUEST_TAKE_PICTURE && resultCode== Activity.RESULT_OK) {
            String pathToPicture = pictureUri.getPath();
            ivPicture.setImageBitmap(getScaled(pathToPicture,500,500));
        }
    }
}

package com.example.pubchem_chemistry_handbook.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

public class AsyncTaskLoadImage  extends AsyncTask<String, String, Bitmap> {
    private final static String TAG = "AsyncTaskLoadImage";

    private WeakReference<ImageView> imageView;
    public AsyncTaskLoadImage(WeakReference<ImageView> imageView) {
        this.imageView = imageView;
    }
    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(params[0]);
            bitmap = BitmapFactory.decodeStream((InputStream)url.getContent());
        } catch (IOException e) {
            if(e.getMessage()!=null){
            Log.e(TAG, e.getMessage());}
        }
        return bitmap;
    }
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.get().setImageBitmap(bitmap);
    }

    public static Bitmap TrimBitmap(Bitmap bmp) {
        int imgHeight = bmp.getHeight();
        int imgWidth  = bmp.getWidth();


        //TRIM WIDTH - LEFT
        int startWidth = 0;
        for(int x = 0; x < imgWidth; x++) {
            if (startWidth == 0) {
                for (int y = 0; y < imgHeight; y++) {
                    if (bmp.getPixel(x, y) != Color.parseColor("#F5F5F5")) {
                        startWidth = x-25;
                        break;
                    }
                }
            } else break;
        }


        //TRIM WIDTH - RIGHT
        int endWidth  = 0;
        for(int x = imgWidth - 1; x >= 0; x--) {
            if (endWidth == 0) {
                for (int y = 0; y < imgHeight; y++) {
                    if (bmp.getPixel(x, y) != Color.parseColor("#F5F5F5")) {
                        endWidth = x+25;
                        break;
                    }
                }
            } else break;
        }



        //TRIM HEIGHT - TOP
        int startHeight = 0;
        for(int y = 0; y < imgHeight; y++) {
            if (startHeight == 0) {
                for (int x = 0; x < imgWidth; x++) {
                    if (bmp.getPixel(x, y) != Color.parseColor("#F5F5F5")) {
                        startHeight = y-25;
                        break;
                    }
                }
            } else break;
        }



        //TRIM HEIGHT - BOTTOM
        int endHeight = 0;
        for(int y = imgHeight - 1; y >= 0; y--) {
            if (endHeight == 0 ) {
                for (int x = 0; x < imgWidth; x++) {
                    if (bmp.getPixel(x, y) != Color.parseColor("#F5F5F5")) {
                        endHeight = y+25;
                        break;
                    }
                }
            } else break;
        }


        return Bitmap.createBitmap(
                bmp,
                startWidth,
                startHeight,
                endWidth - startWidth,
                endHeight - startHeight
        );

    }
}
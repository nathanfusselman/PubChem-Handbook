package com.example.pubchem_chemistry_handbook.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.util.Size;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

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
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.get().setImageBitmap(TrimBitmap(bitmap, "#F5F5F5"));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static Bitmap TrimBitmap(Bitmap bitmap, String trimColor) {
        if((bitmap.getPixel(0, 0))== Color.parseColor(trimColor)){
            int minX = Integer.MAX_VALUE;
            int maxX = 0;
            int minY = Integer.MAX_VALUE;
            int maxY = 0;

            for (int x = 0; x < bitmap.getWidth(); x++) {
                for (int y = 0; y < bitmap.getHeight(); y++) {
                    if (bitmap.getPixel(x, y) != Color.parseColor(trimColor)) {
                        if (x < minX) {
                            minX = x;
                        }
                        if (x > maxX) {
                            maxX = x;
                        }
                        if (y < minY) {
                            minY = y;
                        }
                        if (y > maxY) {
                            maxY = y;
                        }
                    }
                }
            }
            Bitmap bmp = Bitmap.createBitmap(bitmap, minX, minY, maxX - minX + 1, maxY - minY + 1);
            Bitmap background;
            if (maxX - minX + 1 < maxY - minY + 1) {
                background = Bitmap.createBitmap(maxY - minY + 10, maxY - minY + 10, Bitmap.Config.ARGB_8888);
            } else {
                background = Bitmap.createBitmap(maxX - minX + 10, maxX - minX + 10, Bitmap.Config.ARGB_8888);
            }
            background.eraseColor(android.graphics.Color.parseColor(trimColor));
            Canvas canvas = new Canvas(background);
            int centreX = (canvas.getWidth() - bmp.getWidth()) / 2;
            int centreY = (canvas.getHeight() - bmp.getHeight()) / 2;
            canvas.drawBitmap(bmp, centreX, centreY, null);
            return background;
        }
        else{
            return bitmap;
        }
        /*
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
*/
    }
}
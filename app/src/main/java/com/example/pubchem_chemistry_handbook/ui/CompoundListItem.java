package com.example.pubchem_chemistry_handbook.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.example.pubchem_chemistry_handbook.MainActivity;
import com.example.pubchem_chemistry_handbook.R;
import com.example.pubchem_chemistry_handbook.data.Compound;

import java.io.File;
import java.security.MessageDigest;

import jp.wasabeef.glide.transformations.BitmapTransformation;

public class CompoundListItem extends RecyclerView.ViewHolder {
    private Context context;
    private TextView name_TextView;
    private TextView iso_TextView;
    private ImageView image_ImageView;

    public CompoundListItem(View itemView, final RVAdapter.OnItemClickListener listener, Context context) {
        super(itemView);
        itemView.setClickable(true);
        this.context=context;
        image_ImageView = itemView.findViewById(R.id.compound_image);
        name_TextView = itemView.findViewById(R.id.compound_name);
        iso_TextView = itemView.findViewById(R.id.compound_formula);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        listener.onItemClick(pos);
                    }
                }
            }
        });
    }

    void bind(Compound compound) {
        final File directory = context.getFilesDir();
        File fullPath = new File(directory + "/Compounds/" + compound.getCID() + "/images/2d.jpg");
        if(!fullPath.exists()) {
            Glide.with(image_ImageView)
                    .asBitmap()
                    .transform(new MyTransformation())
                    .load("https://pubchem.ncbi.nlm.nih.gov/image/imagefly.cgi?cid=" + compound.getCID() + "&width=300&height=300")
                    .placeholder(R.drawable.ic_cloud_queue_black_24dp)
                    .error(R.drawable.ic_error_black_24dp)
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(image_ImageView);
        }
        else{
            Bitmap myBitmap = BitmapFactory.decodeFile(fullPath.toString());
            image_ImageView.setImageBitmap(myBitmap);
        }
        name_TextView.setText(compound.getName());
        iso_TextView.setText(compound.getFormula());
    }

    private static class MyTransformation extends BitmapTransformation{

        @Override
        protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
                String trimColor = "#F5F5F5";
                if((toTransform.getPixel(0, 0))== Color.parseColor(trimColor)){
                    int minX = Integer.MAX_VALUE;
                    int maxX = 0;
                    int minY = Integer.MAX_VALUE;
                    int maxY = 0;

                    for (int x = 0; x < toTransform.getWidth(); x++) {
                        for (int y = 0; y < toTransform.getHeight(); y++) {
                            if (toTransform.getPixel(x, y) != Color.parseColor(trimColor)) {
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
                    Bitmap bmp = Bitmap.createBitmap(toTransform, minX, minY, maxX - minX + 1, maxY - minY + 1);
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
                    return toTransform;
                }}

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

        }

        @Override
        public boolean equals(Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }
    }
}

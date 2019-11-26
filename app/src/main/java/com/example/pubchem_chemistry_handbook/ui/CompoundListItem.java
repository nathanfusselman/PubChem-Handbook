package com.example.pubchem_chemistry_handbook.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pubchem_chemistry_handbook.MainActivity;
import com.example.pubchem_chemistry_handbook.R;
import com.example.pubchem_chemistry_handbook.data.Compound;

import java.io.File;

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

}

package com.example.pubchem_chemistry_handbook.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pubchem_chemistry_handbook.R;
import com.example.pubchem_chemistry_handbook.data.Compound;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public TextView name_TextView;
    public TextView iso_TextView;
    public ImageView image_ImageView;


    public ItemViewHolder(View itemView, final RVAdapter.OnItemClickListener listener) {
        super(itemView);
        itemView.setClickable(true);
        image_ImageView = (ImageView) itemView.findViewById(R.id.compound_image);
        name_TextView = (TextView) itemView.findViewById(R.id.compound_name);
        iso_TextView = (TextView) itemView.findViewById(R.id.compound_formula);

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

    public void bind(Compound compound) {
        Glide.with(image_ImageView)
                .load("https://pubchem.ncbi.nlm.nih.gov/image/imgsrv.fcgi?cid=" + compound.getEID() +"&t=s")
                .placeholder(R.drawable.ic_cloud_queue_black_24dp)
                .error(R.drawable.ic_error_black_24dp)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(image_ImageView);
        name_TextView.setText(compound.getName());
        iso_TextView.setText(compound.getFormula());
    }

}
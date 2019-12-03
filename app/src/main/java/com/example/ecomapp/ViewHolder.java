package com.example.ecomapp;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    public ImageView mImageView;
    public TextView mPostTitle;
    public TextView mPostDesc;
    public TextView mPostGeneric;
    public TextView mPostType;
    View mview;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mview = itemView;

        mImageView = itemView.findViewById(R.id.image01);
        mPostTitle = itemView.findViewById(R.id.title01);
        mPostDesc = itemView.findViewById(R.id.description01);
        mPostGeneric = itemView.findViewById(R.id.generic01);
        mPostType = itemView.findViewById(R.id.type01);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view,getAdapterPosition());

            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view,getAdapterPosition());
                return true;
            }
        });
    }



    public void setDetails(Context ctx, String title, String image, String description, String generic, String type){
        TextView title01 = mview.findViewById(R.id.title01);
        ImageView image01 = mview.findViewById(R.id.image01);
        TextView description01 = mview.findViewById(R.id.description01);
        TextView generic01 = mview.findViewById(R.id.generic01);
        TextView type01 = mview.findViewById(R.id.type01);
        title01.setText(title);
        description01.setText(description);
        generic01.setText(generic);
        type01.setText(type);
        Picasso.get().load(image).into(image01);
    }
    private ViewHolder.ClickListener mClickListener;


    public interface ClickListener{
        void onItemClick (View view, int position);
        void onItemLongClick(View view, int position);
    }
    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener =  clickListener;
    }
}

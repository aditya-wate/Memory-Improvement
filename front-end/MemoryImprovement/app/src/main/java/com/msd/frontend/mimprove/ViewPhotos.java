package com.msd.frontend.mimprove;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ViewPhotos extends AppCompatActivity {



    Integer[] pics = {
            R.drawable.cert1,
            R.drawable.cert2,
            R.drawable.cert3,
            R.drawable.cert4,
            R.drawable.cert5
    };
    ImageView imageView;
       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photos);
           Gallery ga = (Gallery)findViewById(R.id.Gallery01);
           ga.setAdapter(new ImageAdapter(this));
   }

    public class ImageAdapter extends BaseAdapter {

        private Context ctx;
        int imageBackground;

        public ImageAdapter(Context c) {
            ctx = c;
            TypedArray ta = obtainStyledAttributes(R.styleable.Gallery1);
            imageBackground = ta.getResourceId(R.styleable.Gallery1_android_galleryItemBackground, 1);
            ta.recycle();
        }

        @Override
        public int getCount() {

            return pics.length;
        }

        @Override
        public Object getItem(int arg0) {

            return arg0;
        }

        @Override
        public long getItemId(int arg0) {

            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            ImageView iv = new ImageView(ctx);
            iv.setImageResource(pics[arg0]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            //iv.setLayoutParams(new Gallery.LayoutParams(750,1020));
            iv.setBackgroundResource(imageBackground);
            return iv;
        }

    }
    }

















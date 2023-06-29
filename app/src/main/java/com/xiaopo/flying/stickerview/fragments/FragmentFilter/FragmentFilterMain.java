package com.xiaopo.flying.stickerview.fragments.FragmentFilter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.xiaopo.flying.stickerview.R;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageView;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageRGBFilter;


public class FragmentFilterMain extends Fragment {

    RelativeLayout none,gt1,gt2,gt3,gt4,gt5;
    Bitmap bitmap1, bitmap2,icon;
    ImageView imageView;
    GPUImageView image_filter1,image_filter2,image_filter3,image_filter4,image_filter5;
    GPUImage image_edit;
    CardView cardViewEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        none=getView().findViewById(R.id.none);
        gt1=getView().findViewById(R.id.gt1);
        gt2=getView().findViewById(R.id.gt2);
        gt3=getView().findViewById(R.id.gt3);
        gt4=getView().findViewById(R.id.gt4);
        gt5=getView().findViewById(R.id.gt5);
        imageView=getActivity().findViewById(R.id.Image_editing);
        image_filter1=getView().findViewById(R.id.image_filter1);
        image_filter2=getView().findViewById(R.id.image_filter2);
        image_filter3=getView().findViewById(R.id.image_filter3);
        image_filter4=getView().findViewById(R.id.image_filter4);
        image_filter5=getView().findViewById(R.id.image_filter5);
        cardViewEdit = getActivity().findViewById(R.id.cardViewEdit);
    }

    @Override
    public void onResume() {
        super.onResume();
        bitmap1 = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        bitmap2 = bitmap1;
        image_edit = new GPUImage(getActivity());
        image_edit.setImage(bitmap1);
        icon =BitmapFactory.decodeResource(getResources(),R.drawable.filter1);
        none.setOnClickListener(this::applyFilter);
        gt1.setOnClickListener(this::applyFilter);
        gt2.setOnClickListener(this::applyFilter);
        gt3.setOnClickListener(this::applyFilter);
        gt4.setOnClickListener(this::applyFilter);
        gt5.setOnClickListener(this::applyFilter);
        image_filter1.setImage(icon);
        image_filter2.setImage(icon);
        image_filter3.setImage(icon);
        image_filter4.setImage(icon);
        image_filter5.setImage(icon);
        image_filter1.setFilter(new GPUImageRGBFilter(1.0f,0.75f,1.0f));
        image_filter2.setFilter(new GPUImageRGBFilter(0.75f,1.0f,1.0f));
        image_filter3.setFilter(new GPUImageRGBFilter(1.0f,1.0f,0.75f));
        image_filter4.setFilter(new GPUImageRGBFilter(1.0f,0.75f,0.75f));
        image_filter5.setFilter(new GPUImageRGBFilter(0.75f,0.75f,1.0f));
    }

    public void applyFilter(View view){
        switch (view.getId()){
            case R.id.none:
                imageView.setImageBitmap(bitmap1);
                break;
            case R.id.gt1:
                image_edit.setFilter(new GPUImageRGBFilter(1.0f,0.75f,1.0f));
                saveFilter();
                break;
            case R.id.gt2:
                image_edit.setFilter(new GPUImageRGBFilter(0.75f,1.0f,1.0f));
                saveFilter();
                break;
            case R.id.gt3:
                image_edit.setFilter(new GPUImageRGBFilter(1.0f,1.0f,0.75f));
                saveFilter();
                break;
            case R.id.gt4:
                image_edit.setFilter(new GPUImageRGBFilter(1.0f,0.75f,0.75f));
                saveFilter();
                break;
            case R.id.gt5:
                image_edit.setFilter(new GPUImageRGBFilter(0.75f,0.75f,1.0f));
                saveFilter();
                break;
            default:
                return;
        }
    }

    public void saveFilter(){
        image_edit.saveToPictures("temp", "editingPic.jpg", new GPUImage.OnPictureSavedListener() {
            @Override
            public void onPictureSaved(Uri uri) {
                bitmap2 = BitmapFactory.decodeFile("/storage/emulated/0/Pictures/temp/editingPic.jpg");
                imageView.setImageBitmap(bitmap2);
            }
        });
        image_edit = new GPUImage(getActivity());
        image_edit.setImage(bitmap1);
    }
}
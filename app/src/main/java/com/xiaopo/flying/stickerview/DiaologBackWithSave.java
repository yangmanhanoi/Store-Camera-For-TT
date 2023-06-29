package com.xiaopo.flying.stickerview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DiaologBackWithSave extends DialogFragment implements View.OnClickListener{
    private ListenDialog listenDialog;
    public DiaologBackWithSave(ListenDialog listenDialog)
    {
        this.listenDialog = listenDialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if(getActivity() != null)
        {
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        }
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setLayout((int) (width * 0.8), FrameLayout.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_exit_and_back, container, false);
        hideNavBar();
        v.findViewById(R.id.dialog_save_close).setOnClickListener(this);
        v.findViewById(R.id.dialog_save_photo).setOnClickListener(this);
        return v;

    }

    private void hideNavBar() {
        if(getDialog() != null && getDialog().getWindow() != null)
        {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.dialog_save_close) {
            listenDialog.onClose();
            dismiss();
        } else if (id == R.id.dialog_save_photo) {
            listenDialog.onSavePhoto();
            dismiss();
            dismiss();
        }
    }
    public interface ListenDialog {
        void onClose();
        void onSavePhoto();
    }
}

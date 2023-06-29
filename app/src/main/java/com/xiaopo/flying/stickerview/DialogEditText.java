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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;
import com.xiaopo.flying.sticker.TextSticker;

import java.util.List;

public class DialogEditText extends DialogFragment {
    private ListenDialog listenDialog;
    EditText editText;
    ImageView btn_done;
    private int width = 0;
    private int height = 0;
    private StickerView st;

    public ListenDialog getListenDialog() {
        return listenDialog;
    }

    public void setListener(ListenDialog listenDialog) {
        this.listenDialog = listenDialog;
    }

    public void setSticker(StickerView stickerView) {
        this.st = stickerView;
    }

    public interface ListenDialog {
        void onClose();

        void onAdd(String newText);

        void onClickOutside();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_add_text, container, false);
        //
        editText = v.findViewById(R.id.editText);
        btn_done = v.findViewById(R.id.btn_done);
        hideNaviBar();
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listenDialog != null)
                {
                    String editTextContent = editText.getText().toString();
                    listenDialog.onAdd(editTextContent);
                }
                dismiss();
            }
        });
        //
        return v;
    }

    private void hideNaviBar() {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow()
                    .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(listenDialog != null)
        {
            listenDialog.onClickOutside();
        }
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
        if(st != null)
        {
            Sticker tmp = st.getCurrentSticker();
            if(tmp instanceof TextSticker)
            {
                String str = ((TextSticker) tmp).getText();
                if(str != null)
                {
                    editText.setText(str);
                }
            }
        }
    }
}

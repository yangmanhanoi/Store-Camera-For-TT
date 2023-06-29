package com.xiaopo.flying.stickerview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogOpenPremium extends DialogFragment implements View.OnClickListener {
    private Context context;
    private TextView btn_close;
    private RelativeLayout btn_purchase, btn_see_ads;
    private ListenerEvent listenerEvent;
    public DialogOpenPremium(Context context, ListenerEvent listenerEvent) {
        this.context = context;
        this.listenerEvent = listenerEvent;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_open_premium, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        btn_close = v.findViewById(R.id.btn_close);
        btn_purchase = v.findViewById(R.id.btn_purchase);
        btn_see_ads = v.findViewById(R.id.btn_see_ads);
        btn_close.setOnClickListener(this);
        btn_purchase.setOnClickListener(this);
        btn_see_ads.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_close) {
            if (listenerEvent != null) {
                listenerEvent.clickClose();
            }
        } else if (view.getId() == R.id.btn_purchase) {
            if (listenerEvent != null) {
                listenerEvent.clickPurchase();
            }
        } else if (view.getId() == R.id.btn_see_ads) {
            if (listenerEvent != null) {
                listenerEvent.clickSeeAds();
            }
        }

    }

    public interface ListenerEvent{
        void clickClose();

        void clickPurchase();

        void clickSeeAds();
    }
}

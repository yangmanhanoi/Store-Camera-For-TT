package com.xiaopo.flying.sticker;

import android.view.MotionEvent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * @author wupanjie
 */

public class ZoomIconEvent implements StickerIconEvent {
  @Override public void onActionDown(StickerView stickerView, MotionEvent event) {

  }

  @Override public void onActionMove(StickerView stickerView, MotionEvent event) {
    stickerView.zoomAndRotateCurrentSticker(event);
  }

  @Override public void onActionUp(StickerView stickerView, MotionEvent event) {
    if (stickerView.getOnStickerOperationListener() != null) {
      stickerView.getOnStickerOperationListener()
          .onStickerZoomFinished(stickerView.getCurrentSticker());
    }
  }
}

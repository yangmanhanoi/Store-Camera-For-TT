package com.xiaopo.flying.sticker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.Image;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import java.lang.reflect.Modifier;

/**
 * @author wupanjie
 */
public class DrawableSticker extends Sticker {

  private Drawable drawable;
  private Rect realBounds;
  private int StickerColor = Color.WHITE;
  private int posColor = 0;
  private Paint bitmapPaint;

  public DrawableSticker(Drawable drawable) {
    this.drawable = drawable;
    realBounds = new Rect(0, 0, getWidth(), getHeight());

    bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    bitmapPaint.setFilterBitmap(true);
    bitmapPaint.setAntiAlias(true);
    bitmapPaint.setDither(true);
    bitmapPaint.setStyle(Paint.Style.FILL);
    bitmapPaint.setStrokeJoin(Paint.Join.ROUND);
    bitmapPaint.setStrokeWidth(100);
  }

  @NonNull @Override public Drawable getDrawable() {
    return drawable;
  }

  @Override public DrawableSticker setDrawable(@NonNull Drawable drawable) {
    this.drawable = drawable;
    return this;
  }


  @Override public void draw(@NonNull Canvas canvas) {
    canvas.save();
    canvas.concat(getMatrix());
    drawable.setBounds(realBounds);
    drawable.draw(canvas);
    canvas.restore();
  }

  @NonNull @Override public DrawableSticker setAlpha(@IntRange(from = 0, to = 255) int alpha) {
    drawable.setAlpha(alpha);
    return this;
  }

  public int getAlpha()
  {
    return drawable.getAlpha();
  }
  @Override public int getWidth() {
    return drawable.getIntrinsicWidth();
  }

  @Override public int getHeight() {
    return drawable.getIntrinsicHeight();
  }

  @Override public void release() {
    super.release();
    if (drawable != null) {
      drawable = null;
    }
  }
  public void setColorSticker(int colorSticker) {
    this.StickerColor = colorSticker;
    bitmapPaint.setColor(colorSticker);
    drawable.setColorFilter(colorSticker, PorterDuff.Mode.MULTIPLY);
  }
  public void setDrawableStroke(int colorSticker, float width)
  {
    bitmapPaint.setStrokeWidth(width);
  }
  public int getColorSticker() {
    return StickerColor;
  }

  public int getPosColor() {
    return posColor;
  }
}

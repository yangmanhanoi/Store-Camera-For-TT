package com.xiaopo.flying.sticker;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;

/**
 * Customize your sticker with text and image background.
 * You can place some text into a given region, however,
 * you can also add a plain text sticker. To support text
 * auto resizing , I take most of the code from AutoResizeTextView.
 * See https://adilatwork.blogspot.com/2014/08/android-textview-which-resizes-its-text.html
 * Notice: It's not efficient to add long text due to too much of
 * StaticLayout object allocation.
 * Created by liutao on 30/11/2016.
 */

public class TextSticker extends Sticker {

  /**
   * Our ellipsis string.
   */
  private static final String mEllipsis = "\u2026";
//
  private Boolean bold = false;
  private Boolean itali = false;
  private Boolean underline = false;
  //
  private final Context context;
  public final Rect realBounds;
  public final Rect textRect;
  public final TextPaint textPaint; // đang bỏ final nếu có gì thêm lại
  public Drawable drawable;
  public StaticLayout staticLayout;
  private Layout.Alignment alignment;
  private String text;
  private int alpha;
  private int color;
  private Typeface typeface;

  public Typeface getTypeface() {
    return typeface;
  }

  public int getColor() {
    return color;
  }

  public void setColor(int color) {
    this.color = color;
  }

  public int getAlpha() {
    return alpha;
  }

  /**
   * Upper bounds for text size.
   * This acts as a starting point for resizing.
   */
  private float maxTextSizePixels;

  public float getMaxTextSizePixels() {
    return maxTextSizePixels;
  }

  public void setMaxTextSizePixels(float maxTextSizePixels) {
    this.maxTextSizePixels = maxTextSizePixels;
  }

  /**
   * Lower bounds for text size.
   */
  private float minTextSizePixels;

  /**
   * Line spacing multiplier.
   */
  private float lineSpacingMultiplier = 1.0f;

  /**
   * Additional line spacing.
   */
  private float lineSpacingExtra = 0.0f;

  public TextSticker(@NonNull Context context) {
    this(context, null);
  }

  public TextSticker(@NonNull Context context, @Nullable Drawable drawable) {
    this.context = context;
    this.drawable = drawable;
    if (drawable == null) {
      this.drawable = ContextCompat.getDrawable(context, R.drawable.sticker_transparent_background);
    }
    //
    widthTextBox = this.drawable.getIntrinsicWidth();
    heightTextBox = this.drawable.getIntrinsicHeight();
    //
    textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
    realBounds = new Rect(0, 0, getWidth(), getHeight());
    textRect = new Rect(0, 0, getWidth(), getHeight());
    minTextSizePixels = convertSpToPx(6);
    maxTextSizePixels = convertSpToPx(32);
    alignment = Layout.Alignment.ALIGN_CENTER;
    textPaint.setTextSize(maxTextSizePixels);
  }

  @Override public void draw(@NonNull Canvas canvas) {
    Matrix matrix = getMatrix();
    canvas.save();
    canvas.concat(matrix);
    if (drawable != null) {
      drawable.setBounds(realBounds);
      drawable.draw(canvas);
    }
    canvas.restore();

    canvas.save();
    canvas.concat(matrix);
    if (textRect.width() == getWidth()) {
      int dy = getHeight() / 2 - staticLayout.getHeight() / 2;
      // center vertical
      canvas.translate(0, dy);
    } else {
      int dx = textRect.left;
      int dy = textRect.top + textRect.height() / 2 - staticLayout.getHeight() / 2;
      canvas.translate(dx, dy);
    }
    staticLayout.draw(canvas);
    canvas.restore();
  }

  @Override public int getWidth() {
    return widthTextBox;
  }

  @Override public int getHeight() {
    return heightTextBox;
  }

  @Override public void release() {
    super.release();
    if (drawable != null) {
      drawable = null;
    }
  }

  @NonNull @Override public TextSticker setAlpha(@IntRange(from = 0, to = 255) int alpha) {
    textPaint.setAlpha(alpha);
    this.alpha = alpha;
    return this;
  }
  @NonNull @Override public Drawable getDrawable() {
    return drawable;
  }

  @Override public TextSticker setDrawable(@NonNull Drawable drawable) {
    this.drawable = drawable;
    realBounds.set(0, 0, getWidth(), getHeight());
    textRect.set(0, 0, getWidth(), getHeight());
    return this;
  }

  @NonNull public TextSticker setDrawable(@NonNull Drawable drawable, @Nullable Rect region) {
    this.drawable = drawable;
    realBounds.set(0, 0, getWidth(), getHeight());
    if (region == null) {
      textRect.set(0, 0, getWidth(), getHeight());
    } else {
      textRect.set(region.left, region.top, region.right, region.bottom);
    }
    return this;
  }

  @NonNull public TextSticker setTypeface(@Nullable Typeface typeface) {
    textPaint.setTypeface(typeface);
    this.typeface = typeface;
    return this;
  }

  @NonNull public TextSticker setTextColor(@ColorInt int color) {
    textPaint.setColor(color);
    this.color = color;
    return this;
  }

  @NonNull public TextSticker setTextAlign(@NonNull Layout.Alignment alignment) {
    this.alignment = alignment;
    return this;
  }

  @NonNull public TextSticker setMaxTextSize(@Dimension(unit = Dimension.SP) float size) {
    textPaint.setTextSize(convertSpToPx(size));
    maxTextSizePixels = textPaint.getTextSize();
    return this;
  }

  /**
   * Sets the lower text size limit
   *
   * @param minTextSizeScaledPixels the minimum size to use for text in this view,
   * in scaled pixels.
   */
  @NonNull public TextSticker setMinTextSize(float minTextSizeScaledPixels) {
    minTextSizePixels = convertSpToPx(minTextSizeScaledPixels);
    return this;
  }

  @NonNull public TextSticker setLineSpacing(float add, float multiplier) {
    lineSpacingMultiplier = multiplier;
    lineSpacingExtra = add;
    return this;
  }

  @NonNull public TextSticker setText(@Nullable String text) {
    this.text = text;
    return this;
  }

  @Nullable public String getText() {
    return text;
  }

  /**
   * Resize this view's text size with respect to its width and height
   * (minus padding). You should always call this method after the initialization.
   */
  @NonNull public TextSticker resizeText() {
    final int availableHeightPixels = textRect.height();
    final int availableWidthPixels = textRect.width();
    final CharSequence text = getText();

    // Safety check
    // (Do not resize if the view does not have dimensions or if there is no text)
    if (text == null
        || text.length() <= 0
        || availableHeightPixels <= 0
        || availableWidthPixels <= 0
        || maxTextSizePixels <= 0) {
      return this;
    }

    float targetTextSizePixels = maxTextSizePixels;
    int targetTextHeightPixels =
        getTextHeightPixels(text, availableWidthPixels, targetTextSizePixels);
    // Until we either fit within our TextView
    // or we have reached our minimum text size,
    // incrementally try smaller sizes
    while (targetTextHeightPixels > availableHeightPixels
        && targetTextSizePixels > minTextSizePixels) {
      targetTextSizePixels = Math.max(targetTextSizePixels - 2, minTextSizePixels);

      targetTextHeightPixels =
          getTextHeightPixels(text, availableWidthPixels, targetTextSizePixels);
    }
    // If we have reached our minimum text size and the text still doesn't fit,
    // append an ellipsis
    // (NOTE: Auto-ellipsize doesn't work hence why we have to do it here)
    if (targetTextSizePixels == minTextSizePixels
        && targetTextHeightPixels > availableHeightPixels) {
      // Make a copy of the original TextPaint object for measuring
      TextPaint textPaintCopy = new TextPaint(textPaint);
      textPaintCopy.setTextSize(targetTextSizePixels);

      // Measure using a StaticLayout instance
      StaticLayout staticLayout =
          new StaticLayout(text, textPaintCopy, availableWidthPixels, Layout.Alignment.ALIGN_NORMAL,
              lineSpacingMultiplier, lineSpacingExtra, false);

      // Check that we have a least one line of rendered text
      if (staticLayout.getLineCount() > 0) {
        // Since the line at the specific vertical position would be cut off,
        // we must trim up to the previous line and add an ellipsis
        int lastLine = staticLayout.getLineForVertical(availableHeightPixels) - 1;

        if (lastLine >= 0) {
          int startOffset = staticLayout.getLineStart(lastLine);
          int endOffset = staticLayout.getLineEnd(lastLine);
          float lineWidthPixels = staticLayout.getLineWidth(lastLine);
          float ellipseWidth = textPaintCopy.measureText(mEllipsis);

          // Trim characters off until we have enough room to draw the ellipsis
          while (availableWidthPixels < lineWidthPixels + ellipseWidth) {
            endOffset--;
            lineWidthPixels =
                textPaintCopy.measureText(text.subSequence(startOffset, endOffset + 1).toString());
          }

          setText(text.subSequence(0, endOffset) + mEllipsis);
        }
      }
    }
    textPaint.setTextSize(targetTextSizePixels);
    this.maxTextSizePixels = targetTextSizePixels;
    staticLayout =
        new StaticLayout(this.text, textPaint, textRect.width(), alignment, lineSpacingMultiplier,
            lineSpacingExtra, true);
    return this;
  }
  private StaticLayout tempStaticLayout;
  private Boolean isFitHei(int countLine, float targetTextSizePixels, int avaiWi, int avaiHei) {
    textPaint.setTextSize(targetTextSizePixels);
    tempStaticLayout = new StaticLayout(this.text, this.textPaint, avaiWi, this.alignment,
            this.lineSpacingMultiplier, this.lineSpacingExtra, true);
    return tempStaticLayout.getHeight() <= avaiHei && tempStaticLayout.getLineCount() <= countLine;
  }
  private int countLines(String str) {
    String[] lines = str.split("\r\n|\r|\n");
    return lines.length;
  }
//
public void getFitTextSize(String text, float width, float height) {
  // Set the initial text size
  float textSize = maxTextSizePixels;

  // Get the text bounds at the initial text size
  Rect bounds = new Rect();
  textPaint.setTextSize(textSize);
  textPaint.setLetterSpacing(0);
  textPaint.getTextBounds(text, 0, text.length(), bounds);

  // Adjust the text size until it fits within the given width and height
  while (bounds.width() > width || bounds.height() > height) {
    textSize--;
    textPaint.setTextSize(textSize);
    textPaint.setLetterSpacing(0);
    textPaint.getTextBounds(text, 0, text.length(), bounds);
  }
  textPaint.setTextSize(textSize);
}

  ///
  public void resize2Rect(int avaiWi, int avaHe)
  {
    CharSequence text =getText();
    if(text == null || text.length() <= 0 || this.drawable.getIntrinsicHeight() <= 0 || this.drawable.getIntrinsicWidth() <= 0 || maxTextSizePixels <= 0) return;
    double x0 = System.currentTimeMillis();
    caculateSizeHe((String) text, avaiWi, avaHe);
    double x1 = System.currentTimeMillis(); //= 100 - 700
    Log.d("TAG", "TimeCaculate >>>caculateSizeHe: " + (x1 - x0));
    caculateSizeWi((String) text, avaiWi);
    textPaint.isElegantTextHeight();

    //staticLayout = new StaticLayout(this.text, textPaint, (int) textPaint.measureText(maxLengthString), Layout.Alignment.ALIGN_NORMAL, lineSpacingMultiplier, lineSpacingExtra, true);
    staticLayout = new StaticLayout(this.text, textPaint, avaiWi, Layout.Alignment.ALIGN_NORMAL, lineSpacingMultiplier, lineSpacingExtra, true);
    setWidthTextBox(staticLayout.getWidth());
    setHeightTextBox(staticLayout.getHeight());
    textRect.set(0, 0,avaiWi, avaHe);
    realBounds.set(0, 0,avaiWi, avaHe);

  }
//
  public int widthTextBox;

  public Context getContext() {
    return context;
  }

  public int getWidthTextBox() {
    return widthTextBox;
  }

  public int getHeightTextBox() {
    return heightTextBox;
  }

  public int heightTextBox;

  public void setWidthTextBox(int width) {
    this.widthTextBox = width;
  }
  public void setHeightTextBox(int height){
    this.heightTextBox = height;
  }
  //1 wi
  private float maxLetterSpace = 1f;
  private float minLetterSpace = 0f;
  private String maxLengthString;
  private String getLongestLine(String s, TextPaint mTextPaint)
  {
    TextPaint copyPaint =new TextPaint(mTextPaint);
    copyPaint.setTextSize(20);
    if(text == null) return null;
    String[] lines = text.split("\n" +
            "|\n" +
            "|\n");
    if(lines.length == 0) return null;
    int max = 0, pos = 0;
    for(int i = 0; i < lines.length; i++)
    {
      if(mTextPaint.measureText(lines[i]) > max)
      {
        max = (int) mTextPaint.measureText(lines[i]);
        pos = i;
      }
    }
    return lines[pos];
  }
  private void caculateSizeWi(String text, int avaiWi) {
    maxLengthString = getLongestLine(text, textPaint);
    if(maxLengthString != null)
    {
      float targetLetterSpace = 0.7f;
      Boolean isFit = isFitWi(targetLetterSpace, avaiWi);
      while (!isFit && targetLetterSpace > minLetterSpace) {
        targetLetterSpace = Math.max(targetLetterSpace - 0.1f, minLetterSpace);
        isFit = isFitWi(targetLetterSpace, avaiWi);
      }
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        textPaint.setLetterSpacing(targetLetterSpace);
      }
    }
  }

  private Boolean isFitWi(float targetLetterSpace, int avaiWi) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      textPaint.setLetterSpacing(targetLetterSpace);
    }
    return textPaint.measureText(maxLengthString) <= avaiWi;
  }

  private void caculateSizeHe(String text, int avaiWi, int avaHe) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      textPaint.setLetterSpacing(0f);
    }
    int countline = countLines(text);
    float targetTextSizePixels = 180f;
    Boolean isFit = isFitHei(countline, targetTextSizePixels, avaiWi, avaHe);
    while(!isFit && targetTextSizePixels >minTextSizePixels)
    {
      targetTextSizePixels = Math.max(targetTextSizePixels - 6f, minTextSizePixels);
      isFit = isFitHei(countline, targetTextSizePixels, avaiWi, avaHe);
    }
    textPaint.setTextSize(targetTextSizePixels + 2f);
  }
///
public Boolean getBold() {
  return bold;
}
  public void setBold(Boolean bold) {
    textPaint.setFakeBoldText(bold);
    this.bold = bold;
}
  public Boolean getItali() {
    return itali;
  }

  public void setItali(Boolean itali) {
    if (itali) {
      textPaint.setTextSkewX(-0.25f);
    } else {
      textPaint.setTextSkewX(0);
    }
    this.itali = itali;
  }
  public void setUnderline(Boolean underline) {
    textPaint.setUnderlineText(underline);
    this.underline = underline;
  }
  /**
   * @return lower text size limit, in pixels.
   */
  public float getMinTextSizePixels() {
    return minTextSizePixels;
  }

  /**
   * Sets the text size of a clone of the view's {@link TextPaint} object
   * and uses a {@link StaticLayout} instance to measure the height of the text.
   *
   * @return the height of the text when placed in a view
   * with the specified width
   * and when the text has the specified size.
   */
  protected int getTextHeightPixels(@NonNull CharSequence source, int availableWidthPixels,
      float textSizePixels) {
    textPaint.setTextSize(textSizePixels);
    // It's not efficient to create a StaticLayout instance
    // every time when measuring, we can use StaticLayout.Builder
    // since api 23.
    StaticLayout staticLayout =
        new StaticLayout(source, textPaint, availableWidthPixels, Layout.Alignment.ALIGN_NORMAL,
            lineSpacingMultiplier, lineSpacingExtra, true);
    return staticLayout.getHeight();
  }

  /**
   * @return the number of pixels which scaledPixels corresponds to on the device.
   */
  private float convertSpToPx(float scaledPixels) {
    return scaledPixels * context.getResources().getDisplayMetrics().scaledDensity;
  }
}

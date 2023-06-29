package com.xiaopo.flying.stickerview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.xiaopo.flying.sticker.Sticker;

public class StickerInfor implements Cloneable{
    //General Infor
    public static final String TYPE_STICKER_IMG = "IMG";
    public static final String TYPE_STICKER_TXT = "TXT";
    public static final String DEFAUT_COLOR = "#FFFFFF";

    public static final int SHAPE_DEFAUT = 0; //hinh chu nhat
    public static final int SHAPE_CIRCLE = 1;
    public static final int SHAPE_DACBIET_TEMPLATE = 3; //hinh khac

    public static final int DRAWABLE_NONE = 10; //hinh khac

    private boolean isBold = false;
    private boolean isU = false;
    private boolean isI = false;

    int ID;
    private float angle;
    private Typeface font;
    private String uriPath;
    private String textContent;
    private int color;
    private int opacity;
    private String fontPos;
    private float scale;
    public String stickerPath;
    private Drawable drawableStatic = null;
    private Constraint constraint;
    // Position
    private float x,y;
    // Large
    private float width, height;


    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Typeface getFont() {
        return font;
    }

    public void setFont(Typeface font) {
        this.font = font;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getUriPath() {
        return uriPath;
    }

    public void setUriPath(String uriPath) {
        this.uriPath = uriPath;
    }

    //
    public boolean isBold() {
        return isBold;
    }

    public void setBold(boolean bold) {
        isBold = bold;
    }

    public boolean isU() {
        return isU;
    }

    public void setU(boolean u) {
        isU = u;
    }

    public boolean isI() {
        return isI;
    }

    public void setI(boolean i) {
        isI = i;
    }


    ///

    public Drawable getDrawableStatic() {
        return drawableStatic;
    }

    public void setDrawableStatic(Drawable drawableStatic) {
        this.drawableStatic = drawableStatic;
    }

    public Constraint getConstraint() {
        return constraint;
    }

    public void setConstraint(Constraint constraint) {
        this.constraint = constraint;
    }

    //
    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float strokePaintWidth = 0;
    public int strokePaintColor = Color.BLACK;


    public String getStickerPath() {
        return stickerPath;
    }

    public void setStickerPath(String stickerPath) {
        this.stickerPath = stickerPath;
    }

    public float getStrokePaintWidth() {
        return strokePaintWidth;
    }

    public void setStrokePaintWidth(float strokePaintWidth) {
        this.strokePaintWidth = strokePaintWidth;
    }

    public int getStrokePaintColor() {
        return strokePaintColor;
    }

    public void setStrokePaintColor(int strokePaintColor) {
        this.strokePaintColor = strokePaintColor;
    }


    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }


    public int getOpacity() {
        return opacity;
    }

    public void setOpacity(int opacity) {
        this.opacity = opacity;
    }

    public String getFontPos() {
        return fontPos;
    }

    public void setFontPos(String fontPos) {
        this.fontPos = fontPos;
    }
//
    // Constructor
    public StickerInfor(){}
    public StickerInfor(int ID,String textContent, int color, Typeface font, int opacity, float x, float y, float width, float height, Drawable drawable, float angle, float scale)
    {
        this.scale = scale;
        this.angle = angle;
        this.ID = ID;
        this.font = font;
        this.color = color;
        this.textContent = textContent;
        this.opacity = opacity;
        this.drawableStatic = drawable;
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }
    public StickerInfor(StickerInfor A)
    {
        this.scale = A.scale;
        this.angle = A.angle;
        this.ID = A.ID;
        this.font = A.font;
        this.color = A.color;
        this.textContent = A.textContent;
        this.opacity = A.opacity;
        this.drawableStatic = A.drawableStatic;
        this.x = A.x; this.y = A.y; this.height = A.height; this.width = A.width;
    }
    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

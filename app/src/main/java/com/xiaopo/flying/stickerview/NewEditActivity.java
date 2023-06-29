package com.xiaopo.flying.stickerview;

import static android.graphics.Color.WHITE;
import static android.graphics.Color.YELLOW;
import static java.util.Arrays.*;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.SpriteFactory;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.RotatingPlane;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.xiaopo.flying.sticker.BitmapStickerIcon;
import com.xiaopo.flying.sticker.DeleteIconEvent;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.FlipHorizontallyEvent;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;
import com.xiaopo.flying.sticker.TextSticker;
import com.xiaopo.flying.sticker.ZoomIconEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.attribute.GroupPrincipal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewEditActivity extends AppCompatActivity implements View.OnClickListener, Fragment_Second.fragmentSecondOutput,
        FragmentDetailTextFont.ischosenFont, FragmentDetailAlignment.iClickAlignment, FragmentTextSize.onTextSizeChange, ColorFragment.isColorChosenSent, Fragment_Opacity.onOpacityChange, onBackKeyListener, isChangedAccept{
    // FontFragment.onFontsent
    FrameLayout func_fragment, main_bottom;
    ImageView ic_close, ic_download, img_back;
    ImageView layer, addText, sticker, addPhoto, template;
    TextView content;
    Fragment FirstFragment, SecondFragment, LayerFragment, StickerFragment, photoFragment;
    FragmentDetailTextFont fragmentDetailTextFont = new FragmentDetailTextFont();
    //FragmentDetailAlignment fragmentDetailAlignment = new FragmentDetailAlignment();
    FragmentTextSize fragmentTextSize = new FragmentTextSize();
    ColorFragment colorFragment = new ColorFragment();
    NonFragment nonFragment = new NonFragment();
    Fragment_Opacity fragment_opacity = new Fragment_Opacity();
    FontFragment fontFragment = new FontFragment();
    FragmentLayer fragmentLayer = new FragmentLayer();
    DisplayMetrics displayMetrics = new DisplayMetrics();
    StickerView stickerView;
    LinearLayout changeSticker;
    Context context;
    FrameLayout khung;
    List<StickerInfor> listStickerInfor = new ArrayList<>();
    List<StickerInfor> oldListStickerInfor = new ArrayList<>();
    List<Sticker> list = new ArrayList<>();
    List<TextSticker> listTextSticker = new ArrayList<>();
    Sticker st;
    DialogEditText dialogEditText = new DialogEditText();
    DetailStickerChange detailStickerChange = new DetailStickerChange();
    DialogLoadingTemplate dialogLoadingTemplate;
    public TextSticker textSticker;
    private int count = 0;
    public DrawableSticker drawableSticker;
    AssetManager assetManager;
    String[] fileList;
    ImageView grid;
    int screenWidth;
    Intent intent;
    DiaologBackWithSave diaologBackWithSave;
    ProgressDialog progressDialog;
    public static int LAYER_TAG = 0;
    public static int TEXT_TAG = 1;
    public static int STICKER_TAG = 2;
    public static int PHOTO_TAG = 3;
    public boolean ischanged = false;
    ProgressDialog DialogDownloadTemplates;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_layout);
//
        khung = findViewById(R.id.khung);
        assetManager  = getAssets();
        try {
            fileList = assetManager.list("fonts");
        } catch (IOException e) {
            e.printStackTrace();
        }
        grid = findViewById(R.id.grid);
        template = findViewById(R.id.newImageView);
        content = findViewById(R.id.content);
        ic_close = findViewById(R.id.ic_close);
        ic_download = findViewById(R.id.download);
        main_bottom = findViewById(R.id.main_bottom_container);
        stickerView = findViewById(R.id.sticker);
        changeSticker = findViewById(R.id.change_sticker);
        context = getApplicationContext();

//
        layer = changeSticker.findViewById(R.id.btn_layer);
        addText = changeSticker.findViewById(R.id.btn_addtext);
        sticker = changeSticker.findViewById(R.id.btn_sticker);
        addPhoto = changeSticker.findViewById(R.id.btn_addphoto);
//
        layer.setOnClickListener(this);
        addText.setOnClickListener(this);
        sticker.setOnClickListener(this);
        addPhoto.setOnClickListener(this);
        ic_close.setOnClickListener(this);
        ic_download.setOnClickListener(this);
//
        FirstFragment = new Fragment_First();
        SecondFragment = new Fragment_Second();
        LayerFragment = new FragmentLayer();
        StickerFragment = new StickerFragment();
        photoFragment = new FragmentPhoto();
        fontFragment = new FontFragment();
        //
        colorFragment.setSelected(this);
        colorFragment.setChangeSelected(this);
        fragment_opacity.setSelected(this);
        fragment_opacity.setChangeSelected(this);
        fragmentTextSize.setSelected(this);
        fontFragment.setSelected(this);
//
        initView();
        String result = getIntent().getStringExtra("toNewEdit");
        if(result != null)
        {
            textSticker.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/" + result + ".ttf"));
        }
        if(getFragmentManager() == null) changeSticker.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("resume", "yes");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        changeSticker.setVisibility(View.VISIBLE);
    }

    //
    private Fragment getFragment(int type) {
        if(type == LAYER_TAG) return LayerFragment;
        else if(type == TEXT_TAG) return SecondFragment;
        else if(type == STICKER_TAG) return StickerFragment;
        else return photoFragment;
    }
    public void customStickerView()
    {
        BitmapStickerIcon deleteIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_close_white_18dp),
                BitmapStickerIcon.LEFT_TOP);
        deleteIcon.setIconEvent(new DeleteIconEvent());

        BitmapStickerIcon zoomIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_scale_white_18dp),
                BitmapStickerIcon.RIGHT_BOTOM);
        zoomIcon.setIconEvent(new ZoomIconEvent());

        BitmapStickerIcon flipIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_flip_white_18dp),
                BitmapStickerIcon.RIGHT_TOP);
        flipIcon.setIconEvent(new FlipHorizontallyEvent());

        BitmapStickerIcon heartIcon =
                new BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp),
                        BitmapStickerIcon.LEFT_BOTTOM);
        heartIcon.setIconEvent(new HelloIconEvent());

        stickerView.setIcons(asList(deleteIcon, zoomIcon, flipIcon, heartIcon));
    }
    private void initView()
    {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) stickerView.getLayoutParams();
        layoutParams.height = screenWidth*16/13;
        stickerView.setLayoutParams(layoutParams);
        // Load Template
        intent = this.getIntent();
        DialogDownloadTemplates = new ProgressDialog(this);
        DialogDownloadTemplates.setTitle("Loading Template ...");
        DialogDownloadTemplates.setMessage("On Progressing, Please wait ...");
        Sprite rotatePlane = new RotatingPlane();
        rotatePlane.setColor(WHITE);

        DialogDownloadTemplates.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6F83E9")));
        DialogDownloadTemplates.setIndeterminateDrawable(rotatePlane);
        DialogDownloadTemplates.getWindow().setGravity(Gravity.CENTER);
        if (DialogDownloadTemplates.isShowing()) {
            DialogDownloadTemplates.dismiss();
        }
        DialogDownloadTemplates.show();
        new onDownloadTemplates().execute();
        //
        stickerView.setBackgroundColor(WHITE);
        stickerView.setLocked(false);
        stickerView.setConstrained(true);
        stickerView.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
            @Override
            public void onStickerAdded(@NonNull Sticker sticker) {
                list.add(sticker);
                if(sticker instanceof  TextSticker)
                {
                    String textContent = ((TextSticker) sticker).getText();
                    int color = ((TextSticker) sticker).getColor();
                    Typeface txtTypeface = ((TextSticker) sticker).getTypeface();
                    int alpha = ((TextSticker) sticker).getAlpha();
                    float dx = stickerView.getStickerPoints(sticker)[0];
                    float dy = stickerView.getStickerPoints(sticker)[1];
                    Drawable txtDrawable = sticker.getDrawable();
                    float width = sticker.getWidth();
                    float height = sticker.getHeight();
                    float angle = sticker.getCurrentAngle();
                    float scale = sticker.getCurrentScale();
                    StickerInfor stickerInfor = new StickerInfor(count,textContent, color, txtTypeface, alpha, dx, dy, width, height, txtDrawable, angle, scale);
                    StickerInfor oldStikerInfor = new StickerInfor(count,textContent, color, txtTypeface, alpha, dx, dy, width, height, txtDrawable, angle, scale);
                    listStickerInfor.add(stickerInfor);
                    oldListStickerInfor.add(oldStikerInfor);
                    count++;
                }
                else if(sticker instanceof  DrawableSticker)
                {
                    int color = ((DrawableSticker) sticker).getColorSticker();
                    int alpha = ((DrawableSticker) sticker).getAlpha();
                    float dx = stickerView.getStickerPoints(sticker)[0];
                    float dy = stickerView.getStickerPoints(sticker)[1];
                    Drawable txtDrawable = sticker.getDrawable();
                    float width = sticker.getWidth();
                    float height = sticker.getHeight();
                    float angle = sticker.getCurrentAngle();
                    float scale = sticker.getCurrentScale();
                    StickerInfor stickerInfor = new StickerInfor(count,null, color, null, alpha, dx, dy, width, height, txtDrawable, angle, scale);
                    StickerInfor oldStikerInfor = new StickerInfor(count,null, color, null, alpha, dx, dy, width, height, txtDrawable, angle, scale);
                    listStickerInfor.add(stickerInfor);
                    oldListStickerInfor.add(oldStikerInfor);
                    count++;
                }
            }

            @Override
            public void onStickerClicked(@NonNull Sticker sticker) {
                stickerView.handlingSticker = sticker;
                customStickerView();
                if(sticker instanceof TextSticker)
                {
                    textSticker = (TextSticker) stickerView.getCurrentSticker();
                    changeSticker.setVisibility(View.INVISIBLE);
                    FragmentManager fm = getSupportFragmentManager();
                    //fm.beginTransaction().replace(R.id.main_bottom_container, SecondFragment).addToBackStack(Fragment_Second.TAG).commit();
                    fm.beginTransaction().replace(R.id.main_bottom_container, SecondFragment).commit();
                }
                else if(sticker instanceof  DrawableSticker)
                {
                    drawableSticker = (DrawableSticker) stickerView.getCurrentSticker();
                    changeSticker.setVisibility(View.GONE);
                    FragmentManager fm = getSupportFragmentManager();
                    //fm.beginTransaction().replace(R.id.main_bottom_container, detailStickerChange).addToBackStack(DetailStickerChange.TAG).commit();
                    fm.beginTransaction().replace(R.id.main_bottom_container, detailStickerChange).commit();
                }
                grid.setVisibility(View.GONE);
            }

            @Override
            public void onStickerDeleted(@NonNull Sticker sticker) {
                grid.setVisibility(View.GONE);
                int id = stickerView.stickers.indexOf(sticker);
                Log.e("id", id + "");
                list.remove(sticker);
                oldListStickerInfor.remove(stickerView.stickers.indexOf(sticker));
                listStickerInfor.remove(stickerView.stickers.indexOf(sticker));
                stickerView.stickers.remove(sticker);
                stickerView.invalidate();
            }

            @Override
            public void onStickerDragFinished(@NonNull Sticker sticker) {
                grid.setVisibility(View.GONE);
            }

            @Override
            public void onStickerTouchedDown(@NonNull Sticker sticker) {
                FragmentManager fm = getSupportFragmentManager();
                changeSticker.setVisibility(View.GONE);
                st = stickerView.getCurrentSticker();
                if(st instanceof TextSticker) {
                    fm.beginTransaction().replace(R.id.main_bottom_container, SecondFragment).addToBackStack(Fragment_Second.TAG).commit();
                }
                else if(st instanceof DrawableSticker)
                {
                    fm.beginTransaction().replace(R.id.main_bottom_container, detailStickerChange).addToBackStack(DetailStickerChange.TAG).commit();
                }
                grid.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStickerZoomFinished(@NonNull Sticker sticker) {
                grid.setVisibility(View.GONE);
            }

            @Override
            public void onStickerFlipped(@NonNull Sticker sticker) {
                grid.setVisibility(View.GONE);
            }

            @Override
            public void onStickerDoubleTapped(@NonNull Sticker sticker) {

                if(!dialogEditText.isAdded() && sticker instanceof TextSticker)
                {
                    dialogEditText.setSticker(stickerView);
                    FragmentManager fm = getSupportFragmentManager();
                    dialogEditText.setListener(new DialogEditText.ListenDialog() {
                        @Override
                        public void onClose() {

                        }

                        @Override
                        public void onAdd(String newText) {
                            Sticker st = stickerView.getCurrentSticker();
                            if(st instanceof TextSticker)
                            {
                                ((TextSticker) st).setText(newText);
                                ((TextSticker) st).resizeText();
                                stickerView.invalidate();
                            }
                        }

                        @Override
                        public void onClickOutside() {
                        }
                    });
                    dialogEditText.show(fm, "editText");
                }
            }

            @Override
            public void onStickerTouchOutside() {
                Log.e("check", "Clicked outside");
                stickerView.handlingSticker = null;
                stickerView.invalidate();
                getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(R.id.main_bottom_container, nonFragment).commit();
                changeSticker.setVisibility(View.VISIBLE);
            }
        });
    }//end of initview
    public void setFeatureAxSticker(String result)
    {
        try {
            JSONObject jsonObject = new JSONObject(result);
            int xHeight = jsonObject.getInt("height");
            int xWeight = jsonObject.getInt("width");

            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            screenWidth = displayMetrics.widthPixels;
            int screenHeight = displayMetrics.heightPixels;
            JSONArray jsonArray = jsonObject.getJSONArray("elements");
            Glide.with(context).load(jsonArray.getJSONObject(0).get("uriPath")).into(template);
            for(int i = 1; i < jsonArray.length(); i++)
            {
                TextSticker textSticker = new TextSticker(this);
                textSticker.setTextColor(Color.parseColor(jsonArray.getJSONObject(i).get("textColor").toString()));
                textSticker.setText(jsonArray.getJSONObject(i).get("textContent").toString());
                String fontName = fileList[jsonArray.getJSONObject(i).getInt("font") - 100];
                textSticker.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/" + fontName));
                JSONObject x = (JSONObject) jsonArray.getJSONObject(i).get("constraints");
                Integer h =  x.getJSONObject("height").getInt("constant");
                Integer l =  x.getJSONObject("left").getInt("constant");
                Integer t =  x.getJSONObject("top").getInt("constant");
                Integer w =  x.getJSONObject("width").getInt("constant");
                textSticker.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sticker_transparent_background));
                boolean isU, isBold, isI;
                Log.e("typeFace", fontName + "");
                isBold = (boolean) jsonArray.getJSONObject(i).get("isBold");
                isU = (boolean) jsonArray.getJSONObject(i).get("isU");
                isI = (boolean) jsonArray.getJSONObject(i).get("isI");
                if(isBold) textSticker.setBold(true);
                //
                if(isI) textSticker.setItali(true);
                //
                int newL = screenWidth * l/xWeight; //1300
                int newT = screenWidth * t/xWeight;
                int newWidth = screenWidth * w/xWeight;
                int newHeight = screenWidth * h/xWeight;
                Log.e("new", newWidth + " " + newHeight);
                Integer rotate = jsonArray.getJSONObject(i).getInt("rotate");
                textSticker.getMatrix().setRotate((float) rotate);
                //
                textSticker.resize2Rect(newWidth + 1, newHeight + 1);
                stickerView.mAddStickertoSpeceficPosition(textSticker, newL + 1, newT + 1);

                stickerView.invalidate();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//    }
    public TextSticker createTextSticker(String text, int x, int y, int textColor, int textSize) {
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);

        StaticLayout staticLayout = new StaticLayout(text, textPaint, 500, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        Bitmap bitmap = createBitmapFromLayout(staticLayout);

        Drawable drawable = new BitmapDrawable(context.getResources(), createBitmapFromLayout(staticLayout));
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        TextSticker textSticker = new TextSticker(context);
        textSticker.setDrawable(drawable);

        Matrix matrix = new Matrix();
        matrix.postTranslate(x, y);
        textSticker.setMatrix(matrix);

        return textSticker;
    }
    private Bitmap createBitmapFromLayout(StaticLayout layout) {
        Bitmap bitmap = Bitmap.createBitmap(layout.getWidth(), layout.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        layout.draw(canvas);
        return bitmap;
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.btn_addtext)
        {
            //  Add a StickerText
            content.setText("Add text");
            textSticker = new TextSticker(this);
            textSticker.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sticker_transparent_background));
            textSticker.setText("Your text here!!");
            textSticker.setTextColor(Color.BLACK);
            textSticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
            textSticker.resizeText();
            stickerView.addSticker(textSticker);
            listTextSticker.add(textSticker);
        }
        else if (id == R.id.btn_sticker)
        {
            content.setText("Sticker");
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.main_bottom_container, StickerFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(com.xiaopo.flying.stickerview.StickerFragment.TAG).commit();
        }
        else if(id == R.id.btn_layer)
        {
            if(list.size() >= 2)
            {
                content.setText("Layer");
                changeSticker.setVisibility(View.GONE);
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.main_bottom_container, fragmentLayer).addToBackStack(FragmentLayer.TAG).commit();
            }
        }
        else if(id == R.id.ic_close)
        {
            FragmentManager fm = getSupportFragmentManager();
            diaologBackWithSave = new DiaologBackWithSave(new DiaologBackWithSave.ListenDialog() {
                @Override
                public void onClose() {
                    diaologBackWithSave.dismiss();
                }
                @Override
                public void onSavePhoto() {
                    finish();
                }
            });
            diaologBackWithSave.setCancelable(true);
            diaologBackWithSave.show(fm, "dialog_save");
        }
        else if(id == R.id.download)
        {
            Sprite cubeGrid = new CubeGrid();
            dialogLoadingTemplate = new DialogLoadingTemplate();
            FragmentManager fm = getSupportFragmentManager();
            dialogLoadingTemplate.show(fm, "loadingTemplate");
            new DownloadProject().execute();
        }
    }
    // get from FragmentSecond
    @Override
    public void onF2Listener(String s) {
        Log.e("checkisclicked", s);
        FragmentManager fm = getSupportFragmentManager();
        //if(s.equals("item6")) fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.main_bottom_container, fontFragment).addToBackStack(FontFragment.TAG).commit();
        if(s.equals("item5")) fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.main_bottom_container, fontFragment).addToBackStack(FragmentDetailAlignment.TAG).commit();
        else if(s.equals("item4")) fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.main_bottom_container, fragmentTextSize).addToBackStack(FragmentTextSize.TAG).commit();
        else if(s.equals("item0")) fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.main_bottom_container, colorFragment).addToBackStack(ColorFragment.TAG).commit();
        else if(s.equals("item2")) fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.main_bottom_container, fragment_opacity).addToBackStack(Fragment_Opacity.TAG).commit();
        else if(s.equals("item1"))
        {
            if(stickerView.getCurrentSticker() instanceof  TextSticker)
            {
                Drawable drawableCopy = stickerView.getCurrentSticker().getDrawable();
                Drawable.ConstantState constantState = drawableCopy.getConstantState();
                Drawable copyTextDrawable = constantState.newDrawable().mutate();
                TextSticker txt = new TextSticker(stickerView.getContext(), copyTextDrawable);
                txt.setText(((TextSticker)stickerView.getCurrentSticker()).getText());
                txt.setTypeface(((TextSticker)stickerView.getCurrentSticker()).getTypeface());
                txt.setTextColor(((TextSticker)stickerView.getCurrentSticker()).getColor());
                txt.setAlpha(((TextSticker)stickerView.getCurrentSticker()).getAlpha() - 1);
                txt.resize2Rect(stickerView.getCurrentSticker().getWidth(), stickerView.getCurrentSticker().getHeight());
                stickerView.mAddStickertoSpeceficPosition(txt, stickerView.getStickerPoints(stickerView.getCurrentSticker())[0] + 50, stickerView.getStickerPoints(stickerView.getCurrentSticker())[1] + 50);
                stickerView.invalidate();
            }
        }
    }
    // get from FontFragment
//    @Override
//    public void sendTypeface(String s) {
//        if(s == "YES")
//        {
//            FragmentManager fm = getSupportFragmentManager();
//            fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(R.id.main_bottom_container, fragmentDetailTextFont).addToBackStack(FragmentDetailTextFont.TAG).commit();
//        }
//        else return;
//    }
    // get from FragmentDetailTextFont
    @Override
    public void onFontchosensent(Typeface typeface) {
        ((TextSticker)stickerView.getCurrentSticker()).setTypeface(typeface);
        ((TextSticker)stickerView.getCurrentSticker()).resize2Rect(stickerView.getCurrentSticker().getWidth(), stickerView.getCurrentSticker().getHeight());
        stickerView.invalidate();
        listStickerInfor.get(stickerView.stickers.indexOf(stickerView.getCurrentSticker())).setFont(typeface);
    }
    // get from FragmentTextAlignment
    @Override
    public void isChosenAlignment(int pos) {
        textSticker = (TextSticker) stickerView.handlingSticker;
        //textSticker.drawTextAlign(textSticker.curCanvas, textSticker.getText(), textSticker.getBound().left, textSticker.getBound().top, textSticker.getWidth(), textSticker.getHeight(), pos - 1);
        getFragmentManager().popBackStackImmediate();
        stickerView.invalidate();
    }
    // get from FragmentTextSize
    @Override
    public void onSizeChange(float value) {
        stickerView.zoomStickerBySeekbar(textSticker, value);
        listStickerInfor.get(stickerView.stickers.indexOf(stickerView.getCurrentSticker())).setScale(value);
        stickerView.invalidate();

    }

    @Override
    public void ColorSent(int color) {
        st = stickerView.getCurrentSticker();
        if(st instanceof TextSticker)
        {
            ((TextSticker) st).setTextColor(color);
            stickerView.invalidate();
            listStickerInfor.get(stickerView.stickers.indexOf(st)).setColor(color);
        }
        else if(st instanceof DrawableSticker)
        {
            ((DrawableSticker) st).setColorSticker(color);
            stickerView.invalidate();
            listStickerInfor.get(stickerView.stickers.indexOf(st)).setColor(color);
        }
    }

    @Override
    public void iOpacityProgress(int i) {
        st = stickerView.getCurrentSticker();
        st.setAlpha(i);
        listStickerInfor.get(stickerView.stickers.indexOf(st)).setOpacity(i);
        stickerView.invalidate();
    }

    @Override
    public void onBackPressed(String s) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(R.id.main_bottom_container, nonFragment).commit();
        changeSticker.setVisibility(View.VISIBLE);
    }

    @Override
    public void isAccepted(boolean check) {
        if (check == false) {
            for (int i = 0; i < stickerView.stickers.size(); i++) {
                StickerInfor tmp = oldListStickerInfor.get(i);
                if (stickerView.stickers.get(i) instanceof TextSticker) {
                    ((TextSticker) stickerView.stickers.get(i)).setAlpha(tmp.getOpacity());
                    ((TextSticker) stickerView.stickers.get(i)).setTextColor(tmp.getColor());
                    stickerView.zoomStickerBySeekbar(stickerView.stickers.get(i), tmp.getScale());
                    ((TextSticker) stickerView.stickers.get(i)).setTypeface(tmp.getFont());
                    stickerView.invalidate();
                }
                else if(stickerView.stickers.get(i) instanceof DrawableSticker)
                {
                    ((DrawableSticker)stickerView.stickers.get(i)).setAlpha(tmp.getOpacity());
                    ((DrawableSticker)stickerView.stickers.get(i)).setColorSticker(tmp.getColor());
                    stickerView.invalidate();
                }
            }
        }
        else
        {
            for(int i = 0; i < stickerView.stickers.size(); i++)
            {
                oldListStickerInfor.set(i, new StickerInfor(listStickerInfor.get(i)));
            }
        }
    }
    class DownloadProject extends AsyncTask<Void, Integer, Boolean>
    {

        @Override
        protected Boolean doInBackground(Void... voids) {
            //return null;
            String string = "/storage/emulated/0/DCIM/StoreCamera/";
            Bitmap bitmap = Bitmap.createBitmap(stickerView.getWidth(), stickerView.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            stickerView.draw(canvas);

            String filename = String.format("%d.jpg", System.currentTimeMillis());
            File outFile = new File(string, filename);
            FileOutputStream outStream = null;
            try {
                outStream = new FileOutputStream(outFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                outStream.flush();
                outStream.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    dialogLoadingTemplate.dismiss();
                    Toast.makeText(context, "Downloaded successfully", Toast.LENGTH_LONG).show();
                }
            };
            handler.postDelayed(runnable, 1500);
        }
    }
    class onDownloadTemplates extends AsyncTask<Void, Integer, Boolean>
    {

        @Override
        protected Boolean doInBackground(Void... voids) {
//            Intent intent = content.getIntent();
//            Bundle bundle = intent.getExtras();
//            String link = intent.getStringExtra("Link Template");
//            Ion.with(context).load(link).asString().setCallback(new FutureCallback<String>() {
//                @Override
//                public void onCompleted(Exception e, String result) {
//                    setFeatureAxSticker(result);
//                }
//            });
            String link = intent.getStringExtra("Link Template");
            Ion.with(context).load(link).asString().setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                    setFeatureAxSticker(result);
                }
            });
            //DialogDownloadTemplates.dismiss();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    DialogDownloadTemplates.
                            dismiss();
                    Toast.makeText(context, "Ready", Toast.LENGTH_LONG).show();
                }
            };
            handler.postDelayed(runnable, 1500);
        }
    }
}

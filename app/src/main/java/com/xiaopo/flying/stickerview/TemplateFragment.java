package com.xiaopo.flying.stickerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TemplateFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String serverUrl = "https://marketingvideo.liforte.com/apimb/v1/categoriestemplate";
    String url = "https://marketingvideo.liforte.com";
    String data = "";
    public TemplateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TemplateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TemplateFragment newInstance(String param1, String param2) {
        TemplateFragment fragment = new TemplateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    RecyclerView rcl_type, rcl_templates;
    LinearLayoutManager HorizontalLayout;
    LinearLayoutManager linearLayoutManager;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<Pair<String, Integer>> listImage = new ArrayList<>();
    ArrayList<String> contentName = new ArrayList<>();
    ArrayList<JSONObject> listobj = new ArrayList<>();
    Context context;
    DialogOpenPremium dialogOpenPremium;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.templates_layout, container, false);
        rcl_templates = v.findViewById(R.id.rcl_templates);
        Log.e("2", "template");
        context = this.getContext();
        rcl_type = v.findViewById(R.id.rec_type);
        //
//        addLink();
//        addString();
        getJsonTemples(serverUrl);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                runX();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 2000);
        return v;
    }
    private void getJsonTemples(String serverUrl)
    {
        Ion.with(TemplateFragment.this).load(serverUrl).
                setHeader("appid", "com.postermaker.lf").
                setHeader("timezone", "20112022")
                .setHeader("sversion", "33")
                .setHeader("os", "Android")
                .setHeader("lfcode", "5e611d1845da0a4884bb7c2f92cfc3ff")
                .asString().setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            JSONArray data = jsonObject.getJSONArray("data");
                            for(int i = 0; i < data.length(); i++)
                            {
                                JSONObject cnt_name = data.getJSONObject(i);
                                listobj.add(cnt_name);
                                contentName.add((String) data.getJSONObject(i).get("Name"));
                                Log.e("Name", (String) data.getJSONObject(i).get("Name"));
                            }
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }

                    }
                });

    }
    public void runX()
    {
        HorizontalLayout = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        linearLayoutManager = new GridLayoutManager(context, 2);

        AdapterTitle adapterTitle = new AdapterTitle(context, R.layout.custom_templates_selected, contentName, new AdapterTitle.onTitleClicked() {
            @Override
            public void iTitleClick(int pos) {
                try {
                    listImage.clear();
                    JSONArray list = listobj.get(pos).getJSONArray("ListTemplate");
                    for(int i = 0; i < list.length(); i++)
                    {
                        String link = url + (String) list.getJSONObject(i).get("LinkThumbnail");
                        int isActive = (Integer) list.getJSONObject(i).get("IsActive");
                        Pair<String, Integer> tmp = new Pair<>(link, isActive);
                        listImage.add(tmp);
                    }
                    AdapterTemplates adapterTemplates = new AdapterTemplates(context, R.layout.custom_templates, listImage, new AdapterTemplates.onTemplatesClicked() {
                        @Override
                        public void iTemplatesClicked(int posTemp) {
                            if(listImage.get(posTemp).second == 1) {
                                Intent intent = new Intent(context, NewEditActivity.class);
                                Bundle bundle = new Bundle();
                                try {
                                    bundle.putString("Link Template", url + list.getJSONObject(posTemp).get("LinkJsonUrl"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                            else
                            {
                                FragmentManager fm = getChildFragmentManager();
                                dialogOpenPremium = new DialogOpenPremium(getActivity(), new DialogOpenPremium.ListenerEvent() {
                                    @Override
                                    public void clickClose() {
                                        dialogOpenPremium.dismiss();
                                    }

                                    @Override
                                    public void clickPurchase() {

                                    }

                                    @Override
                                    public void clickSeeAds() {
                                        dialogOpenPremium.dismiss();
                                    }
                                });
                                dialogOpenPremium.show(fm, "openPremium");
                            }
                        }
                    });
                    rcl_templates.setLayoutManager(linearLayoutManager);
                    rcl_templates.setAdapter(adapterTemplates);
                    adapterTemplates.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rcl_type.setLayoutManager(HorizontalLayout);
        rcl_type.setAdapter(adapterTitle);
        adapterTitle.notifyDataSetChanged();
    }
}

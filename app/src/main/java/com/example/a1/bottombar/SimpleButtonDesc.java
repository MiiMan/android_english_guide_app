package com.example.a1.bottombar;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;
import android.widget.TextView;

public class SimpleButtonDesc extends SimpleButton {

    String subtext;
    TextView subTextView;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    SimpleButtonDesc(int resource_id, Context context, ViewGroup root, String text, String subText, int image_res){
        super(resource_id, context, root, text, image_res);
        initResource(context, root);
        setSubText(subText);
    }

    void initResource(Context context, ViewGroup root){
        subTextView =  view.findViewById(R.id.card_text);

    }
    void setSubText(String text){
        text = text.length() > 100? text.substring(0, 100) + "..." : text;
        subTextView.setText(text);
    }
}

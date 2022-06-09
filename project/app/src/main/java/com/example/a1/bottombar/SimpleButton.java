package com.example.a1.bottombar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SimpleButton {

    private int resource_id;
    private String text;

    protected View view;
    protected ImageView imageView;
    protected TextView textView;

    public interface OnClickListener {
        void onClick(String text);
    }

    private void initResource(Context context, ViewGroup root){
        view = LayoutInflater.from(context).inflate(resource_id, root, false);
        imageView = view.findViewById(R.id.image);
        textView = view.findViewById(R.id.card_title);
    }

    SimpleButton(int resource_id, Context context, ViewGroup root, String text, int image_res) {
        this.text = text;
        this.resource_id = resource_id;
        initResource(context, root);
        setText(text);
        setImage(image_res);
    }

    void setText(String text) {
        textView.setText(text);
    }

    void setImage(int image){
        imageView.setImageResource(image);
    }

    void setOnClickListener(final OnClickListener l) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l.onClick(text);
            }
        });
    }

    public View toView(){
        return view;
    }
}

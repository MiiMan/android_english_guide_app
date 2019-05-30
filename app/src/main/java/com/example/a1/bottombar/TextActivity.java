package com.example.a1.bottombar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class TextActivity extends AppCompatActivity {

    LinearLayout main;
    TextView textView;
    String title;

    View[] view_text;
    TextView[] title_text, text_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        title = getIntent().getExtras().getString("title");

        textView = findViewById(R.id.title);
        textView.setText(title);
        main = findViewById(R.id.mainlayout);


        ArrayList<String[]> arrayList = new ArrayList<>();

        if (title.equals(getString(R.string.deepr))) {
            for (int i = 0; i < 2; i++) {
                arrayList.add(new String[]{getResources().getStringArray(R.array.deepr_info)[i], getResources().getStringArray(R.array.deepr_text)[i]});
            }
        }
        view_text = new View[arrayList.size()];
        title_text = new TextView[view_text.length];
        text_text = new TextView[view_text.length];

        for (int i = 0; i < view_text.length; i++) {
            view_text[i] = LayoutInflater.from(this).inflate(R.layout.textbox, main, false);
            main.addView(view_text[i]);

            title_text[i] = view_text[i].findViewById(R.id.title);
            text_text[i] = view_text[i].findViewById(R.id.text);

            title_text[i].setText(arrayList.get(i)[0]);
            text_text[i].setText(arrayList.get(i)[1]);
        }
    }
}

package com.example.a1.bottombar;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class TheoryActivity extends AppCompatActivity {

    private TextView titleText, langTitle, information, textView;
    private LinearLayout a, cardLayout;

    private HashMap<String, Integer[]> resource_list;
    private ArrayList<String> cardText, cardEx;
    private String[] text,ex;
    private String title, lang, inform;
    private LinearLayout linearLayout;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {
        title = getIntent().getExtras().getString("title");
        lang = getIntent().getExtras().getString("orig");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory);


        initItem();
        initTop();
        initInformation();

        //
    }

    private void initItem(){
        resource_list = new HashMap<>();
        resource_list.put("Apostrophe", new Integer[]{R.array.apostrophe_text, R.array.apostrophe_ex, R.string.apostrophe_inform});
        resource_list.put("Brackets", new Integer[]{R.array.brackets_text, R.array.brackets_ex, R.string.brackets_inform});
        resource_list.put("Colon", new Integer[]{R.array.colon_text, R.array.colon_ex, R.string.colon_inform});
        resource_list.put("Comma", new Integer[]{R.array.comma_text, R.array.comma_ex, R.string.comma_inform});
        resource_list.put("Exclamation mark", new Integer[]{R.array.ex_mark_text, R.array.ex_mark_ex, R.string.ex_mark_inform});

        try {
            text = getResources().getStringArray(resource_list.get(title)[0]);
            ex = getResources().getStringArray(resource_list.get(title)[1]);
            inform = getResources().getString(resource_list.get(title)[2]);
        } catch (Exception e) {
            text = new String[]{"Пока нет информации"};
            ex = new String[]{"Пока нет информации"};
            inform = "Пока нет описания";
        }

    }

    private void initTop(){
        titleText = findViewById(R.id.title_theory);
        titleText.setText(title);
        information = findViewById(R.id.informationText);

        langTitle = findViewById(R.id.lang_theory);
        langTitle.setText(lang);

        information.setText("- " + inform);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initInformation(){
        a = findViewById(R.id.example);

        for (int i = 0; i < text.length; i++) {
            cardText = new ArrayList<>();
            cardEx = new ArrayList<>();
            for (int b = 0; i < text.length; i++, b++) {
                if (cardText.size() != 0 && text[i].substring(text[i].length() - 1).equals(":")) {
                    i--;
                    break;
                } else if (text[i].substring(text[i].length() - 1).equals(":")) {
                    cardText.add(text[i]);
                    cardEx.add(ex[i]);
                    i++;
                    for (; i < text.length; i++) {
                        if (text[i].substring(0, 1).equals("-")) {
                            cardText.add(cardText.size() + ")" + text[i].substring(1));
                            cardEx.add(ex[i]);
                        } else {

                            i--;
                            break;
                        }
                    }
                } else {
                    cardText.add(text[i]);
                    cardEx.add(ex[i]);
                    break;
                }
            }

            View view = getLayoutInflater().inflate(R.layout.caselayout, a,false);
            textView = view.findViewById(R.id.title);
            textView.setText(cardText.get(0));


            linearLayout = view.findViewById(R.id.linearlayout);

            if(!cardEx.get(0).equals("")) {
                textView = new TextView(this);
                textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setTextAppearance(this, android.R.style.TextAppearance_Small);
                textView.setPadding(0, (int) pxFromDp(10), 0, 0);
                textView.setTextColor(Color.DKGRAY);
                textView.setText(cardEx.get(0));
                linearLayout.addView(textView);
            }

            cardLayout = view.findViewById(R.id.linearlayout);

            for (int b = 1; b < cardText.size(); b++) {
                textView = new TextView(this);
                textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setTextAppearance(this, android.R.style.TextAppearance_Medium);
                textView.setPadding(0,(int) pxFromDp(10), 0,0);
                textView.setTextColor(Color.BLACK);
                textView.setText(cardText.get(b));
                cardLayout.addView(textView);

                if (!cardEx.get(b).equals("")) {

                    textView = new TextView(this);
                    textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    textView.setTextAppearance(this, android.R.style.TextAppearance_Small);
                    textView.setPadding(0, (int) pxFromDp(10), 0, 0);
                    textView.setTextColor(Color.DKGRAY);
                    textView.setText(cardEx.get(b));

                    cardLayout.addView(textView);
                }
            }

            a.addView(view);
        }
    }

    private float pxFromDp(float dp) {
        return dp
                * getApplicationContext().getResources().getDisplayMetrics().density;
    }
}

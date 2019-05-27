package com.example.a1.bottombar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private PrefManager prefManager;
    private byte currentMode;
    private String[] currentModes;
    private String currentModeName;


    private LinearLayout linearLayout;
    private RecyclerView recyclerViewTheory;
    private ItemAdapter itemAdapterTheory;
    private ArrayList<CardItem> listTheory;

    private View viewMain;
    private RecyclerView recyclerViewMain;
    private LinearLayout linearLayoutMain;
    private ArrayList<CardItem> listMain;
    private ItemAdapter itemAdapterMain;
    private TextView textViewStatistic;

    String[] orig, local, tests_orig, tests_local;

    int[] images = {R.mipmap.first,
                    R.mipmap.second,
                    R.mipmap.third,
                    R.mipmap.fourth,
                    R.mipmap.fifth};

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            linearLayout.removeAllViews();
            switch (item.getItemId()) {
                case R.id.theory:
                    linearLayout.addView(recyclerViewTheory);
                    return true;
                case R.id.test:
                    linearLayout.addView(viewMain);
                    return true;
            }
            return false;
        }
    };

    private Spinner.OnItemSelectedListener spinnerClickListener = new Spinner.OnItemSelectedListener(){
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            setMode((byte) position);
            prefManager.setPref(position);
            textViewStatistic.setText("Режим " + currentModeName.toLowerCase());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    ItemAdapter.OnItemClickListener onItemClickListenerTheory = new ItemAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(CardItem item, TextView v) {

            Intent intent = new Intent(MainActivity.this, TheoryActivity.class);
            intent.putExtra("title", item.title);
            intent.putExtra("orig", item.text);
            intent.putExtra("image", item.image);
            startActivity(intent);
            }

    };

    ItemAdapter.OnItemClickListener onItemClickListenerMain = new ItemAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(CardItem item, TextView v) {

            Intent intent = new Intent(MainActivity.this, TestActivity.class);
            intent.putExtra("title", item.title);
            startActivity(intent);
        }

    };

    SimpleButton.OnClickListener onClickListenerButton = new SimpleButton.OnClickListener() {
        @Override
        public void onClick(String text) {
            buttonStartIntent(text);
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar);

        prefManager = new PrefManager(this);
        currentModes = getResources().getStringArray(R.array.scenario_name);
        setMode(prefManager.getCurrentMode());

        spinner = actionBar.getCustomView().findViewById(R.id.spinner);
        spinner.setSelection(currentMode);
        spinner.setOnItemSelectedListener(spinnerClickListener);

        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initTheory();
        initMain();
    }

    private void initTheory() {
        orig = getResources().getStringArray(R.array.menu_orig);
        local = getResources().getStringArray(R.array.menu_ru);
        linearLayout = findViewById(R.id.mainlayout);

        recyclerViewTheory = new RecyclerView(this);
        recyclerViewTheory.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        recyclerViewTheory.setHasFixedSize(true);
        recyclerViewTheory.setLayoutManager(new LinearLayoutManager(this));

        listTheory = new ArrayList<>();

        for (int i = 0; i < orig.length; i++) {
            listTheory.add(new CardItem(orig[i], local[i], R.mipmap.theory));
        }
        itemAdapterTheory = new ItemAdapter(onItemClickListenerTheory, listTheory, R.layout.list_item, true);
        recyclerViewTheory.setAdapter(itemAdapterTheory);

        linearLayout.addView(recyclerViewTheory);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initMain() {

        tests_orig = getResources().getStringArray(R.array.tests_name);
        tests_local = getResources().getStringArray(R.array.tests_desc);

        viewMain = getLayoutInflater().inflate(R.layout.mainlayout, linearLayout,false);
        linearLayoutMain = viewMain.findViewById(R.id.linearlayout);


        textViewStatistic = viewMain.findViewById(R.id.statistic_title_text);
        textViewStatistic.setText("Режим " + currentModeName.toLowerCase());


        recyclerViewMain = viewMain.findViewById(R.id.cards);
        recyclerViewMain.setHasFixedSize(true);
        recyclerViewMain.setOverScrollMode(View.OVER_SCROLL_NEVER);

        listMain = new ArrayList<>();

        for (int i = 0; i < tests_orig.length; i++) {
            listMain.add(new CardItem(tests_orig[i], tests_local[i], images[i]));
        }

        itemAdapterMain = new ItemAdapter(onItemClickListenerMain, listMain, R.layout.testlist_item, true);
        recyclerViewMain.setAdapter(itemAdapterMain);
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));


        SimpleButton a = new SimpleButton(R.layout.mainbutton, this, linearLayoutMain,
                getString(R.string.question_test), images[3]);
        a.setOnClickListener(onClickListenerButton);
        linearLayoutMain.addView(a.toView());

        SimpleButton b = new SimpleButton(R.layout.mainbutton, this, linearLayoutMain,
                getString(R.string.question_test_desc), images[4]);

        b.setOnClickListener(onClickListenerButton);
        linearLayoutMain.addView(b.toView());
    }

    private void buttonStartIntent(String title){
        Intent intent = new Intent(MainActivity.this, TestActivity.class);
        intent.putExtra("title", title);
        startActivity(intent);
    }

    private void setMode(byte currentMode){
        this.currentMode = currentMode;
        currentModeName = currentModes[currentMode];
    }

    private float pxFromDp(float dp) {
        return dp
                * getApplicationContext().getResources().getDisplayMetrics().density;
    }
}
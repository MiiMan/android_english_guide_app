package com.example.a1.bottombar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FirstChangeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnNext;
    private PrefManager prefManager;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private String[] modeName, modeDesc;
    private ArrayList<CardItem> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchActivity();
        }
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        setContentView(R.layout.activity_first_change);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnNext = (Button) findViewById(R.id.next);

        layouts = new int[]{
                R.layout.welcome_1,
                R.layout.welcome_2,
                R.layout.welcome_3};

        modeName = getResources().getStringArray(R.array.scenario_name);
        modeDesc = getResources().getStringArray(R.array.scenario_desc);

        list = new ArrayList<>();

        for (int i = 0; i < modeName.length; i++) {
            list.add(new CardItem(modeName[i], modeDesc[i]));
        }

        addBottomDots(0);


        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);

                }
            }
        });

    }

    private void launchActivity(){
        startActivity(new Intent(FirstChangeActivity.this, MainActivity.class));
        finish();
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);

            if (position == 2) {
                recyclerView = view.findViewById(R.id.scenarioRecycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(FirstChangeActivity.this));

                ItemAdapter.OnItemClickListener onItemClickListener = new ItemAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(CardItem item, TextView v) {
                        prefManager.setPref(list.indexOf(item),false);
                        launchActivity();
                    }
                };

                itemAdapter = new ItemAdapter(onItemClickListener, list, R.layout.list_item);
                recyclerView.setAdapter(itemAdapter);

            }


            container.addView(view);

            return view;
        }

        public int getCount() {
            return layouts.length;
        }

        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}

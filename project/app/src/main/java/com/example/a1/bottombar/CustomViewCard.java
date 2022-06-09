package com.example.a1.bottombar;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class CustomViewCard {

    private int resource_id = R.layout.cardtestlayout;
    private String text;
    boolean answer;

    boolean firstQuestion = false;

    Context context;

    View view;
    TextView textView, left, right;
    CardView cardView;

    OnChange listener;

    Animation animation;
    AlphaAnimation alpha;
    float touchX, touchY;


    public interface OnChange {
        void onSwipedWrong();
        void onSwipedRight();
        void defaultMethod();
    }

    CustomViewCard(Context context, ViewGroup root){
        this.answer = answer;
        this.context = context;

        initResource(root);
    }

    private void initResource(ViewGroup root){
        view = LayoutInflater.from(context).inflate(resource_id, root, false);
        cardView = view.findViewById(R.id.card);
        textView = view.findViewById(R.id.text);
        left = view.findViewById(R.id.left);
        right = view.findViewById(R.id.right);

        alpha = new AlphaAnimation(0.0f, 0.0f);
        alpha.setDuration(0);
        alpha.setFillAfter(true);
        left.startAnimation(alpha);
        right.startAnimation(alpha);

        textView.setText(text);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onTouchEvent(event);
            }
        });
    }

    public void setOnChangeListener(OnChange l) {
        listener = l;
    }

    public void setText(final String text) {

        this.text = text;

        if (firstQuestion) {
            cardView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textView.setText(text);
                }
            }, 350);
        } else {textView.setText(text);}

    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    void check(boolean a) {
        firstQuestion = true;
        if (a == answer) {
            listener.onSwipedRight();
        } else {
            listener.onSwipedWrong();
        }
        listener.defaultMethod();
    }

    public boolean onTouchEvent(MotionEvent event) {
        float finalY = event.getY();
        float finalX = event.getX();


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                touchX = event.getX();
                touchY = event.getY();

                alpha = new AlphaAnimation(0.0f, 1.0f);
                alpha.setDuration(500);
                alpha.setFillAfter(true);
                left.startAnimation(alpha);
                right.startAnimation(alpha);

                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:

                if (finalX-touchX > 100) {
                    animation = AnimationUtils.loadAnimation(context, R.anim.right_translate);
                    cardView.startAnimation(animation);
                    check(true);

                } else if (finalX - touchX < -100) {
                    animation = AnimationUtils.loadAnimation(context, R.anim.left_translate);
                    cardView.startAnimation(animation);
                    check(false);
                } else {}

                alpha = new AlphaAnimation(1.0f, 0.0f);
                alpha.setDuration(500);
                alpha.setFillAfter(true);
                left.startAnimation(alpha);
                right.startAnimation(alpha);


                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    View toView() {
        return view;
    }

}

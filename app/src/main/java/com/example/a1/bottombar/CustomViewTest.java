package com.example.a1.bottombar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.HashMap;

public class CustomViewTest {

    public interface OnChange {
        void onTouchedWrong();
        void onTouchedRight();
    }

    static class Element {
        String question;
        HashMap<String, Boolean> answers;

        Element (String q, HashMap<String, Boolean> a) {
            question = q;
            answers = a;
        }

        String[] getAnswers() {
            return answers.keySet().toArray(new String[answers.size()]);
        }
    }

    static class Answer{

        View root;
        TextView textView;
        boolean isTrue;
        boolean isTouched = false;

        Answer(View root, String text, boolean answer) {
            this.root = root;
            textView = root.findViewById(R.id.text);
            textView.setText(text);

            this.isTrue = answer;

            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setColor();
                }
            });
        }

        void setColor() {
            if (!isTouched) {
                root.setBackgroundColor(Color.BLUE);
                isTouched = true;
            } else {
                root.setBackgroundColor(Color.WHITE);
                isTouched = false;
            }
        }

        View toView() {
            return root;
        }
    }

    Context context;
    ViewGroup root;
    LinearLayout container;
    Element element;
    View view;
    Answer[] answers;

    RelativeLayout cardView;
    TextView textView;

    CustomViewTest (Context context, ViewGroup root) {
        this.context = context;
        this.root = root;
    }

    void setElement(Element e) {
        element = e;
        initView();
    }

    void initView() {
        view = LayoutInflater.from(context).inflate(R.layout.originaltestlayout, root, false);
        cardView = view.findViewById(R.id.card);

        container = view.findViewById(R.id.container);

        textView = view.findViewById(R.id.text);
        textView.setText(element.question);

        answers = new Answer[element.getAnswers().length];
        for (int i = 0; i < answers.length; i++) {
            answers[i] = new Answer(LayoutInflater.from(context).inflate(R.layout.cardtest_item, cardView, false), element.getAnswers()[i], element.answers.get(element.getAnswers()[i]));

            container.addView(answers[i].toView());
        }
    }

    void check(){}


    View toView(){
        return view;
    }

}

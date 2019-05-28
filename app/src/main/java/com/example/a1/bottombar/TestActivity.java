package com.example.a1.bottombar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class TestActivity extends AppCompatActivity {

    RelativeLayout main;
    TextView textView, right;
    String title;

    ScrollView menu_text;
    LinearLayout linear_text;

    View[] view_text;
    TextView[] title_text, text_text;

    int n = 0;
    int t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getIntent().getExtras().getString("title");

        setContentView(R.layout.activity_test);

        textView = findViewById(R.id.title);
        textView.setText(title);

        right = findViewById(R.id.rightAnswer);
        main = findViewById(R.id.main);

        if (title.equals("Составление вопроса")){
            questionTest();
        } else if (title.equals("Карточки")) {
            cardTest();
        } else if (title.equals("Тесты")){
            testTest();
        }

    }

    void testTest(){
        HashMap<String,Boolean> g = new HashMap<>();
        g.put("Да", false);
        g.put("Нет", false);
        g.put("Не знаю", true);
        CustomViewTest test = new CustomViewTest(this,main);
        test.setElement(new CustomViewTest.Element("Тебя зовут гена?", g ));


        test.setOnClickListener(new CustomViewTest.OnClickListener() {
            @Override
            public void onClickedWrong() {
                right.setText("no");
            }

            @Override
            public void onClickedRight() {
                right.setText("yes");
            }

            @Override
            public void defaultMethod() {

            }
        });
        main.addView(test.toView());
    }
    void questionTest(){

        final ArrayList<String[]> words = new ArrayList<>();
        words.add(new String[]{"Do", "you", "love", "me"});
        words.add(new String[]{"Am", "I", "a", "joke", "to", "you"});
        words.add(new String[]{"Will", "you", "eat", "it"});
        words.add(new String[]{"Did", "you", "do", "your", "homework"});
        words.add(new String[]{"Will", "you", "eat", "it"});
        words.add(new String[]{"Do", "you", "have", "a", "dog"});
        words.add(new String[]{"Does","he","read","this","book"});
        words.add(new String[]{"Is","she","a","student"});
        Collections.shuffle(words);


        View view = LayoutInflater.from(this).inflate(R.layout.questionlayout, main);
        final CustomViewQuestion a = view.findViewById(R.id.test);
        a.setArray(words.get(0));
        Collections.shuffle(words);

        final TextView textView = view.findViewById(R.id.text);
        final Button b = view.findViewById(R.id.check);

        right.setText("Составьте вопрос перетаскиванием");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a.check()) {
                    right.setText("Правильно!");
                    a.setArray(words.get(0));
                    Collections.shuffle(words);
                } else {
                    right.setText("Попробуйте снова!");
                }
            }
        });
    }
    void cardTest(){
        class CardForTest{
            String question;
            boolean answer;

            CardForTest(String question, boolean answer) {
                this.question = question;
                this.answer = answer;
            }
        }

        final ArrayList<CardForTest> list = new ArrayList<>();
        list.add(new CardForTest("Апостроф ставится в конце предложения", false));
        list.add(new CardForTest("Точка ставится в конце слова", false));
        list.add(new CardForTest("Восклицательный знак ставится в конце предложения", true));

        final CustomViewCard a = new CustomViewCard(this, main);

        a.setText(list.get(n).question);
        a.setAnswer(list.get(n).answer);

        right.setText("Правильно " + t + "/" + list.size());


        a.setOnChangeListener(new CustomViewCard.OnChange() {
            @Override
            public void onSwipedWrong() {

            }

            @Override
            public void onSwipedRight() {
                t++;
                right.setText("Правильно " + t + "/" + list.size());
            }

            @Override
            public void defaultMethod() {
                n = n > list.size()-2? n: n+1;
                a.setText(list.get(n).question);
                a.setAnswer(list.get(n).answer);
            }
        });
        main.addView(a.toView());
    }

}

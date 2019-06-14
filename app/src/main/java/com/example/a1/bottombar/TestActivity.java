package com.example.a1.bottombar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class TestActivity extends AppCompatActivity {

    RelativeLayout main;
    TextView textView, right;
    String title;



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
        } else if (title.equals("Контрольный тест")){
            controlTest();
        }

    }

    void finishTest(String test) {
        Intent intent = new Intent(TestActivity.this, ResultActivity.class);
        intent.putExtra("text", test);
        startActivity(intent);
    }

    void controlTest(){
        final CustomViewTest test = new CustomViewTest(this,main);
        final CustomViewCard card = new CustomViewCard(this, main);

        final ArrayList<HashMap<String,Boolean>> a = new ArrayList<>();
        a.add(new HashMap<String, Boolean>());
        a.get(0).put("Да", false);
        a.get(0).put("Нет", true);
        a.get(0).put("Не знаю", false);
        a.add(new HashMap<String, Boolean>());
        a.get(1).put("Выделения причастного оборота", false);
        a.get(1).put("Для разделения однородных членов", false);
        a.get(1).put("Для выделения названий", true);


        final ArrayList<CustomViewTest.Element> el = new ArrayList<>();
        el.add(new CustomViewTest.Element("Запятую ставят в конце предложения",a.get(0)));
        el.add(new CustomViewTest.Element("Запятая не используется для",a.get(1)));

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

        test.setElement(el.get(0));

        t = 0;

        test.setOnClickListener(new CustomViewTest.OnClickListener() {
            @Override
            public void onClickedWrong() {
                right.setText("no");
            }

            @Override
            public void onClickedRight() {
                t++;
            }

            @Override
            public void defaultMethod() {

                if (n == el.size()-1) {
                    main.removeAllViews();
                    card.setText(list.get(0).question);
                    card.setAnswer(list.get(0).answer);
                    n = 0;
                    main.addView(card.toView());
                } else {
                    n++;
                    right.setText("Правильно " + t + "/" + (el.size()+a.size()));
                    test.setElement(el.get(n));
                }
            }
        });

        card.setOnChangeListener(new CustomViewCard.OnChange() {
            @Override
            public void onSwipedWrong() {

            }

            @Override
            public void onSwipedRight() {
                t++;
                right.setText("Правильно " + t + "/" + (list.size()+el.size()-1));
            }

            @Override
            public void defaultMethod() {
                if (n == a.size()-1) {
                    finishTest("Правильно " + t + "/" + (list.size()+el.size()-1));
                } else {
                    n++;
                    card.setText(list.get(n).question);
                    card.setAnswer(list.get(n).answer);
                }
            }
        });

        main.addView(test.toView());
    }

    void testTest(){
        final ArrayList<HashMap<String,Boolean>> a = new ArrayList<>();
        a.add(new HashMap<String, Boolean>());
        a.get(0).put("Да", false);
        a.get(0).put("Нет", true);
        a.get(0).put("Не знаю", false);
        a.add(new HashMap<String, Boolean>());
        a.get(1).put("Выделения причастного оборота", false);
        a.get(1).put("Для разделения однородных членов", false);
        a.get(1).put("Для выделения названий", true);


        final ArrayList<CustomViewTest.Element> el = new ArrayList<>();
        el.add(new CustomViewTest.Element("Запятую ставят в конце предложения",a.get(0)));
        el.add(new CustomViewTest.Element("Запятая не используется для",a.get(1)));

        right.setText("Правильно " + 0 + "/" + el.size());

        final CustomViewTest test = new CustomViewTest(this,main);
        test.setElement(el.get(n));


        t = 0;
        test.setOnClickListener(new CustomViewTest.OnClickListener() {
            @Override
            public void onClickedWrong() {
                right.setText("no");
            }

            @Override
            public void onClickedRight() {
                t++;
            }

            @Override
            public void defaultMethod() {

                if (n == el.size()-1) {
                    finishTest("Правильно " + t + "/" + el.size());
                } else {
                    n++;
                    right.setText("Правильно " + t + "/" + el.size());
                    test.setElement(el.get(n));
                }
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
                if (n == list.size()-1) {
                    finishTest("Правильно " + t + "/" + list.size());
                } else {
                    n++;
                    a.setText(list.get(n).question);
                    a.setAnswer(list.get(n).answer);
                }
            }
        });
        main.addView(a.toView());
    }

}

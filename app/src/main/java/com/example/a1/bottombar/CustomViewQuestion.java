package com.example.a1.bottombar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;


public class CustomViewQuestion extends View{

    Context context;
    int width, height;

    float size_x, size_y, size_text;


    class Card{

        String text;

        Card(String text){
            this.text = text;
        }

        float x = 0;
        float y = 0;

        boolean touched = false;

        void setPos(float x, float y) {
            this.x = x;

            this.y = y;
        }

        boolean isTouched(float x, float y){
            if (x >= this.x && x <= this.x+size_x && y >= this.y && y <= this.y+size_y){
                touched = true;
                return true;
            } else {
                touched = false;
                return false;
            }
        }

        float distance(){
            return x;
        }

        float[] touchedAt(float x, float y) {
            float[] a = {x-this.x,y-this.y};
            return a;
        }

        void draw(Canvas canvas, Paint paint) {
            paint.setAntiAlias(true);
            paint.setColor(Color.LTGRAY);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.FILL);

            if (touched) {
                paint.setShadowLayer(3.0f, 5.0f, 5.0f, Color.DKGRAY);
            } else {
                paint.setShadowLayer(0.5f, 1.0f, 1.0f, Color.DKGRAY);
            }


            final RectF rect = new RectF();
            rect.set(x, y, x+size_x,
                    y+size_y);
            canvas.drawRoundRect(rect, size_x/10, size_x/10, paint);

            paint.setColor(Color.BLACK);
            paint.setTextSize(size_text);
            paint.setShadowLayer(0, 0, 0, Color.BLACK);
            canvas.drawText(text, x + size_x/2-text.length()*(size_text/4), y+size_y/2+(size_text/2), paint);

        }

    }
    String[] orig_words = {};
    LinkedList<Card> cards;


    public CustomViewQuestion(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        this.size_x = convertDpToPixel(30,context);
        this.size_y = convertDpToPixel(15,context);
        this.size_text = convertDpToPixel(4,context);


    }

    void initCards(){
        cards = new LinkedList<>();
        ArrayList<String> shuffled_words = new ArrayList<>(Arrays.asList(orig_words));
        Collections.shuffle(shuffled_words);

        int x = 0;
        int y = 0;
        for (int i = 0; i < shuffled_words.size(); i++) {

            if (x+size_x > width) {
                x = 0;
                y += size_y;
            }
            Card card = new Card((shuffled_words.get(i)));
            card.setPos(x, y);
            x += size_x;

            cards.add(card);
        }
    }

    public void setArray(String[] array){
        orig_words = array;
        initCards();
    }

    public boolean check(){
        Collections.sort(cards, new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                if (o1.distance() > o2.distance()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        Log.d("CustomView", "--");

        for (int i = 0; i < orig_words.length; i++) {
            Log.d("CustomView", cards.get(i).text);
            if (!cards.get(i).text.equals(orig_words[i])){
               return false;
            }
        }


        invalidate();
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width,height);

        initCards();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Paint paint= new Paint();

        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).draw(canvas,paint);
        }

    }

    float initialX, initialY;
    boolean touched;
    float[] a;
    public boolean onTouchEvent(MotionEvent event) {
        float finalY;
        float finalX;


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                float touchX = event.getX();
                float touchY = event.getY();


                for (int i = cards.size()-1; i >= 0; i--) {
                    if (cards.get(i).isTouched(touchX, touchY)) {
                        touched = true;

                        Card card = cards.get(i);
                        cards.remove(i);
                        cards.addLast(card);
                        a = cards.getLast().touchedAt(touchX,touchY);
                        break;
                    };

                    touched = false;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (touched) {

                    finalY = event.getY();
                    finalX = event.getX();

                    cards.getLast().setPos(finalX - a[0], finalY - a[1]);
                }
                break;

            case MotionEvent.ACTION_UP:
                cards.getLast().touched = false;
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }

        initialX = event.getX();
        initialY = event.getY();

        invalidate();
        return true;
    }

    public float convertDpToPixel(float dp, Context context) {
        return dp * context.getResources().getDisplayMetrics().density*5;
    }
}
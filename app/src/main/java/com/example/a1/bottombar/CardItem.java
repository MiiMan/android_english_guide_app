package com.example.a1.bottombar;

public class CardItem {
        String title;
        String text;
        int image;

        CardItem (String title, String text) {
            this.title = title;
            this.text = text;
        }

        CardItem (String title, String text, int image) {
            this(title,text);
            this.image = image;
        }
}

package com.example.a1.bottombar;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterAdder {
    private ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    private HashMap <String, String> text = new HashMap<>();

    private String firstText, secondText;

    AdapterAdder(String firstText, String secondText) {
        this.firstText = firstText;
        this.secondText = secondText;
    }

    void put(String f, String s){

        text.put(firstText,f);
        text.put(secondText,s);
        arrayList.add((HashMap) text.clone());
        text.clear();
    }

    ArrayList toArrayList() {
        return arrayList;
    }

    HashMap getByID(int id) {
        return  arrayList.get(id);
    }
}

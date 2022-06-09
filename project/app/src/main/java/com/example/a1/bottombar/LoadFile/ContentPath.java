package com.example.a1.bottombar.LoadFile;

import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ContentPath {

    final String path = "modules/";

    public ContentPath () {
    }

    public String[] list(Activity activity) throws IOException {
        AssetManager am = activity.getAssets();
        ArrayList<String> l = new ArrayList<>();

        for (String i : am.list(path)){
            if (!i.contains(".")) {
                l.add(i);
            }
        }
        Log.d("CustomView", l.toString());

        return l.toArray(new String[l.size()]);
    }

    public FileModule[] get(String path, Activity activity) throws IOException {
        AssetManager am = activity.getAssets();

        InputStream is;
        String result;
        GsonBuilder builder = new GsonBuilder();
        Gson gson= builder.create();

        ArrayList<FileModule> l = new ArrayList<>();
        path = this.path + path;

        for (String i: am.list(path)) {
            is = am.open(path + "/" + i);
            result = isToString(is);
            is.close();

            l.add((FileModule) gson.fromJson(result, FileModule.class));

        }

        return l.toArray(new FileModule[l.size()]);
    }


    static private String isToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        return result;
    }


}

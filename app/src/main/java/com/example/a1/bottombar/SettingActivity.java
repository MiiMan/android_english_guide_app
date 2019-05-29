package com.example.a1.bottombar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {

    ListView choiceList;
    TextView title, subtitle;
    String[] elements;
    String[] current;

    Button button;

    String name;


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SparseBooleanArray sbArray = choiceList.getCheckedItemPositions();
            current = new String[sbArray.size()];
            for (int i = 0; i < sbArray.size(); i++) {
                int key = sbArray.keyAt(i);
                if (sbArray.get(key))
                    current[i] = elements[key];
            }

            if (current.length > 0) {
                buttonStartTestIntent();
            }
        }
    };

    private void buttonStartTestIntent(){
        Intent intent = new Intent(SettingActivity.this, TestActivity.class);
        intent.putExtra("title", name);
        intent.putExtra("elements", current);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        name = getIntent().getExtras().getString("title");

        if (name.equals(getString(R.string.question_test))) {
            Intent intent = new Intent(SettingActivity.this, TestActivity.class);
            intent.putExtra("title", name);
            startActivity(intent);
        }


        setContentView(R.layout.activity_setting);

        title = findViewById(R.id.title);
        subtitle = findViewById(R.id.subtitle);
        title.setText(getString(R.string.options));
        subtitle.setText(name);

        elements = getResources().getStringArray(R.array.menu_orig);
        choiceList = findViewById(R.id.elements);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, elements);
        choiceList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        choiceList.setAdapter(adapter);


        Button button = findViewById(R.id.start);
        button.setOnClickListener(listener);

    }
}

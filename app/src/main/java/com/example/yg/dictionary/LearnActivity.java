package com.example.yg.dictionary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LearnActivity extends AppCompatActivity {

//    private static String[] WORDS = {
//            "Dog", "Woof",
//            "Cat", "Meow",
//            "Duck", "Quack"
//    };

    private Map<String, String> dictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        registerForContextMenu(findViewById(R.id.mainView));

        dictionary = new HashMap<>();
//        for (int i = 0; i < WORDS.length; i += 2) {
//            dictionary.put(WORDS[i], WORDS[i + 1]);
//        }
        Scanner scanner = new Scanner(getResources().openRawResource(R.raw.animalsoundtext));
        readFileHelper(scanner);
        Scanner scanner2 = null;
        try {
            scanner2 = new Scanner(openFileInput("added_text.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        readFileHelper(scanner2);

        ListView list = (ListView) findViewById(R.id.mobile_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.activity_listview,
                new ArrayList<String>(dictionary.keySet()));
        list.setAdapter(adapter);
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String animal = parent.getItemAtPosition(position).toString();
//                String sound = dictionary.get(animal);
//                Toast.makeText(LearnActivity.this, sound, Toast.LENGTH_SHORT).show();
//            }
//        });
        list.setOnItemClickListener((parent, view, position, id) ->
                {
                    String animal = parent.getItemAtPosition(position).toString();
                    String sound = dictionary.get(animal);
                    Toast.makeText(this, sound, Toast.LENGTH_SHORT).show();
                }
        );

    }

    private void readFileHelper(Scanner scanner) {
        while(scanner.hasNextLine())
        {
            String line = scanner.nextLine();
            String[] parts = line.split("->");
            dictionary.put(parts[0], parts[1]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1:
                Log.i("menu", "Item 1");
                return true;
            case R.id.menu2:
                Log.i("menu", "Item 2");
                Toast.makeText(this, "Item2", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu3:
                Log.i("menu", "Item 3");
                Toast.makeText(this, "Item3", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context1:
                Toast.makeText(this, "context1", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.context2:
                Toast.makeText(this, "context2", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void returnToMain(View view) {
        finish();
    }
}

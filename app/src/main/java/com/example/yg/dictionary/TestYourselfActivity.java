package com.example.yg.dictionary;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class TestYourselfActivity extends AppCompatActivity {

    private List<String> animals;
    private Map<String, String> dictionary;
    private int currentscore;
    private int highestscore;

    private void readFileData() {
        Scanner scanner = new Scanner(getResources().openRawResource(R.raw.animalsoundtext));
        scanFileHelper(scanner);
        Scanner scanner2 = null;
        try {
            scanner2 = new Scanner(openFileInput("added_text.txt"));
            scanFileHelper(scanner2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void scanFileHelper(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split("->");
            dictionary.put(parts[0], parts[1]);
            animals.add(parts[0]);
        }
    }

    private void chooseWords() {
        Random r1 = new Random();
        int randomIndex = r1.nextInt(animals.size());
        String theWord = animals.get(randomIndex);

        List<String> sounds = new ArrayList<String>(dictionary.values());
        String correctSound = dictionary.get(theWord);
        sounds.remove(correctSound);
        Collections.shuffle(sounds);
        sounds = sounds.subList(0, 4);
        sounds.add(correctSound);
        Collections.shuffle(sounds);

        TextView tv = findViewById(R.id.theword);
        tv.setText(theWord);

        ListView lv = findViewById(R.id.soundlist);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.activity_listview,
                new ArrayList<String>(sounds));
        lv.setAdapter(adapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_yourself);
        animals = new ArrayList<String>();
        dictionary = new HashMap<String, String>();

        readFileData();
        chooseWords();

        SharedPreferences prefs = getSharedPreferences("myprefs", MODE_PRIVATE);
        highestscore = prefs.getInt("highestScore", 0);
        DisplayCurrentScore();
        DisplayHighestScore();

        ListView lv = findViewById(R.id.soundlist);
        lv.setOnItemClickListener((parent, view, position, id) ->
        {
            TextView tv = findViewById(R.id.theword);
            String theWord = tv.getText().toString();
            String chosenSound = parent.getItemAtPosition(position).toString();
            String correctSound = dictionary.get(theWord);
            if (chosenSound.equalsIgnoreCase(correctSound)) {
                Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
                currentscore++;
                if (currentscore > highestscore) {
                    highestscore = currentscore;

                    SharedPreferences.Editor prefsEditor = prefs.edit();
                    prefsEditor.putInt("highestScore", highestscore);
                    prefsEditor.apply();
                }
            } else {
                Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
                currentscore--;
            }
            DisplayCurrentScore();
            DisplayHighestScore();
            chooseWords();
        });



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentScore", currentscore);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentscore = savedInstanceState.getInt("currentScore", 0);
        DisplayCurrentScore();
    }

    public void DisplayCurrentScore(){
        TextView currentScoreView = findViewById(R.id.currentscore);
        currentScoreView.setText("Current Score: "+currentscore);
    }

    public void DisplayHighestScore(){
        TextView highestScoreView = findViewById(R.id.highestscore);
        highestScoreView.setText("Highest Score: "+highestscore);
    }

    public void Back(View view) {
        finish();
    }
}

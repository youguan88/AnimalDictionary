package com.example.yg.dictionary;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {
    private static final int REQ_CODE_ADD_WORD = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void learn(View view) {
        Intent intent = new Intent(this, LearnActivity.class);
        startActivity(intent);
    }

    public void testYourself(View view) {
        Intent intent = new Intent(this, TestYourselfActivity.class);
        startActivity(intent);
    }

    public void addWords(View view) {
        Intent intent = new Intent(this, AddNewWordActivity.class);
        startActivityForResult(intent, REQ_CODE_ADD_WORD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_ADD_WORD && resultCode == RESULT_OK){
            String newAnimal = data.getStringExtra("newAnimal");
            String newSound = data.getStringExtra("newSound");
            Toast.makeText(this, "You've added "+newAnimal+" : "+newSound, Toast.LENGTH_LONG).show();
        }
    }
}

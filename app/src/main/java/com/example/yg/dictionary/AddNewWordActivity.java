package com.example.yg.dictionary;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class AddNewWordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_word);
    }

    public void addneword(View view) throws FileNotFoundException {
        EditText animalText = findViewById(R.id.newAnimal);
        EditText soundText = findViewById(R.id.newSound);
        String newAnimal = animalText.getText().toString();
        String newSound = soundText.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Saving new animal and sound");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PrintStream output = null;
                try {
                    output = new PrintStream(
                            openFileOutput("added_text.txt", MODE_PRIVATE|MODE_APPEND));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                output.println(newAnimal+"->"+newSound);
                output.close();

                Intent goback = new Intent();
                goback.putExtra("newAnimal", newAnimal);
                goback.putExtra("newSound", newSound);
                setResult(RESULT_OK, goback);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

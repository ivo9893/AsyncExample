package com.example.asyncexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fm;
    private EditText editText;
    private TextView success;
    private Button button;
    private Button secondTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edittext);
        success = findViewById(R.id.success);
        button = findViewById(R.id.button);
        secondTask = findViewById(R.id.secondTask);
        secondTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(i);
            }
        });

        fm = getSupportFragmentManager();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(editText.getText().toString());
                if(number <= 0){
                    Toast.makeText(getApplicationContext(), "Please enter a number bigger than 0", Toast.LENGTH_LONG).show();
                } else {
                    new ProgressAsync().execute(number);
                }
            }
        });
    }

    class ProgressAsync extends AsyncTask<Integer, Integer, Void> {

        private Fragment fragment;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            fragment = Fragment.newInstance();
            fragment.show(fm, "fragment_layout");
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            while(integers[0] > 0){

                try {
                    publishProgress(integers[0]);
                    Thread.sleep(1000);
                } catch(Exception e){
                    e.printStackTrace();
                }

                integers[0]--;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            fragment.setTextToTextview(String.valueOf(values[0]));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            fragment.dismiss();
            success.setVisibility(View.VISIBLE);
            success.setText("Success!!");
            success.setTextColor(Color.GREEN);
        }
    }
}

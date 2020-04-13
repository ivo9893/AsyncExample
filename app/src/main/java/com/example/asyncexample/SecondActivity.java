package com.example.asyncexample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class SecondActivity extends AppCompatActivity {

    TextView answer;
    Button firstAsync;
    Button secondAsync;
    Button bothAsync;
    ProgressBar bar;

    boolean hasFinishedFirst = false;
    boolean hasFinishedSecond = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        answer = findViewById(R.id.answer);

        bar = findViewById(R.id.progressBar);

        firstAsync = findViewById(R.id.firstAsync);
        firstAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer.setText("");
                bar.setVisibility(View.VISIBLE);
                new AsyncFirst().execute(true);
            }
        });

        secondAsync = findViewById(R.id.secondAsync);
        secondAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer.setText("");
                bar.setVisibility(View.VISIBLE);
                new AsyncSecond().execute(true);

            }
        });

        bothAsync = findViewById(R.id.bothAsync);
        bothAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer.setText("");
                bar.setVisibility(View.VISIBLE);
                new AsyncFirst().execute(false);
                new AsyncSecond().execute(false);
            }
        });
    }

    public void setTextFirst(boolean bool){
        if(bool){
            answer.setText("LOGIN SUCCESSFUL");
            answer.setTextColor(Color.GREEN);
        } else {

            answer.setText("Error");
            answer.setTextColor(Color.RED);
        }
    }

    public void setTextSecond(boolean bool){
        if(bool){
            answer.setText("DOWNLOAD SUCCESSFUL");
            answer.setTextColor(Color.GREEN);
        } else {

            answer.setText("Error");
            answer.setTextColor(Color.RED);
        }
    }

    public void hideProgressBar(){
        bar.setVisibility(View.GONE);
    }

    class AsyncFirst extends AsyncTask<Boolean, Void, Boolean> {
        private int seconds;

        @Override
        protected Boolean doInBackground(Boolean... booleans) {

            try {
                Thread.sleep(seconds * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            int rand = new Random().nextInt(2);
            if(booleans[0]) {
                setTextFirst(rand == 1);
            } else {

            }

            return booleans[0];
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            seconds = new Random().nextInt((5 - 2) + 1) + 2;
        }

        @Override
        protected void onPostExecute(Boolean booleans) {
            super.onPostExecute(booleans);

            if(booleans){
                hideProgressBar();
            } else {
                hasFinishedFirst = true;
                bothFinished();
                Log.d("Finished", "task 1 finished");
            }
        }
    }

        class AsyncSecond extends AsyncTask<Boolean, Void, Boolean>{
            private int seconds;
            @Override
            protected Boolean doInBackground(Boolean... booleans) {

                try{
                    Thread.sleep(seconds * 1000);
                } catch (Exception e){
                    e.printStackTrace();
                }

                int rand = new Random().nextInt(2);
                if(booleans[0]){
                    setTextSecond(rand == 1);
                } else {

                }

                return booleans[0];
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                seconds = new Random().nextInt((5 - 3) + 1) + 3;
            }

            @Override
            protected void onPostExecute(Boolean booleans) {
                super.onPostExecute(booleans);

                if(booleans){
                    hideProgressBar();
                } else {
                    Log.d("Finished", "task 2 finished");
                    hasFinishedSecond = true;
                    bothFinished();
                }
            }

    }

    public void bothFinished(){
        if(hasFinishedSecond && hasFinishedFirst){

            bar.setVisibility(View.GONE);
            int rand = new Random().nextInt(2);
            if(rand == 1){
                answer.setText("Login and downloading photo successfull!");
                answer.setTextColor(Color.GREEN);

                hasFinishedFirst = false;
                hasFinishedSecond = false;
            } else {
                answer.setText("Error!");
                answer.setTextColor(Color.RED);
                hasFinishedFirst = false;
                hasFinishedSecond = false;
            }
        }
    }
}

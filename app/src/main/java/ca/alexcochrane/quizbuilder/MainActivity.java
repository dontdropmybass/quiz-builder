package ca.alexcochrane.quizbuilder;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ca.alexcochrane.quizbuilder.util.QuizUtilities;

public class MainActivity extends AppCompatActivity {

    boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button play = (Button)findViewById(R.id.play);
        Button quit = (Button)findViewById(R.id.quit);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,QuizChooserActivity.class);
                try {
                    intent.putExtras(getIntent().getExtras());
                }
                catch (NullPointerException e) {
                    Log.wtf(QuizUtilities.TAG,e.getMessage());
                }
                startActivity(intent);
            }
        });
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finishAffinity(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3000);

        }
    }
}

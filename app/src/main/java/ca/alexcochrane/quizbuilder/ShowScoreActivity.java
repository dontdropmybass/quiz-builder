package ca.alexcochrane.quizbuilder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import ca.alexcochrane.quizbuilder.util.QuizUtilities;

public class ShowScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_score);

        TextView scoreView = (TextView) findViewById(R.id.score);

        String score = String.valueOf(getIntent().getIntExtra("SCORE",-1));
        String playerName = getIntent().getStringExtra(QuizUtilities.PLAYER_NAME);
        String quizName = getIntent().getStringExtra(QuizUtilities.QUESTION_SET);
        try {
            for (String s : QuizUtilities.getQuizzes(this).keySet()) {
                if (QuizUtilities.getQuizzes(this).get(s).equals(quizName)) {
                    quizName = s;
                    break;
                }
            }
        }
        catch (IOException e) {
            Log.wtf(QuizUtilities.TAG,e.getCause().getMessage());
        }
            String fmt = getString(R.string.score);
            scoreView.setText(String.format(fmt, playerName, score, quizName));
    }

    public void returnMain(View v) {
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    public void onBackPressed() {
        returnMain(new View(this));
        super.onBackPressed();
    }
}

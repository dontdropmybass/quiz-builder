package ca.alexcochrane.quizbuilder;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import ca.alexcochrane.quizbuilder.util.QuizUtilities;

public class QuizActivity extends AppCompatActivity {

    Map<String,String[]> questions;
    Map<String,String> answers;

    String question;
    String[] answer;

    int questionNum;

    TextView questionText;
    TextView possibleAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        try {
            questions = QuizUtilities.loadSampleQuestionSet(this);
            answers = QuizUtilities.loadSampleAnswerSet(this);
            questionNum = 0;
            nextQuestion();
        }
        catch (FileNotFoundException e) {
            Log.wtf("FFFFFFFFFF",e.getCause().getMessage());
            e.printStackTrace();
        }
    }

    public void nextQuestion() {
        try {
            getActionBar().setTitle(getString(R.string.question) + " " + questionNum);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        question = new ArrayList<>(questions.keySet()).get(0);
        answer = (String[]) QuizUtilities.shuffleArray(questions.get(question));

        questionText.setText(question);
        StringBuilder sb = new StringBuilder();
        sb.append("A: ").append(answer[0]).append("\n");
        sb.append("B: ").append(answer[1]).append("\n");
        sb.append("C: ").append(answer[2]).append("\n");
        sb.append("D: ").append(answer[3]);
        possibleAnswers.setText(sb.toString());
        questions.remove(questionText.getText().toString());
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                if (answers.get(question).equals(answer[0])) {
                    correct();
                }
                else {
                    incorrect();
                }
                break;
            case R.id.button2:
                if (answers.get(question).equals(answer[1])) {
                    correct();
                }
                else {
                    incorrect();
                }
                break;
            case R.id.button3:
                if (answers.get(question).equals(answer[2])) {
                    correct();
                }
                else {
                    incorrect();
                }
                break;
            case R.id.button4:
                if (answers.get(question).equals(answer[3])) {
                    correct();
                }
                else {
                    incorrect();
                }
                break;
            default:
                break;
        }
        nextQuestion();
    }

    public void correct() {
        if (Build.VERSION.SDK_INT >= 23) {
            findViewById(R.id.activity_quiz).setBackgroundColor(getColor(R.color.correct));
        }
        else {
            findViewById(R.id.activity_quiz).setBackgroundColor(getResources().getColor(R.color.correct));
        }
    }

    public void incorrect() {
        if (Build.VERSION.SDK_INT >= 23) {
            findViewById(R.id.activity_quiz).setBackgroundColor(getColor(R.color.incorrect));
        }
        else {
            findViewById(R.id.activity_quiz).setBackgroundColor(getResources().getColor(R.color.incorrect));
        }
    }
}

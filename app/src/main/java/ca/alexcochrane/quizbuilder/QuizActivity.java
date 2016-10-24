package ca.alexcochrane.quizbuilder;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.Map;

import ca.alexcochrane.quizbuilder.util.QuizUtilities;

public class QuizActivity extends AppCompatActivity {

    Map<String,String[]> questions;
    Map<String,String> answers;

    String question;
    String[] answer;

    int questionNum;

    int score;

    TextView questionText;
    TextView possibleAnswers;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionText = (TextView) findViewById(R.id.questionText);
        possibleAnswers = (TextView) findViewById(R.id.possibleAnswers);
        try {
            Map[] quiz = QuizUtilities.loadQuiz(this, getIntent().getStringExtra(QuizUtilities.QUESTION_SET));
            questions = quiz[0];
            answers = quiz[1];
            questionNum = 0;
            nextQuestion();
        }
        catch (IOException e) {
            Log.wtf(QuizUtilities.TAG,e.getCause().getMessage());
            e.printStackTrace();
        }
    }

    public void nextQuestion() {
        questionNum++;

        question = (String) questions.keySet().toArray()[0];
        answer = (String[]) QuizUtilities.shuffleArray(questions.get(question));

        questionText.setText(question);
        StringBuilder sb = new StringBuilder();
        sb.append("Question #").append(questionNum).append(":\n\n");
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
        if (questions.size()>0) {
            nextQuestion();
        }
        else {
            Intent intent = new Intent(this,ShowScoreActivity.class);
            intent.putExtras(getIntent());
            intent.putExtra("SCORE",score);
            startActivity(intent);
        }
    }

    public void correct() {
        score++;
        if (Build.VERSION.SDK_INT >= 23) {
            findViewById(R.id.activity_quiz).setBackgroundColor(getColor(R.color.correct));
        }
        else {
            findViewById(R.id.activity_quiz).setBackgroundColor(getResources().getColor(R.color.correct));
        }

        animateColorWhite(R.color.correct);
    }

    public void incorrect() {
        if (Build.VERSION.SDK_INT >= 23) {
            findViewById(R.id.activity_quiz).setBackgroundColor(getColor(R.color.incorrect));
        }
        else {
            findViewById(R.id.activity_quiz).setBackgroundColor(getResources().getColor(R.color.incorrect));
        }

        animateColorWhite(R.color.incorrect);
    }

    public void animateColorWhite(int colorId) {
        int colorFrom = getResources().getColor(colorId);
        int colorTo = getResources().getColor(R.color.white);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(1000);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                findViewById(R.id.activity_quiz).setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();
    }
}

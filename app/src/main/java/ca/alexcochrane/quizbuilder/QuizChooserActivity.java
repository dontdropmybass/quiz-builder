package ca.alexcochrane.quizbuilder;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.Map;

import ca.alexcochrane.quizbuilder.util.QuizUtilities;

public class QuizChooserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_chooser);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.quizList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        try {
            RecyclerView.Adapter mAdapter = new QuizAdapter(QuizUtilities.getQuizzes(this));
            mRecyclerView.setAdapter(mAdapter);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            Button button;

            ViewHolder(Button v) {
                super(v);
                button = v;
            }
        }

        Map<String,String> content;

        QuizAdapter(Map<String,String> content) {
            this.content = content;
        }
        @Override
        public QuizAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.quiz_list, parent, false);

            return new ViewHolder((Button)v);
        }

        @Override
        public void onBindViewHolder(final QuizAdapter.ViewHolder holder, int position) {
            holder.button.setText(
                content.keySet().toArray(new String[content.keySet().size()])[holder.getAdapterPosition()]
            );
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (QuizUtilities.validatePlayerName(
                            ((EditText)findViewById(R.id.playerName)).getText().toString())
                            ) {
                        Intent intent = new Intent(QuizChooserActivity.this, QuizActivity.class);
                        intent.putExtra(
                                QuizUtilities.QUESTION_SET,
                                content.get(
                                        content.keySet().toArray(
                                                new String[content.size()]
                                        )[holder.getAdapterPosition()]
                                )
                        );
                        intent.putExtra(
                                QuizUtilities.PLAYER_NAME,
                                ((EditText) findViewById(R.id.playerName)).getText().toString()
                        );
                        startActivity(intent);
                    }
                    else {
                        new AlertDialog.Builder(QuizChooserActivity.this)
                                .setTitle(R.string.bad_name_title)
                                .setMessage(R.string.bad_name_message)
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return content.size();
        }
    }
}

package ca.alexcochrane.quizbuilder.util;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public final class QuizUtilities {

    public static final String QUESTION_SET = "question_set";

    public static Map<String, String> loadSampleAnswerSet(Context context) throws IOException {
        Map<String, String> answers = new HashMap<>();
        Scanner scanner = new Scanner(context.getAssets().open("sample_answers.txt"));

        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(":");
            answers.put(line[0],line[1]);
        }
        scanner.close();
        return answers;
    }

    public static Map<String, String[]> loadSampleQuestionSet(Context context) throws IOException {
        Map<String, String[]> questions = new HashMap<>();
        File file = new File("/src/main/assets/sample_questions.txt");
        Scanner scanner = new Scanner(context.getAssets().open("sample_questions.txt"));
        while (scanner.hasNextLine()) {
            List<String> line = new LinkedList<>(Arrays.asList(scanner.nextLine().split(":")));
            questions.put(line.remove(0),line.toArray(new String[4]));
        }
        scanner.close();
        for (String[] a : questions.values()) {
            for (String s : a) {
                System.out.println(s);
            }
        }
        return questions;
    }

    public static Object[] shuffleArray(Object[] a) {
        List<Object> l = Arrays.asList(a);
        Collections.sort(l, new Comparator<Object>() {

            @Override
            public int compare(Object o1, Object o2) {
                return new Random().nextInt(3)-1;
            }
        });
        return l.toArray();
    }
}

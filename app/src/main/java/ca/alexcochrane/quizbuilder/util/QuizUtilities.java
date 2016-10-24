package ca.alexcochrane.quizbuilder.util;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public final class QuizUtilities {

    public static final String QUESTION_SET = "QUESTION_SET";
    public static final String PLAYER_NAME = "PLAYER_NAME";
    public static final String TAG = "Android Quiz Builder";

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
        Scanner scanner = new Scanner(context.getAssets().open("sample_questions.txt"));

        while (scanner.hasNextLine()) {
            List<String> line = new ArrayList<>(Arrays.asList(scanner.nextLine().split(":")));
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

    public static Map[] loadQuiz(Context context,String filename) throws IOException {
        Map[] maps = new Map[2];
        Map<String,String[]> questions = new HashMap<>();
        Map<String,String> answers = new HashMap<>();

        Scanner scanner = new Scanner(context.getAssets().open(filename));

        while (scanner.hasNextLine()) {
            List<String> line = new ArrayList<>(Arrays.asList(scanner.nextLine().split(":")));
            answers.put(line.get(0),line.get(1));
            questions.put(line.remove(0),line.toArray(new String[4]));
        }
        scanner.close();

        maps[0] = questions;
        maps[1] = answers;

        return maps;
    }

    public static Map<String,String> getQuizzes(Context context) throws IOException {
        Map<String,String> files = new HashMap<>();
        Scanner scanner = new Scanner(context.getAssets().open("file_manifest"));
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(":");
            files.put(line[0],line[1]);
        }
        return files;
    }

    public static Object[] shuffleArray(Object[] a) {
        List<Object> l = Arrays.asList(a);
        Collections.shuffle(l);
        return l.toArray();
    }

    public static boolean validatePlayerName(String name) {
        return Pattern.matches("^[A-Za-z]+$",name);
    }
}

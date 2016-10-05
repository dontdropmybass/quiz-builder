package ca.alexcochrane.quizbuilder.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by alex on 2016-10-05.
 */

public final class QuizUtilies {
    public static Map<String, String> loadSampleAnswerSet() {
        Map<String, String> answers = new HashMap<>();
        Scanner scanner = new Scanner("src/main/assets/sample_answers.txt");
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(":");
            answers.put(line[0],line[1]);
        }
        scanner.close();
        return answers;
    }

    public static Map<String, String[]> loadSampleQuestionSet() {
        Map<String, String[]> questions = new HashMap<>();
        Scanner scanner = new Scanner("/src/main/assets/sample_questions.txt");
        while (scanner.hasNextLine()) {
            List<String> line = Arrays.asList(scanner.nextLine().split(":"));
            questions.put(line.remove(0),line.toArray(new String[4]));
        }
        scanner.close();
        return questions;
    }
}

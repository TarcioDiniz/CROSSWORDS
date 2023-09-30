package org.cross.words.core.word;

import org.cross.words.Utilities.Vector2D;

import java.util.ArrayList;


/*
 *
 * This class handles operations related to words,
 * including validity check, handling of
 * characters and storage of information associated with the
 * word. The class is well encapsulated and provides methods
 * useful for these specific tasks.
 *
 */


public class Word {

    private String word;
    private int size;
    private ArrayList<Vector2D> vector2DArrayList;
    private String question;

    public Word(String word) {
        setWord(word);
    }

    public void setWord(String word) {
        if (isWord(word)) {
            this.word = word.toUpperCase();
            this.size = word.length();
        }
    }

    public static boolean isWord(String text) {
        text = text.trim();
        if (text.isEmpty()) {
            return false;
        }
        return text.chars().allMatch(Character::isLetter);
    }

    public String getWord() {
        return word;
    }

    public int getSize() {
        return size;
    }

    public ArrayList<Vector2D> getVector2DArrayList() {
        return vector2DArrayList;
    }

    public void setVector2DArrayList(ArrayList<Vector2D> vector2DArrayList) {
        this.vector2DArrayList = vector2DArrayList;
    }

    public static boolean isVowel(char c) {
        char[] vowels = {'A', 'E', 'I', 'O', 'U'};
        for (char vowel : vowels) {
            if (c == vowel) {
                return true;
            }
        }
        return false;
    }

    public static void removeWhitespace(ArrayList<String> stringList) {
        for (int i = 0; i < stringList.size(); i++) {
            String originalString = stringList.get(i);
            String stringWithoutWhitespace = originalString.replaceAll("\\s+", " ");
            stringList.set(i, stringWithoutWhitespace);
        }
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public boolean containsChar(char ch) {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == ch) {
                return true;
            }
        }
        return false;
    }
}

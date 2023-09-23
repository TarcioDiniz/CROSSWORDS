    package org.cross.words.core.word;

    import org.cross.words.Utilities.Vector2D;

    import java.util.ArrayList;
    import java.util.List;

    public class Word {

        private String word;
        private int size;
        private ArrayList<Vector2D> vector2DArrayList;

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
    }

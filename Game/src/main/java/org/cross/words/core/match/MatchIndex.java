package org.cross.words.core.match;

import org.cross.words.core.word.Word;

import java.util.ArrayList;

public class MatchIndex {
    private Word word1;
    private Word word2;
    private ArrayList<Integer> index;

    public MatchIndex(Word word1, Word word2, ArrayList<Integer> index) {
        this.word1 = word1;
        this.word2 = word2;
        this.index = index;
    }

    public Word getWord1() {
        return word1;
    }

    public Word getWord2() {
        return word2;
    }

    public ArrayList<Integer> getIndex() {
        return index;
    }
}

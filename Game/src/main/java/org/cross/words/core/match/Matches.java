package org.cross.words.core.match;

import org.cross.words.core.word.Word;

import java.util.ArrayList;
import java.util.Comparator;

public class Matches {
    ArrayList<Word> words;
    ArrayList<MatchIndex> matchIndices = new ArrayList<>();

    public Matches(ArrayList<Word> newWords) {
        this.words = newWords;
        createMachIndex();
    }

    public static ArrayList<Integer> findPositionsOfMatchingCharacters(String str1, String str2) {
        ArrayList<Integer> positions = new ArrayList<>();
        for (int i = 0; i < str1.length(); i++) {
            for (int j = 0; j < str2.length(); j++) {
                if (str1.charAt(i) == str2.charAt(j)) {
                    positions.add(i);
                    break;
                }
            }
        }
        return positions;
    }

    private void createMachIndex() {

        for (int i = 0; i < words.size(); i++) {
            for (int j = 0; j < words.size(); j++) {
                if (i != j) { // Evita formar pares iguais (por exemplo, "A" e "A")
                    Word element1 = words.get(i);
                    Word element2 = words.get(j);
                    MatchIndex NewMatchIndex = new MatchIndex(
                            element1,
                            element2,
                            findPositionsOfMatchingCharacters(element1.getWord(), element2.getWord()));
                    this.matchIndices.add(NewMatchIndex);
                }
            }
        }
    }

    public ArrayList<MatchIndex> getMatchIndices() {
        return matchIndices;
    }

    public ArrayList<Word> getWords() {
        return words;
    }

    public Word getWordMaxSize() {
        return words.stream()
                .max(Comparator.comparingInt(Word::getSize))
                .orElse(null);
    }

    public int getMaxNumberOfLettersInWord() {
        return words.stream().mapToInt(Word::getSize).sum();
    }

}

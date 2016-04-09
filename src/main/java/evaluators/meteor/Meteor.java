package evaluators.meteor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Meteor {

    public double getScore(String translation, String reference) {
        translation = translation.toLowerCase().trim();
        reference = reference.toLowerCase().trim();
        List<String> translationWords = Arrays.asList(translation.split(" "));
        List<String> referenceWords = Arrays.asList(reference.split(" "));
        double numInBoth = numUngiramsInBoth(translationWords, referenceWords);
        double precision = numInBoth / (double) translationWords.size();
        double recall = numInBoth / (double) referenceWords.size();
        double fmean = (10 * precision * recall) / (recall + (9 * precision));
        if (Double.isNaN(fmean)) {
            fmean = 0;
        }
        double p = calculateChunkingPenalty(translationWords, referenceWords);
        if (Double.isNaN(p)) {
            p = 0;
        }
        return fmean * (1 - p);
    }

    private double numUngiramsInBoth(List<String> translationWords, List<String> referenceWords) {
        int count = 0;
        for (String word : translationWords) {
            if (referenceWords.contains(word)) count++;
        }
        return (double) count;
    }

    private double calculateChunkingPenalty(List<String> translationWords, List<String> referenceWords) {
        int numChunks = 0;
        List<String> subString = new ArrayList<String>();
        List<String> usedWords = new ArrayList<String>();
        for (int i = 0; i < translationWords.size(); i++) {
            subString.add(translationWords.get(i));
            int index = Collections.indexOfSubList(referenceWords, subString);
            if (index > -1 && i + 1 == translationWords.size()) {
                usedWords.addAll(subString);
                numChunks++;
            } else if (index > -1) {
                subString.add(translationWords.get(i + 1));
                int index2 = Collections.indexOfSubList(referenceWords, subString);
                subString.remove(subString.size() - 1);
                if (index2 == -1) {
                    usedWords.addAll(subString);
                    numChunks++;
                    subString.clear();
                }
            } else subString.clear();
        }
        double calc = (double) numChunks / (double) usedWords.size();
        if (Double.isNaN(calc)) {
            calc = 0;
        }
        return 0.5 * Math.pow(calc, 3);
    }
}
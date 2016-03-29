package evaluators.bleu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bleu {

    private List<String> ngrams(String[] words, int n) {
        List<String> ngrams = new ArrayList<String>();
        for (int i = 0; i <= words.length - n; i++) {
            String gram = "";
            for (int j = i; j < i + n; j++) {
                gram += words[j] + " ";
            }
            gram = gram.substring(0, gram.length() - 1);
            ngrams.add(gram);
        }
        return ngrams;
    }

    public double getScore(String translation, String reference, int n) {
        String[] translationWords = translation.split(" ");
        String[] referenceWords = reference.split(" ");
        double min = Math.min(1, (((double) translationWords.length) / referenceWords.length));
        double score = 0;
        for (int i = 1; i <= n; i++) {
            double correctNgram = 0;
            List<String> translationNgrams = ngrams(translationWords, i);
            double totalNgramLength = translationNgrams.size();
            List<String> referenceNgrams = ngrams(referenceWords, i);
            translationNgrams = clipNgrams(translationNgrams, reference);
            for (String word : translationNgrams) {
                if (referenceNgrams.contains(word)) {
                    correctNgram = correctNgram + 1;
                }
            }
            double precision = correctNgram / totalNgramLength;
            score = score * precision;
        }
        return min * (Math.pow(score, (1 / n)));
    }

    private List<String> clipNgrams(List<String> translatedNGrams, String reference) {
        Map<String, Integer> repeated = new HashMap<String, Integer>();
        for (String gram : translatedNGrams) {
            if (!repeated.containsKey(gram)) {
                repeated.put(gram, 1);
            } else {
                repeated.put(gram, repeated.get(gram) + 1);
            }
        }
        for (String word : repeated.keySet()) {
            int count = count(reference, word);
            int wordCount = repeated.get(word);
            if (wordCount >= count) {
                for (int i = 0; i < wordCount - count; i++) {
                    translatedNGrams.remove(word);
                }
            }
        }
        return translatedNGrams;
    }

    private int count(String string, String substring) {
        int count = 0, idx = 0;
        while ((idx = string.indexOf(substring, idx)) != -1) {
            idx++;
            count++;
        }
        return count;
    }
}
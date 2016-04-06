package translators.google;

import evaluators.bleu.Bleu;
import readers.api.GoogleApiReader;
import readers.file.TextFileReader;
import writers.TextFileWriter;

import java.io.IOException;
import java.util.List;

/**
 * Runs evaluation for Google
 *
 * @author John Brennan - john.brennan55@mail.dcu.ie
 * @version 1.0
 * @since 06/04/2016
 */
public class Google {
    private TextFileReader textFileReader;
    private TextFileWriter textFileWriter;
    private GoogleApiReader googleApiReader;
    private Bleu bleu;

    private String source;
    private String target;
    private List<String> text;
    private List<String> reference;
    private String file;

    public Google(TextFileReader textFileReader, TextFileWriter textFileWriter, Bleu bleu) {
        this.googleApiReader = new GoogleApiReader();
        this.textFileReader = textFileReader;
        this.textFileWriter = textFileWriter;
        this.bleu = bleu;
    }

    public void init(String source, String target, List<String> text, List<String> reference) {
        this.source = source;
        this.target = target;
        this.text = text;
        this.reference = reference;
        this.file = "./src/main/resources/translations/google_" + source + "_" + target + ".txt";
    }

    public void translate(int lines) throws IOException {
        textFileWriter.setWriter(file);
        for (int i = 0; i < lines; i++) {
            if(text.get(i).length() > 5) {
                String translation = googleApiReader.read(source, target, text.get(i));
                System.out.println(translation);
                textFileWriter.write(translation);
            }
        }
        textFileWriter.close();
    }

    public void evaluate() throws IOException {
        List<String> translations = textFileReader.read(file);
        double bleuScore = 0;
        double nistScore = 0;
        double meteorScore = 0;

        for (int i = 0; i < translations.size(); i++) {
            double score = bleu.getScore(translations.get(i), reference.get(i), 4);
            bleuScore += score;
        }

        bleuScore /= translations.size();
        nistScore /= translations.size();
        meteorScore /= translations.size();

        print(bleuScore, nistScore, meteorScore);
    }

    public void print(double bleu, double nist, double meteor) {
        System.out.println("____ GOOGLE ____");
        System.out.println();
        System.out.println("BLEU: " + bleu);
        System.out.println("NIST: " + nist);
        System.out.println("METEOR: " + meteor);
        System.out.println();
    }
}

package translators.google;

import evaluators.bleu.Bleu;
import evaluators.meteor.Meteor;
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
    private GoogleApiReader googleApiReader;
    private TextFileReader textFileReader;
    private TextFileWriter textFileWriter;
    private Bleu bleu;
    private Meteor meteor;

    private String file;
    private String source;
    private String target;
    private List<String> text;
    private List<String> reference;


    public Google(TextFileWriter textFileWriter, TextFileReader textFileReader, Bleu bleu, Meteor meteor) {
        this.textFileReader = textFileReader;
        this.textFileWriter = textFileWriter;
        this.bleu = bleu;
        this.meteor = meteor;
        this.googleApiReader = new GoogleApiReader();
    }

    public void init(String source, String target, List<String> text, List<String> reference) {
        this.source = source;
        this.target = target;
        this.text = text;
        this.reference = reference;
        this.file = "translations/google_" + source + "_" + target + ".txt";
    }

    public void translate(int lines) throws Exception {
        textFileWriter.setWriter(file);
        int read = 0;
        int write = 0;
        while(write <= lines) {
            if (text.get(read).length() > 5) {
                textFileWriter.write(googleApiReader.read(source, target, text.get(read)));
                write++;
            }
            read++;
        }
        textFileWriter.close();
    }

    public void evaluate() throws IOException {
        List<String> translations = textFileReader.read(file);
        double bleuScore = 0;
        double meteorScore = 0;
        double nistScore = 0;

        for (int i = 0; i < translations.size(); i++) {
            bleuScore += bleu.getScore(translations.get(i), reference.get(i), 4);
            meteorScore += meteor.getScore(translations.get(i), reference.get(i));
        }

        bleuScore /= translations.size();
        meteorScore /= translations.size();
        nistScore /= translations.size();

        print(bleuScore, meteorScore, nistScore);
    }

    private void print(double bleu, double meteor, double nist) {
        System.out.println("____ GOOGLE ____");
        System.out.println();
        System.out.println("BLEU: " + bleu);
        System.out.println("METEOR: " + meteor);
        System.out.println("NIST: " + nist);
        System.out.println();
    }

}

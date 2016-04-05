import evaluators.bleu.Bleu;
import readers.TextFileReader;
import translators.Moses;
import writers.TextFileWriter;

import java.io.IOException;
import java.util.List;

/**
 * Launcher
 *
 * @author Conor Hughes - hello@conorhughes.me
 * @version 1.0
 * @since 29/03/2016
 */

public class Launcher {

    private static final String ENGLISH_CZECH_FILENAME = "en-cz.txt";
    private static final String CZECH_ENGLISH_FILENAME = "cz-en.txt";

    public static void main(String[] args) {
        TextFileReader textFileReader = new TextFileReader();
        TextFileWriter textFileWriter = new TextFileWriter();
        Bleu bleu = new Bleu();

        try {
            List<String> english_czech_file = textFileReader.readResources(ENGLISH_CZECH_FILENAME);
            List<String> czech_english_file = textFileReader.readResources(CZECH_ENGLISH_FILENAME);

            Moses moses = new Moses(textFileReader, textFileWriter, bleu);
            moses.init("en", "cs", english_czech_file, czech_english_file);
            //moses.translate(100);
            moses.evaluate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
import evaluators.bleu.Bleu;
import readers.file.TextFileReader;
import translators.bing.Bing;
import translators.moses.Moses;
import translators.yandex.Yandex;
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

            moses(textFileReader, textFileWriter, bleu, "en", "cs", english_czech_file, czech_english_file, false);
            yandex(textFileReader, textFileWriter, bleu, "en", "cs", english_czech_file, czech_english_file, false);
            bing(textFileReader, textFileWriter, bleu, "en", "cs", english_czech_file, czech_english_file, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void moses(TextFileReader textFileReader, TextFileWriter textFileWriter, Bleu bleu, String sourceLanguage, String targetLanguage, List<String> source, List<String> reference, boolean translate) throws IOException {
        Moses moses = new Moses(textFileReader, textFileWriter, bleu);
        moses.init(sourceLanguage, targetLanguage, source, reference);
        if(translate){
            moses.translate(100);
        }
        moses.evaluate();
    }

    private static void yandex(TextFileReader textFileReader, TextFileWriter textFileWriter, Bleu bleu, String sourceLanguage, String targetLanguage, List<String> source, List<String> reference, boolean translate) throws IOException {
        Yandex yandex = new Yandex(textFileReader, textFileWriter, bleu);
        yandex.init(sourceLanguage, targetLanguage, source, reference);
        if(translate) {
            yandex.translate(100);
        }
        yandex.evaluate();
    }

    private static void bing(TextFileReader textFileReader, TextFileWriter textFileWriter, Bleu bleu, String sourceLanguage, String targetLanguage, List<String> source, List<String> reference, boolean translate) throws Exception {
        Bing bing = new Bing(textFileReader, textFileWriter, bleu);
        bing.init(sourceLanguage, targetLanguage, source, reference);
        if(translate) {
            bing.translate(100);
        }
        bing.evaluate();
    }
}
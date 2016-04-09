package controllers;

import evaluators.bleu.Bleu;
import evaluators.meteor.Meteor;
import readers.file.TextFileReader;
import translators.bing.Bing;
import translators.google.Google;
import translators.moses.Moses;
import translators.yandex.Yandex;
import writers.TextFileWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * Launcher
 *
 * @author Conor Hughes - hello@conorhughes.me
 * @version 1.0
 * @since 29/03/2016
 */

public class TranslatorController {

    private final String ENGLISH_CZECH_FILENAME = "en-cz.txt";
    private final String CZECH_ENGLISH_FILENAME = "cz-en.txt";

    private TextFileReader textFileReader;
    private TextFileWriter textFileWriter;
    private Bleu bleu;
    private Meteor meteor;

    private Bing bing;
    private Google google;
    private Moses moses;
    private Yandex yandex;

    public void setup() {
        textFileReader = new TextFileReader();
        textFileWriter = new TextFileWriter();
        bleu = new Bleu();
        meteor = new Meteor();

        bing = new Bing(textFileWriter, textFileReader, bleu, meteor);
        google = new Google(textFileWriter, textFileReader, bleu, meteor);
        moses = new Moses(textFileWriter, textFileReader, bleu, meteor);
        yandex = new Yandex(textFileWriter, textFileReader, bleu, meteor);
    }

    public void setLanguages(String sourceLanguage, String targetLanguage) {
        List<String> source = new ArrayList<String>();
        List<String> reference = new ArrayList<String>();
        try {
            if (sourceLanguage.equals("en") && targetLanguage.equals("cs")) {
                source = textFileReader.readResources(ENGLISH_CZECH_FILENAME);
                reference = textFileReader.readResources(CZECH_ENGLISH_FILENAME);
            } else if (sourceLanguage.equals("cs") && targetLanguage.equals("en")) {
                source = textFileReader.readResources(CZECH_ENGLISH_FILENAME);
                reference = textFileReader.readResources(ENGLISH_CZECH_FILENAME);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        bing.init(sourceLanguage, targetLanguage, source, reference);
        google.init(sourceLanguage, targetLanguage, source, reference);
        moses.init(sourceLanguage, targetLanguage, source, reference);
        yandex.init(sourceLanguage, targetLanguage, source, reference);
    }

    public void translate(int lines) {
        try {
            bing.translate(lines);
            google.translate(lines);
            moses.translate(lines);
            yandex.translate(lines);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void evaluate() {
        try {
            bing.evaluate();
            google.evaluate();
            moses.evaluate();
            yandex.evaluate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package translators;

import evaluators.bleu.Bleu;
import readers.MosesApiReader;
import readers.TextFileReader;
import writers.TextFileWriter;

import java.io.IOException;
import java.util.List;

/**
 * Runs evaluation for Moses
 *
 * @author Conor Hughes - hello@conorhughes.me
 * @version 1.0
 * @since 31/03/2016
 */
public class Moses {

    private MosesApiReader mosesApiReader;
    private TextFileReader textFileReader;
    private TextFileWriter textFileWriter;
    private Bleu bleu;

    private String source;
    private String target;
    private List<String> text;
    private List<String> reference;
    private String file;


    public Moses(TextFileReader textFileReader, TextFileWriter textFileWriter, Bleu bleu) {
        mosesApiReader = new MosesApiReader();
        this.textFileReader = textFileReader;
        this.textFileWriter = textFileWriter;
        this.bleu = bleu;
    }

    public void init(String source, String target, List<String> text, List<String> reference) {
        this.source = source;
        this.target = target;
        this.text = text;
        this.reference = reference;
        this.file = "translations/moses_" + source + "_" + target + ".txt";
    }

    public void translate(int lines) throws IOException {
        textFileWriter.setWriter(file);
        for (int i = 0; i < lines; i++) {
            if(text.get(i).length() > 5) {
                String translation = mosesApiReader.read(source, target, text.get(i));
                textFileWriter.write(translation);
            }
        }
        textFileWriter.close();
    }

    public void evaluate() throws IOException {
        List<String> translations = textFileReader.read(file);
        double bleuScore = 0;
        for (int i = 0; i < translations.size(); i++) {
            double score = bleu.getScore(translations.get(i), reference.get(i), 4);
            System.out.println(translations.get(i) + " vs " + reference.get(i));
            System.out.println(score);
            bleuScore += score;
        }
    }

}

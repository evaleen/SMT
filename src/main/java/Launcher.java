import evaluators.bleu.Bleu;
import readers.File;
import readers.Translator;
import writers.FileWriter;

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
    public static void main(String[] args) {
        try {
            List<String> en_cz_lines = readFile("en-cz.txt");
            List<String> cz_en_lines = readFile("cz-en.txt");

            Translator translator = new Translator();
            FileWriter fileWriter = new FileWriter("test.txt");
            Bleu bleu = new Bleu();

            for (int i = 0; i < 10; i++) {
                String translation = translator.readMoses("en", "cs", en_cz_lines.get(i));
                double score = bleu.getScore(translation, cz_en_lines.get(i), 4);
                System.out.println(translation);
                System.out.println(cz_en_lines.get(i));
                System.out.println(score);
                System.out.println();
                fileWriter.write(translation);
            }
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> readFile(String file) throws IOException {
        File fileReader = new File();
        return fileReader.read(file);
    }
}
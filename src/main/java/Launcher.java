import readers.File;

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
            List<String> cz_en_lines = readFile("cz-en.txt");
            List<String> en_cz_lines = readFile("en-cz.txt");
            System.out.println(cz_en_lines.size());
            System.out.println(en_cz_lines.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> readFile(String file) throws IOException {
        File fileReader = new File();
        return fileReader.read(file);
    }
}
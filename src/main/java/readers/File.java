package readers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads in files
 *
 * @author Conor Hughes - hello@conorhughes.me
 * @version 1.0
 * @since 29/03/2016
 */
public class File {

    public List<String> read(String filePath) throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        java.io.File file = new java.io.File(classLoader.getResource(filePath).getFile());

        List<String> lines = new ArrayList<String>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }
}

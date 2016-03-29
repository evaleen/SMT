package writers;


import java.io.*;

/**
 * Writes to file
 *
 * @author Conor Hughes - hello@conorhughes.me
 * @version 1.0
 * @since 29/03/2016
 */
public class FileWriter {

    private Writer writer;

    public FileWriter(String path) throws UnsupportedEncodingException, FileNotFoundException {
        OutputStream outputStream = new FileOutputStream(path);
        writer = new OutputStreamWriter(outputStream, "UTF-8");
    }

    public void write(String line) throws IOException {
        writer.write(line + "\n");
    }

    public void close() throws IOException {
        writer.close();
    }
}

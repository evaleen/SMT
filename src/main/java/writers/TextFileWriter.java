package writers;


import java.io.*;

/**
 * Writes to file
 *
 * @author Conor Hughes - hello@conorhughes.me
 * @version 1.0
 * @since 29/03/2016
 */
public class TextFileWriter {

    private Writer writer;

    public void setWriter(String path) throws UnsupportedEncodingException, FileNotFoundException {
        File file = new File(path);
        OutputStream outputStream = new FileOutputStream(file);
        writer = new OutputStreamWriter(outputStream, "UTF-8");
    }

    public void write(String line) throws IOException {
        writer.write(line + "\n");
    }

    public void close() throws IOException {
        writer.close();
    }
}

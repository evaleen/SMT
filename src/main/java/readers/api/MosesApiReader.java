package readers.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import parsers.StringParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Reads from url
 *
 * @author Conor Hughes - hello@conorhughes.me
 * @version 1.0
 * @since 29/03/2016
 */
public class MosesApiReader {

    public String read(String sourceLanguage, String targetLanguage, String text) throws IOException {
        text = StringParser.addUrlSpacing(text);
        String url = "http://lindat.mff.cuni.cz/services/moses/request.php?action=translate&model=0&sourceLang=" + sourceLanguage + "&targetLang=" + targetLanguage + "&text=" + text;
        InputStream inputStream = new URL(url).openStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(result.toString());
        return element.getAsJsonObject().get("translation").getAsJsonArray().get(0).getAsJsonObject().get("translated").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString();
    }
}

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
public class YandexApiReader {

    public String read(String sourceLanguage, String targetLanguage, String text) throws IOException {
        text = StringParser.addUrlSpacing(text);
        String url = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20160405T122943Z.f3e14188fead19a9.4dc5979d51ae22c223a83396b633dde22921cbe3&text=" + text + "&lang=" + sourceLanguage + "-" + targetLanguage;
        InputStream inputStream = new URL(url).openStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(result.toString());
        return element.getAsJsonObject().get("text").getAsJsonArray().get(0).getAsString();
    }
}

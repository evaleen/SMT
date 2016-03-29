package readers;

import org.json.JSONArray;
import org.json.JSONObject;
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
public class Translator {

    public String readMoses(String sourceLanguage, String targetLanguage, String text) throws IOException {
        text = StringParser.addUrlSpacing(text);
        String url = "http://lindat.mff.cuni.cz/services/moses/request.php?action=translate&model=0&sourceLang=" + sourceLanguage + "&targetLang=" + targetLanguage + "&text=" + text;
        InputStream inputStream = new URL(url).openStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        JSONObject jsonObject = new JSONObject(result.toString());
        JSONArray jsonArray = jsonObject.getJSONArray("translation");
        JSONObject translations = (JSONObject) jsonArray.get(0);
        JSONArray translation = (JSONArray) translations.get("translated");
        JSONObject translated = (JSONObject) translation.get(0);
        return translated.get("text").toString();
    }
}

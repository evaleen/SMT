package readers.api;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

/**
 * Reads from url
 *
 * @author Conor Hughes - hello@conorhughes.me
 * @version 1.0
 * @since 29/03/2016
 */
public class BingApiReader {

    public String read(String sourceLanguage, String targetLanguage, String text) throws Exception {
        Translate.setClientId("ca4012");
        Translate.setClientSecret("dKx1FmPe0Vv0bIQierKY3ELyGP+rTBvvtYaXKjSzO44=");
        Language language = getLanguage(targetLanguage);
        return Translate.execute(text, language);
    }

    private Language getLanguage(String language) {
        if(language.equals("cs")) {
            return Language.CZECH;
        }
        return null;
    }
}


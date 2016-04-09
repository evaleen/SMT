import controllers.TranslatorController;

/**
 * Launcher
 *
 * @author Conor Hughes - hello@conorhughes.me
 * @version 1.0
 * @since 29/03/2016
 */

public class Launcher {

    public static void main(String[] args) {
        TranslatorController translatorController = new TranslatorController();
        translatorController.setup();

        System.out.println("ENGLISH TO CZECH");
//        translatorController.setLanguages("en", "cs");
//        translatorController.translate(1515);
//        translatorController.evaluate();

        System.out.println("CZECH TO ENGLISH");
        translatorController.setLanguages("cs", "en");
        translatorController.translate(1515);
        translatorController.evaluate();
    }
}
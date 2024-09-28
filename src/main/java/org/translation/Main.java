package org.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    public static void main(String[] args) {

        Translator translator = new JSONTranslator();

        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {

        CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
        LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();

        while (true) {

            String quit = "quit";
            String country = promptForCountry(translator);
            if (quit.equals(country)) {
                break;
            }

            String language = promptForLanguage(translator, country);

            if (quit.equals(language)) {
                break;
            }

            String countryName = countryCodeConverter.fromCountryCode(country);
            String languageName = languageCodeConverter.fromLanguageCode(language);

            System.out.println(countryName + " in " + languageName + " is " + translator.translate(country, language));
            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (quit.equals(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {

        List<String> countries = translator.getCountries();
        CountryCodeConverter countryCodeConverter = new CountryCodeConverter();

        for (int i = 0; i < countries.size(); i++) {
            String countryCode = countries.get(i);
            String countryName = countryCodeConverter.fromCountryCode(countryCode);
            countries.set(i, countryName);
        }

        Collections.sort(countries);

        for (String name : countries) {
            System.out.println(name);
        }

        System.out.println(countries);

        System.out.println("select a country from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {

        List<String> languageCodes = translator.getCountryLanguages(country);
        LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();

        for (int i = 0; i < languageCodes.size(); i++) {
            String languagesCode = languageCodes.get(i);
            String languagesName = languageCodeConverter.fromLanguageCode(languagesCode);
            languageCodes.set(i, languagesName);
        }

        Collections.sort(languageCodes);

        for (String label : languageCodes) {
            System.out.println(label);
        }

        System.out.println("Select a language from above:");
        Scanner s = new Scanner(System.in);

        return s.nextLine();
    }
}

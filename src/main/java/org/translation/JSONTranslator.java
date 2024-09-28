package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    private final Map<String, Map<String, String>> countriestranslations;

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        countriestranslations = new HashMap<>();

        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject countryObject = jsonArray.getJSONObject(i);
                String countryCode = countryObject.getString("alpha3");

                Map<String, String> translations = new HashMap<>();

                for (String key : countryObject.keySet()) {

                    if (!"id".equals(key) && !"alpha2".equals(key) && !"alpha3".equals(key)) {
                        String languageCode = key;
                        String countryName = countryObject.getString(languageCode);
                        translations.put(languageCode, countryName);
                    }
                }

                // Add the country's translations to the main map
                countriestranslations.put(countryCode, translations);
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        Map<String, String> translations = countriestranslations.get(country);

        return new ArrayList<>(translations.keySet());

    }

    @Override
    public String translate(String country, String language) {
        Map<String, String> translations = countriestranslations.get(country);
        if (translations != null) {
            String translatedName = translations.get(language);
            if (translatedName != null) {
                return translatedName;
            }
        }
        return null;
    }
}


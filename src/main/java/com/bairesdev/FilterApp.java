package com.bairesdev;

import com.bairesdev.entity.Profile;
import com.bairesdev.util.AppUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This is the main class of the application, the filters logic is applied here using
 * streams.
 * @auhtor Daniel Moreno - Agosto 18 de 2016
 */
public class FilterApp {

    private static final String PROPS_NAME = "dictionary.properties";
    private static final String PROP_TITLE = "title";
    private static final String PROP_ROLE = "current.role";
    private static final String PROP_INDUSTRY = "industry";
    private static List<Profile> list = new ArrayList<>();
    private static List<String> titles = new ArrayList<>();
    private static List<String> roles = new ArrayList<>();
    private static List<String> industries = new ArrayList<>();


    public static void main(String[] args) {

        String fileName = "";

        /**
         * The file required to be analyzed should be pass to the application as the first parameter
         * in the command line (see usage below)
         */
        if (args.length > 0) {
            fileName = args[0];
        } else {
            System.out.println("Proper usage is: java -jar filterApp.jar [file-path]");
            System.exit(0);
        }

        Properties prop = new Properties();
        InputStream input = null;

        /**
         * This block gets the properties file as a resource stream
         */
        try {
            input = FilterApp.class.getClassLoader().getResourceAsStream(PROPS_NAME);
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * This block reads the file and call the method buildObject for each line found on it
         */
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(line -> buildObject(line));
        } catch (IOException e) {
            System.err.print("The file doesn't exist!");
        }

        /**
         * Each list contains a set of words specified in the dictionary for an specific field
         * (see dictionary.properties)
         */
        titles = AppUtils.setPropertyArray(prop.getProperty(PROP_TITLE));
        roles = AppUtils.setPropertyArray(prop.getProperty(PROP_ROLE));
        industries = AppUtils.setPropertyArray(prop.getProperty(PROP_INDUSTRY));

        //This filter removes the profiles that doesn't contain an industry related to the ones found in the dictionary
        list.removeIf(profile -> !industries.stream().anyMatch(industry -> profile.getIndustry().contains(industry)));
        //This filter removes the profiles that doesn't contain a title related to the ones found in the dictionary
        list.removeIf(profile -> !titles.stream().anyMatch(title -> profile.getTitle().contains(title)));
        //This filter removes the profiles that doesn't contain a role related to the ones found in the dictionary
        list.removeIf(profile -> !roles.stream().anyMatch(role -> profile.getCurrentRole().contains(role)));
        list.removeIf(profile -> profile.getNumberOfConnections() == -1 || profile.getNumberOfRecommendations() == -1);

        //The list gets sorted from the lowest values to the biggest values of NumberOfRecommendations and NumberOfConnections
        list.sort(Comparator.comparing(Profile::getNumberOfRecommendations).thenComparing(Profile::getNumberOfConnections));
        Collections.reverse(list);


        //Finally a sublist is created with the first 30 positions of the filtered list
        List<Profile> filteredList = list
                .stream()
                .limit(30)
                .collect(Collectors.toList());

        //The name, lastname and URL are printed for each profile
        filteredList.forEach(p -> System.out.println(p.getName() + " - " + p.getLastName() + " - " + p.getPublicProfileURL()));

    }

    /**
     * Creates a Profile DTO for each line found in the file specified
     * @param line
     */
    public static void buildObject(String line) {

        StringTokenizer tokenizer = new StringTokenizer(line, "|");
        String recommendations = "";
        String connections = "";

        //The profiles should have no more or less than the 10 fields specified in the file
        if (tokenizer.countTokens() == 10) {
            while (tokenizer.hasMoreElements()) {
                Profile p = new Profile();
                p.setPublicProfileURL(tokenizer.nextElement().toString().trim());
                p.setName(tokenizer.nextElement().toString().trim());
                p.setLastName(tokenizer.nextElement().toString().trim());
                p.setTitle(tokenizer.nextElement().toString().trim());
                p.setGeographicArea(tokenizer.nextElement().toString().trim());
                recommendations = tokenizer.nextElement().toString().trim();
                p.setNumberOfRecommendations(recommendations.equals("") ? -1 : Integer.valueOf(recommendations));
                connections = tokenizer.nextElement().toString().trim();
                p.setNumberOfConnections(connections.equals("") ? -1 : Integer.valueOf(connections));
                p.setCurrentRole(tokenizer.nextElement().toString().trim());
                p.setIndustry(tokenizer.nextElement().toString().trim());
                p.setCountry(tokenizer.nextElement().toString().trim());
                list.add(p);
            }
        }
    }
}

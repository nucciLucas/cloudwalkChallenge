import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.util.MeansOfDeathClass;
import com.util.Pair;

public class MeansOfDeath {

    public static void printMeansOfDeath(String logFilePath, int startLine, int endLine,
            MeansOfDeathClass meanOfDeathObj) {

        Map<String, Integer> kills = new HashMap<>();
        String[] meansOfDeathInMatch = getMeansDeathMatch(logFilePath, startLine, endLine);
        Integer[] meansKills = meansOfDeathAmount(logFilePath, startLine, endLine, meansOfDeathInMatch);
        List<Pair> pairs = meansKills(meansOfDeathInMatch, meansKills);

        for (Pair pair : pairs) {
            kills.put(pair.getKey(), pair.getValue());
        }

        meanOfDeathObj.setKills(kills);

        // System.out.println("\nMEANS OF DEATH:");
        System.out.println("\n" + meanOfDeathObj.toString() + "\n");
    }

    private static String[] getMeansDeathMatch(String logFilePath, int startLine, int endLine) {
        List<String> meansOfDeath = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber >= startLine && lineNumber <= endLine && line.contains("Kill:")) {

                    // String regex = "(.+?) killed (.+?) by (.+?)";
                    String regex = "(?<=by\\s)(.+)$";

                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        String meanOfDeath = matcher.group(1).trim();
                        meansOfDeath.add(meanOfDeath);
                    } else {
                        System.out.println("Error: Means of Death not found.");
                    }

                }
                // Stop reading after reaching endLine
                if (lineNumber > endLine) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return players.toArray(new String[0]);
        return removeDuplicates(meansOfDeath.toArray(new String[0]));
    }

    private static String[] removeDuplicates(String[] array) {
        // Convert the array to a Set to remove duplicates
        Set<String> set = new HashSet<>(Arrays.asList(array));

        // Convert the Set back to an array
        return set.toArray(new String[0]);
    }

    private static int findMeansOfDeathIndex(String[] meansOfDeath, String meanOfDeath) {
        int index = -1;
        for (int i = 0; i < meansOfDeath.length; i++) {
            if (meansOfDeath[i].equals(meanOfDeath)) {
                index = i;
                break;
            }
        }

        return index;
    }

    private static Integer[] meansOfDeathAmount(String logFilePath, int startLine, int endLine, String[] meansOfDeath) {
        List<Integer> amount = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {

            String line;
            int lineNumber = 0;

            for (int i = 0; i < meansOfDeath.length; i++) {
                amount.add(0);
            }

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber >= startLine && lineNumber <= endLine && line.contains("Kill:")) {

                    // String regex = "(.+?) killed (.+?) by (.+?)";
                    String regex = "(?<=by\\s)(.+)$";

                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(line);

                    if (matcher.find()) {
                        String meanOfDeath = matcher.group(1).trim();

                        // add 1 to each kill with Mean of Death in Match
                        int index = findMeansOfDeathIndex(meansOfDeath, meanOfDeath);
                        int currentPoint = amount.get(index);
                        amount.set(index, currentPoint + 1);

                    } else {
                        System.out.println("Mean of Death not found.");
                    }
                }
                // Stop reading after reaching endLine
                if (lineNumber > endLine) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // return an array with values each corresponding to the same position in the
        // meansOfDeath array
        return amount.toArray(new Integer[0]);
    }

    private static List<Pair> meansKills(String[] meansOfDeath, Integer[] kills) {
        List<Pair> pairs = new ArrayList<>();
        for (int i = 0; i < meansOfDeath.length; i++) {
            pairs.add(new Pair(meansOfDeath[i], kills[i]));
        }

        return pairs;
    }

}

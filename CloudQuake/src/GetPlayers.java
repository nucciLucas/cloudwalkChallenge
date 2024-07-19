import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetPlayers {

    public static String[] getPlayersMatch(String logFilePath, int startLine, int endLine) {
        List<String> players = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber >= startLine && lineNumber <= endLine) {
                    if (line.contains("ClientUserinfoChanged")) {

                        // Regex to find players in match
                        Pattern patternName = Pattern.compile("n\\\\(.*?)\\\\t");
                        Matcher matcherName = patternName.matcher(line);
                        if (matcherName.find()) {
                            String playerName = matcherName.group(1);
                            players.add(playerName);

                        } else {
                            System.out.println("Error: Player name not found.");
                        }

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
        return removeDuplicates(players.toArray(new String[0]));
    }

    private static String[] removeDuplicates(String[] array) {
        // Convert the array to a Set to remove duplicates
        Set<String> set = new HashSet<>(Arrays.asList(array));

        // Convert the Set back to an array
        return set.toArray(new String[0]);
    }

}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.util.GameData;
import com.util.Pair;

public class GameStatistics {

    public static void addPlayersKillsGameData(String logFilePath, int startLine, int endLine, String[] players,
            GameData gameData) {

        Map<String, Integer> kills = new HashMap<>();
        Integer[] playerKills = playersKillsAmount(logFilePath, startLine, endLine, players);
        List<Pair> pairs = playerKills(players, playerKills);

        for (Pair pair : pairs) {
            kills.put(pair.getKey(), pair.getValue());
        }

        gameData.setKills(kills);
    }

    public static int totalDeaths(String logFilePath, int startLine, int endLine) {
        try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            int lineNumber = 0;
            int totalKills = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber >= startLine && lineNumber <= endLine) {

                    if (line.contains("Kill:")) {
                        totalKills++;
                    }

                }

                // Stop reading after reaching endLine
                if (lineNumber > endLine) {
                    break;
                }
            }

            return totalKills;

        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

    }

    public static void printPlayerRanking(String logFilePath, int startLine, int endLine, String[] players) {
        Integer[] playerPoints = playerPoints(logFilePath, startLine, endLine, players);
        List<Pair> pairs = playerRanking(players, playerPoints);

        System.out.println("\n!! Match Ranking !!");
        for (Pair pair : pairs) {
            System.out.println(pair.getKey() + ": " + pair.getValue());
        }
    }

    private static Integer[] playerPoints(String logFilePath, int startLine, int endLine, String[] players) {

        try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
            List<Integer> points = new ArrayList<>();
            String line;
            int lineNumber = 0;

            for (int i = 0; i < players.length; i++) {
                points.add(0);
            }

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber >= startLine && lineNumber <= endLine && line.contains("Kill:")) {

                    String regex = "(.+?) killed (.+?) by";

                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(line.split(": ")[2]);

                    if (matcher.find()) {

                        String victim = matcher.group(2).trim();
                        String killer = matcher.group(1).trim();

                        // Points to each kill gets +1 point
                        // Diyng to yourself gets -1 point
                        // Diyng to <world> gets -1 point
                        if (!killer.contains("<world>") && !killer.equals(victim)) {
                            int index = findPlayerIndex(players, killer);
                            int currentPoint = points.get(index);
                            points.set(index, currentPoint + 1);
                        } else {
                            int index = findPlayerIndex(players, victim);
                            int currentPoint = points.get(index);
                            points.set(index, currentPoint - 1);
                        }

                    } else {
                        System.out.println("Information not found.");
                    }

                }

                // Stop reading after reaching endLine
                if (lineNumber > endLine) {
                    break;
                }

            }

            return points.toArray(new Integer[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Integer[] playersKillsAmount(String logFilePath, int startLine, int endLine, String[] players) {

        try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
            List<Integer> kills = new ArrayList<>();
            String line;
            int lineNumber = 0;

            for (int i = 0; i < players.length; i++) {
                kills.add(0);
            }

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber >= startLine && lineNumber <= endLine && line.contains("Kill:")) {

                    String regex = "(.+?) killed (.+?) by";

                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(line.split(": ")[2]);

                    if (matcher.find()) {

                        String killer = matcher.group(1).trim();

                        // Sum +1 to each player kill,
                        // if a player kills it self, it counts towards its kill value
                        if (!killer.contains("<world>")) {
                            int index = findPlayerIndex(players, killer);
                            int currentKills = kills.get(index);
                            kills.set(index, currentKills + 1);
                        }

                    } else {
                        System.out.println("Information not found.");
                    }

                }

                // Stop reading after reaching endLine
                if (lineNumber > endLine) {
                    break;
                }

            }

            return kills.toArray(new Integer[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static List<Pair> playerKills(String[] players, Integer[] kills) {
        List<Pair> pairs = new ArrayList<>();
        for (int i = 0; i < players.length; i++) {
            pairs.add(new Pair(players[i], kills[i]));
        }

        return pairs;
    }

    private static int findPlayerIndex(String[] players, String player) {
        int index = -1;
        for (int i = 0; i < players.length; i++) {
            if (players[i].equals(player)) {
                index = i;
                break;
            }
        }

        return index;
    }

    private static List<Pair> playerRanking(String[] players, Integer[] points) {
        List<Pair> pairs = new ArrayList<>();
        for (int i = 0; i < players.length; i++) {
            pairs.add(new Pair(players[i], points[i]));
        }

        pairs.sort(Comparator.comparingInt(Pair::getValue).reversed());

        return pairs;
    }

}

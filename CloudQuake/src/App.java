import java.util.Arrays;

import com.util.GameData;
import com.util.MeansOfDeathClass;

public class App {

    public static void main(String[] args) {

        String logFilePath = "src/qgames.log";
        int gameStartLine, gameEndLine;
        // get matches in log
        int[] lineNumbers = QuakeLog.extractInitGameLineNumbers(logFilePath);
        String[] players;
        int totalKills;

        System.out.println("LOG PARSER QUAKE 3 ARENA\n");

        for (int i = 0; i < lineNumbers.length - 1; i++) {
            // Create new object GameData with match info
            GameData gameData = new GameData(i);
            // Create new object MeansOfDeathClass with MODs death counts
            MeansOfDeathClass meanDeath = new MeansOfDeathClass(i);
            gameStartLine = lineNumbers[i];
            gameEndLine = lineNumbers[i + 1];

            // Get total kills in match
            totalKills = GameStatistics.totalDeaths(logFilePath, gameStartLine, gameEndLine);
            // add total kills to GameData
            gameData.setTotalKills(totalKills);
            // Array of players in given match
            players = GetPlayers.getPlayersMatch(logFilePath, gameStartLine, gameEndLine);
            // add players to GameData
            gameData.setPlayers(Arrays.asList(players));
            // add kills for each player to GameData
            GameStatistics.addPlayersKillsGameData(logFilePath, gameStartLine, gameEndLine, players, gameData);
            // Print GameData
            System.out.println(gameData.toString());
            // Print match ranking
            GameStatistics.printPlayerRanking(logFilePath, gameStartLine, gameEndLine, players);
            // Print Means of Death Summary of given match
            MeansOfDeath.printMeansOfDeath(logFilePath, gameStartLine, gameEndLine, meanDeath);
            // Print end of match
            // System.out.println("\nxxxx MATCH END xxxx\n");
        }
    }

}
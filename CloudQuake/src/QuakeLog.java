import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuakeLog {

    public static int[] extractInitGameLineNumbers(String logFilePath) {
        List<Integer> gameStart = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            int lineNumber = 0;
            int lastLine = -1;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.contains("InitGame:")) {
                    gameStart.add(lineNumber);
                }
                lastLine = lineNumber;
            }
            gameStart.add(lastLine);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert List to array
        return gameStart.stream().mapToInt(Integer::intValue).toArray();
    }
}

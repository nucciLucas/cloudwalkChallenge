package com.util;

import java.util.Map;

public class MeansOfDeathClass {
    private String gameNumber;
    private Map<String, Integer> kills;

    // Constructor
    public MeansOfDeathClass(int gameNumber) {
        this.gameNumber = "game-" + gameNumber;
    }

    // Getters and Setters
    public String getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber = "game-" + gameNumber;
    }

    public Map<String, Integer> getKills() {
        return kills;
    }

    public void setKills(Map<String, Integer> kills) {
        this.kills = kills;
    }

    @Override
    public String toString() {
        return gameNumber + ": {" +
                "\nkills_by_means: " + kills +
                "\n}";
    }
}

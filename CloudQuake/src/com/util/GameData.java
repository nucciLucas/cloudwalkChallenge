package com.util;

import java.util.List;
import java.util.Map;

public class GameData {
    private String gameNumber;
    private int totalKills;
    private List<String> players;
    private Map<String, Integer> kills;

    // Constructor
    public GameData(int totalKills, List<String> players, Map<String, Integer> kills) {
        this.totalKills = totalKills;
        this.players = players;
        this.kills = kills;
    }

    public GameData(int gameNumber) {
        this.gameNumber = "game_" + gameNumber;
    }

    // Getters and Setters
    public String getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber = "game_" + gameNumber;
    }

    public int getTotalKills() {
        return totalKills;
    }

    public void setTotalKills(int totalKills) {
        this.totalKills = totalKills;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
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
                "\ntotalKills: " + totalKills +
                ",\nplayers: " + players +
                ",\nkills: " + kills +
                "\n}";
    }
}
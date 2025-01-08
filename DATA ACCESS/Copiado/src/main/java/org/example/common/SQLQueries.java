package org.example.common;

public class SQLQueries {
    public static final String INSERT_INTO_COMPLETED_MISSIONS_MISSION_ID_CHARACTER_ID_COMPLETION_DATE_VALUES = "INSERT INTO Completed_Missions(Mission_ID, Character_ID, Completion_Date) VALUES (?, ?, ?)";
    public static final String DELETE_FROM_COMPLETED_MISSIONS = "DELETE FROM Completed_Missions WHERE Character_ID = ?";
    public static final String SELECT_FROM_COMPLETED_MISSIONS_CHARACTERID = "SELECT * FROM Completed_Missions WHERE Character_ID = ?";
    public static final String INSERT_INTO_PLAYERS = "INSERT INTO Players(Player_ID, Name, Email, Tokens) VALUES (?, ?, ?, ?)";
    public static final String INSERT_INTO_CHARACTERS = "INSERT INTO Characters(Character_ID, Name, Type, Level, Player_ID) VALUES (?, ?, ?, ?, ?)";
    public static final String DELETE_FROM_CHARACTERS = "DELETE FROM Characters WHERE Character_ID = ?";
    public static final String SELECT_FROM_CHARACTERS = "SELECT * FROM Characters WHERE Character_ID = ?";
    public static final String SELECT_FROM_MISSIONS = "SELECT * FROM Missions WHERE Mission_ID = ?";
    public static final String SELECT_FROM_PLAYERS = "SELECT * FROM Players WHERE Player_ID = ?";
    public static final String SELECT_MAX_ID_PLAYER = "SELECT * FROM Players order by Player_ID desc limit 1";
    public static final String UPDATE_TOKENS_PLAYER = "UPDATE Players SET Tokens = ? WHERE Player_ID = ?";
    public static final String SELECT_MAX_ID_CHARACTER = "SELECT * FROM Characters order by Character_ID desc limit 1";
}

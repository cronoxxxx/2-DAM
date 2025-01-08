package org.example.common;

public class SQLQueries {
    public static final String INSERT_FACTION =  "INSERT INTO faction (fname, contact, planet, number_controlled_systems, date_last_purchase) VALUES (?, ?, ?, ?, ?)";
    public static final String INSERT_WEAPON = "INSERT INTO weapons (wname, wprice) VALUES (?, ?)";
    public static final String INSERT_FACTION_WEAPON = "INSERT INTO weapons_factions (name_faction, id_weapon) VALUES (?, ?)";
    public static final String UPDATE_WEAPON = "UPDATE weapons SET wname = ?, wprice = ? WHERE id = ?";
    public static final String INSERT_BATTLE = "INSERT INTO battles (bname, faction_one, faction_two, bplace, bdate, id_spy) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String INSERT_SPY = "INSERT INTO spies (sname, srace) VALUES (?, ?)";
    public static final String SELECT_ALL_SPY = "SELECT * FROM spies";
    public static final String SELECT_ALL_BATTLE = "SELECT * FROM battles";
    public static final String GET_ALL_WEAPONS = "SELECT * FROM weapons";
    public static final String GET_ALL_FACTIONS = "SELECT * FROM faction";
    public static final String FACTION_WEAPONS_NESTED = "SELECT f.fname, f.contact, f.planet, f.number_controlled_systems, f.date_last_purchase, w.id as weapon_id, " +
            "w.wname as weapon_name, w.wprice as weapon_price " +
            "FROM faction f, " +
            "weapons w " +
            "WHERE w.id IN " +
            "(SELECT id_weapon FROM weapons_factions " +
            "WHERE name_faction = f.fname) ORDER BY f.fname, w.id";

    public static final String FACTON_WEAPONS_INNER_JOIN = "SELECT f.fname, f.contact, f.planet, f.number_controlled_systems, f.date_last_purchase, " +
            "w.id as weapon_id, w.wname as weapon_name, w.wprice as weapon_price " +
            "FROM faction f " +
            "INNER JOIN weapons_factions wf ON f.fname = wf.name_faction " +
            "INNER JOIN weapons w ON wf.id_weapon = w.id " +
            "ORDER BY f.fname, w.id";
    public static final String GET_WEAPONS_FACTIONS = "SELECT w.wname, w.wprice " +
            "FROM weapons w " +
            "INNER JOIN weapons_factions wf ON w.id = wf.id_weapon " +
            "WHERE wf.name_faction = ?";
    public static final String DELETE_ID_SPY_BATTLE = "DELETE FROM battles WHERE id_spy = ?";
    public static final String DELETE_SPY_BY_ID=  "DELETE FROM spies WHERE id = ?";
}

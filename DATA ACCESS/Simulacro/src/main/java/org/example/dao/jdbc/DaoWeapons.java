package org.example.dao.jdbc;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.common.SQLQueries;
import org.example.dao.jdbc.utils.DBConnectionPool;
import org.example.dao.mappers.jdbc.WeaponMapper;
import org.example.domain.errors.FactionsError;
import org.example.domain.model.Faction;
import org.example.domain.model.FactionWeapons;
import org.example.domain.model.Weapon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoWeapons {
    private final DBConnectionPool dbConnectionPool;
    private final WeaponMapper weaponMapper;

    @Inject
    public DaoWeapons(DBConnectionPool dbConnectionPool, WeaponMapper weaponMapper) {
        this.dbConnectionPool = dbConnectionPool;
        this.weaponMapper = weaponMapper;
    }


    public List<Weapon> getAllWeapons() {
        List<Weapon> weapons = new ArrayList<>();
        try (Connection connection = dbConnectionPool.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SQLQueries.GET_ALL_WEAPONS);
            while (rs.next()) {
                Weapon weapon = weaponMapper.mapRow(rs);
                weapons.add(weapon);
            }
        } catch (SQLException e){
            log.error(e);
        }
        return weapons;
    }
    public int saveWeapon(Weapon weapon) {
        try (Connection connection = dbConnectionPool.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(SQLQueries.INSERT_WEAPON, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, weapon.getName());
            ps.setInt(2, weapon.getPrice());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                connection.rollback();
            }
            connection.commit();
            return 1;
        } catch (SQLException e) {
            log.error(e);
        }
        return 0;
    }


    public Either<FactionsError, Integer> updateWeapon(Weapon weapon) {
        try (Connection con = dbConnectionPool.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(SQLQueries.UPDATE_WEAPON)) {
                ps.setString(1, weapon.getName());
                ps.setInt(2, weapon.getPrice());
                ps.setInt(3, weapon.getId());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 0) {
                    return Either.left(new FactionsError("Cannot update weapon"));
                }
                return Either.right(1);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            return Either.left(new FactionsError("Cannot update weapon"));
        }
    }


    //ejercicio 5

    public List<FactionWeapons> getFactionWeaponsWithInnerJoin() {
        List<FactionWeapons> factionWeaponsList = new ArrayList<>();
        String query = SQLQueries.FACTON_WEAPONS_INNER_JOIN;

        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            String currentFaction = null;
            FactionWeapons currentFactionWeapons = null;

            while (rs.next()) {
                String factionName = rs.getString("fname");

                if (!factionName.equals(currentFaction)) {
                    // New faction, create a new FactionWeapons object
                    currentFaction = factionName;
                    Faction faction = new Faction(
                            factionName,
                            rs.getString("contact"),
                            rs.getString("planet"),
                            rs.getInt("number_controlled_systems"),
                            rs.getDate("date_last_purchase") != null ? rs.getDate("date_last_purchase").toLocalDate() : null
                    );
                    currentFactionWeapons = new FactionWeapons(faction, new ArrayList<>());
                    factionWeaponsList.add(currentFactionWeapons);
                }

                Weapon weapon = new Weapon(
                        rs.getInt("weapon_id"),
                        rs.getString("weapon_name"),
                        rs.getInt("weapon_price")
                );
                currentFactionWeapons.getWeapons().add(weapon);
            }
        } catch (SQLException e) {
            log.error("Error fetching faction weapons with INNER JOIN", e);
        }

        return factionWeaponsList;
    }

    public List<FactionWeapons> getFactionWeaponsWithNestedWhere() {
        List<FactionWeapons> factionWeaponsList = new ArrayList<>();
        String query = SQLQueries.FACTION_WEAPONS_NESTED;

        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            String currentFaction = null;
            FactionWeapons currentFactionWeapons = null;

            while (rs.next()) {
                String factionName = rs.getString("fname");

                if (!factionName.equals(currentFaction)) {
                    // New faction, create a new FactionWeapons object
                    currentFaction = factionName;
                    Faction faction = new Faction(
                            factionName,
                            rs.getString("contact"),
                            rs.getString("planet"),
                            rs.getInt("number_controlled_systems"),
                            rs.getDate("date_last_purchase") != null ? rs.getDate("date_last_purchase").toLocalDate() : null
                    );
                    currentFactionWeapons = new FactionWeapons(faction, new ArrayList<>());
                    factionWeaponsList.add(currentFactionWeapons);
                }

                Weapon weapon = new Weapon(
                        rs.getInt("weapon_id"),
                        rs.getString("weapon_name"),
                        rs.getInt("weapon_price")
                );
                currentFactionWeapons.getWeapons().add(weapon);
            }
        } catch (SQLException e) {
            log.error("Error fetching faction weapons with nested WHERE", e);
        }

        return factionWeaponsList;
    }


    public List<Weapon> getWeaponsForFaction(String factionName) {
        List<Weapon> weaponInfoList = new ArrayList<>();
        String query = SQLQueries.GET_WEAPONS_FACTIONS;

        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, factionName);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    String weaponName = rs.getString("wname");
                    int weaponPrice = rs.getInt("wprice");
                    weaponInfoList.add(new Weapon(0,weaponName, weaponPrice));
                }
            }
        } catch (SQLException e) {
            log.error("Error fetching weapons for faction: " + factionName, e);
        }

        return weaponInfoList;
    }
}

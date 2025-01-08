package org.example.common;

public class SQLQueries {
    public static final String INSERT_VISIT = "INSERT INTO Animal_Visits VALUES (?,?,?)";
    public static final String GET_ALL_ANIMALS = "SELECT * FROM Animals";
    public static final String DELETE_ANIMAL_ID = "DELETE FROM Animals WHERE Animal_ID = ?";
    public static final String DELETE_ANIMAL_WITH_VISITS = "DELETE FROM Animal_Visits WHERE Animal_ID = ?";
    public static final String COUNT_VISITS_BY_ANIMAL_ID = "SELECT COUNT(*) AS visitCount FROM Animal_Visits WHERE Animal_ID = ?";
    public static final String INSERT_VISITORS = "INSERT INTO Visitors (Name, Email, Tickets) VALUES (?,?,?)" ;
    public static final String GET_ALL_VISITORS = "SELECT * FROM Visitors";
}

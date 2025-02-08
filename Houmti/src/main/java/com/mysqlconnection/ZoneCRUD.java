package com.mysqlconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ZoneCRUD {

    //Create
    public static void createZone(String nom, int population, Time tempsCollecte) throws SQLException {
        String sql = "INSERT INTO zone_de_collecte (nom, population, temps_de_collecte) VALUES (?, ?, ?)";

        try (Connection conn = com.mysqlconnection.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nom);
            pstmt.setInt(2, population);
            pstmt.setTime(3, tempsCollecte);
            pstmt.executeUpdate();
        }
    }

    // Read
    public static List<Zone> getAllZones() throws SQLException {
        List<Zone> zones = new ArrayList<>();
        String sql = "SELECT * FROM zone_de_collecte";

        try (Connection conn = com.mysqlconnection.DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                zones.add(new Zone(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getInt("population"),
                        rs.getTime("temps_de_collecte")
                ));
            }
        }
        return zones;
    }


    //Update
    public static void updateZone(int id, String newNom, Integer newPopulation, Time newTempsCollecte) throws SQLException {
        StringBuilder sql = new StringBuilder("UPDATE zone_de_collecte SET ");
        List<Object> params = new ArrayList<>();

        if (newNom != null) {
            sql.append("nom = ?, ");
            params.add(newNom);
        }
        if (newPopulation != null) {
            sql.append("population = ?, ");
            params.add(newPopulation);
        }
        if (newTempsCollecte != null) {
            sql.append("temps_de_collecte = ?, ");
            params.add(newTempsCollecte);
        }

        // Remove the last comma and space
        sql.setLength(sql.length() - 2);
        sql.append(" WHERE id = ?");
        params.add(id);

        try (Connection conn = com.mysqlconnection.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            pstmt.executeUpdate();
        }
    }


    // Delete
    public static void deleteZone(int id) throws SQLException {
        String sql = "DELETE FROM zone_de_collecte WHERE id = ?";

        try (Connection conn = com.mysqlconnection.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }



    public static class Zone {
        private int id;
        private String nom;
        private int population;
        private Time tempsCollecte;

        public Zone(int id, String nom, int population, Time tempsCollecte) {
            this.id = id;
            this.nom = nom;
            this.population = population;
            this.tempsCollecte = tempsCollecte;
        }

        @Override
        public String toString() {
            return String.format("Zone [ID: %d, Nom: %s, Population: %d, Heure: %s]",
                    id, nom, population, tempsCollecte);
        }
    }
}
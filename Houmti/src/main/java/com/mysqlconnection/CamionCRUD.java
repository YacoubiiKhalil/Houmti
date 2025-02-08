package com.mysqlconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CamionCRUD {

    // Create
    public static void createCamion(String type, String statut, int capacity, int idZone) throws SQLException {
        String sql = "INSERT INTO camion (type, statut, capacity, id_zone) VALUES (?, ?, ?, ?)";

        try (Connection conn = com.mysqlconnection.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, type);
            pstmt.setString(2, statut);
            pstmt.setInt(3, capacity);
            pstmt.setInt(4, idZone);
            pstmt.executeUpdate();
        }
    }

    // Read All
    public static List<Camion> getAllCamions() throws SQLException {
        List<Camion> camions = new ArrayList<>();
        String sql = "SELECT * FROM camion";

        try (Connection conn = com.mysqlconnection.DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                camions.add(new Camion(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getString("statut"),
                        rs.getInt("capacity"),
                        rs.getInt("id_zone")
                ));
            }
        }
        return camions;
    }

    // Update
    public static void updateCamion(int id, String newtype, String newStatut, Integer newCapacity) throws SQLException {
        StringBuilder sql = new StringBuilder("UPDATE camion SET ");
        boolean first = true;

        if (newtype != null) {
            sql.append("type = ?");
            first = false;
        }
        if (newStatut != null) {
            if (!first) sql.append(", ");
            sql.append("statut = ?");
            first = false;
        }
        if (newCapacity != null) {
            if (!first) sql.append(", ");
            sql.append("capacity = ?");
        }
        sql.append(" WHERE id = ?");

        try (Connection conn = com.mysqlconnection.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (newtype != null) {
                pstmt.setString(index++, newtype);
            }
            if (newStatut != null) {
                pstmt.setString(index++, newStatut);
            }
            if (newCapacity != null) {
                pstmt.setInt(index++, newCapacity);
            }
            pstmt.setInt(index, id);
            pstmt.executeUpdate();
        }
    }

    // Delete
    public static void deleteCamion(int id) throws SQLException {
        String sql = "DELETE FROM camion WHERE id = ?";

        try (Connection conn = com.mysqlconnection.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    // Classe modèle
    public static class Camion {
        private int id;
        private String type;
        private String statut;
        private int capacity;
        private int idZone;

        public Camion(int id, String type, String statut, int capacity, int idZone) {
            this.id = id;
            this.type = type;
            this.statut = statut;
            this.capacity = capacity;
            this.idZone = idZone;
        }

        @Override
        public String toString() {
            return String.format("Camion [ID: %d, Type: %s, Statut: %s, Capacité: %d, Zone: %d]",
                    id, type, statut, capacity, idZone);
        }
    }
}


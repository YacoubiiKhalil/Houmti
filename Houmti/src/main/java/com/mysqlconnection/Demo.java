package com.mysqlconnection;

import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        try {
            // Créer une zone
            com.mysqlconnection.ZoneCRUD.createZone("Zone Nord", 15000, Time.valueOf("08:30:00"));
            System.out.println("Zone créée !");

            // Créer un camion lié à la zone 1
            com.mysqlconnection.CamionCRUD.createCamion("Benne", "En service", 200, 1);
            System.out.println("Camion créé !");

            // Afficher tous les camions
            List<com.mysqlconnection.CamionCRUD.Camion> camions = com.mysqlconnection.CamionCRUD.getAllCamions();
            System.out.println("\nListe des camions :");
            camions.forEach(System.out::println);

            // Mettre à jour camion 1
            com.mysqlconnection.CamionCRUD.updateCamion(1,  "chargement latéral", "En service", 200);
            System.out.println("\nCamion mis à jour !");

            //Mettre à jour type camion 1
            com.mysqlconnection.CamionCRUD.updateCamion(1, "Aspirateurs", "En maintenance", 180);
             System.out.println("\nCamion mis à jour");

            //Mettre à jour type camion 1
            com.mysqlconnection.CamionCRUD.updateCamion(2, "Bennie", null, null);
            System.out.println("\nCamion mis à jour");


            // Créer une nouvelle zone
            ZoneCRUD.createZone("Zone Sud", 5000, Time.valueOf("08:00:00"));
            System.out.println("Zone créée");

            // Mettre à jour une zone
            ZoneCRUD.updateZone(1, "Zone Nord", 8000, Time.valueOf("09:00:00"));
            System.out.println("Zone mise à jour");


        } catch (SQLException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }
}
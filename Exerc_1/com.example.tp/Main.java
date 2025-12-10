package com.example.tp;

public class Main {
    public static void main(String[] args) {
        Entreprise ent = new Entreprise();

        ent.ajouterEmploye(new EmployeHoraire("El Idrissi", "Mohamed", 15.0, 160));
        ent.ajouterEmploye(new EmployeSalarie("Bentaleb", "Fatima", 2500.0));
        ent.ajouterEmploye(new Vendeur("Chouaib", "Youssef", 2000.0, 500.0));
        ent.ajouterEmploye(new EmployeHoraire("Lahlou", "Salma", 12.0, 120));

        ent.afficherPaie();
    }
}

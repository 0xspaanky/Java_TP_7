package com.example.tp;

public abstract class Employe {
    protected String nom, prenom;

    public Employe(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public abstract double calculerSalaire();

    @Override
    public String toString() {
        return nom + " " + prenom + " → Salaire = " +
               String.format("%.2f", calculerSalaire()) + "€";
    }
}

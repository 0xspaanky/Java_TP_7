package com.example.tp;

public class Entreprise {
    private Employe[] employes;
    private int nb;

    public Entreprise() {
        this.employes = new Employe[4];
        this.nb = 0;
    }

    public void ajouterEmploye(Employe e) {
        if (nb == employes.length) {
            Employe[] temp = new Employe[employes.length * 2];
            System.arraycopy(employes, 0, temp, 0, employes.length);
            employes = temp;
        }
        employes[nb++] = e;
    }

    public void afficherPaie() {
        System.out.println("=== Bulletin de paie ===");
        for (int i = 0; i < nb; i++) {
            System.out.println(employes[i]);
        }
        System.out.println("Masse salariale totale : " +
            String.format("%.2f", masseSalariale()) + "€");
    }

    public double masseSalariale() {
        double total = 0;
        for (int i = 0; i < nb; i++) {
            total += employes[i].calculerSalaire();
        }
        return total;
    }
}

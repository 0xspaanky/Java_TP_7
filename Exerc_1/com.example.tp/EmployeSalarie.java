package com.example.tp;

public class EmployeSalarie extends Employe {
    protected double salaireMensuel;

    public EmployeSalarie(String nom, String prenom, double salaireMensuel) {
        super(nom, prenom);
        this.salaireMensuel = salaireMensuel;
    }

    @Override
    public double calculerSalaire() {
        return salaireMensuel;
    }
}

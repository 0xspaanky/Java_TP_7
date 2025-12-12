# Exercise 1: Employee Payroll Management

## Objective
Master abstract classes and polymorphism by modeling a salary calculation system for different employee types.

## Key Concepts
- Abstract classes with abstract methods
- Polymorphism for salary calculation
- Dynamic array management
- Method overriding
- Multi-level inheritance

## Class Hierarchy

```
          Employe (abstract)
          ├─ nom : String
          ├─ prenom : String
          └─ calculerSalaire() : double (abstract)
                    |
      +-------------+-------------+
      |                           |
EmployeHoraire              EmployeSalarie
├─ tauxHoraire                ├─ salaireMensuel
├─ heuresTravaillees          |
└─ calculerSalaire()          └─ calculerSalaire()
                                      |
                                  Vendeur
                                  ├─ commission
                                  └─ calculerSalaire()
```

## Implementation

### Employe (Abstract Base)
```java
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
        return nom + " " + prenom
             + " → Salaire = "
             + String.format("%.2f", calculerSalaire()) + "€";
    }
}
```

### EmployeHoraire (Hourly Worker)
```java
public class EmployeHoraire extends Employe {
    private double tauxHoraire;
    private double heuresTravaillees;

    public EmployeHoraire(String nom, String prenom,
                          double tauxHoraire,
                          double heuresTravaillees) {
        super(nom, prenom);
        this.tauxHoraire = tauxHoraire;
        this.heuresTravaillees = heuresTravaillees;
    }

    @Override
    public double calculerSalaire() {
        return tauxHoraire * heuresTravaillees;
    }
}
```

### EmployeSalarie (Salaried Employee)
```java
public class EmployeSalarie extends Employe {
    protected double salaireMensuel;

    public EmployeSalarie(String nom, String prenom,
                          double salaireMensuel) {
        super(nom, prenom);
        this.salaireMensuel = salaireMensuel;
    }

    @Override
    public double calculerSalaire() {
        return salaireMensuel;
    }
}
```

### Vendeur (Salesperson with Commission)
```java
public class Vendeur extends EmployeSalarie {
    private double commission;

    public Vendeur(String nom, String prenom,
                   double salaireMensuel,
                   double commission) {
        super(nom, prenom, salaireMensuel);
        this.commission = commission;
    }

    @Override
    public double calculerSalaire() {
        return salaireMensuel + commission;
    }
}
```

### Entreprise (Company Management)
```java
public class Entreprise {
    private Employe[] employes;
    private int nb;

    public Entreprise() {
        this.employes = new Employe[4];
        this.nb = 0;
    }

    public void ajouterEmploye(Employe e) {
        if (nb == employes.length) {
            Employe[] tmp = new Employe[employes.length * 2];
            System.arraycopy(employes, 0, tmp, 0, employes.length);
            employes = tmp;
        }
        employes[nb++] = e;
    }

    public void afficherPaie() {
        System.out.println("=== Bulletin de paie ===");
        for (int i = 0; i < nb; i++) {
            System.out.println(employes[i]);
        }
        System.out.println("Masse salariale totale : "
            + String.format("%.2f", masseSalariale()) + "€");
    }

    public double masseSalariale() {
        double somme = 0;
        for (int i = 0; i < nb; i++) {
            somme += employes[i].calculerSalaire();
        }
        return somme;
    }
}
```

## Usage Example
```java
package com.example.tp;

public class Main {
    public static void main(String[] args) {
        Entreprise ent = new Entreprise();

        ent.ajouterEmploye(
          new EmployeHoraire("El Idrissi","Mohamed", 15.0, 160)
        );
        ent.ajouterEmploye(
          new EmployeSalarie("Bentaleb","Fatima", 2500.0)
        );
        ent.ajouterEmploye(
          new Vendeur("Chouaib","Youssef", 2000.0, 500.0)
        );
        ent.ajouterEmploye(
          new EmployeHoraire("Lahlou","Salma", 12.0, 120)
        );

        ent.afficherPaie();
    }
}
```

## Expected Output
```
=== Bulletin de paie ===
El Idrissi Mohamed → Salaire = 2400.00€
Bentaleb Fatima → Salaire = 2500.00€
Chouaib Youssef → Salaire = 2500.00€
Lahlou Salma → Salaire = 1440.00€
Masse salariale totale : 8840.00€
```

## Compilation & Execution
```bash
cd src
javac com/example/tp/*.java
java com.example.tp.Main
```

## Salary Calculation Logic

| Employee Type | Formula |
|--------------|---------|
| EmployeHoraire | tauxHoraire × heuresTravaillees |
| EmployeSalarie | salaireMensuel |
| Vendeur | salaireMensuel + commission |

## Key Features
- **Abstract base class**: Forces all employees to implement `calculerSalaire()`
- **Polymorphism**: `Employe[]` array holds different employee types
- **Dynamic array**: Doubles capacity when full (starts at 4)
- **Multi-level inheritance**: Vendeur extends EmployeSalarie

## Extensions
- Add `Manager` class (salary + bonus)
- Add `Stagiaire` (intern with hourly rate cap)
- Implement overtime calculation for hourly workers
- Add employee ID and hire date
- Calculate taxes and net salary

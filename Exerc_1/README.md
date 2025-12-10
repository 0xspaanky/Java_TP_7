# Exercise 1: Employee Payroll Management System

![Exercise 1 Diagram](./image.png)

## Objective
Consolidate mastery of abstract classes and polymorphism by modeling a salary calculation system for different types of employees.

## Description
This exercise creates a complete payroll management system using abstract classes to define employee contracts and polymorphism to calculate salaries differently based on employee type. The system demonstrates inheritance hierarchies with multi-level depth and dynamic array management.

## UML Class Diagram

```
          +---------------------+
          |      Employe        | «abstract»
          +---------------------+
          | - nom : String      |
          | - prenom : String   |
          +---------------------+
          | + calculerSalaire():double «abstract» |
          | + toString():String |
          +---------▲-----------+
                    |
     +--------------+--------------+
     |              |              |
+-----------+ +--------------+ +---------------------+
|Employe    | |EmployeSalarie| |Vendeur              |
|Horaire    | |              | |(extends             |
|           | |              | |EmployeSal.)         |
+-----------+ +--------------+ +---------------------+
| - taux    | | - salaire    | | - commission        |
| - heures  | |              | |                     |
+-----------+ +--------------+ +---------------------+
| +calculerSalaire()         | | +calculerSalaire()  |
+-----------+ +--------------+ +---------------------+

        +----------------------------------+
        |   Entreprise                     |
        +----------------------------------+
        | - employes[]                     |
        | - nb : int                       |
        +----------------------------------+
        | + ajouterEmploye(e:Employe):void |
        | + afficherPaie():void            |
        | + masseSalariale():double        |
        +----------------------------------+
```

## Class Structure

### Employe (Abstract Base Class)
Located in `com.example.tp` package

**Attributes:**
- `nom` (protected String): Employee's last name
- `prenom` (protected String): Employee's first name

**Constructor:**
```java
public Employe(String nom, String prenom)
```

**Abstract Method:**
```java
public abstract double calculerSalaire();
```
**Must be implemented by all employee types!**

**Concrete Method:**
```java
@Override
public String toString() {
    return nom + " " + prenom
         + " → Salaire = "
         + String.format("%.2f", calculerSalaire()) + "€";
}
```

### EmployeHoraire (Hourly Employee)

**Salary Calculation:** Hourly rate × Hours worked

**Attributes:**
- `tauxHoraire` (private double): Hourly rate (€/hour)
- `heuresTravaillees` (private double): Hours worked

**Constructor:**
```java
public EmployeHoraire(String nom, String prenom,
                      double tauxHoraire,
                      double heuresTravaillees)
```

**Implementation:**
```java
@Override
public double calculerSalaire() {
    return tauxHoraire * heuresTravaillees;
}
```

**Example:**
- Rate: 15.0 €/hour
- Hours: 160
- Salary: 2400.0€

### EmployeSalarie (Salaried Employee)

**Salary Calculation:** Fixed monthly salary

**Attributes:**
- `salaireMensuel` (protected double): Monthly salary

**Constructor:**
```java
public EmployeSalarie(String nom, String prenom,
                      double salaireMensuel)
```

**Implementation:**
```java
@Override
public double calculerSalaire() {
    return salaireMensuel;
}
```

**Example:**
- Monthly: 2500.0€
- Salary: 2500.0€

### Vendeur (Salesperson - extends EmployeSalarie)

**Salary Calculation:** Base salary + Commission

**Attributes:**
- Inherits `salaireMensuel` from EmployeSalarie
- `commission` (private double): Sales commission

**Constructor:**
```java
public Vendeur(String nom, String prenom,
               double salaireMensuel,
               double commission)
```

**Implementation:**
```java
@Override
public double calculerSalaire() {
    return salaireMensuel + commission;
}
```

**Example:**
- Base: 2000.0€
- Commission: 500.0€
- Total Salary: 2500.0€

### Entreprise (Company)

**Attributes:**
- `employes` (private Employe[]): Dynamic array of employees
- `nb` (private int): Current employee count

**Constructor:**
```java
public Entreprise()
```
- Initializes array with capacity of 4

**Methods:**

#### ajouterEmploye(Employe e)
Adds employee, expanding array if full.
```java
public void ajouterEmploye(Employe e) {
    if (nb == employes.length) {
        Employe[] tmp = new Employe[employes.length * 2];
        System.arraycopy(employes, 0, tmp, 0, employes.length);
        employes = tmp;
    }
    employes[nb++] = e;
}
```

#### afficherPaie()
Displays payroll for all employees.
```java
public void afficherPaie() {
    System.out.println("=== Bulletin de paie ===");
    for (int i = 0; i < nb; i++) {
        System.out.println(employes[i]);
    }
    System.out.println("Masse salariale totale : "
        + String.format("%.2f", masseSalariale()) + "€");
}
```

#### masseSalariale()
Calculates total payroll cost.
```java
public double masseSalariale() {
    double somme = 0;
    for (int i = 0; i < nb; i++) {
        somme += employes[i].calculerSalaire();
    }
    return somme;
}
```

## Example Usage

```java
public class Main {
    public static void main(String[] args) {
        Entreprise ent = new Entreprise();

        // Add hourly employee
        ent.ajouterEmploye(
            new EmployeHoraire("El Idrissi", "Mohamed", 15.0, 160)
        );

        // Add salaried employee
        ent.ajouterEmploye(
            new EmployeSalarie("Bentaleb", "Fatima", 2500.0)
        );

        // Add salesperson
        ent.ajouterEmploye(
            new Vendeur("Chouaib", "Youssef", 2000.0, 500.0)
        );

        // Add another hourly employee
        ent.ajouterEmploye(
            new EmployeHoraire("Lahlou", "Salma", 12.0, 120)
        );

        // Display payroll
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

**Calculation Breakdown:**
- El Idrissi: 15.0 × 160 = 2400.00€
- Bentaleb: 2500.00€
- Chouaib: 2000 + 500 = 2500.00€
- Lahlou: 12.0 × 120 = 1440.00€
- **Total: 8840.00€**

## Compilation & Execution

```bash
# From src/ directory
cd src
javac com/example/tp/*.java
java com.example.tp.Main
```

## Key Concepts Demonstrated

### 1. Abstract Classes
```java
public abstract class Employe {
    public abstract double calculerSalaire();  // No implementation
}
```

**Purpose:**
- Cannot be instantiated directly
- Forces all subclasses to implement `calculerSalaire()`
- Provides common behavior (`toString()`)

### 2. Multi-Level Inheritance
```
Employe (abstract)
    ├─ EmployeHoraire
    ├─ EmployeSalarie
    │      └─ Vendeur
```

**Vendeur** extends **EmployeSalarie** which extends **Employe** (3 levels!)

### 3. Polymorphism
```java
Employe[] employes = new Employe[4];
employes[0] = new EmployeHoraire(...);
employes[1] = new EmployeSalarie(...);
employes[2] = new Vendeur(...);

// Uniform call, different implementations
for (Employe e : employes) {
    double salaire = e.calculerSalaire();  // Polymorphic!
}
```

### 4. Protected Access
- `salaireMensuel` is protected in EmployeSalarie
- Accessible to Vendeur subclass
- Allows Vendeur to use it in calculation

### 5. Dynamic Array Management
- Starts with capacity 4
- Doubles when full
- No size limit for employees

### 6. Template Method Pattern
```java
// Template in abstract class
public String toString() {
    return nom + " " + prenom + " → Salaire = "
        + calculerSalaire();  // Calls abstract method
}
```

## Hierarchy Comparison

| Employee Type | Inheritance Level | Salary Formula | Variable Pay |
|--------------|------------------|----------------|--------------|
| EmployeHoraire | 2 (direct) | rate × hours | Yes (hours) |
| EmployeSalarie | 2 (direct) | fixed | No |
| Vendeur | 3 (via EmployeSalarie) | fixed + commission | Yes (commission) |

## Verification Checklist
- [ ] Cannot instantiate Employe directly
- [ ] All employee types implement calculerSalaire()
- [ ] Dynamic array expands when needed
- [ ] Polymorphism works (correct salary calculated)
- [ ] Total payroll calculation is accurate
- [ ] Output format matches expected
- [ ] Vendeur properly extends EmployeSalarie

## Possible Extensions

### 1. Add Manager Class
```java
public class Manager extends EmployeSalarie {
    private double prime;

    public Manager(String nom, String prenom,
                   double salaire, double prime) {
        super(nom, prenom, salaire);
        this.prime = prime;
    }

    @Override
    public double calculerSalaire() {
        return salaireMensuel + prime;
    }
}
```

### 2. Overtime Pay
```java
public class EmployeHoraire extends Employe {
    private static final double OVERTIME_RATE = 1.5;

    @Override
    public double calculerSalaire() {
        double regularPay = Math.min(heuresTravaillees, 40) * tauxHoraire;
        double overtime = Math.max(heuresTravaillees - 40, 0)
                        * tauxHoraire * OVERTIME_RATE;
        return regularPay + overtime;
    }
}
```

### 3. Deductions and Net Salary
```java
public abstract class Employe {
    public abstract double calculerSalaire();  // Gross

    public double calculerSalaireNet() {
        double brut = calculerSalaire();
        double taxes = brut * 0.20;  // 20% tax
        return brut - taxes;
    }
}
```

### 4. Performance Bonuses
```java
public class EmployeSalarie extends Employe {
    private double performanceBonus;

    public void attribuerBonus(double percentage) {
        performanceBonus = salaireMensuel * (percentage / 100);
    }

    @Override
    public double calculerSalaire() {
        return salaireMensuel + performanceBonus;
    }
}
```

### 5. Salary History
```java
public abstract class Employe {
    private List<Double> historiqueS alaires = new ArrayList<>();

    protected void enregistrerSalaire() {
        historiqueSalaires.add(calculerSalaire());
    }

    public double getSalaireMoyen() {
        return historiqueSalaires.stream()
            .mapToDouble(Double::doubleValue)
            .average()
            .orElse(0.0);
    }
}
```

### 6. Employee Statistics
```java
public class Entreprise {
    public double getSalaireMaximum() {
        double max = 0;
        for (int i = 0; i < nb; i++) {
            max = Math.max(max, employes[i].calculerSalaire());
        }
        return max;
    }

    public double getSalaireMoyen() {
        return masseSalariale() / nb;
    }

    public Employe getEmployeLePlusPaye() {
        Employe max = employes[0];
        for (int i = 1; i < nb; i++) {
            if (employes[i].calculerSalaire()
                > max.calculerSalaire()) {
                max = employes[i];
            }
        }
        return max;
    }
}
```

## Common Mistakes to Avoid

### 1. Trying to Instantiate Abstract Class
```java
// Compile error!
Employe e = new Employe("Test", "User");

// Correct
Employe e = new EmployeSalarie("Test", "User", 2000);
```

### 2. Not Calling super()
```java
// Wrong - parent not initialized
public EmployeHoraire(String nom, String prenom,
                      double taux, double heures) {
    this.tauxHoraire = taux;  // nom/prenom not set!
}

// Correct
public EmployeHoraire(String nom, String prenom,
                      double taux, double heures) {
    super(nom, prenom);  // Initialize parent first
    this.tauxHoraire = taux;
    this.heuresTravaillees = heures;
}
```

### 3. Forgetting @Override
```java
// Typo creates new method instead of overriding
public double calculeSalaire() {  // Wrong method name!
    return salaireMensuel;
}

// Correct with @Override annotation
@Override
public double calculerSalaire() {
    return salaireMensuel;
}
```

### 4. Wrong Access Modifier
```java
// Private - Vendeur can't access
private double salaireMensuel;

// Protected - Vendeur can access
protected double salaireMensuel;
```

## Files
- `Employe.java`: Abstract base class
- `EmployeHoraire.java`: Hourly employee
- `EmployeSalarie.java`: Salaried employee
- `Vendeur.java`: Salesperson with commission
- `Entreprise.java`: Company with dynamic employee array
- `Main.java`: Test program with payroll display
- `subject.txt`: Complete exercise specifications

## Learning Outcomes
- Understanding abstract classes and forced implementations
- Multi-level inheritance design
- Polymorphic salary calculations
- Dynamic array management for business objects
- Template method pattern application
- Protected access for inheritance hierarchies

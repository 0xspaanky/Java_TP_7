# TP_7 - Abstract Classes and Polymorphism

This repository contains 2 exercises focusing on abstract classes, polymorphism, and recursive algorithms.

## Exercises Overview

### Exercise 1: Employee Payroll Management
Multi-type employee salary calculation with abstract base class.

**Key Concepts:** Abstract classes, polymorphism, dynamic arrays

**Hierarchy:**
```
Employe (abstract)
  ├─ EmployeHoraire (hourly worker)
  ├─ EmployeSalarie (salaried employee)
  └─ Vendeur extends EmployeSalarie (salesperson with commission)
```

**Salary Calculation:**
- EmployeHoraire: `tauxHoraire × heuresTravaillees`
- EmployeSalarie: `salaireMensuel`
- Vendeur: `salaireMensuel + commission`

---

### Exercise 2: File System Modeling
Recursive file system with files and directories.

**Key Concepts:** Abstract classes, recursive size calculation, tree structures

**Hierarchy:**
```
FsItem (abstract)
  ├─ FileItem (fixed size)
  └─ Directory (recursive size = sum of children)
```

**Features:**
- Nested directories
- Recursive size calculation
- Tree display with indentation
- Dynamic array expansion

---

## Comparison

| Feature | Exercise 1 | Exercise 2 |
|---------|-----------|-----------|
| Abstract Method | calculerSalaire() | getSize() |
| Key Algorithm | Salary formulas | Recursive summation |
| Structure | Flat list | Tree (nested) |
| Special Feature | Multi-level inheritance | Recursive traversal |

## Key Concepts

### Abstract Classes
```java
public abstract class Base {
    public abstract ReturnType method();  // Must be implemented
}
```

**Benefits:**
- Force implementation in subclasses
- Share common code (concrete methods)
- Enable polymorphism without interfaces

### Polymorphism
```java
Employe[] employees = new Employe[10];
employees[0] = new EmployeHoraire(...);
employees[1] = new Vendeur(...);

for (Employe e : employees) {
    e.calculerSalaire();  // Calls correct implementation
}
```

### Dynamic Arrays
Both exercises use the doubling strategy:
```java
if (count == array.length) {
    Type[] tmp = new Type[array.length * 2];
    System.arraycopy(array, 0, tmp, 0, array.length);
    array = tmp;
}
```

## Technologies
- Java 8+
- Abstract classes, Polymorphism, Recursion
- Dynamic array management

## Compilation
```bash
cd Exerc_X/src
javac com/example/tp/*.java
java com.example.tp.Main
```

## Learning Objectives
- Define and use abstract classes
- Implement abstract methods in subclasses
- Apply polymorphism with abstract base classes
- Manage dynamic arrays without collections
- Implement recursive algorithms (Exercise 2)
- Multi-level inheritance patterns (Exercise 1)

## Abstract Classes vs Interfaces

| Feature | Abstract Class | Interface |
|---------|---------------|-----------|
| Implementation | Can have concrete methods | All abstract (Java < 8) |
| Fields | Can have instance fields | Only constants |
| Constructor | Can have constructors | No constructors |
| Inheritance | Single (extends) | Multiple (implements) |
| Use Case | IS-A relationship + shared code | Contract definition |

**When to use abstract classes:**
- Share common code among subclasses
- Define protected members
- Have constructor logic
- Single inheritance is sufficient

---

**Course:** Object-Oriented Programming with Java
**Institution:** FST
**Focus:** Abstract Classes and Polymorphism

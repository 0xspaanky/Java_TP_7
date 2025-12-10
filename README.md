# TP_6 - Abstract Classes and Design Patterns

This repository contains 2 Java exercises focusing on abstract classes, polymorphism, and the Composite design pattern.

## Repository Structure

```
TP_6/
├── Exerc_1/    # Employee Payroll Management System
└── Exerc_2/    # File System Modeling
```

## Exercises Overview

### Exercise 1: Employee Payroll Management System
Comprehensive payroll system with multiple employee types and salary calculations.

**Key Concepts:**
- Abstract classes with forced implementations
- Multi-level inheritance (3 levels)
- Polymorphic salary calculations
- Dynamic array management
- Template method pattern

**Classes:**
- `Employe` (Abstract base)
- `EmployeHoraire` (Hourly: rate × hours)
- `EmployeSalarie` (Salaried: fixed monthly)
- `Vendeur` (Salesperson: salary + commission)
- `Entreprise` (Company container)

**Features:**
- Three different salary calculation methods
- Vendeur extends EmployeSalarie (multi-level)
- Dynamic employee array
- Total payroll calculation
- Formatted salary display

**Example:**
```java
Entreprise ent = new Entreprise();
ent.ajouterEmploye(new EmployeHoraire("El Idrissi", "Mohamed", 15.0, 160));
ent.ajouterEmploye(new Vendeur("Chouaib", "Youssef", 2000.0, 500.0));
ent.afficherPaie();
// Displays: El Idrissi Mohamed → Salaire = 2400.00€
//           Chouaib Youssef → Salaire = 2500.00€
//           Masse salariale totale : 4900.00€
```

---

### Exercise 2: File System Modeling
Hierarchical file system with recursive operations and tree visualization.

**Key Concepts:**
- Composite design pattern
- Recursive algorithms
- Tree data structures
- Abstract classes for common interface
- Type checking with instanceof

**Classes:**
- `FsItem` (Abstract base)
- `FileItem` (Leaf - file with size)
- `Directory` (Composite - contains items)
- `FileSystem` (Root container)

**Features:**
- Nested directories (unlimited depth)
- Recursive size calculation
- Tree visualization with indentation
- Dynamic arrays in both Directory and FileSystem
- Polymorphic getSize() calls

**Example:**
```java
FileSystem fs = new FileSystem();
Directory home = new Directory("home");
home.add(new FileItem("doc.txt", 1200));

Directory docs = new Directory("docs");
docs.add(new FileItem("report.pdf", 80000));
home.add(docs);  // Nested directory

fs.addRoot(home);
fs.listAll();
// Displays tree structure with sizes
```

---

## Comparison Table

| Feature | Exercise 1 (Payroll) | Exercise 2 (FileSystem) |
|---------|---------------------|------------------------|
| Pattern | Template Method | Composite |
| Hierarchy Depth | 3 levels | 2 levels |
| Abstract Methods | `calculerSalaire()` | `getSize()` |
| Recursion | No | Yes (size, tree) |
| Nesting | No | Yes (directories) |
| Special Feature | Multi-level inheritance | Recursive operations |
| Complexity | Moderate | Advanced |

## Core Concepts

### Abstract Classes

**Definition:** Classes that cannot be instantiated and may contain abstract methods.

**Purpose:**
1. Define contracts that subclasses must implement
2. Provide common functionality
3. Enable polymorphism
4. Force consistent interface

**Example from Exercise 1:**
```java
public abstract class Employe {
    protected String nom, prenom;

    // Concrete method - inherited by all
    public String toString() { ... }

    // Abstract method - must be implemented
    public abstract double calculerSalaire();
}
```

**Example from Exercise 2:**
```java
public abstract class FsItem {
    protected String name;

    // Concrete method
    public String getName() { return name; }

    // Abstract method
    public abstract long getSize();
}
```

### Design Patterns

#### Template Method Pattern (Exercise 1)

**Definition:** Define skeleton of algorithm in base class, let subclasses fill in details.

**Implementation:**
```java
public abstract class Employe {
    // Template method
    public String toString() {
        return nom + " " + prenom + " → Salaire = "
            + calculerSalaire();  // Calls abstract method
    }

    // Hook method - implemented by subclasses
    public abstract double calculerSalaire();
}
```

**Benefits:**
- Consistent output format for all employees
- Salary calculation varies by type
- Common behavior defined once

#### Composite Pattern (Exercise 2)

**Definition:** Treat individual objects and compositions uniformly.

**Structure:**
```
Component (FsItem)
    ├─ Leaf (FileItem)
    └─ Composite (Directory)
           └─ Can contain Components
```

**Implementation:**
```java
// Component
public abstract class FsItem {
    public abstract long getSize();
}

// Leaf
public class FileItem extends FsItem {
    public long getSize() { return size; }
}

// Composite
public class Directory extends FsItem {
    public long getSize() {
        long total = 0;
        for (FsItem child : children) {
            total += child.getSize();  // Recursive!
        }
        return total;
    }
}
```

**Benefits:**
- Files and directories treated uniformly
- Recursive operations natural
- Flexible tree structure
- Easy to add new item types

## Multi-Level Inheritance

### Exercise 1 Hierarchy
```
Employe (abstract)
    ├─ EmployeHoraire
    ├─ EmployeSalarie
    │      └─ Vendeur (3 levels deep!)
```

**Key Points:**
- Vendeur extends EmployeSalarie extends Employe
- Vendeur uses `salaireMensuel` from parent
- Each level adds functionality
- All must implement `calculerSalaire()`

**Example:**
```java
// Vendeur uses protected field from EmployeSalarie
public class Vendeur extends EmployeSalarie {
    private double commission;

    @Override
    public double calculerSalaire() {
        return salaireMensuel + commission;  // From parent
    }
}
```

### Exercise 2 Hierarchy
```
FsItem (abstract)
    ├─ FileItem
    └─ Directory
```

**Key Points:**
- Simpler 2-level hierarchy
- Both implement `getSize()` differently
- Directory can contain FsItem (including other directories)
- Enables recursive tree structure

## Recursion in Exercise 2

### Recursive Size Calculation
```java
public long getSize() {
    long total = 0;
    for (int i = 0; i < count; i++) {
        total += children[i].getSize();  // May call recursively
    }
    return total;
}
```

**How it works:**
1. Directory sums all children
2. If child is FileItem: returns stored size
3. If child is Directory: calls getSize() recursively
4. Recursion ends at leaf nodes (files)

### Recursive Tree Display
```java
public void list(int indent) {
    System.out.println("  ".repeat(indent) + this);
    for (FsItem child : children) {
        if (child instanceof Directory) {
            ((Directory)child).list(indent + 1);  // Recursive
        } else {
            System.out.println("  ".repeat(indent + 1) + child);
        }
    }
}
```

**Traversal:** Depth-first, pre-order (print node then children)

## Dynamic Arrays in Both Exercises

### Pattern Used
```java
private Item[] items = new Item[4];  // Initial capacity
private int count = 0;

public void add(Item item) {
    if (count == items.length) {
        // Double capacity
        Item[] tmp = new Item[items.length * 2];
        System.arraycopy(items, 0, tmp, 0, items.length);
        items = tmp;
    }
    items[count++] = item;
}
```

**Used in:**
- Exercise 1: Entreprise (employees array)
- Exercise 2: Directory (children array), FileSystem (roots array)

## Polymorphism Examples

### Exercise 1: Salary Calculation
```java
Employe[] employes = {
    new EmployeHoraire(...),
    new EmployeSalarie(...),
    new Vendeur(...)
};

for (Employe e : employes) {
    double salaire = e.calculerSalaire();  // Different implementations
}
```

### Exercise 2: Size Calculation
```java
FsItem[] items = {
    new FileItem("doc.txt", 1200),
    new Directory("home")
};

for (FsItem item : items) {
    long size = item.getSize();  // Different implementations
}
```

## Technologies
- **Language:** Java 8+
- **Concepts:** Abstract Classes, Design Patterns, Recursion
- **Patterns:** Template Method, Composite

## Compilation & Execution

Both exercises use the same structure:

```bash
# Navigate to exercise directory
cd Exerc_X

# Compile
cd src
javac com/example/tp/*.java

# Run
java com.example.tp.Main
```

## Learning Objectives

### Fundamental Concepts
- Understanding abstract classes and methods
- Forced implementation contracts
- Protected access for inheritance
- Multi-level inheritance design

### Intermediate Concepts
- Design patterns (Template Method, Composite)
- Recursive algorithms
- Dynamic array management
- Type checking with instanceof

### Advanced Concepts
- Composite pattern for tree structures
- Recursive tree traversal
- Depth-first search
- Polymorphic method resolution

## When to Use Each Pattern

### Template Method (Exercise 1)
**Use when:**
- Algorithm structure is fixed
- Implementation details vary
- Common behavior needs consistent format
- Want to avoid code duplication

**Example use cases:**
- Data processing pipelines
- Report generation
- Game AI behaviors
- HTTP request handling

### Composite (Exercise 2)
**Use when:**
- Part-whole hierarchies
- Treat individual and composite objects uniformly
- Tree structures
- Recursive operations on hierarchies

**Example use cases:**
- File systems
- GUI component trees
- Organization charts
- Menu systems
- Expression trees

## Best Practices

### 1. Always Use @Override
```java
@Override
public double calculerSalaire() {
    // Compiler catches errors
}
```

### 2. Call super() First in Constructor
```java
public EmployeHoraire(String nom, String prenom, ...) {
    super(nom, prenom);  // Initialize parent first
    // Then initialize own fields
}
```

### 3. Use Protected for Inheritance
```java
protected double salaireMensuel;  // Accessible to subclasses
```

### 4. Document Recursive Methods
```java
/**
 * Recursively calculates total size of directory.
 * @return size in bytes including all nested items
 */
@Override
public long getSize() { ... }
```

## Common Mistakes to Avoid

### 1. Trying to Instantiate Abstract Class
```java
// Compile error!
Employe e = new Employe("Test", "User");

// Correct
Employe e = new EmployeSalarie("Test", "User", 2000);
```

### 2. Forgetting Recursion Base Case
```java
// Infinite recursion!
public long getSize() {
    return getSize();  // Wrong!
}

// Correct - base case implicit (FileItem returns size)
public long getSize() {
    return size;  // Base case
}
```

### 3. Not Handling null in Recursion
```java
// May throw NullPointerException
public long getSize() {
    long total = 0;
    for (FsItem child : children) {
        total += child.getSize();  // What if child is null?
    }
    return total;
}
```

## Verification Tips

For both exercises:
- [ ] Cannot instantiate abstract classes
- [ ] All abstract methods implemented
- [ ] Polymorphism works correctly
- [ ] Dynamic arrays expand when needed
- [ ] Output matches specifications
- [ ] No NullPointerException errors
- [ ] Recursion terminates (Exercise 2)
- [ ] Multi-level inheritance works (Exercise 1)

## Extensions and Projects

### Exercise 1 Extensions
- Add Manager with bonus
- Implement overtime pay
- Add salary history
- Tax and deductions
- Performance bonuses

### Exercise 2 Extensions
- File permissions (read/write/execute)
- File search by name or type
- Copy/move operations
- File statistics (count, average size)
- Symbolic links

### Combined Project
Create a document management system:
- Employees create and manage documents
- Documents stored in hierarchical folders
- Track who created what
- Calculate storage used per employee

## Documentation
Each exercise directory contains:
- `README.md`: Detailed specifications and examples
- `subject.txt`: Original French requirements
- Java source files: Complete implementations
- `image.png`: UML or concept diagrams

---

**Course:** Advanced Object-Oriented Programming with Java
**Institution:** FST
**Focus:** Abstract Classes and Design Patterns
**Difficulty:** Intermediate to Advanced

## Key Takeaways

1. **Abstract classes** enforce implementation contracts while providing common functionality
2. **Template Method** pattern keeps algorithm structure consistent while varying details
3. **Composite pattern** enables treating individuals and groups uniformly
4. **Recursion** is natural for tree structures
5. **Multi-level inheritance** allows progressive specialization
6. **Dynamic arrays** provide flexibility without collections framework

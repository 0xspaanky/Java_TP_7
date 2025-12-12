# Exercise 2: File System Modeling

## Objective
Learn to define and use abstract classes in Java by modeling a mini file system where each element (FsItem) can be a file or directory capable of calculating its size.

## Key Concepts
- Abstract classes with abstract methods
- Recursive size calculation
- Dynamic array management
- Tree structure with nested directories
- Polymorphism with files and folders

## Class Hierarchy

```
          FsItem (abstract)
          ├─ name : String
          └─ getSize() : long (abstract)
                    |
      +-------------+-------------+
      |                           |
  FileItem                    Directory
  ├─ size : long              ├─ children[] : FsItem[]
  └─ getSize()                ├─ count : int
                              ├─ add(item)
                              ├─ getSize() (recursive)
                              └─ list(indent)
```

## Implementation

### FsItem (Abstract Base)
```java
package com.example.tp;

public abstract class FsItem {
    protected String name;

    public FsItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /** Returns size in bytes */
    public abstract long getSize();

    @Override
    public String toString() {
        return name + " (" + getSize() + " B)";
    }
}
```

### FileItem
```java
public class FileItem extends FsItem {
    private long size; // in bytes

    public FileItem(String name, long size) {
        super(name);
        this.size = size;
    }

    @Override
    public long getSize() {
        return size;
    }
}
```

### Directory
```java
public class Directory extends FsItem {
    private FsItem[] children;
    private int count;

    public Directory(String name) {
        super(name);
        this.children = new FsItem[4];
        this.count = 0;
    }

    /** Adds a child (file or subdirectory) */
    public void add(FsItem item) {
        if (count == children.length) {
            FsItem[] tmp = new FsItem[children.length * 2];
            System.arraycopy(children, 0, tmp, 0, children.length);
            children = tmp;
        }
        children[count++] = item;
    }

    @Override
    public long getSize() {
        long total = 0;
        for (int i = 0; i < count; i++) {
            total += children[i].getSize();
        }
        return total;
    }

    /** Displays tree structure with indentation */
    public void list(int indent) {
        System.out.println("  ".repeat(indent) + this);
        for (int i = 0; i < count; i++) {
            FsItem f = children[i];
            if (f instanceof Directory) {
                ((Directory)f).list(indent + 1);
            } else {
                System.out.println("  ".repeat(indent + 1) + f);
            }
        }
    }
}
```

### FileSystem
```java
public class FileSystem {
    private FsItem[] items;
    private int count;

    public FileSystem() {
        this.items = new FsItem[4];
        this.count = 0;
    }

    /** Adds a root element */
    public void addRoot(FsItem item) {
        if (count == items.length) {
            FsItem[] tmp = new FsItem[items.length * 2];
            System.arraycopy(items, 0, tmp, 0, items.length);
            items = tmp;
        }
        items[count++] = item;
    }

    /** Lists entire system */
    public void listAll() {
        for (int i = 0; i < count; i++) {
            FsItem f = items[i];
            if (f instanceof Directory) {
                ((Directory)f).list(0);
            } else {
                System.out.println(f);
            }
        }
    }

    /** Total size of all root elements */
    public long totalSize() {
        long sum = 0;
        for (int i = 0; i < count; i++) {
            sum += items[i].getSize();
        }
        return sum;
    }
}
```

## Usage Example
```java
package com.example.tp;

public class Main {
    public static void main(String[] args) {
        FileSystem fs = new FileSystem();

        // Create files
        FileItem f1 = new FileItem("doc.txt", 1200);
        FileItem f2 = new FileItem("image.png", 450000);
        FileItem f3 = new FileItem("notes.pdf", 80000);

        // Directories
        Directory home = new Directory("home");
        home.add(f1);
        home.add(f2);

        Directory docs = new Directory("docs");
        docs.add(f3);
        home.add(docs);

        fs.addRoot(home);
        fs.addRoot(new FileItem("readme.md", 3000));

        // Display
        fs.listAll();
        System.out.println("Taille totale : "
            + fs.totalSize() + " B");
    }
}
```

## Expected Output
```
home (531200 B)
  doc.txt (1200 B)
  image.png (450000 B)
  docs (80000 B)
    notes.pdf (80000 B)
readme.md (3000 B)
Taille totale : 534200 B
```

## Compilation & Execution
```bash
cd src
javac com/example/tp/*.java
java com.example.tp.Main
```

## Key Features
- **Abstract base class**: Forces implementation of `getSize()`
- **Recursive calculation**: Directory size = sum of all children's sizes
- **Tree structure**: Nested directories supported
- **Dynamic arrays**: Both Directory and FileSystem expand when full
- **Polymorphism**: `FsItem[]` holds both files and directories

## Size Calculation

| Type | Formula |
|------|---------|
| FileItem | Returns stored size value |
| Directory | Recursively sums `getSize()` of all children |

## Extensions
- Add `delete(String name)` method
- Implement `search(String name)` to find items
- Add file permissions (read/write/execute)
- Track creation/modification dates
- Add `move(FsItem item, Directory dest)` method
- Calculate and display sizes in KB/MB/GB

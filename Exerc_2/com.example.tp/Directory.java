package com.example.tp;

public class Directory extends FsItem {
    private FsItem[] children;
    private int count;

    public Directory(String name) {
        super(name);
        this.children = new FsItem[4];
        this.count = 0;
    }

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

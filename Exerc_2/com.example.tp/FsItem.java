package com.example.tp;

public abstract class FsItem {
    protected String name;

    public FsItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract long getSize();

    @Override
    public String toString() {
        return name + " (" + getSize() + " B)";
    }
}

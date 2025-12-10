package com.example.tp;

public class FileItem extends FsItem {
    private long size;

    public FileItem(String name, long size) {
        super(name);
        this.size = size;
    }

    @Override
    public long getSize() {
        return size;
    }
}

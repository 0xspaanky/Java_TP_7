package com.example.tp;

public class Main {
    public static void main(String[] args) {
        FileSystem fs = new FileSystem();

        // Fichiers
        FileItem f1 = new FileItem("doc.txt", 1200);
        FileItem f2 = new FileItem("image.png", 450000);
        FileItem f3 = new FileItem("notes.pdf", 80000);

        // Répertoires imbriqués
        Directory home = new Directory("home");
        home.add(f1);
        home.add(f2);

        Directory docs = new Directory("docs");
        docs.add(f3);

        home.add(docs);

        // Ajout des éléments au système de fichiers
        fs.addRoot(home);
        fs.addRoot(new FileItem("readme.md", 3000));

        // Affichage
        fs.listAll();
        System.out.println("Taille totale : " + fs.totalSize() + " B");
    }
}

/*
 * POO1 Laboratoire 07 - HANOI MODE CONSOL ET MODE DISPLAYER
 *
 * Fichier      : Pile.java - Construit une pile constitué d'Element
 * Auteurs      : Edoardo Carpita _ Dimitri Lambert
 * Date         : Monday 2 December 2019, 16:35
 */
package outils;

public class Pile {
    private Element tete;
    private int taille;

    // ====================== CONSTRUCTEUR
    public Pile() {
        taille = 0;
    }

    // ====================== METHODES
    public void push(Object datas) {
        Element nextElement = null;

        if (tete != null) {
            nextElement = new Element(tete.getDatas(), tete.nextElement());
        }

        tete = new Element(datas, nextElement);
        ++taille;
    }

    public Object pop() {
        if (tete == null) {
            throw new RuntimeException("ERROR : La pile est vide");
        }
        Object toPop = tete.getDatas();
        tete = tete.nextElement();
        --taille;
        return toPop;
    }

    public PileIterateur getIterateur() {
        if (tete == null) {
            throw new RuntimeException("ERROR : La pile est vide");
        }
        return new PileIterateur(tete);
    }

    public Object[] getListeElementsPile() {
        if (tete == null) {
            return new Object[0];
        }

        Object[] listeDesPiles = new Object[taille];

        PileIterateur it = new PileIterateur(tete);
        int compteur = 0;

        while (it.possedeSuivant()) {
            listeDesPiles[compteur] = it.suivant();
            compteur++;

        }

        return listeDesPiles;
    }

    // ====================== SURCHARGES
    @Override
    public String toString() {
        StringBuilder chaine = new StringBuilder();
        Object[] listeElementsPile = this.getListeElementsPile();

        chaine.append("[ ");
        for (Object Element : listeElementsPile) {
            chaine.append("<").append(Element).append("> ");
        }
        chaine.append("]");

        return chaine.toString();
    }
}
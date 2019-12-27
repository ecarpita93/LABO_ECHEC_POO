/*
 * POO1 Laboratoire 07 - HANOI MODE CONSOL ET MODE DISPLAYER
 *
 * Fichier      : PileIterateur.java - Est une classe permettant d'itérer sur notre pile
 * Auteurs      : Edoardo Carpita _ Dimitri Lambert
 * Date         : Monday 2 December 2019, 16:35
 */
package outils;

public class PileIterateur {

    private Element element; // Noeud sur lequel on va iterer

    // ====================== CONSTRUCTEURS
    PileIterateur(Element element) {
        this.element = element;
    }

    // ====================== METHODES
    public Object suivant() {
        if (element == null) {
            return null;
        }
        Object data = this.element.getDatas();
        this.element = this.element.nextElement();

        return data;
    }

    public boolean possedeSuivant() {
        return this.element != null;
    }
}

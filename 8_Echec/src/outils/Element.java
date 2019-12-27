/*
 * POO1 Laboratoire 07 - HANOI MODE CONSOL ET MODE DISPLAYER
 *
 * Fichier      : Element.java - Est un element de la pile
 * Auteurs      : Edoardo Carpita _ Dimitri Lambert
 * Date         : Monday 2 December 2019, 16:35
 */
package outils;

class Element {
    private Object data;
    private Element next;

    // ====================== CONSTRUCTEUR
    Element(Object data, Element next) {
        this.next = next;
        this.data = data;
    }

    // ====================== METHODES
    Object getDatas() {
        return data;
    }

    Element nextElement() {
        return next;
    }
}

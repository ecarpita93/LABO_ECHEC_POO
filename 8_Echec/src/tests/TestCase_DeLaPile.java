/*
 * POO1 Laboratoire 07 - HANOI MODE CONSOL ET MODE DISPLAYER
 *
 * Fichier      : TestCase_DeLaPile.java - Test de la classe Pile
 * Auteurs      : Edoardo Carpita _ Dimitri Lambert
 * Date         : Monday 2 December 2019, 16:35
 */
package tests;

import outils.Pile;
import outils.PileIterateur;

public abstract class TestCase_DeLaPile {

    private static Pile pileDeTest;

    private static void testPileVide(){
        pileDeTest = new Pile();
        System.out.println(pileDeTest);
        System.out.println();
    }
    private static void testPopSurPileVide(){
        pileDeTest = new Pile();
        try {
            pileDeTest.pop();
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println(" -> Check erreur Ok");
        }
    }
    private static void testPushPop(){
        pileDeTest = new Pile();
        pileDeTest.push(2);
        System.out.println("  push(2) - met (2) dans une pile vide : " + pileDeTest);
        pileDeTest.push(4);
        System.out.println("  push(4) - push suivant (4) : " + pileDeTest);
        System.out.print("  Pop d'un element de la pile (4) : ");
        try {
            pileDeTest.pop();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(pileDeTest);
        System.out.println();
    }
    private static void testIterateur(){
        pileDeTest = new Pile();
        System.out.println("  Etat de la pile avant le parcours avec un iterateur: " + pileDeTest);
        pileDeTest.push(9);
        pileDeTest.push(1);
        pileDeTest.push(0);
        pileDeTest.push(2);
        System.out.println("  Push de 4 elements dans la pile : " + pileDeTest);
        PileIterateur pileIterateur = pileDeTest.getIterateur();
        Object ptr = new Object(); // Création d'un "pointeur" sur la pile
        while (pileIterateur.possedeSuivant()) { // On test le pointeur jusqu'au bout
            ptr = pileIterateur.suivant();
            if(ptr != null) {
                System.out.println("  Parcours valeur suivante : " + ptr);
            }
        }
        System.out.println("  Aucune valeur n'a été modifié après le parcours : " + pileDeTest);
    }

    public static void testDeLaPile() {

        System.out.print("Test Pile  - Pile vide : ");
        testPileVide();
        System.out.println();

        System.out.println("Test Pile  - Pop sur une pile vide provoque l'erreur : ");
        testPopSurPileVide();
        System.out.println();

        System.out.println("Test Pile - Test de la fonction push() et pop()");
        testPushPop();
        System.out.println();

        System.out.println("Test Pile - Test iterateur");
        testIterateur();
    }
}
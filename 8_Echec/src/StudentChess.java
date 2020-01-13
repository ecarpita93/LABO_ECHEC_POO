/***********************************************************************************************************************
 *
 * Auteurs:    Edoardo Carpita - Dimitri Lambert
 * Date:      12-01-2020
 *
 * Projet: Laboratoire POO1 - Implementation jeu d'echec
 * Fichier: StudentChess.java
 * Brief: fichier principal (main) qui s'occupe de l’ouverture du programme
 *
 *
 **********************************************************************************************************************/


import chess.ChessController;
import chess.ChessView;
import chess.views.console.ConsoleView;
import chess.views.gui.GUIView;
import engine.GameController;

import static java.lang.System.exit;

public class StudentChess {

    /**
     * Main du programme
     * @param args prends un seul argument pour lancer le programme (GUI pour la version graphique, CON pour console)
     */
    public static void main(String[] args) {

        boolean selected_GUI_type = false;

        /* 1. Gestion des arguments du programme */
        if (args.length == 1) {
            switch (args[0]) {
                case "GUI":
                    selected_GUI_type = true;
                    break;
                case "CON":
                    break;
                default:
                    System.out.println("Please give a valid argument");
                    System.out.println("-- GUI for the graphic interface version");
                    System.out.println("-- CON for the console version");
                    exit(0);
                    break;
            }
        } else {
            System.out.println("Please give a only one argument");
            System.out.println("-- GUI for the graphic interface version");
            System.out.println("-- CON for the console version");
            exit(0);
        }

        /* 2. Création du contrôleur pour gérer le jeu d’échec */
        ChessController gameController = new GameController();
        /* 3. Création de la vue desire */
        ChessView guiView = new GUIView(gameController);
        ConsoleView consoleView = new ConsoleView(gameController);
        /* 4. Lancement du jeu */
        if (selected_GUI_type) {
            gameController.start(guiView);
        } else {
            gameController.start(consoleView);
        }
    }
}

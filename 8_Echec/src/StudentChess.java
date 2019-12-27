import chess.ChessController;
import chess.ChessView;
import chess.views.console.ConsoleView;
import chess.views.gui.GUIView;
import engine.GameController;

import tests.TestCase_DeLaPile;

public class StudentChess {

    public static void main(String[] args) {
        //   1. Création du contrôleur pour gérer le jeu d’échec
        ChessController controller = new GameController();
        // 2. Création de la vue désirer
        ChessView view = new GUIView(controller);
        // ou:
        ConsoleView cView = new ConsoleView(controller);
        // 3. Lancement du jeu
        controller.start(view);
    }
}

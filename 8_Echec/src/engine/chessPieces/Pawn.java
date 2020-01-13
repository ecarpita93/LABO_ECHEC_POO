/***********************************************************************************************************************
 *
 * Auteurs:    Edoardo Carpita - Dimitri Lambert
 * Date:      12-01-2020
 *
 * Projet: Laboratoire POO1 - Implementation jeu d'echec
 * Fichier: Pawn.java
 * Brief: classe avec l'implementation du Pion
 *
 *
 **********************************************************************************************************************/

package engine.chessPieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.chessElements.ChessBoard;

import java.awt.*;

public class Pawn extends FirstMovePiece {

    private int vector;                     /* permet de definir le vecteur de deplacement d'une piece sur le plateau */
    private boolean can_move_en_passant;    /* permet d'indiquer que les conditions pour effectuer une prise en passant sont valides*/
    private boolean has_double_start;       /* flag qui nous indique pendant deux tours que la piece a commence par un mouvement double*/
    private int turn_counter;               /* permet de compter le nombre de tours sont passe depuis le lancement du flag*/

    /******************************** Matrices de rotations du Chevalier **********************************************/

    private static final Point[] PAWN_MOVE_MATRIX = {new Point(0, 1), new Point(0, 2)};
    private static final Point[] PAWN_EAT_MATRIX = {new Point(1, 1), new Point(-1, 1)};

    /*******************************************************************************************************************/

    private static final int WHITE_VECTOR = 1;
    private static final int BLACK_VECTOR = -1;
    private static final int WHITE_PROMOTION_Y = 7;
    private static final int BLACK_PROMOTION_Y = 0;
    private static final int DOUBLE_START_OFFSET = 2;
    private static final int TURN_COUNTER_LIMIT = 2;

    /**
     * Constructeur de Pion
     * @param chessboard le plateau du jeu
     * @param player le joueur qui possède cette piece
     * @param piece_type le type de la piece
     * @param position la position qu'elle va prendre
     */
    public Pawn(ChessBoard chessboard, PlayerColor player, PieceType piece_type, Point position) {
        super(chessboard, player, piece_type, position);
        vector = this.player == PlayerColor.BLACK ? BLACK_VECTOR : WHITE_VECTOR;  /* on definit le vecteur de deplacement par rapport au joueur qui possede la piece*/
        has_double_start = false;                                                 /* initialisation des autres attributs à la valeur par default*/
        can_move_en_passant = false;
        turn_counter = TURN_COUNTER_LIMIT;
    }

    /**
     * Renvoi un booleen pour indiquer si la piece a atteint la fin adverse du plateau (comme les pions ne peuvent
     * pas reculer, aucune autre verifications ulterieure est necessaire)
     * @return true si la condition est vraie, false autrement
     */
    public boolean canBePromoted() {
        return position.getY() == WHITE_PROMOTION_Y || position.getY() == BLACK_PROMOTION_Y;
    }

    /**
     * Permet d'activer le flag de double mouvement de depart si le mouvement a un offset vertical superieur a 2
     * @param toY la position future Y avec laquelle on va calculer l'offset du mouvement
     */
    public void hasDoubleStarted(int toY) {
        has_double_start = (Math.abs(position.getY() - toY) == DOUBLE_START_OFFSET);
    }

    /**
     * Permet de verifier si la piece est en train d'effectuer une prise en en passant. Si la piece peut effectuer
     * un en passant, le booleen can_move_en_passant sera actif. Ensuite si le offset vertical et le offset horizontal
     * sont egaux (et donc on a en faite effectué un mouvement diagonal) la condition est vraie
     * @param toX la position future X avec laquelle on va calculer l'offset du mouvement
     * @param toY la position future Y avec laquelle on va calculer l'offset du mouvement
     * @return true si la condition est vraie, false autrement
     */
    public boolean isDoingEnPassant(int toX, int toY) {
        if (can_move_en_passant) {
            return (Math.abs(position.getX() - toX) - Math.abs(position.getY() - toY) == 0);
        }
        return false;
    }

    /**
     * Renvoie le flag de double mouvement de depart
     * @return le flag de double mouvement de depart
     */
    private boolean getDoubleStart() {
        return has_double_start;
    }

    /**
     * Fonction qui effectue le calcul de mouvements de Pion
     * (si le flag de first move est faux on va seulement ragarder le point de mouvement de base)
     */
    private void checkPawnMoves() {
        for (Point other : PAWN_MOVE_MATRIX) {
            Piece obstacle;
            Point tester = new Point(position);
            tester.translate((int) other.getX(), (int) other.getY() * vector);
            if (chessboard.checkPositionInBoardLimits(tester)) {
                obstacle = chessboard.getPieceAtPosition((int) tester.getX(), (int) tester.getY());
                if (obstacle == null) {
                    possible_moves.add(new Point(tester));
                }
            }

            if (!this.getFirstMove()) {
                break;
            }
        }
    }

    /**
     * Fonction qui effectue le calcul les prise de Pion
     * (meme chose que dans la super classe mais comme les mouvements sont calculés differament on est aussi
     * obbliges de creer une methode qui regarde seulement pour les prises)
     */
    private void checkPawnEats() {
        for (Point other : PAWN_EAT_MATRIX) {
            Piece obstacle;
            Point tester = new Point(position);
            tester.translate((int) other.getX(), (int) other.getY() * vector);
            if (chessboard.checkPositionInBoardLimits(tester)) {
                obstacle = chessboard.getPieceAtPosition((int) tester.getX(), (int) tester.getY());
                if (obstacle != null && obstacle.getPlayer() != this.player) {
                    possible_eats.add(new Point(tester));
                }
            }
        }
    }

    /**
     * Fonction qui effectue le calcul les prise de type en Passant pour Pion
     * (meme chose que dans la super classe mais comme les mouvements sont calculés differament on est aussi
     * obbliges de creer une methode qui regarde seulement pour les prises)
     */
    private void checkEnPassant() {
        can_move_en_passant = false;

        for (Point other : PAWN_EAT_MATRIX) {
            if (chessboard.checkPositionInBoardLimits(new Point((int) (position.getX() + other.getX()), (int) position.getY()))) {
                Piece obstacle = chessboard.getPieceAtPosition((int) (position.getX() + other.getX()), (int) position.getY());

                if (obstacle instanceof Pawn && obstacle.getPlayer() != this.player) {
                    if (((Pawn) obstacle).getDoubleStart()) {
                        can_move_en_passant = true;
                        possible_moves.add(new Point((int) (position.getX() + other.getX()), (int) (position.getY() + other.getY() * vector)));
                    }
                }
            }
        }
    }

    /**
     * Fonction qui s'occupe de garder le flag de double actif pendant deux tours (ceci permet la prise en passant seulement
     * si le joueur adversaire profite du moment).
     */
    private void checkDoubleStartSignalValidity() {
        if (has_double_start) {               /* si le signal start est valide */
            turn_counter--;                   /* on decremente le compteur */
            if (turn_counter == 0) {          /* si le compteur atteint 0 on reinitialise à la valeur par default le counter et on desactive la flag*/
                has_double_start = false;
                turn_counter = TURN_COUNTER_LIMIT;
            }
        }
    }

    /**
     * Pour le Pion, le calcul de moves qui se fait a chaque tour nous permet de donner une limite a le signal de flag
     * qui sera donc incrementé si necessaire. En autre on appelle les fonctions de calcul de prises et des mouvemement
     */
    @Override
    public void calculatePossibleMoves() {
        super.calculatePossibleMoves();

        checkDoubleStartSignalValidity();

        checkPawnMoves();
        checkPawnEats();
        checkEnPassant();

    }

}

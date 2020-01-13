/***********************************************************************************************************************
 *
 * Auteurs:    Edoardo Carpita - Dimitri Lambert
 * Date:      12-01-2020
 *
 * Projet: Laboratoire POO1 - Implementation jeu d'echec
 * Fichier: Piece.java
 * Brief: classe abstraite qui regroupe toute l'implementation commune à toute pièce
 *
 *
 **********************************************************************************************************************/

package engine.chessPieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.chessElements.ChessBoard;

import java.awt.*;
import java.util.ArrayList;

public abstract class Piece {

    ChessBoard chessboard;     /* comme chaque piece doit savoir la position courante des autres pour calculer les mouvement,
                                  on garde une reference vers le plateau*/
    PlayerColor player;        /* le joueur auquel cette piece appartient */
    PieceType piece_type;      /* le type de piece NOTE D'IMPLEMENTATION on pense on aurait pu eliminer cet attribut du projet
                                  car il est relativement utile au final, mais nous n avons pas eu le temps d'effectuer et verifier cette idee */
    Point position;            /* la position de la pièce */
    ArrayList<Point> possible_moves;   /* liste des movements possibles */
    ArrayList<Point> possible_eats;    /* liste des prises possibles */


    /************************************** Matrices de rotations communes à plusieurs pieces *************************/

    private static final Point[] DIAGONAL_MATRIX = {new Point(1, 1), new Point(1, -1), new Point(-1, -1), new Point(-1, 1)};
    private static final Point[] VERTICAL_MATRIX = {new Point(0, 1), new Point(0, -1)};
    private static final Point[] HORIZONTAL_MATRIX = {new Point(1, 0), new Point(-1, 0)};

    /*******************************************************************************************************************/


    /**
     * Constructeur de piece
     * @param chessboard le plateau du jeu
     * @param player le joueur qui possède cette piece
     * @param piece_type le type de la piece
     * @param position la position qu'elle va prendre
     */
    Piece(ChessBoard chessboard, PlayerColor player, PieceType piece_type, Point position) {
        this.chessboard = chessboard;
        this.player = player;
        this.piece_type = piece_type;
        this.position = position;

        possible_moves = new ArrayList<>();
        possible_eats = new ArrayList<>();
        addPieceIntoGame();                 /* des sa construction la piece est rajouté au jeu */
    }


    /**
     * Methode qui sera overrided par chaque piece pour calculer les mouvements dans les arrays possible_moves et
     * possible_eats selon les criteres de chaque piece. Au niveau de la Piece on nettoye juste les arrays pour chaque
     * nouveau tour
     */
    public void calculatePossibleMoves() {
        resetMovesArrays();
    }

    /**
     * Methode privé qui nettoye les listes
     */
    private void resetMovesArrays() {
        possible_moves.clear();
        possible_eats.clear();
    }

    /**
     * Focntion qui permet de savoir si la piece peut bouger vers une position
     * @param toX coordonner X à verifier
     * @param toY coordonner Y à verifier
     * @return true si le deplacement est possible, false au contraire
     */
    public boolean canMoveTo(int toX, int toY) {
        return findInArray(possible_moves, toX, toY);
    }

    /**
     * Focntion qui permet de savoir si la piece peut effectuer une prise vers une position
     * @param toX coordonner X à verifier
     * @param toY coordonner Y à verifier
     * @return true si la prise est possible, false au contraire
     */
    public boolean canEatTo(int toX, int toY) {
        return findInArray(possible_eats, toX, toY);
    }

    /**
     * Methode prive qui permet de chercher si un point donnee est present dans une liste
     * @param possible_goes la liste a verifier
     * @param toX coordonner X à verifier
     * @param toY coordonner Y à verifier
     * @return si la coordonne est bien presente dans l'array
     */
    private boolean findInArray(ArrayList<Point> possible_goes, int toX, int toY) {
        for (Point possible_go : possible_goes) {
            if (possible_go.getX() == toX && possible_go.getY() == toY) {
                return true;
            }
        }
        return false;
    }

    /**
     * Permet de rajouter une piece dans le jeu
     */
    private void addPieceIntoGame() {
        chessboard.addPieceList(this);
        chessboard.setPieceAtPosition(this, (int) position.getX(), (int) position.getY());
    }

    /**
     * Permet d'enlever une piece du jeu (effect de prise normalement)
     */
    public void removePieceFromGame() {
        position = new Point(-30, -30); /*position arbitraire en dehors du plateau*/
        chessboard.removePieceList(this);
    }

    /**
     * verifie les deplacements horizontaux
     */
    void checkHorizontalMovesAndEats() {
        checkMovesAndEatsMatrixUntilNotPossible(HORIZONTAL_MATRIX);
    }

    /**
     * Verifie les deplacements verticaux
     */
    void checkVerticalMovesAndEats() {
        checkMovesAndEatsMatrixUntilNotPossible(VERTICAL_MATRIX);
    }

    /**
     * Verifie les deplacement diagonaux
     */
    void checkDiagonalMovesAndEats() {
        checkMovesAndEatsMatrixUntilNotPossible(DIAGONAL_MATRIX);
    }

    /**
     * Fonction qui prends la matrice de translaction en parametre et calcule pour chaque point translaté par rapport
     * à la position d'origine si cette nouvelle position est valable (et s'il s'agit d'une prise ou un mouvement)
     * @param otherMatrix la matrice de translaction à utiliser
     */
    void checkMovesAndEatsMatrix(Point[] otherMatrix) {
        for (Point other : otherMatrix) {                   /* pour chaque point de la matrice */
            Piece obstacle;
            Point tester = new Point(position);

            tester.translate((int) other.getX(), (int) other.getY());    /*on translate la position de la piece par rapport au point matrice*/

            if (chessboard.checkPositionInBoardLimits(tester)) {   /*si le point translaté reste dans le limite du plateau */
                obstacle = chessboard.getPieceAtPosition((int) tester.getX(), (int) tester.getY());
                if (obstacle == null) {                                /* si le point translaté est inoccupé */
                    possible_moves.add(new Point(tester));             /* on ajoute la position aux mouvements psossibles*/
                } else if (obstacle.getPlayer() != this.getPlayer()) { /* autrement s'il est occupé par une piece adverse*/
                    possible_eats.add(new Point(tester));              /* on ajoute la position aux prises psossibles*/
                }
            }
        }
    }

    /**
     * Fonction similaire à la fonction de calcul ci dessus, avec la difference que celle ci calcule la translaction
     * tant que le mouvememt est possible (pour calculer des lignes entieres par exemple)
     * @param otherRecursiveMatrix la matrice de translaction à utiliser
     */
    private void checkMovesAndEatsMatrixUntilNotPossible(Point[] otherRecursiveMatrix) {
        /*fonctionnement presque identique à la variante ci dessus, a une verification supplementaire */
        for (Point other : otherRecursiveMatrix) {
            Piece obstacle;
            Point tester = new Point(position);
            do {        /*pour cette version on va donc translater la position tant qu'on recontre pas un obstacle*/
                tester.translate((int) other.getX(), (int) other.getY());
                if (chessboard.checkPositionInBoardLimits(tester)) {
                    obstacle = chessboard.getPieceAtPosition((int) tester.getX(), (int) tester.getY());
                    if (obstacle == null) {
                        possible_moves.add(new Point(tester));
                    } else if (obstacle.getPlayer() != this.getPlayer()) {
                        possible_eats.add(new Point(tester));
                    }
                } else { /*on arrete aussi la boucle do si on arrive en dehors des limites du plateau */
                    break;
                }
            } while (obstacle == null);
        }
    }

    /**
     * Fonction qui retourne les prises possibles de la piece
     * @return liste des prises possibles
     */
    public ArrayList<Point> getPossibleEats() {
        return possible_eats;
    }

    /**
     * Fonction qui retourne les mouvements possibles de la piece
     * @return liste des mouvements possibles
     */
    public ArrayList<Point> getPossibleMoves() {
        return possible_moves;
    }

    /**
     * Fonction qui retourne le joueur qui possede la piece
     * @return le PlayerColor du joueur correspodnat
     */
    public PlayerColor getPlayer() {
        return player;
    }

    /**
     * Fonction qui retourne le type de la piece
     * @return le PieceType de la piece
     */
    public PieceType getPieceType() {
        return piece_type;
    }

    /**
     * Fonction qui retourne la position de la piece
     * @return le Point position
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Permet de changer l'attribut position de la piece
     * @param position le nouveau Point position de la piece
     */
    public void setPosition(Point position) {
        this.position = position;
    }
}

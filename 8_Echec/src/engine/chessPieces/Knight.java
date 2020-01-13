/***********************************************************************************************************************
 *
 * Auteurs:    Edoardo Carpita - Dimitri Lambert
 * Date:      12-01-2020
 *
 * Projet: Laboratoire POO1 - Implementation jeu d'echec
 * Fichier: Knight.java
 * Brief: classe avec l'implementation du Chevalier
 *
 *
 **********************************************************************************************************************/

package engine.chessPieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.chessElements.ChessBoard;

import java.awt.*;

public class Knight extends Piece {

    /******************************** Matrices de rotations du Chevalier **********************************************/

    private static final Point[] KNIGHT_MATRIX = {new Point(2, 1), new Point(2, -1), new Point(-2, -1), new Point(-2, 1), new Point(1, 2), new Point(1, -2), new Point(-1, -2), new Point(-1, 2)};

    /*******************************************************************************************************************/

    /**
     * Constructeur de Chevalier
     * @param chessboard le plateau du jeu
     * @param player le joueur qui possède cette piece
     * @param piece_type le type de la piece
     * @param position la position qu'elle va prendre
     */
    public Knight(ChessBoard chessboard, PlayerColor player, PieceType piece_type, Point position) {
        super(chessboard, player, piece_type, position);
    }

    /**
     * Pour le Chevalier, le calcul des moves se fait via sa propre matrice seulement
     */
    @Override
    public void calculatePossibleMoves() {
        super.calculatePossibleMoves();
            checkMovesAndEatsMatrix(KNIGHT_MATRIX);
    }

}

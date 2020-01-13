/***********************************************************************************************************************
 *
 * Auteurs:    Edoardo Carpita - Dimitri Lambert
 * Date:      12-01-2020
 *
 * Projet: Laboratoire POO1 - Implementation jeu d'echec
 * Fichier: Bishop.java
 * Brief: classe avec l'implementation du Fou
 *
 *
 **********************************************************************************************************************/

package engine.chessPieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.chessElements.ChessBoard;

import java.awt.*;


public class Bishop extends Piece {
    /**
     * Constructeur Fou
     * @param chessboard le plateau du jeu
     * @param player le joueur qui possède cette piece
     * @param piece_type le type de la piece
     * @param position la position qu'elle va prendre
     */
    public Bishop(ChessBoard chessboard, PlayerColor player, PieceType piece_type, Point position) {
        super(chessboard, player, piece_type, position);
    }

    /**
     * Pour le Fou, le calcul des moves se fait via les diagonaux
     */
    @Override
    public void calculatePossibleMoves() {
        super.calculatePossibleMoves();
            checkDiagonalMovesAndEats();

    }

}

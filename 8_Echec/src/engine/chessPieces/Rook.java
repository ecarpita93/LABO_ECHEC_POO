/***********************************************************************************************************************
 *
 * Auteurs:    Edoardo Carpita - Dimitri Lambert
 * Date:      12-01-2020
 *
 * Projet: Laboratoire POO1 - Implementation jeu d'echec
 * Fichier: Rook.java
 * Brief: classe avec l'implementation de la Tour
 *
 *
 **********************************************************************************************************************/

package engine.chessPieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.chessElements.ChessBoard;

import java.awt.*;

public class Rook extends FirstMovePiece {

    /**
     * Constructeur de Tour
     * @param chessboard le plateau du jeu
     * @param player le joueur qui possède cette piece
     * @param piece_type le type de la piece
     * @param position la position qu'elle va prendre
     */
    public Rook(ChessBoard chessboard, PlayerColor player, PieceType piece_type, Point position) {
        super(chessboard, player, piece_type, position);
    }

    /**
     * Pour la Tour, le calcul des moves et des prises se fait avec les matrices verticales et horizontales de base
     */
    @Override
    public void calculatePossibleMoves() {
        super.calculatePossibleMoves();
            checkHorizontalMovesAndEats();
            checkVerticalMovesAndEats();
    }
}

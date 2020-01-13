/***********************************************************************************************************************
 *
 * Auteurs:    Edoardo Carpita - Dimitri Lambert
 * Date:      12-01-2020
 *
 * Projet: Laboratoire POO1 - Implementation jeu d'echec
 * Fichier: FirstMovePiece.java
 * Brief: classe abstraite qui regroupe toute l'implementation commune à toute pièce dont savoir si elle a bouge une fois
 *        est important
 *
 *
 **********************************************************************************************************************/

package engine.chessPieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.chessElements.ChessBoard;

import java.awt.*;

public abstract class FirstMovePiece extends Piece {

    private boolean first_move; /* on rajoute donc boolean qui vas nous indiquer si la piece a bouge ou pas une fois*/

    /**
     * Constructeur de first move , on initialise le boolean par default à true
     * @param chessboard le plateau du jeu
     * @param player le joueur qui possède cette piece
     * @param piece_type le type de la piece
     * @param position la position qu'elle va prendre
     */
    FirstMovePiece(ChessBoard chessboard, PlayerColor player, PieceType piece_type, Point position) {
        super(chessboard, player, piece_type, position);
        first_move = true;
    }

    /**
     * Permet de recuperer la valeur du booleen du first move
     * @return first_move
     */
    public boolean getFirstMove() {
        return first_move;
    }

    /**
     * Permet de changer la valeur du booleen de first_move
     * @param new_value nouvelle valeur de first_move
     */
    public void setFirstMove(boolean new_value) {
        this.first_move = new_value;
    }
}

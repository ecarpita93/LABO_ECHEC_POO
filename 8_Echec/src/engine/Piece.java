package engine;

import chess.PieceType;
import chess.PlayerColor;

public abstract class Piece {
    public PlayerColor couleur;
    public PieceType pieceType;

    public Piece(PlayerColor couleur, PieceType pieceType){
        this.couleur = couleur;
        this.pieceType = pieceType;
    }
    public PlayerColor getCouleur() {
        return couleur;
    }



}

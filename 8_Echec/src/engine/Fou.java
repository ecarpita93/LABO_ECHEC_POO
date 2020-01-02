package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class Fou extends Piece  {
    public Fou(PlayerColor couleur, PieceType pieceType, Point position, int pieceID) {
        super(couleur, pieceType, position, pieceID);
    }

    @Override
    public boolean canMoveTo(int toX, int toY) {
       if (checkDiagonalMove(toX, toY)) {
           return true;
       }
       return false;
    }





}

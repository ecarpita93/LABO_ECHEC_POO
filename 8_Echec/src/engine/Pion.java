package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class Pion extends Piece  {
    public Pion(PlayerColor couleur, PieceType pieceType, Point position, int pieceID) {
        super(couleur, pieceType, position, pieceID);
    }
}

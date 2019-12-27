package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class Roi extends Piece  {
    public Roi(PlayerColor couleur, PieceType pieceType, Point position, int pieceID) {
        super(couleur, pieceType, position, pieceID);
    }
}

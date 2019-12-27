package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class Cavalier extends Piece  {
    public Cavalier(PlayerColor couleur, PieceType pieceType, Point position, int pieceID) {
        super(couleur, pieceType, position, pieceID);
    }
}

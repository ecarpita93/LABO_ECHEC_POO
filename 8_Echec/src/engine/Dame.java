package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class Dame extends Piece  {
    public Dame(PlayerColor couleur, PieceType pieceType, Point position, int pieceID) {
        super(couleur, pieceType, position, pieceID);
    }
}

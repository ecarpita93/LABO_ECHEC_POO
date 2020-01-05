package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class Cavalier extends Piece {

    private Point[] knightMatrix = {new Point(2, 1), new Point(2, -1), new Point(-2, -1), new Point(-2, 1), new Point(1, 2), new Point(1, -2), new Point(-1, -2), new Point(-1, 2)};

    public Cavalier(ChessBoard chessboard, PlayerColor couleur, PieceType pieceType, Point position, int pieceID) {
        super(chessboard, couleur, pieceType, position, pieceID);
    }

    @Override
    public void calculatePossibleMoves() {
        super.calculatePossibleMoves();
        checkMovesAndEatsMatrix(knightMatrix);
    }

}

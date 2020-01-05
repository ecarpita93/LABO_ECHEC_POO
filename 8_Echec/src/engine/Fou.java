package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class Fou extends Piece {
    public Fou(ChessBoard chessboard, PlayerColor couleur, PieceType pieceType, Point position, int pieceID) {
        super(chessboard, couleur, pieceType, position, pieceID);
    }

    @Override
    public void calculatePossibleMoves() {
        super.calculatePossibleMoves();
        checkDiagonalMovesAndEats();
    }

}

package engine.chessPieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.chessElements.ChessBoard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class King extends FirstMovePiece {

    private Point[] kingMoveMatrix = {new Point(0, 1), new Point(0, -1), new Point(1, 0), new Point(-1, 0), new Point(1, -1), new Point(-1, 1), new Point(1, 1), new Point(-1, -1)};
    private Point[] kingBigCastlingMatrix = {new Point(-3, 0)};
    private Point[] kingLittleCastlingMatrix = {new Point(2, 0)};

    public King(ChessBoard chessboard, PlayerColor player, PieceType piece_type, Point position) {
        super(chessboard, player, piece_type, position);
        chessboard.setPlayerKing(player, this);
    }

    public boolean isBigCastling(int toX) {
        System.out.println(position.getX() + " - " + toX + " = " + (Math.abs(position.getX() - toX)));
        return (Math.abs(position.getX() - toX) > 2);
    }

    public boolean isLittleCastling(int toX) {
        return (Math.abs(position.getX() - toX) > 1);
    }


    @Override
    public void calculatePossibleMoves() {
        super.calculatePossibleMoves();
        checkMovesAndEatsMatrix(kingMoveMatrix);

        if (chessboard.areBigCastlingPiecesInPosition(player)) {
            checkMovesAndEatsMatrix(kingBigCastlingMatrix);
        }

        if (chessboard.areLittleCastlingPiecesInPosition(player)) {
            checkMovesAndEatsMatrix(kingLittleCastlingMatrix);
        }

    }
}

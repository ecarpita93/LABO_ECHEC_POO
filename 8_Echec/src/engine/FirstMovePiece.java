package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class FirstMovePiece extends Piece {
    private boolean firstMove;

    public FirstMovePiece(ChessBoard chessboard, PlayerColor player, PieceType piece_type, Point position) {
        super(chessboard, player, piece_type, position);
        firstMove = true;
    }

    public boolean getFirstMove() {
        return firstMove;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }
}

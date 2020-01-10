package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public class FirstMovePiece extends Piece {

    private boolean firstMove;


    public FirstMovePiece(ChessBoard chessboard, PlayerColor player, PieceType piece_type, Point position, int piece_ID) {
        super(chessboard, player, piece_type, position, piece_ID);
        firstMove = true;
    }

    public boolean getFirstMove() {
        return firstMove;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }
}

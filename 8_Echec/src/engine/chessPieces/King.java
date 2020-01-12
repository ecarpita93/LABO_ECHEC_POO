package engine.chessPieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.chessElements.ChessBoard;

import java.awt.*;


public class King extends FirstMovePiece {

    private static final Point[] KING_MOVE_MATRIX = {new Point(0, 1), new Point(0, -1), new Point(1, 0), new Point(-1, 0), new Point(1, -1), new Point(-1, 1), new Point(1, 1), new Point(-1, -1)};
    private static final Point[] KING_BIG_CASTLING_MATRIX = {new Point(-1, 0), new Point(-1, 0), new Point(-1, 0)};
    private static final Point[] KING_LITTLE_CASTLING_MATRIX = {new Point(1, 0), new Point(1, 0)};
    private static final int BIG_CASTLING_OFFSET = 2;
    private static final int LITTLE_CASTLING_OFFSET = 1;

    public King(ChessBoard chessboard, PlayerColor player, PieceType piece_type, Point position) {
        super(chessboard, player, piece_type, position);
        chessboard.setPlayerKing(player, this);
    }

    public boolean isBigCastling(int toX) {
        return (Math.abs(position.getX() - toX) > BIG_CASTLING_OFFSET);
    }

    public boolean isLittleCastling(int toX) {
        return (Math.abs(position.getX() - toX) > LITTLE_CASTLING_OFFSET);
    }

    public static int getBigCastlingOffset() {
        return BIG_CASTLING_OFFSET;
    }

    public static int getLittleCastlingOffset() {
        return LITTLE_CASTLING_OFFSET;
    }

    private void checkCastlingMatrix(Point[] otherMatrix) {
        Piece obstacle;
        PlayerColor other_player = this.player == PlayerColor.BLACK ? PlayerColor.WHITE : PlayerColor.BLACK;
        Point tester = new Point(position);
        System.out.println();
        for (int i = 0; i < otherMatrix.length; i++) {
            tester.translate((int) otherMatrix[i].getX(), (int) otherMatrix[i].getY());
            if (chessboard.checkPositionInBoardLimits(tester)) {
                obstacle = chessboard.getPieceAtPosition((int) tester.getX(), (int) tester.getY());
                if (obstacle == null) {
                    if (chessboard.isPieceInDangerAtPosition(tester, chessboard.getPlayerPieces(other_player))) {
                        return;
                    }
                    if (i == otherMatrix.length - 1) {
                        possible_moves.add(new Point(tester));
                    }
                } else {
                    return;
                }
            }
        }

    }

    @Override
    public void calculatePossibleMoves() {
        super.calculatePossibleMoves();
        checkMovesAndEatsMatrix(KING_MOVE_MATRIX);

        if (chessboard.areBigCastlingPiecesInPosition(player)) {
            checkCastlingMatrix(KING_BIG_CASTLING_MATRIX);
        }

        if (chessboard.areLittleCastlingPiecesInPosition(player)) {
            checkCastlingMatrix(KING_LITTLE_CASTLING_MATRIX);
        }
    }


}

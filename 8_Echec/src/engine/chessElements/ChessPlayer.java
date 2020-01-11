package engine.chessElements;

import engine.chessPieces.FirstMovePiece;
import engine.chessPieces.Piece;

import java.util.ArrayList;

public class ChessPlayer {

    private FirstMovePiece king;
    private FirstMovePiece little_castling_rook;
    private FirstMovePiece big_castling_rook;
    private ArrayList<Piece> pieces;
    private boolean check;

    ChessPlayer() {
        pieces = new ArrayList<>();
    }

    void clearPiecesList() {
        pieces.clear();
        little_castling_rook = null;
        big_castling_rook = null;
        king = null;
    }

    void addPieceList(Piece piece) {
        pieces.add(piece);
    }

    void removePieceList(Piece piece) {
        pieces.remove(piece);
    }

    ArrayList<Piece> getPieces() {
        return pieces;
    }

    Piece getKing() {
        return king;
    }

    void setKing(FirstMovePiece king) {
        this.king = king;
    }

    void setLittleCastlingRook(FirstMovePiece little_castling_Rook) {
        this.little_castling_rook = little_castling_Rook;
    }

    void setBigCastlingRook(FirstMovePiece big_castling_Rook) {
        this.big_castling_rook = big_castling_Rook;
    }

    boolean getCheck() {
        return check;
    }

    void setCheck(boolean check) {
        this.check = check;
    }


    boolean areLittleCastlingPiecesInPosition() {
        if (king.getFirstMove()) {
            return little_castling_rook.getFirstMove();
        }
        return false;
    }

    boolean areBigCastlingPiecesInPosition() {
        if (king.getFirstMove()) {
            return big_castling_rook.getFirstMove();
        }
        return false;
    }

    public FirstMovePiece getLittleCastlingRook() {
        return little_castling_rook;
    }

    public FirstMovePiece getBigCastlingRook() {
        return big_castling_rook;
    }


}

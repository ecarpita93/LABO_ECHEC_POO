package engine;

import java.util.ArrayList;

public class ChessPlayer {

    private FirstMovePiece king;
    private FirstMovePiece little_castling_rook;
    private FirstMovePiece big_castling_rook;
    private ArrayList<Piece> pieces;
    private boolean check;

    public ChessPlayer() {
        pieces = new ArrayList<>();
    }

    public void addPieceList(Piece piece) {
            pieces.add(piece);
    }

    public void removePieceList(Piece piece) {
            pieces.remove(piece);
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public Piece getKing() {
        return king;
    }

    public void setKing(FirstMovePiece king) {
        this.king = king;
    }

    public void setLittleCastlingRook(FirstMovePiece little_castling_Rook) {
        this.little_castling_rook = little_castling_Rook;
    }

    public void setBigCastlingRook(FirstMovePiece big_castling_Rook) {
        this.big_castling_rook = big_castling_Rook;
    }

    public boolean getCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }


    public FirstMovePiece getLittleCastlingRook() {
        return little_castling_rook;
    }

    public FirstMovePiece getBigCastlingRook() {
        return big_castling_rook;
    }

    public boolean areLittleCastlingPiecesInPosition() {
        if (king.getFirstMove()){
            return little_castling_rook.getFirstMove();
        }
        return false;
    }


    public boolean areBigCastlingPiecesInPosition() {
        if (king.getFirstMove()){
            return big_castling_rook.getFirstMove();
        }
        return false;
    }

}

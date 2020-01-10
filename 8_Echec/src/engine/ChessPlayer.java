package engine;

import java.util.ArrayList;

public class ChessPlayer {

    private Piece king;
    private ArrayList<Piece> pieces;
    private boolean check;
    private boolean little_castling_possible;
    private boolean big_castling_possible;

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

    public void setKing(Piece king) {
        this.king = king;
    }

    public boolean getCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean getLittleCastlingPossible() {
        return little_castling_possible;
    }

    public void setLittleCastlingPossible(boolean little_castling_possible) {
        this.little_castling_possible = little_castling_possible;
    }

    public boolean getBigCastlingPossible() {
        return big_castling_possible;
    }

    public void setBigCastlingPossible(boolean big_castling_possible) {
        this.big_castling_possible = big_castling_possible;
    }


}

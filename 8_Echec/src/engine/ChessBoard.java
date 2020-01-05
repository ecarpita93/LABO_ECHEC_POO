package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class ChessBoard {

    private int game_turns;
    private Piece[][] game_board;
    private Piece white_king;
    private Piece black_king;
    private ArrayList<Piece> white_pieces;
    private ArrayList<Piece> black_pieces;
    private ChessView view;


    public ChessBoard() {
        game_turns = 0;
        game_board = new Piece[8][8];
        white_pieces = new ArrayList<Piece>();
        black_pieces = new ArrayList<Piece>();
    }


    public void initStandardBoard() {

        Piece piece;

        piece = new Tour(this, PlayerColor.WHITE, PieceType.ROOK, new Point(0, 0), 1);
        piece = new Tour(this, PlayerColor.WHITE, PieceType.ROOK, new Point(7, 0), 2);

        piece = new Cavalier(this, PlayerColor.WHITE, PieceType.KNIGHT, new Point(1, 0), 1);
        piece = new Cavalier(this, PlayerColor.WHITE, PieceType.KNIGHT, new Point(6, 0), 2);

        piece = new Fou(this, PlayerColor.WHITE, PieceType.BISHOP, new Point(2, 0), 1);
        piece = new Fou(this, PlayerColor.WHITE, PieceType.BISHOP, new Point(5, 0), 2);

        piece = new Dame(this, PlayerColor.WHITE, PieceType.QUEEN, new Point(3, 0), 1);
        piece = new Roi(this, PlayerColor.WHITE, PieceType.KING, new Point(4, 0), 1);

        for (int i = 0; i < 8; i++) {
            piece = new Pion(this, PlayerColor.WHITE, PieceType.PAWN, new Point(i, 1), i);
        }

        piece = new Tour(this, PlayerColor.BLACK, PieceType.ROOK, new Point(0, 7), 1);
        piece = new Tour(this, PlayerColor.BLACK, PieceType.ROOK, new Point(7, 7), 2);

        piece = new Cavalier(this, PlayerColor.BLACK, PieceType.KNIGHT, new Point(1, 7), 1);
        piece = new Cavalier(this, PlayerColor.BLACK, PieceType.KNIGHT, new Point(6, 7), 2);

        piece = new Fou(this, PlayerColor.BLACK, PieceType.BISHOP, new Point(2, 7), 1);
        piece = new Fou(this, PlayerColor.BLACK, PieceType.BISHOP, new Point(5, 7), 2);

        piece = new Dame(this, PlayerColor.BLACK, PieceType.QUEEN, new Point(3, 7), 1);
        piece = new Roi(this, PlayerColor.BLACK, PieceType.KING, new Point(4, 7), 1);

        for (int i = 0; i < 8; i++) {
            piece = new Pion(this, PlayerColor.BLACK, PieceType.PAWN, new Point(i, 6), i);
        }
    }

    public boolean checkPositionInBoardLimits(Point position){
        return (position.getX() >= 0 && position.getX() < 8) && (position.getY() >= 0 && position.getY() < 8);

    }
    public Piece getPieceAtPosition(int x, int y) {
        return game_board[x][y];
    }

    public void setPieceAtPosition(Piece piece, int x, int y) {
        game_board[x][y] = piece;
        view.putPiece(piece.getPieceType(), piece.getPlayer(), piece.getPosition().x, piece.getPosition().y);
    }

    public void removePieceFromPosition(int x, int y) {
        game_board[x][y] = null;
        view.removePiece(x, y);
    }


    public void addPieceList(Piece piece) {
        if (piece.getPlayer() == PlayerColor.BLACK) {
            black_pieces.add(piece);
        } else {
            white_pieces.add(piece);
        }
    }


    public void removePieceList(Piece piece) {
        if (piece.getPlayer() == PlayerColor.BLACK) {
            black_pieces.remove(piece);
        } else {
            white_pieces.remove(piece);
        }
    }

    public ArrayList<Piece> getWhite_pieces() {
        return white_pieces;
    }

    public void setWhite_pieces(ArrayList<Piece> white_pieces) {
        this.white_pieces = white_pieces;
    }

    public ArrayList<Piece> getBlack_pieces() {
        return black_pieces;
    }

    public void setBlack_pieces(ArrayList<Piece> black_pieces) {
        this.black_pieces = black_pieces;
    }


    public Piece getWhite_king() {
        return white_king;
    }

    public void setWhite_king(Piece white_king) {
        this.white_king = white_king;
    }

    public Piece getBlack_king() {
        return black_king;
    }

    public void setBlack_king(Piece black_king) {
        this.black_king = black_king;
    }

    public ChessView getView() {
        return view;
    }

    public void setView(ChessView view) {
        this.view = view;
    }

    public int getGameTurn() {
        return game_turns;
    }

    public void incrementGameTurn() {
        game_turns++;
    }
}

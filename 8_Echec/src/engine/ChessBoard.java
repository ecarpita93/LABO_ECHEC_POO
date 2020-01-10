package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;
import java.util.ArrayList;

public class ChessBoard {

    private Piece[][] game_board;
    private ChessPlayer[] players;
    private ChessView view;

    private ArrayList<Point> checkPath;


    public ChessBoard() {
        players = new ChessPlayer[]{new ChessPlayer(), new ChessPlayer()};
        game_board = new Piece[8][8];
    }


    public void initStandardBoard() {

        Piece piece;

        piece = new Rook(this, PlayerColor.WHITE, PieceType.ROOK, new Point(0, 0));
        players[PlayerColor.WHITE.ordinal()].setBigCastlingRook((FirstMovePiece) piece);
        piece = new Rook(this, PlayerColor.WHITE, PieceType.ROOK, new Point(7, 0));
        players[PlayerColor.WHITE.ordinal()].setLittleCastlingRook((FirstMovePiece) piece);
        piece = new Knight(this, PlayerColor.WHITE, PieceType.KNIGHT, new Point(1, 0));
        piece = new Knight(this, PlayerColor.WHITE, PieceType.KNIGHT, new Point(6, 0));

        piece = new Bishop(this, PlayerColor.WHITE, PieceType.BISHOP, new Point(2, 0));
        piece = new Bishop(this, PlayerColor.WHITE, PieceType.BISHOP, new Point(5, 0));

        piece = new Queen(this, PlayerColor.WHITE, PieceType.QUEEN, new Point(3, 0));
        piece = new King(this, PlayerColor.WHITE, PieceType.KING, new Point(4, 0));

        for (int i = 0; i < 8; i++) {
             piece = new Pawn(this, PlayerColor.WHITE, PieceType.PAWN, new Point(i, 1));
        }

        piece = new Rook(this, PlayerColor.BLACK, PieceType.ROOK, new Point(0, 7));
        players[PlayerColor.BLACK.ordinal()].setBigCastlingRook((FirstMovePiece) piece);

        piece = new Rook(this, PlayerColor.BLACK, PieceType.ROOK, new Point(7, 7));
        players[PlayerColor.BLACK.ordinal()].setLittleCastlingRook((FirstMovePiece) piece);

        piece = new Knight(this, PlayerColor.BLACK, PieceType.KNIGHT, new Point(1, 7));
        piece = new Knight(this, PlayerColor.BLACK, PieceType.KNIGHT, new Point(6, 7));

        piece = new Bishop(this, PlayerColor.BLACK, PieceType.BISHOP, new Point(2, 7));
        piece = new Bishop(this, PlayerColor.BLACK, PieceType.BISHOP, new Point(5, 7));

        piece = new Queen(this, PlayerColor.BLACK, PieceType.QUEEN, new Point(3, 7));
        piece = new King(this, PlayerColor.BLACK, PieceType.KING, new Point(4, 7));

        for (int i = 0; i < 8; i++) {
              piece = new Pawn(this, PlayerColor.BLACK, PieceType.PAWN, new Point(i, 6));
        }

        updateBoardMoves();

    }

    public void updateBoardMoves() {
        for (ChessPlayer player : players) {
            for (Piece p : player.getPieces()) {
                p.calculatePossibleMoves();
            }
        }
    }

    public boolean checkPositionInBoardLimits(Point position) {
        return (position.getX() >= 0 && position.getX() < 8) && (position.getY() >= 0 && position.getY() < 8);
    }

    public Piece getPieceAtPosition(int x, int y) {
        return game_board[x][y];
    }

    public void setPieceAtPosition(Piece piece, int toX, int toY) {

        piece.setPosition(new Point(toX, toY));
        game_board[toX][toY] = piece;

        if (piece instanceof FirstMovePiece) {
            if (((FirstMovePiece) piece).getFirstMove()) {
                ((FirstMovePiece) piece).setFirstMove(false);
            }
        }

        view.putPiece(piece.getPiece_type(), piece.getPlayer(), (int) piece.getPosition().getX(), (int) piece.getPosition().getY());
    }



    public void removePieceFromPosition(int x, int y) {
        game_board[x][y] = null;
        view.removePiece(x, y);
    }

    public void addPieceList(Piece piece) {
        players[piece.getPlayer().ordinal()].addPieceList(piece);

    }

    public void removePieceList(Piece piece) {
        players[piece.getPlayer().ordinal()].removePieceList(piece);
    }

//    public void calculateCheckPath(Piece king_under_check, Piece opponent_piece){
//        checkPath.add(new Point((int)opponent_piece.getPosition().getX(), (int)opponent_piece.getPosition().getY())); // the first value is the same as the origin
//    }


    public ArrayList<Piece> getPlayerPieces(PlayerColor player) {
        return players[player.ordinal()].getPieces();
    }

    public Piece getPlayerKing(PlayerColor player) {
        return players[player.ordinal()].getKing();

    }

    public void setPlayerKing(PlayerColor player, Piece king) {
        players[player.ordinal()].setKing((FirstMovePiece) king);
    }

    public ChessView getView() {
        return view;
    }

    public void setView(ChessView view) {
        this.view = view;
    }


    public void setCheck(PlayerColor player_under_check, boolean check) {
        players[player_under_check.ordinal()].setCheck(check);
    }

    public boolean getCheck(PlayerColor player) {
        return players[player.ordinal()].getCheck();
    }

    public boolean areLittleCastlingPiecesInPosition(PlayerColor player) {
        return players[player.ordinal()].areLittleCastlingPiecesInPosition();
    }

    public boolean areBigCastlingPiecesInPosition(PlayerColor player) {
        return players[player.ordinal()].areBigCastlingPiecesInPosition();
    }

    public ChessPlayer[] getPlayers() {
        return players;
    }

}

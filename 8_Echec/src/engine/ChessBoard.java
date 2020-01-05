package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;
import java.util.ArrayList;

public class ChessBoard {

    private Piece[][] game_board;
    private Piece white_king;
    private Piece black_king;
    private ArrayList<Piece> white_pieces;
    private ArrayList<Piece> black_pieces;
    private boolean white_check;
    private boolean black_check;
    private ChessView view;
    private ArrayList<Point> checkPath;


    public ChessBoard() {
        game_board = new Piece[8][8];
        white_pieces = new ArrayList<Piece>();
        black_pieces = new ArrayList<Piece>();
    }


    public void initStandardBoard() {

        Piece piece;

        piece = new Rook(this, PlayerColor.WHITE, PieceType.ROOK, new Point(0, 0), 1);
        piece = new Rook(this, PlayerColor.WHITE, PieceType.ROOK, new Point(7, 0), 2);

        piece = new Knight(this, PlayerColor.WHITE, PieceType.KNIGHT, new Point(1, 0), 1);
        piece = new Knight(this, PlayerColor.WHITE, PieceType.KNIGHT, new Point(6, 0), 2);

        piece = new Bishop(this, PlayerColor.WHITE, PieceType.BISHOP, new Point(2, 0), 1);
        piece = new Bishop(this, PlayerColor.WHITE, PieceType.BISHOP, new Point(5, 0), 2);

        piece = new Queen(this, PlayerColor.WHITE, PieceType.QUEEN, new Point(3, 0), 1);
        piece = new King(this, PlayerColor.WHITE, PieceType.KING, new Point(4, 0), 1);

        for (int i = 0; i < 8; i++) {
            piece = new Pawn(this, PlayerColor.WHITE, PieceType.PAWN, new Point(i, 1), i);
        }

        piece = new Rook(this, PlayerColor.BLACK, PieceType.ROOK, new Point(0, 7), 1);
        piece = new Rook(this, PlayerColor.BLACK, PieceType.ROOK, new Point(7, 7), 2);

        piece = new Knight(this, PlayerColor.BLACK, PieceType.KNIGHT, new Point(1, 7), 1);
        piece = new Knight(this, PlayerColor.BLACK, PieceType.KNIGHT, new Point(6, 7), 2);

        piece = new Bishop(this, PlayerColor.BLACK, PieceType.BISHOP, new Point(2, 7), 1);
        piece = new Bishop(this, PlayerColor.BLACK, PieceType.BISHOP, new Point(5, 7), 2);

        piece = new Queen(this, PlayerColor.BLACK, PieceType.QUEEN, new Point(3, 7), 1);
        piece = new King(this, PlayerColor.BLACK, PieceType.KING, new Point(4, 7), 1);

        for (int i = 0; i < 8; i++) {
            piece = new Pawn(this, PlayerColor.BLACK, PieceType.PAWN, new Point(i, 6), i);
        }

        updateBoardMoves();

    }

    public void updateBoardMoves() {
        for (Piece p : white_pieces) {
            p.calculatePossibleMoves();
        }
        for (Piece p : black_pieces) {
            p.calculatePossibleMoves();
        }
    }

    public boolean checkPositionInBoardLimits(Point position) {
        return (position.getX() >= 0 && position.getX() < 8) && (position.getY() >= 0 && position.getY() < 8);
    }

    public Piece getPieceAtPosition(int x, int y) {
        return game_board[x][y];
    }

    public void setPieceAtPosition(Piece piece, int x, int y) {
        game_board[x][y] = piece;
        if (piece instanceof Pawn) {
            if (((Pawn) piece).getFirstMove()) {
                ((Pawn) piece).setFirstMove(false);
            }
            if (((Pawn) piece).canBePromoted()) {
                promotePawn((Pawn) piece);
            }
        }
        view.putPiece(piece.getPiece_type(), piece.getPlayer(), piece.getPosition().x, piece.getPosition().y);
    }

    public void promotePawn(Pawn pawn_to_promote) {
        ChessView.UserChoice user = new ChessView.UserChoice() {
            @Override
            public String textValue() {
                return "king me!";
            }
        };
        System.out.println("promoting");
        view.askUser("Pawn prmottion", "how should be promoted", user);
        System.out.println("promoting");

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

//    public void calculateCheckPath(Piece king_under_check, Piece opponent_piece){
//        checkPath.add(new Point((int)opponent_piece.getPosition().getX(), (int)opponent_piece.getPosition().getY())); // the first value is the same as the origin
//    }

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


    public void setCheck(PlayerColor player_under_check, boolean check) {
        if (player_under_check == PlayerColor.WHITE) {
            this.white_check = check;
        } else {
            this.black_check = check;
        }
    }

    public boolean getCheck(PlayerColor player) {
        if (player == PlayerColor.WHITE) {
            return white_check;
        } else {
            return black_check;
        }
    }

}

package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import engine.chessElements.ChessBoard;
import engine.chessPieces.King;
import engine.chessPieces.Pawn;
import engine.chessPieces.Piece;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class GameController implements ChessController {

    private int game_turn;
    private PlayerColor current_player;
    private Stack game_history;
    private ChessView view;
    private ChessBoard chessboard;

    public GameController() {
        chessboard = new ChessBoard();
    }

    public void initGame() {
        game_turn = 0;
        chessboard.setView(view);
        chessboard.initStandardBoard();
    }

    public void endGame() {
        chessboard.clearGameBoard();
        chessboard.clearPlayers();
    }

    private boolean checkForCheck() {
        PlayerColor other_player;
        Piece other_player_king;
        ArrayList<Piece> current_player_pieces;

        if (current_player == PlayerColor.BLACK) {
            other_player = PlayerColor.WHITE;
        } else {
            other_player = PlayerColor.BLACK;
        }

        other_player_king = chessboard.getPlayerKing(other_player);
        current_player_pieces = chessboard.getPlayerPieces(current_player);

        for (Piece piece : current_player_pieces) {
            for (Point possible_eats : piece.getPossibleEats()) {
                if (other_player_king.getPosition().getX() == possible_eats.getX() && other_player_king.getPosition().getY() == possible_eats.getY()) {
                    chessboard.setCheck(other_player, true);
                    //chessboard.calculateCheckPath(other_player_king, piece);
                    return true;
                }
            }
        }
        chessboard.setCheck(other_player, false);
        return false;

    }

    private void checkForCastling(King king, int toX) {
        if (king.isBigCastling(toX)) {
            doBigCastling(king);
        } else if (king.isLittleCastling(toX)) {
            doLittleCastling(king);
        }
    }

    private void checkForPawnPromotion(Pawn pawn) {
        if (pawn.canBePromoted()) {
            doPromotion(pawn);
        }
    }

    private void doPromotion(Pawn pawn_to_promote) {
//        ChessView.UserChoice user = new ChessView.UserChoice() {
//            @Override
//            public String textValue() {
//                return "king me!";
//            }
//        };
        System.out.println("promoting");
        //  view.askUser("Pawn prmottion", "how should be promoted", user);
        //   System.out.println("promoting");

    }

    private void doBigCastling(King king_castling) {
        Piece rook = chessboard.getPlayers()[king_castling.getPlayer().ordinal()].getBigCastlingRook();
        doMove(rook, (int) king_castling.getPosition().getX() - 2, (int) king_castling.getPosition().getY());
        game_turn--;

    }

    private void doLittleCastling(King king_castling) {
        Piece rook = chessboard.getPlayers()[king_castling.getPlayer().ordinal()].getLittleCastlingRook();
        doMove(rook, (int) king_castling.getPosition().getX() + 1, (int) king_castling.getPosition().getY());
        game_turn--;
    }

    private void doMove(Piece piece_to_move, int toX, int toY) {

        chessboard.removePieceFromPosition((int) piece_to_move.getPosition().getX(), (int) piece_to_move.getPosition().getY());

        if (piece_to_move instanceof King) {
            checkForCastling((King) piece_to_move, toX);
        }

        chessboard.setPieceAtPosition(piece_to_move, toX, toY);

        if (piece_to_move instanceof Pawn) {
            checkForPawnPromotion((Pawn) piece_to_move);
        }

        chessboard.updateBoardMoves();


        if (checkForCheck()) {
            view.displayMessage("Check!");
            chessboard.updateBoardMoves();
        }

        game_turn++;
    }

    private void doMoveAndEat(Piece piece_to_move, Piece piece_to_eat, int toX, int toY) {
        piece_to_eat.removePieceFromGame();
        doMove(piece_to_move, toX, toY);
    }


    @Override
    public void start(ChessView view) {
        this.view = view;
        this.view.startView();
        initGame();
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {

        current_player = PlayerColor.values()[game_turn % 2];

        Piece piece_to_move = chessboard.getPieceAtPosition(fromX, fromY);
        Piece piece_at_new_position = chessboard.getPieceAtPosition(toX, toY);

        if (piece_to_move != null && piece_to_move.getPlayer() == current_player) {

            if (piece_at_new_position != null) {
                if (piece_to_move.canEatTo(toX, toY)) {
                    doMoveAndEat(piece_to_move, piece_at_new_position, toX, toY);
                    return true;
                }
            } else {
                if (piece_to_move.canMoveTo(toX, toY)) {
                    doMove(piece_to_move, toX, toY);
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public void newGame() {
        endGame();
        initGame();
    }

}

package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import chess.views.console.ConsoleView;
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
    private PlayerColor other_player;
    private Stack game_history;
    private ChessView view;
    private ChessBoard chessboard;
    private Piece piece_to_be_eliminated_if_valid_move;
    private final Promotion[] promotion_possibilities = new Promotion[]{new Promotion(PieceType.QUEEN),
                                                                        new Promotion(PieceType.BISHOP),
                                                                        new Promotion(PieceType.ROOK),
                                                                        new Promotion(PieceType.KNIGHT)};

    private void initGame() {
        game_turn = 0;
        chessboard = new ChessBoard();
        piece_to_be_eliminated_if_valid_move = null;
        chessboard.setView(view);
        chessboard.initStandardBoard();
    }

    private void endGame() {
        chessboard.clearGameBoard();
        chessboard.clearPlayers();
    }

    private boolean checkForFriendlyCheck() {
        Piece current_player_king;
        ArrayList<Piece> other_player_pieces;

        current_player_king = chessboard.getPlayerKing(current_player);
        other_player_pieces = chessboard.getPlayerPieces(other_player);

        if (chessboard.isPieceInDangerAtPosition(current_player_king.getPosition(), other_player_pieces)) {
            System.out.println("our king is in danger, not possible to move here");
            return true;
        }
        return false;

    }

    private boolean checkForEnemyCheck() {
        Piece other_player_king;
        ArrayList<Piece> current_player_pieces;

        other_player_king = chessboard.getPlayerKing(other_player);
        current_player_pieces = chessboard.getPlayerPieces(current_player);

        if (chessboard.isPieceInDangerAtPosition(other_player_king.getPosition(), current_player_pieces)) {
            view.displayMessage(other_player + " king is in check!");
            chessboard.setCheck(other_player, true);
            return true;
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
        Promotion choice = view.askUser("Pawn promotion", "How should be promoted your pawn? ", promotion_possibilities);
        Point position = pawn_to_promote.getPosition();
        pawn_to_promote.removePieceFromGame();
        chessboard.addPromotedPiece(current_player, choice.getPromoteTo(), position);
    }

    private void doBigCastling(King king_castling) {
        Piece rook = chessboard.getPlayers()[king_castling.getPlayer().ordinal()].getBigCastlingRook();
        doMove(rook, (int) rook.getPosition().getX(), (int) rook.getPosition().getY(), (int) king_castling.getPosition().getX() - 2, (int) king_castling.getPosition().getY());
        game_turn--;

    }

    private void doLittleCastling(King king_castling) {
        Piece rook = chessboard.getPlayers()[king_castling.getPlayer().ordinal()].getLittleCastlingRook();
        doMove(rook, (int) rook.getPosition().getX(), (int) rook.getPosition().getY(), (int) king_castling.getPosition().getX() + 1, (int) king_castling.getPosition().getY());
        game_turn--;
    }


    private void unDoMove(Piece piece_to_move, int fromX, int fromY, int toX, int toY) {

        chessboard.removePieceFromPosition((int) piece_to_move.getPosition().getX(), (int) piece_to_move.getPosition().getY());
        chessboard.setPieceAtPosition(piece_to_move, toX, toY);

        if (piece_to_be_eliminated_if_valid_move != null) {
            chessboard.setPieceAtPosition(piece_to_be_eliminated_if_valid_move, fromX, fromY);
            piece_to_be_eliminated_if_valid_move = null;
        }

        chessboard.updateBoardMoves();
    }

    private boolean doMove(Piece piece_to_move, int fromX, int fromY, int toX, int toY) {

        chessboard.removePieceFromPosition((int) piece_to_move.getPosition().getX(), (int) piece_to_move.getPosition().getY());

        if (piece_to_move instanceof King) {
            checkForCastling((King) piece_to_move, toX);
        }

        chessboard.setPieceAtPosition(piece_to_move, toX, toY);

        if (piece_to_move instanceof Pawn) {
            checkForPawnPromotion((Pawn) piece_to_move);
        }

        chessboard.updateBoardMoves();

        if (checkForFriendlyCheck()) {
            unDoMove(piece_to_move, toX, toY, fromX, fromY);
            return false;
        } else {

            if (piece_to_be_eliminated_if_valid_move != null) {
                piece_to_be_eliminated_if_valid_move.removePieceFromGame();
                piece_to_be_eliminated_if_valid_move = null;
            }

            if (checkForEnemyCheck()) {
                chessboard.updateBoardMoves();
            }

            game_turn++;
            return true;
        }
    }

    private boolean doMoveAndEat(Piece piece_to_move, Piece piece_to_eat, int fromX, int fromY, int toX, int toY) {
        piece_to_be_eliminated_if_valid_move = piece_to_eat;
        return doMove(piece_to_move, fromX, fromY, toX, toY);
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
        other_player = PlayerColor.values()[(game_turn + 1) % 2];

        Piece piece_to_move = chessboard.getPieceAtPosition(fromX, fromY);
        Piece piece_at_new_position = chessboard.getPieceAtPosition(toX, toY);

        if (piece_to_move != null && piece_to_move.getPlayer() == current_player) {

            if (piece_at_new_position != null) {
                if (piece_to_move.canEatTo(toX, toY)) {
                    return doMoveAndEat(piece_to_move, piece_at_new_position, fromX, fromY, toX, toY);
                }
            } else {
                if (piece_to_move.canMoveTo(toX, toY)) {
                    return doMove(piece_to_move, fromX, fromY, toX, toY);
                }
            }

        }
        return false;
    }

    @Override
    public void newGame() {
       if (chessboard != null) {
           endGame();
       }
        initGame();
    }


    public class Promotion implements ChessView.UserChoice {

        private PieceType promote_to;

        Promotion(PieceType possible_promotion) {
            promote_to = possible_promotion;
        }

        PieceType getPromoteTo() {
            return promote_to;
        }

        @Override
        public String textValue() {
            return promote_to.name();
        }
    }

}

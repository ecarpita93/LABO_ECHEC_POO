package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class GameController implements ChessController {

    private int game_turn;
    private PlayerColor current_player;
    private Stack game_history;  // Pour avoir l'historic des coups
    private ChessView view;
    private ChessBoard chessboard;

    public GameController() {
        game_turn = 0;
        chessboard = new ChessBoard();
    }

    public void clearBoard(ChessView view) {
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                view.removePiece(x, y);
            }
        }
    }

    public void initGame(ChessView view) {
        chessboard.setView(view);
        chessboard.initStandardBoard();
    }

    private void simpleMove(Piece piece_to_move, int fromX, int fromY, int toX, int toY) {
        piece_to_move.setPosition(new Point(toX, toY));
        chessboard.removePieceFromPosition(fromX, fromY);
        chessboard.setPieceAtPosition(piece_to_move, toX, toY);
        chessboard.updateBoardMoves();

        if (checkForCheck()){
            view.displayMessage("Check!");
            chessboard.updateBoardMoves();

        }

        game_turn++;
    }

    private void moveAndEat(Piece piece_to_move, Piece piece_to_eat, int fromX, int fromY, int toX, int toY) {
        piece_to_eat.removePieceFromGame();
        simpleMove(piece_to_move, fromX, fromY, toX, toY);
    }

    private boolean checkForCheck(){
        PlayerColor other_player;
        Piece other_player_king;
        ArrayList<Piece> current_player_pieces;

        if (current_player == PlayerColor.BLACK){
            other_player = PlayerColor.WHITE;
            other_player_king = chessboard.getWhite_king();
            current_player_pieces = chessboard.getBlack_pieces();
        } else {
            other_player = PlayerColor.BLACK;
            other_player_king = chessboard.getBlack_king();
            current_player_pieces = chessboard.getWhite_pieces();
        }

        for (Piece piece : current_player_pieces){
            for (Point possible_eats : piece.getPossibleEats()){
                if (other_player_king.getPosition().getX() == possible_eats.getX() && other_player_king.getPosition().getY() == possible_eats.getY()){
                    chessboard.setCheck(other_player, true);
                    return true;
                }
            }
        }
        chessboard.setCheck(other_player, false);
        return false;

    }
    @Override
    public void start(ChessView view) {
        this.view = view;
        this.view.startView();
        initGame(this.view);
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        current_player = PlayerColor.values()[game_turn % 2];

        Piece piece_to_move = chessboard.getPieceAtPosition(fromX, fromY);
        Piece piece_at_new_position = chessboard.getPieceAtPosition(toX, toY);

        if (piece_to_move != null && piece_to_move.getPlayer() == current_player) {

            if (piece_at_new_position != null) {
                if (piece_to_move.canEatTo(toX, toY)) {
                    moveAndEat(piece_to_move, piece_at_new_position, fromX, fromY, toX, toY);
                    return true;
                }
            } else {
                if (piece_to_move.canMoveTo(toX, toY)) {
                    simpleMove(piece_to_move, fromX, fromY, toX, toY);
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public void newGame() {
        clearBoard(view);
        initGame(view);
    }

}

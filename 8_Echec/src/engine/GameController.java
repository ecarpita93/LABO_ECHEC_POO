package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;

import java.awt.*;
import java.util.Stack;

public class GameController implements ChessController {

    private Stack game_history;  // Pour avoir l'historic des coups
    private ChessView view;
    private ChessBoard chessboard;

    public GameController() {
        chessboard = new ChessBoard();
    }

    // Clear board
    public void clearBoard(ChessView view) {
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                view.removePiece(x, y);
            }
        }
    }

    // Init game
    public void init_Game(ChessView view) {
        chessboard.setView(view);
        chessboard.initStandardBoard();
    }

    @Override
    public void start(ChessView view) {
        this.view = view;
        this.view.startView();
        init_Game(this.view);
    }


    private void simpleMove(Piece piece_to_move, int fromX, int fromY, int toX, int toY) {
        piece_to_move.setPosition(new Point(toX, toY));
        chessboard.removePieceFromPosition(fromX, fromY);
        chessboard.setPieceAtPosition(piece_to_move, toX, toY);
        chessboard.incrementGameTurn();
    }

    private void moveAndEat(Piece piece_to_move, Piece piece_to_eat, int fromX, int fromY, int toX, int toY) {
        piece_to_eat.removePieceFromGame();
        simpleMove(piece_to_move, fromX, fromY, toX, toY);
    }


    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {

        PlayerColor currentPlayer = PlayerColor.values()[chessboard.getGameTurn() % 2];
        Piece piece_to_move = chessboard.getPieceAtPosition(fromX, fromY);
        Piece piece_at_new_position = chessboard.getPieceAtPosition(toX, toY);

        if (piece_to_move != null && piece_to_move.getPlayer() == currentPlayer) {
            if (piece_at_new_position != null) {
                if (piece_at_new_position.getPlayer() != currentPlayer && piece_to_move.canEatTo(piece_at_new_position)) {
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
        init_Game(view);
    }

}

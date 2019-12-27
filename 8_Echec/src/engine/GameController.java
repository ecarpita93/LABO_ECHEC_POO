package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

import java.util.Stack;

public class GameController implements ChessController {

    private Stack coupJouee = new Stack();
    ChessView view;

    // Clear board
    public void clearBoard(ChessView view){
        for (int x = 0; x < 7; x++){
            for (int y = 0; y < 7; y++){
                view.removePiece(x, y);
            }
        }

    }
    // Init game
    public void init_Game(ChessView view){
        coupJouee.empty();
        // Les blancs en place
        view.putPiece(PieceType.ROOK, PlayerColor.WHITE, 0, 0);
        view.putPiece(PieceType.ROOK, PlayerColor.WHITE, 7, 0);
        view.putPiece(PieceType.KNIGHT, PlayerColor.WHITE, 1, 0);
        view.putPiece(PieceType.KNIGHT, PlayerColor.WHITE, 6, 0);
        view.putPiece(PieceType.BISHOP, PlayerColor.WHITE, 2, 0);
        view.putPiece(PieceType.BISHOP, PlayerColor.WHITE, 5, 0);
        view.putPiece(PieceType.QUEEN, PlayerColor.WHITE, 3, 0);
        view.putPiece(PieceType.KING, PlayerColor.WHITE, 4, 0);
        // Pions blancs et noir
        for (int i = 0; i < 8; i++){
            view.putPiece(PieceType.PAWN, PlayerColor.WHITE, i, 1);
            view.putPiece(PieceType.PAWN, PlayerColor.BLACK, i, 6);
        }
        // Les noirs en place
        view.putPiece(PieceType.ROOK, PlayerColor.BLACK, 0, 7);
        view.putPiece(PieceType.ROOK, PlayerColor.BLACK, 7, 7);
        view.putPiece(PieceType.KNIGHT, PlayerColor.BLACK, 1, 7);
        view.putPiece(PieceType.KNIGHT, PlayerColor.BLACK, 6, 7);
        view.putPiece(PieceType.BISHOP, PlayerColor.BLACK, 2, 7);
        view.putPiece(PieceType.BISHOP, PlayerColor.BLACK, 5, 7);
        view.putPiece(PieceType.QUEEN, PlayerColor.BLACK, 3, 7);
        view.putPiece(PieceType.KING, PlayerColor.BLACK, 4, 7);
    }
    @Override
    public void start(ChessView view) {
        this.view = view;
        this.view.startView();
        init_Game(this.view);
        boolean moveOk = move(0, 0, 0, 5);
        view.putPiece(PieceType.ROOK, PlayerColor.WHITE, 0, 5);
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        view.removePiece(fromX, fromY);

        return true;
    }

    @Override
    public void newGame() {
        clearBoard(view);
        init_Game(view);
    }

}

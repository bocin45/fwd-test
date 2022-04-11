package com.fwd.tictactoe.service.impl;

import com.fwd.tictactoe.data.GameData;
import com.fwd.tictactoe.repository.GameDataRepository;
import com.fwd.tictactoe.service.GameService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameServiceImpl implements GameService {

    private GameDataRepository gameDataRepository;

    public GameServiceImpl(GameDataRepository gameDataRepository) {
        this.gameDataRepository = gameDataRepository;
    }

    @Override
    public GameData createGame(UUID id, int size) {
        String[][] board = new String[size][size];
        for (int j = 0; j < board.length; j++) {
            board[j][0] = j + "" + 0;
            for (int k = 1; k < board[j].length; k++) {
                board[j][k] = j + "" + k;
            }
        }
        List<Integer> availableSlot = updateAvailableSlot(board);

        GameData gameData = GameData.builder().id(id).size(size).board(board).availableSlot(availableSlot).build();

        return gameDataRepository.save(gameData);
    }

    @Override
    public GameData updateBoard(UUID id, int row, int column, String player) {
        Optional<GameData> gameData = gameDataRepository.findById(id);
        if (gameData.isPresent()) {
            gameData.get().getBoard()[row][column] = player;
            gameData.get().setAvailableSlot(updateAvailableSlot(gameData.get().getBoard()));
            return gameDataRepository.save(gameData.get());
        }
        return gameData.get();
    }

    @Override
    public void printBoard(GameData gameData) {
        String[][] board = gameData.getBoard();
        for (int j = 0; j < board.length; j++) {
            System.out.print("|  " + board[j][0] + "  ");
            for (int k = 1; k < board[j].length; k++) {
                System.out.print("|  " + board[j][k] + "  ");
            }
            System.out.println("|");
        }

    }

    @Override
    public boolean checkAvailableSlot(UUID id, int slot) {
        Optional<GameData> gameData = gameDataRepository.findById(id);
        if (gameData.isPresent()) {
            return gameData.get().getAvailableSlot().contains(slot) ? true : false;
        }
        return false;
    }

    @Override
    public boolean checkWinner(String[][] board) {
        boolean winner = false;
        //Check from Row
        for (int j = 0; j < board.length; j++) {
            if (board[j][0].equals("X")) {
                for (int k = 1; k < board[j].length; k++) {
                    if (board[j][k].equals("X")) {
                        winner = true;
                    } else {
                        winner = false;
                        break;
                    }
                }
                if (winner) {
                    return true;
                }
            } else if (board[j][0].equals("O")) {
                for (int k = 1; k < board[j].length; k++) {
                    if (board[j][k].equals("O")) {
                        winner = true;
                    } else {
                        winner = false;
                        break;
                    }
                }
                if (winner) {
                    return true;
                }
            }
        }


        //check From Column
        for (int k = 0; k < board.length; k++) {
            if (board[0][k].equals("X")) {
                for (int j = 1; j < board.length; j++) {
                    if (board[j][k].equals("X")) {
                        winner = true;
                    } else {
                        winner = false;
                        break;
                    }
                }
                if (winner) {
                    return true;
                }
            } else if (board[0][k].equals("O")) {
                for (int j = 1; j < board.length; j++) {
                    if (board[j][k].equals("O")) {
                        winner = true;
                    } else {
                        winner = false;
                        break;
                    }
                }
                if (winner) {
                    return true;
                }
            }
        }

        //Check For Diagonal from Top Right
        if (board[0][0].equals("X")) {
            for (int j = 1, k = 1; j < board.length; k++, j++) {
                if (board[j][k].equals("X")) {
                    winner = true;
                } else {
                    winner = false;
                    break;
                }
            }
            if (winner) {
                return true;
            }
        } else if (board[0][0].equals("O")) {
            for (int j = 1, k = 1; j < board.length; k++, j++) {
                if (board[j][k].equals("O")) {
                    winner = true;
                } else {
                    winner = false;
                    break;
                }
            }
            if (winner) {
                return true;
            }
        }


        //Check For Diagonal from top Left
        if (board[0][board.length - 1].equals("X")) {
            for (int j = 1, k = board.length - 2; j < board.length; j++, k--) {
                if (board[j][k].equals("X")) {
                    winner = true;
                } else {
                    winner = false;
                    break;
                }
            }
            if (winner) {
                return true;
            }
        } else if (board[0][board.length - 1].equals("O")) {
            for (int j = 1, k = board.length - 2; j < board.length; j++, k--) {
                if (board[j][k].equals("O")) {
                    winner = true;
                } else {
                    winner = false;
                    break;
                }
            }
            if (winner) {
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean isNumeric(String input) {
        if (input == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(input);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private List<Integer> updateAvailableSlot(String[][] board) {
        List<Integer> available = new ArrayList<>();
        for (int j = 0; j < board.length; j++) {
            if (isNumeric(board[j][0])) {
                available.add(Integer.valueOf(board[j][0]));
            }
            for (int k = 1; k < board[j].length; k++) {
                if (isNumeric(board[j][k])) {
                    available.add(Integer.valueOf(board[j][k]));
                }
            }
        }

        return available;
    }
}

package com.fwd.tictactoe.service;

import com.fwd.tictactoe.data.GameData;

import java.util.UUID;

public interface GameService {

    public GameData createGame(UUID id, int size);

    public GameData updateBoard(UUID id, int row, int column, String player);

    public boolean checkWinner(String[][] board);

    public void printBoard(GameData gameData);

    public boolean checkAvailableSlot(UUID id,int slot);

    public boolean isNumeric(String input);
}

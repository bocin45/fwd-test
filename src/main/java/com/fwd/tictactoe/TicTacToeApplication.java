package com.fwd.tictactoe;

import com.fwd.tictactoe.data.GameData;
import com.fwd.tictactoe.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;
import java.util.UUID;

@SpringBootApplication
public class TicTacToeApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(TicTacToeApplication.class, args);
        GameService service = applicationContext.getBean(GameService.class);
        Scanner in = new Scanner(System.in);
        UUID id = UUID.randomUUID();

        boolean correctSize = false;
        String sizeInput = "";
        while (!correctSize) {
            System.out.println("============== Please Input the Size of the Board ===================");
            sizeInput = in.next();
            if (!service.isNumeric(sizeInput)) {
                System.out.println("Incorrect Size");
                continue;
            }
            correctSize = true;
        }

        GameData gameData = service.createGame(id, Integer.valueOf(sizeInput));
        boolean winner = false;
        boolean draw = false;
        System.out.println("============ GAME START =======================");
        String player = "X";
        while (!winner) {
            service.printBoard(gameData);
            System.out.println("IT IS player "+ player + " Turns");

            //Validate Input
            String input = in.next();
            if (!service.isNumeric(input)) {
                System.out.println("Invalid Input, must be a number");
                continue;
            }
            //Validate Availibility
            int slot = Integer.parseInt(input);
            if (!service.checkAvailableSlot(id, slot)) {
                System.out.println(
                        "Invalid input; Slot is not Exist or Already Taken");
                continue;
            }

            int row = slot / 10;
            int column = slot % 10;
            gameData = service.updateBoard(id, row, column, player);
            winner = service.checkWinner(gameData.getBoard());
            if (winner) {
                continue;
            }
            if (gameData.getAvailableSlot().isEmpty()) {
                draw = true;
                break;
            }
            player = player.equals("X") ? "O" : "X";
        }

        System.out.println("---------------------------------------------------------------------------------------");
        if (winner) {
            System.out.println("WE'VE GO THE WINNER, THE WINNER IS " + player);
        } else if (draw) {
            System.out.println("IT'S A DRAW!");
        }
        service.printBoard(gameData);

        System.out.println("=============================== THANK YOU FOR PLAYING =================================");

        applicationContext.close();
    }

}

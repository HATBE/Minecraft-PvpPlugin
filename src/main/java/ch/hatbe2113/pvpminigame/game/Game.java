package ch.hatbe2113.pvpminigame.game;

import ch.hatbe2113.pvpminigame.Main;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Game {
    private Main main;
    private Player senderP;
    private Player targetP;

    public Game(Main main, Player senderP, Player targetP) {
        this.main = main;
        this.senderP = senderP;
        this.targetP = targetP;

        start();
    }

    public void start() {
        // start a game
        // add this game to arraylist in main class
        main.getGames().add(this);
        // send messages to sender and target
        senderP.sendMessage("started");
        targetP.sendMessage("started");

        startupSetup();
    }

    public void stop() {
        // stop a game
        // send messages to sender and target
        senderP.sendMessage("stopped");
        targetP.sendMessage("stopped");
        // remove this game in arraylist in main class
        main.getGames().remove(this);
    }

    private void startupSetup() {
        // Teleport players to startup position
        // TODO:
    }

    // GETTERS AND SETTERS

    public ArrayList<Player> getPlayers() {
        // get the two players of this game
        ArrayList<Player> players = new ArrayList<>();
        players.add(senderP);
        players.add(targetP);
        return players;
    }

}

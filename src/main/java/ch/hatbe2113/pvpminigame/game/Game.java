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
        main.getGames().add(this);
        senderP.sendMessage("started");
        targetP.sendMessage("started");
    }

    public void stop() {
        main.getGames().remove(this);
    }

    public ArrayList<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(senderP);
        players.add(targetP);
        return players;
    }

}

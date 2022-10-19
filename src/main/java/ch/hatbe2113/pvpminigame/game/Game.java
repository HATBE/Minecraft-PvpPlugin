package ch.hatbe2113.pvpminigame.game;

import ch.hatbe2113.pvpminigame.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Game {
    private Main main;
    private Player senderP;
    private Player targetP;

    private Location senderPosBeforeGame;
    private Location targetPosBeforeGame;

    private Location gameSpawn;

    public Game(Main main, Player senderP, Player targetP) {
        this.main = main;
        this.senderP = senderP;
        this.targetP = targetP;
        this.senderPosBeforeGame = senderP.getLocation();
        this.targetPosBeforeGame = targetP.getLocation();

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

        senderP.teleport(senderPosBeforeGame);
        targetP.teleport(targetPosBeforeGame);
        // remove this game in arraylist in main class
        main.getGames().remove(this);
    }

    private void startupSetup() {
        // Teleport players to startup position
        gameSpawn = new Location(
                Bukkit.getWorld(main.getPvpConfig().getString("gamespawn.world")),
                main.getPvpConfig().getDouble("gamespawn.x"),
                main.getPvpConfig().getDouble("gamespawn.y"),
                main.getPvpConfig().getDouble("gamespawn.z")
        );
        int radius = main.getPvpConfig().getInt("gamespawn.spawnradius");
        senderP.teleport(gameSpawn);
        targetP.teleport(gameSpawn);
    }

    public void ended(Player looser, Player winner) {
        Bukkit.broadcastMessage(looser.getDisplayName() + " has lost " + winner.getDisplayName() + " has won");
        senderP.teleport(senderPosBeforeGame);
        targetP.teleport(targetPosBeforeGame);
        this.stop();
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

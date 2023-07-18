package ch.hatbe2113.pvpminigame.game;

import ch.hatbe2113.pvpminigame.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Game {
    private Main main;
    private Player senderPlayer;
    private Player targetPlayer;

    private Location senderPlayerPosBeforeGame;
    private Location targetPlayerPosBeforeGame;

    private Location gameSpawn;

    public Game(Main main, Player senderPlayer, Player targetPlayer) {
        this.main = main;
        this.senderPlayer = senderPlayer;
        this.targetPlayer = targetPlayer;
        this.senderPlayerPosBeforeGame = senderPlayer.getLocation();
        this.targetPlayerPosBeforeGame = targetPlayer.getLocation();

        start();
    }

    public void start() {
        // start a game
        // add this game to arraylist in main class
        main.getGames().add(this);
        // send messages to sender and target
        senderPlayer.sendMessage("The game has started.");
        targetPlayer.sendMessage("The game has started.");

        startupSetup();
    }

    public void stop() {
        // stop a game
        // send messages to sender and target
        senderPlayer.sendMessage("The game has stopped.");
        targetPlayer.sendMessage("The game has stopped.");

        senderPlayer.teleport(senderPlayerPosBeforeGame);
        targetPlayer.teleport(targetPlayerPosBeforeGame);
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
        senderPlayer.teleport(gameSpawn);
        targetPlayer.teleport(gameSpawn);
    }

    public void ended(Player looser, Player winner) {
        Bukkit.broadcastMessage((String.format("%s has lost, %s has won", looser.getDisplayName(), winner.getDisplayName())));
        senderPlayer.teleport(senderPlayerPosBeforeGame);
        targetPlayer.teleport(targetPlayerPosBeforeGame);
        this.stop();
    }

    // GETTERS AND SETTERS

    public ArrayList<Player> getPlayers() {
        // get the two players of this game
        ArrayList<Player> players = new ArrayList<>();
        players.add(senderPlayer);
        players.add(targetPlayer);
        return players;
    }

}

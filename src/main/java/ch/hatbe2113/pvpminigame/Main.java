package ch.hatbe2113.pvpminigame;

import ch.hatbe2113.pvpminigame.commands.PvpCommand;
import ch.hatbe2113.pvpminigame.commands.PvpadminCommand;
import ch.hatbe2113.pvpminigame.events.OnPlayerQuitEvent;
import ch.hatbe2113.pvpminigame.game.Game;
import ch.hatbe2113.pvpminigame.io.CustomConfigHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

/*
Name: PvpPlugin
Author: hatbe2113
Date: 10.22
*/

public final class Main extends JavaPlugin {

    public static final String PLUGIN_NAME = "PvpPlugin";

    private PluginManager plManager = Bukkit.getPluginManager();
    private CustomConfigHandler pvpConfig;

    // sender player // target player
    private HashMap<Player, Player> gameRequests = new HashMap<>();
    private ArrayList<Game> games = new ArrayList<>();

    @Override
    public void onEnable() {
        Bukkit.getLogger().info(String.format("%s starting up", PLUGIN_NAME));

        // check if something is wrong
        // if something was detected, disable plugin
        if(!checkStartupRoutine()) {
            plManager.disablePlugin(this);
            return;
        }

        registerEvents();
        registerCommands();

        debug();
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(String.format("%s shutting down", PLUGIN_NAME));

    }

    private Boolean checkStartupRoutine() {
        // in this function all necessary settings are checked.
        // if false is returned, something went wrong
        createDefaultConfig();
        // TODO:
        return true;
    }

    private void registerCommands() {
        // in this function every command is registered

        // /pvp <player> *[accept/deny/revoke]
        getCommand("pvp").setExecutor(new PvpCommand(this));
        // /pvpadmin
        getCommand("pvpadmin").setExecutor(new PvpadminCommand());
    }

    private void registerEvents() {
        // in this function every event is registered

        plManager.registerEvents(new OnPlayerQuitEvent(this), this);
    }

    private void debug() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                System.out.println("requests" + gameRequests.keySet());
                System.out.println("games: " + games.size());
            }
        }, 20L * 1, 20L * 3);
    }

    private void createDefaultConfig() {
        pvpConfig = new CustomConfigHandler(this, "pvpconfig");
        pvpConfig.addDefault("gamespawn.spawnradius", 10);
        pvpConfig.addDefault("gamespawn.world", "world");
        pvpConfig.addDefault("gamespawn.x", 0);
        pvpConfig.addDefault("gamespawn.y", 0);
        pvpConfig.addDefault("gamespawn.z", 80);
        pvpConfig.save();
    }

    // GETTERS and SETTERS

    public HashMap<Player, Player> getGameRequests() {
        return gameRequests;
    }

    public ArrayList<Game> getGames() {
        return games;
    }
}

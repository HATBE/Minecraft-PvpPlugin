package ch.hatbe2113.pvpminigame;

import ch.hatbe2113.pvpminigame.commands.PvpCommand;
import ch.hatbe2113.pvpminigame.commands.PvpadminCommand;
import ch.hatbe2113.pvpminigame.events.OnPlayerQuitEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/*
Name: PvpPlugin
Author: hatbe2113
Date: 10.22
*/

public final class Main extends JavaPlugin {

    public static final String PLUGIN_NAME = "PvpPlugin";

    private PluginManager plManager = Bukkit.getPluginManager();

    // sender player // target player
    private  HashMap<Player, Player> gameRequests = new HashMap<>();

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
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(String.format("%s shutting down", PLUGIN_NAME));

    }

    private Boolean checkStartupRoutine() {
        // in this function all necessary settings are checked.
        // if false is returned, something went wrong

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

    // GETTERS and SETTERS
    public HashMap<Player, Player> getGameRequests() {
        return gameRequests;
    }
}

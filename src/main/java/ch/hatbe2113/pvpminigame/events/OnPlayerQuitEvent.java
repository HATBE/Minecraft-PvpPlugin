package ch.hatbe2113.pvpminigame.events;

import ch.hatbe2113.pvpminigame.Main;
import ch.hatbe2113.pvpminigame.game.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerQuitEvent implements Listener {

    private Main main;

    public OnPlayerQuitEvent(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        // remove user from pvp requests list sender
        if(main.getGameRequests().containsValue(e.getPlayer())) {
            main.getGameRequests().remove(e.getPlayer());
        }
        // remove user from pvp requests list if target
        if(main.getGameRequests().containsValue(e.getPlayer())) {
            main.getGameRequests().values().remove(e.getPlayer());
        }

        // if player is in a game, stop game
        GameManager.stopGameOfPlayer(main, e.getPlayer());
    }
}

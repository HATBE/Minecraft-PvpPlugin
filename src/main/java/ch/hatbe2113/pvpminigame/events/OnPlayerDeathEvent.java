package ch.hatbe2113.pvpminigame.events;

import ch.hatbe2113.pvpminigame.Main;
import ch.hatbe2113.pvpminigame.game.Game;
import ch.hatbe2113.pvpminigame.game.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class OnPlayerDeathEvent implements Listener {

    private Main main;

    public OnPlayerDeathEvent(Main main) {
        this.main = main;
    }

    @EventHandler
    public void OnPlayerDeath(PlayerDeathEvent e) {
        if(GameManager.isPlayerInGame(main, e.getEntity().getPlayer())) {
            Game game = GameManager.getGameOfPlayer(main, e.getEntity().getPlayer());
            game.ended(e.getEntity().getPlayer(), e.getEntity().getKiller().getPlayer());
        }
    }
}

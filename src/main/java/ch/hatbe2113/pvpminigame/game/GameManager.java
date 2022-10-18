package ch.hatbe2113.pvpminigame.game;

import ch.hatbe2113.pvpminigame.Main;
import org.bukkit.entity.Player;

public class GameManager {
    public static boolean isPlayerInGame(Main main, Player player) {
        for(Game game : main.getGames()) {
            if(game.getPlayers().contains(player)) {
                return true;
            }
        }
        return false;
    }

    public static Game getGameOfPlayer(Main main, Player player) {
        for(Game game : main.getGames()) {
            if(game.getPlayers().contains(player)) {
                return game;
            }
        }
        return null;
    }

    public static void stopGameOfPlayer(Main main, Player player) {
        if(isPlayerInGame(main, player)) {
            getGameOfPlayer(main, player).stop();
        }
    }
}

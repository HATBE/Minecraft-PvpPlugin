package ch.hatbe2113.pvpminigame.commands;

import ch.hatbe2113.pvpminigame.Main;
import ch.hatbe2113.pvpminigame.game.Game;
import ch.hatbe2113.pvpminigame.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PvpCommand implements CommandExecutor {

    private Main main;

    private Player senderP;
    private Player targetP;

    public PvpCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // if the command sender is not a player
        if(!(sender instanceof Player)) {
            sender.sendMessage("You have to be a player to execute this command!");
            return false;
        }

        senderP = (Player) sender;

        // return if player is in a game
        if(GameManager.isPlayerInGame(main, senderP)) {
            senderP.sendMessage(String.format("You are currently in a game, you can't use this command right now"));
            return false;
        }

        switch(args.length) {
            case 1:
                // create a pvp request
                // expected args: arg0 = target player

                targetP = (Player) Bukkit.getPlayer(args[0]);

                // if player can't be resolved, return error to sender
                if(targetP == null) {
                    senderP.sendMessage(String.format("%s is currently not playing on the server.", args[0]));
                    return false;
                }
                // if target is sender, return error to sender
                if(targetP == senderP) {
                    senderP.sendMessage("You can't send a pvp request to yourself");
                    return false;
                }
                // check if sender already sent a pvp request to target
                if(main.getGameRequests().containsKey(senderP) && main.getGameRequests().containsValue(targetP)) {
                    senderP.sendMessage(String.format("You already sent a pvp request to %s", targetP.getDisplayName()));
                    return false;
                }
                // check if target already send a pvp request to sender
                if(main.getGameRequests().containsKey(targetP) && main.getGameRequests().containsValue(senderP)) {
                    acceptPvpRequest();
                    return true;
                }

                createPvpRequest();
                break;
            case 2:
                // accept, deny or revoke a pvp request
                // expected args: arg0 = sender player, arg1 = accept, deny or revoke

                // check if sender sent a request to player
                targetP = (Player) Bukkit.getPlayer(args[0]);

                // if player can't be resolved, return error to sender
                if(targetP == null) {
                    senderP.sendMessage(String.format("You don't have a pvp request from %s.", args[0]));
                    return false;
                }
                // if target is sender, return error to sender
                if(targetP == senderP) {
                    senderP.sendMessage("You can't perform this action on yourself");
                    return false;
                }

                // check if arg2 is correct (accept, deny or revoke)
                if(args[1].equalsIgnoreCase("accept")) {
                    // accept a pvp request

                    // check if target sent a pvp request to sender
                    if(! (main.getGameRequests().containsKey(targetP) && main.getGameRequests().containsValue(senderP))) {
                        senderP.sendMessage(String.format("You don't have a pvp request from %s", targetP.getDisplayName()));
                        return false;
                    }
                    // if nothing failed, accept request
                    acceptPvpRequest();
                } else if(args[1].equalsIgnoreCase("deny")) {
                    // deny a pvp request

                    // check if target sent a pvp request to sender
                    if(! (main.getGameRequests().containsKey(targetP) && main.getGameRequests().containsValue(senderP))) {
                        senderP.sendMessage(String.format("You don't have a pvp request from %s", targetP.getDisplayName()));
                        return false;
                    }
                    // if nothing failed, deny request
                    denyPvpRequest();
                } else if(args[1].equalsIgnoreCase("revoke")) {
                    // revoke a pvp request

                    // check if sender sent a pvp request to target
                    if(! (main.getGameRequests().containsKey(senderP) && main.getGameRequests().containsValue(targetP))) {
                        senderP.sendMessage(String.format("You don't sent %s a pvp request", targetP.getDisplayName()));
                        return false;
                    }
                    // if nothing failed, revoke request
                    revokePvpRequest();
                } else {
                    // command was not executed correctly
                    senderP.sendMessage(String.format("You are using the wrong format, please use /%s <player> *[accept/deny/revoke] ", command.getName()));
                }
                break;
            default:
                // command was not executed correctly
                senderP.sendMessage(String.format("You are using the wrong format, please use /%s <player> *[accept/deny/revoke] ", command.getName()));
                return false;
        }
        // if anything went right, success of command
        return true;
    }

    private void createPvpRequest() {
        // create a new pvp match request sender -> target
        // add sender and target to hashmap "gameRequests"
        main.getGameRequests().put(senderP, targetP);
        // send messages to sender and target
        senderP.sendMessage(String.format("You have sent %s a pvp request", targetP.getDisplayName()));
        targetP.sendMessage(String.format("%s sent you a pvp request", senderP.getDisplayName()));
    }

    private void acceptPvpRequest() {
        // the target accepts the request from the sender
        // check if target is already in a game
        if(GameManager.isPlayerInGame(main, targetP)) {
            senderP.sendMessage(String.format("%s is currently in a game", targetP.getDisplayName()));
            return;
        }
        // remove sender and target to hashmap "gameRequests"
        main.getGameRequests().remove(targetP, senderP);
        // send messages to sender and target
        senderP.sendMessage(String.format("You have accepted the pvp request from %s", targetP.getDisplayName()));
        targetP.sendMessage(String.format("%s has accepted your pvp request", senderP.getDisplayName()));
        // create a new game with sender and player
        new Game(main, senderP, targetP);
    }

    private void denyPvpRequest() {
        // the target denys the request from the sender
        // remove sender and target to hashmap "gameRequests"
        main.getGameRequests().remove(targetP, senderP);
        // send messages to sender and target
        senderP.sendMessage(String.format("You have denied the pvp request from %s", targetP.getDisplayName()));
        targetP.sendMessage(String.format("%s denied your pvp request", senderP.getDisplayName()));
    }

    private void revokePvpRequest() {
        // the sender revokes his request to the target
        // remove sender and target to hashmap "gameRequests"
        main.getGameRequests().remove(senderP, targetP);
        // send messages to sender and target
        senderP.sendMessage(String.format("You have revoked a pvp request to %s", targetP.getDisplayName()));
        targetP.sendMessage(String.format("%s revoked the pvp request", senderP.getDisplayName()));
    }
}
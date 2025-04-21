package fr.vutivo.tntrun.events;

import fr.vutivo.tntrun.ScoreBoard.ScoreboardManager;
import fr.vutivo.tntrun.TntRun;
import fr.vutivo.tntrun.game.State;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class OnJoin implements Listener {
    private TntRun main;
    private ScoreboardManager board;

    public OnJoin(TntRun main) {
        this.main = main;
        this.board = new ScoreboardManager(main);
    }

    @EventHandler
    public void OnJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        e.setJoinMessage(null);

        if(main.isState(State.WAITTING)){
            main.placeholders.put("player", p.getName()); // %player%

            // Envoie le message
            Bukkit.broadcastMessage( main.formatMessage(main.getConfig().getString("Message.JoinMessage"), main.placeholders,true));

            p.teleport(main.Spawn);
            p.getInventory().clear();
            p.setExp(0);
            p.setLevel(0);
            p.setFoodLevel(20);
            p.setHealth(20);
            p.setGameMode(GameMode.SURVIVAL);
            if(main.PlayerHost.contains(p) || p.isOp()){
                p.getInventory().setItem(4,main.BuildItems(Material.NETHER_STAR,0,1,"§bConfiguration"));
            }

            p.getInventory().setItem(8,main.BuildItems(Material.CHEST,0,1,"§6Kit"));
        }else {
            p.sendMessage(main.formatMessage(main.getConfig().getString("Message.JoinMessageAfterStart"),main.placeholders,true));
            p.setGameMode(GameMode.SPECTATOR);
        }
        board.Setscoreboard(p);

    }
    @EventHandler
    public void OnQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        main.placeholders.put("player", p.getName()); // %player%
        e.setQuitMessage(null);

        Bukkit.broadcastMessage(main.formatMessage(
                main.getConfig().getString("Message.QuitMessage"),
                main.placeholders,true));


    }

}

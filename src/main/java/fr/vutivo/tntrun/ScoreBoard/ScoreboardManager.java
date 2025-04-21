package fr.vutivo.tntrun.ScoreBoard;

import fr.mrmicky.fastboard.FastBoard;

import fr.vutivo.tntrun.TntRun;
import fr.vutivo.tntrun.game.State;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static fr.vutivo.tntrun.game.TntGame.Chrono;


public class ScoreboardManager implements Listener {
    private final Map<UUID, FastBoard> boards = new HashMap<>() ;
  private final TntRun main;




    public ScoreboardManager(TntRun main) {
        this.main = main;





        new BukkitRunnable() {
            @Override
            public void run() {
                for (FastBoard board : boards.values()) {
                    try {
                        if (main.isState(State.WAITTING)) {
                            updateWaitingBoard(board);
                        }
                        if (main.isState(State.GAME)){
                            updateGameBoard(board);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.runTaskTimer(main, 0L, 20L);

    }

  public void Setscoreboard(Player player){

      FastBoard board = new FastBoard(player);
      board.updateTitle("§4TNT §6Run");
      boards.put(player.getUniqueId(),board);

  }


    private void updateWaitingBoard(FastBoard board) {
        try {
            updateBoard(board,
                    "",
                    "",
                    "&a En attente ...",
                    "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateGameBoard(FastBoard board) {
        try {


            updateBoard(board,
                    "",
                        "§aJoueur en vie :§b "+ main.PlayerIG.size(),
                        "",
                        "§eTimer :§b "+ Chrono(main.Timer)

                    );


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updateBoard(FastBoard board,String... lines){
      for (int x = 0; x < lines.length; ++x){
          lines[x] = ChatColor.translateAlternateColorCodes('&',lines[x]);
      }
        board.updateLines(lines);
    }



  }


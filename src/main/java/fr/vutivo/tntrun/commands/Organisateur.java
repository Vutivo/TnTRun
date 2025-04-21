package fr.vutivo.tntrun.commands;

import fr.vutivo.tntrun.ScoreBoard.ScoreboardManager;
import fr.vutivo.tntrun.TntRun;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Organisateur implements CommandExecutor {
    private TntRun main;

    public Organisateur(TntRun main) {
        this.main = main;
    }
    private String error = "§cErreur tu ne peux pas executer cette commande";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(error);
            return false;
        }
        Player p = (Player) sender;

        if (args.length == 0){
            p.sendMessage("§3Les commandes disponibles");
            p.sendMessage("§9-/h reload/rl");
            p.sendMessage("§9 /h sethost <Pseudo>");
            return false;
        }
        if(args[0].equalsIgnoreCase("reload")||args[0].equalsIgnoreCase("rl")){
           restart(p);
        }
        if(args[0].equalsIgnoreCase("sethost")){
            Player target = Bukkit.getPlayer(args[1]) ;
            if(target == null){
                p.sendMessage("§cErreur le joueur est deconnecté ou n'existe pas !");
                return false;
            }
            if(main.PlayerHost.contains(target)){
                p.sendMessage("§cErreur le joueur est deja l'host de la partie");
                return false;

            }

            main.PlayerHost.add(target);
            p.getInventory().setItem(4,main.BuildItems(Material.NETHER_STAR,0,1,"§bConfiguration"));
            return false;


        }
        return false;
    }




    private void restart(Player sender){
        long startTime = System.currentTimeMillis(); // Commence le timer

        // Reload du plugin
        Bukkit.getPluginManager().disablePlugin(main);
        Bukkit.getPluginManager().enablePlugin(main);

        ScoreboardManager board = new ScoreboardManager(main);

        for(Player pl : Bukkit.getOnlinePlayers()) {
            pl.teleport(main.Spawn);
            pl.setGameMode(GameMode.SURVIVAL);
            board.Setscoreboard(pl);
        }


            long endTime = System.currentTimeMillis(); // Fin du timer
            long elapsedTime = endTime - startTime; // Calcul du temps écoulé

            sender.sendMessage("§a Le plugin a été rechargé en " + elapsedTime + " ms.");


    }
}

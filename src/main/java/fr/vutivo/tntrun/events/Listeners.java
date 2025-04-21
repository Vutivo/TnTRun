package fr.vutivo.tntrun.events;

import fr.vutivo.tntrun.TntRun;
import fr.vutivo.tntrun.game.State;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Listeners implements Listener {
    private TntRun main;

    public Listeners(TntRun main) {
        this.main = main;
    }

    @EventHandler
    public void OnFood(FoodLevelChangeEvent e){
        e.setCancelled(true);
    }
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e){
        e.setCancelled(true);
    }
    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        if(!(p.getGameMode().equals(GameMode.CREATIVE))){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void BlocPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        if(!p.isOp() && !(p.getGameMode().equals(GameMode.CREATIVE))){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onDamage(EntityDamageEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onRun(PlayerMoveEvent e){
        if(!main.isState(State.GAME)) return;
        Player p = e.getPlayer();
        main.placeholders.put("player", p.getName()); // %player%

        if(!(main.PlayerIG.contains(p))) return;

        // Récupère la position des pieds du joueur
        Block blockUnder = p.getLocation().getBlock().getRelative(0, -1, 0);

        // Vérifie le type de bloc sous les pieds
        Material blockType = blockUnder.getType();

        if(blockType.equals(Material.GLASS) && main.PlayerIG.contains(p)){
            Bukkit.broadcastMessage( main.formatMessage(main.getConfig().getString("Message.MessagedeMort"), main.placeholders,true));
            p.setGameMode(GameMode.SPECTATOR);
            main.PlayerIG.remove(p);
        }

        new BukkitRunnable(){
            @Override
            public void run(){

                if(!(blockType.equals(Material.AIR)) && blockType.equals(Material.SANDSTONE) ||blockType.equals(Material.RED_SANDSTONE)){
                    blockUnder.setType(Material.AIR);

                }
            }
        }.runTaskLater(main,10);

    }
}

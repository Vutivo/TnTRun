package fr.vutivo.tntrun.events;

import fr.vutivo.tntrun.TntRun;
import fr.vutivo.tntrun.game.State;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
    public void onRun(PlayerMoveEvent e){
        if(!main.isState(State.GAME)) return;
        Player p = e.getPlayer();

        // Récupère la position des pieds du joueur
        Block blockUnder = p.getLocation().getBlock().getRelative(0, -1, 0);

        // Vérifie le type de bloc sous les pieds
        Material blockType = blockUnder.getType();

        if(blockType.equals(Material.GLASS) && main.PlayerIG.contains(p)){
            p.sendMessage("Messagedemort");
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

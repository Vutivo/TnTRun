package fr.vutivo.tntrun.events;

import fr.vutivo.tntrun.TntRun;
import fr.vutivo.tntrun.game.State;
import fr.vutivo.tntrun.gui.Gui;
import fr.vutivo.tntrun.gui.waitingGUI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import static fr.vutivo.tntrun.gui.Gui.BuildItems;

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
    public void OnInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack it = e.getItem();
        Action action = e.getAction();

        //Ne pas intéragir si l'item est null
        if (it == null) return;
        if (main.isState(State.WAITTING) || main.isState(State.STARTING)) {
            if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
                if (it.isSimilar(BuildItems(Material.NETHER_STAR, 0, 1, "§bConfiguration"))) {
                    waitingGUI gui = new waitingGUI(main);
                    gui.open(p);
                }
            }
        }
    }
//    @EventHandler
//    public void  onInventoryClick (InventoryClickEvent e){
//        if (!(e.getWhoClicked() instanceof Player)) {
//            return; // Si ce n'est pas un joueur qui a cliqué, on ignore
//        }
//        Player player = (Player) e.getWhoClicked();
//        InventoryHolder holder = e.getInventory().getHolder();
//        Bukkit.broadcastMessage("§aLa partie commence !1");
//        e.setCancelled(true);
//
//        // Vérifie si l'inventaire cliqué est une instance de ta classe Gui
//        if (holder instanceof Gui) {
//            e.setCancelled(true); // Empêche le joueur de prendre l'item
//            ItemStack clickedItem = e.getCurrentItem();
//            Bukkit.broadcastMessage("§aLa partie commence2 !");
//            if (clickedItem != null) {
//                // Appelle la méthode de ta classe Gui pour gérer le clic
//                ((Gui) holder).handleInventoryClick(player, e.getSlot(), clickedItem);
//                Bukkit.broadcastMessage("§aLa partie commence 5!");
//            }
//        }
//        Bukkit.broadcastMessage("§aLa partie commence3 !");
//        // Si l'inventaire n'est pas une de tes GUIs, l'événement se déroule normalement
//    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player) e.getWhoClicked();
        InventoryHolder holder = e.getInventory().getHolder();

        if (holder != null) {
            Bukkit.getLogger().info("Type de l'holder de l'inventaire cliqué : " + holder.getClass().getName());
        } else {
            Bukkit.getLogger().info("L'holder de l'inventaire cliqué est null.");
        }

        if (holder instanceof Gui) {
            e.setCancelled(true);
            ItemStack clickedItem = e.getCurrentItem();
            if (clickedItem != null) {
                ((Gui) holder).handleInventoryClick(player, e.getSlot(), clickedItem);
            }
        }
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

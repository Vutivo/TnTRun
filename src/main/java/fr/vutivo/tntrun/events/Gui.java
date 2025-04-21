package fr.vutivo.tntrun.events;

import fr.vutivo.tntrun.TntRun;
import fr.vutivo.tntrun.game.State;
import fr.vutivo.tntrun.game.TntStart;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static fr.vutivo.tntrun.TntRun.BuildItems;

public class Gui implements Listener {
    private TntRun main;
    private TntStart start ;


    public Gui(TntRun main) {
        this.main = main;
        this.start = new TntStart(main);
    }
    @EventHandler
    public void OnInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack it = e.getItem();
        Action action = e.getAction();

        //Ne pas intéragir si l'item est null
        if (it == null) return;
        if(main.isState(State.WAITTING)|| main.isState(State.STARTING)){

            if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {

                if (it.isSimilar(BuildItems(Material.NETHER_STAR,0,1,"§bConfiguration"))) {
                    Configuration(p);
                }
                if (it.isSimilar(BuildItems(Material.CHEST,0,1,"§6Kit"))) {
                    Kit(p);
                }


            }


        }

    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();
        ItemMeta meta = clickedItem != null ? clickedItem.getItemMeta() : null;



        if (clickedItem == null || clickedItem.getType() == Material.AIR || meta == null || meta.getDisplayName() == null) {
            return;
        }

        if (e.getView().getTitle().equals("§bConfig")||
            e.getView().getTitle().equals("§6Kit")){

            e.setCancelled(true);


             switch ((meta).getDisplayName()){

                 case "§aStart":
                     main.setState(State.STARTING);
                     start.runTaskTimer(main,0,20);
                     p.closeInventory();
                     break;
                 case "§cStop":
                     start.Stop();
                     p.closeInventory();
                     break;


             }
        }
    }

    public void Configuration(Player p){
        Inventory inv = Bukkit.createInventory(null, 27, "§bConfig");

        ItemStack glass = BuildItems(Material.STAINED_GLASS_PANE,3,1," ");
        ItemStack Start = BuildItems(Material.WOOL,13,1,"§aStart");
        ItemStack Stop = BuildItems(Material.WOOL,14,1,"§cStop");

        int[] glassSlots = {0, 1, 7, 8, 9, 17, 18, 19, 25, 26};
        for (int slotg : glassSlots) {
            inv.setItem(slotg, glass);
        }
        if(main.isState(State.WAITTING)){
            inv.setItem(4,Start);
        }else if(main.isState(State.STARTING))
            inv.setItem(4,Stop);
        p.openInventory(inv);




    }

    public void Kit(Player p){
        Inventory config = Bukkit.createInventory(null, 27, "§6Kit");

        p.openInventory(config);
    }

}

package fr.vutivo.tntrun.gui;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


public class Gui implements InventoryHolder {

    private Inventory inventory;
    private final Map<Integer, Consumer<Player>> Itemaction = new HashMap<>();

    //Creation de la class GUI
    public Gui(String title, int size) {
        this.inventory = Bukkit.createInventory(null, size, title);

        /**
         * Bukkit.createInventory(InventoryHolder owner, int size, String title) crée une nouvelle instance
         * d'un inventaire.
         * - Le premier argument (null ici) est l'InventoryHolder. Puisque cette GUI n'appartient pas
         * à une entité spécifique (comme un coffre), on met null.
         * - Le deuxième argument est la taille de l'inventaire.
         * - Le troisième argument est le titre de l'inventaire, avec les codes de couleur Minecraft (&) traduits.
         */

    }

    /**
     * Définit un item spécifique dans un slot donné de la GUI et associe une action à ce clic.
     *
     * @param slot        L'index du slot (de 0 à la taille - 1) où l'item sera placé.
     * @param items    Le type de matériau de l'item (ex: Material.STONE, Material.DIAMOND_SWORD).
     * @param action      Une fonction (Consumer<Player>) qui sera exécutée lorsque le joueur cliquera sur cet item.
     * Si aucune action n'est souhaitée pour cet item, peut être null.
     */


    public void setItem(int slot, ItemStack items, Consumer<Player> action) {


        ItemStack item = new ItemStack(items);

        inventory.setItem(slot, item);

        if (action != null) {
            Itemaction.put(slot, action);
            // On associe l'action à l'index du slot
        }
    }
    // Méthode pour gérer le clic dans l'inventaire
    public void handleInventoryClick(Player player, int slot, ItemStack clickedItem) {
        /**
         * Cette méthode est appelée par le listener d'événements (EventManager) lorsqu'un joueur clique
         * dans l'inventaire de cette GUI.
         *
         * @param player      Le joueur qui a cliqué.
         * @param slot        L'index du slot qui a été cliqué.
         * @param clickedItem L'ItemStack qui se trouvait dans le slot cliqué. Peut être null si le joueur a cliqué dans le vide.
         */
        if (clickedItem != null && this.Itemaction.containsKey(slot)) {
            /**
             * Vérifie si un item a été cliqué et si une action est associée à ce slot dans la Map itemActions.
             */
            this.Itemaction.get(slot).accept(player);
            /**
             * Récupère la fonction (Consumer<Player>) associée au slot cliqué et l'exécute, en passant le joueur
             * comme argument. C'est ici que la logique spécifique à l'item cliqué est exécutée.
             */
        }
    }

    public  static ItemStack BuildItems(Material material, int color, int amount, String name){
        ItemStack item = new ItemStack(material , amount, (short) color); // si pas de couleur le mettre à 0
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);

        item.setItemMeta(meta);
        return item;

    }


    public  static ItemStack BuildItems(Material material,int color,int amount, String name,String lore){
        ItemStack item = new ItemStack(material , amount, (short) color); // si pas de couleur le mettre à 0
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Collections.singletonList(lore));
        meta.setDisplayName(name);

        item.setItemMeta(meta);
        return item;

    }


    public void open(Player player){
        /**
         * Ouvre l'inventaire pour le joueur donné.
         * @param player Le joueur pour lequel l'inventaire sera ouvert.
         */
        player.openInventory(this.inventory);
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}

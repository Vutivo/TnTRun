package fr.vutivo.tntrun.gui;

// La classe WaitingGui étend la classe Gui. Cela signifie qu'elle hérite de toutes les fonctionnalités de Gui
// (comme la création de l'inventaire, la définition des items, l'ouverture de la GUI et la gestion des clics).

import fr.vutivo.tntrun.TntRun;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class waitingGUI extends Gui{
    private TntRun main;

    public waitingGUI(TntRun main) {
        super("§bConfiguration",36);
        this.main = main;
        InitItems();

    }

    private void InitItems() {
        ItemStack start = BuildItems(Material.NETHER_STAR, 0, 1, "§aDebut de la partie");
        setItem(4, start, (p) -> { // Ici tu définis l'action pour le clic
            Bukkit.broadcastMessage("§aLa partie commence !");
        });


    }

}

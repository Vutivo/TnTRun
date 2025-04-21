package fr.vutivo.tntrun;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;


import java.util.HashMap;
import java.util.Map;

import static org.bukkit.Bukkit.getLogger;

public class WorldManager {
    private TntRun main;

    public WorldManager(TntRun main) {
        this.main = main;
    }

    private  final Map<Location, Material> savedArena = new HashMap<>();


    public void SaveArena(Location corner1, Location corner2) {
        savedArena.clear();

        World world = corner1.getWorld();

        if (world == null|| world != corner2.getWorld()) {
            getLogger().severe("Les corners ne sont pas dans le même monde !");
            return;
        }

        int minX = Math.min(corner1.getBlockX(), corner2.getBlockX());
        int minY = Math.min(corner1.getBlockY(), corner2.getBlockY());
        int minZ = Math.min(corner1.getBlockZ(), corner2.getBlockZ());

        int maxX = Math.max(corner1.getBlockX(), corner2.getBlockX());
        int maxY = Math.max(corner1.getBlockY(), corner2.getBlockY());
        int maxZ = Math.max(corner1.getBlockZ(), corner2.getBlockZ());


        //Porcourir la map pour aller du point A au point B
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Location loc = new Location(world, x, y, z);
                    Material material = loc.getBlock().getType();
                    savedArena.put(loc, material);
                }
            }
        }
        Bukkit.broadcastMessage("MapSaved");
        getLogger().info("Arène sauvegardée : " + savedArena.size() + " blocs.");
    }




    public void pregenArena() { // Rendre non statique et utiliser la savedArena de l'instance
        savedArena.forEach((location, material) -> location.getBlock().setType(material));
        getLogger().info("Arène recréée avec " + savedArena.size() + " blocs.");
    }
}

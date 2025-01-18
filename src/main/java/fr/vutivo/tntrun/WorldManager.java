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

    public static final Map<Location, Material> savedArena = new HashMap<>();


    public static void SaveArena(Location corner1, Location corner2) {
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


    public static void deleteArena(Location corner1, Location corner2) {
        try {
            if (corner1.getWorld() != corner2.getWorld()) {
                throw new NullPointerException("Impossible de sauvegarder une map dans 2 mondes différents");
            }
        } catch (NullPointerException e) {
            e.printStackTrace(); // Affiche la stack trace pour diagnostiquer le problème
        }
        World world = corner1.getWorld();
        int minX = Math.min(corner1.getBlockX(), corner2.getBlockX());
        int minY = Math.min(corner1.getBlockY(), corner2.getBlockY());
        int minZ = Math.min(corner1.getBlockZ(), corner2.getBlockZ());

        int maxX = Math.min(corner1.getBlockX(), corner2.getBlockX());
        int maxY = Math.min(corner1.getBlockY(), corner2.getBlockY());
        int maxZ = Math.min(corner1.getBlockZ(), corner2.getBlockZ());

        //Porcourir la map pour aller du point A au point B
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Location loc = new Location(world, x, y, z);
                    loc.getBlock().setType(Material.AIR);
                }
            }
        }
        Bukkit.broadcastMessage("MapDelete");
    }

    public static void pregenArena(Location corner1, Location corner2, Map<Location, Material> savedBlocks) {
        savedBlocks.forEach((location, material) -> location.getBlock().setType(material));
        getLogger().info("Arène recréée avec " + savedBlocks.size() + " blocs.");

    }
}

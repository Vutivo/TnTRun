package fr.vutivo.tntrun;

import fr.vutivo.tntrun.commands.CommandsManager;
import fr.vutivo.tntrun.events.EventManager;
import fr.vutivo.tntrun.game.State;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.bukkit.Bukkit.getLogger;

public final class TntRun extends JavaPlugin {

    //Liste des joueurs qui sont en jeu
    public ArrayList <Player> PlayerIG = new ArrayList<>();

    //Liste des Orga de la partie
    public ArrayList <Player> PlayerHost = new ArrayList<>();

    public  Map<String, String> placeholders = new HashMap<>();

    public Location Spawn;
    public World world;

    public int Timer;
    public State state;

    //Configurable
    public String Gametag;

    public String JoinMessage;
    public String QuitMessage;
    public String JoinMessageAfterStart;
    public String AnnonceTimerGame;
    public String AnnonceGameStart;


    private WorldManager worldManager;





    @Override
    public void onEnable() {
        EventManager.RegisterEvent(this);
        CommandsManager.RegisterCommands(this);
        saveDefaultConfig();
        loadConfig();

        worldManager = new WorldManager(this);


        world.setDifficulty(Difficulty.PEACEFUL);


        setState(State.WAITTING);
        Location corner1 = new Location(world, 102 , 101, -9);
        Location corner2 = new Location(world, 133  , 77, -31);
        getLogger().warning("Corner1 World: " + corner1.getWorld().getName() + ", Corner2 World: " + corner2.getWorld().getName());
        getLogger().warning("Corner1 Location: " + corner1.toString() + ", Corner2 Location: " + corner2.toString());

        worldManager.SaveArena(corner1, corner2);



    }

    @Override
    public void onDisable() {
        worldManager.pregenArena();




    }


    private void loadConfig() {
        Gametag = getConfig().getString("Gametag");

        world = Bukkit.getWorld(getConfig().getString("Spawn.worldName"));
        Spawn = getLocation(getConfig().getString("Spawn.spawn"));


        // Message
        JoinMessage = getConfig().getString("Message.JoinMessage");
        QuitMessage = getConfig().getString("Message.QuitMessage");
        JoinMessageAfterStart = getConfig().getString("Message.JoinMessageAfterStart");
        AnnonceTimerGame = getConfig().getString("Message.AnnonceTimerGame");
        AnnonceGameStart = getConfig().getString("Message.AnnonceGameStart");

    }

    private Location getLocation(String loc) {
        String[] args = loc.split(",");

        double x = Double.parseDouble(args[0]);
        double y = Double.parseDouble(args[1]);
        double z = Double.parseDouble(args[2]);

        return new Location(world, x, y, z);
    }

    public String formatMessage(String message, Map<String, String> placeholders,boolean includeGameTag) {
        String gameTag = includeGameTag ? getConfig().getString("Gametag", "") : "";

        if (placeholders != null) {
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                message = message.replace("%" + entry.getKey() + "%", entry.getValue());
            }
        }
        return gameTag.replace("&", "§") + message.replace("&", "§");
    }
    public void setState(State state){
        this.state = state;
    }
    public boolean isState(State state){
        return this.state == state;
    }


}
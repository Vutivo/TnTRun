package fr.vutivo.tntrun.events;

import fr.vutivo.tntrun.ScoreBoard.ScoreboardManager;
import fr.vutivo.tntrun.TntRun;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class EventManager {

    public static void RegisterEvent (TntRun main){
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new OnJoin(main),main);
        pm.registerEvents(new Listeners(main),main);

        pm.registerEvents(new Gui(main),main);

        pm.registerEvents(new ScoreboardManager(main),main);
    }
}

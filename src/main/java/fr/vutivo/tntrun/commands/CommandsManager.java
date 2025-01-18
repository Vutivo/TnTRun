package fr.vutivo.tntrun.commands;

import fr.vutivo.tntrun.ScoreBoard.ScoreboardManager;
import fr.vutivo.tntrun.TntRun;
import fr.vutivo.tntrun.events.Gui;
import fr.vutivo.tntrun.events.Listeners;
import fr.vutivo.tntrun.events.OnJoin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class CommandsManager {
        public static void RegisterCommands(TntRun main) {

        main.getCommand("host").setExecutor(new Organisateur(main));
    }
}

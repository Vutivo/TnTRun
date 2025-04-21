package fr.vutivo.tntrun.commands;

import fr.vutivo.tntrun.TntRun;


public class CommandsManager {
        public static void RegisterCommands(TntRun main) {

        main.getCommand("host").setExecutor(new Organisateur(main));
    }
}

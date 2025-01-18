package fr.vutivo.tntrun.game;

import fr.vutivo.tntrun.TntRun;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class TntGame extends BukkitRunnable {
    private TntRun main;
    private int Time;
    public TntGame(TntRun main) {
        this.main = main;

    }

    @Override
    public void run() {
        main.Timer = Time;
        Time ++;

    }

    public static String Chrono(int number){
        int remainingSecondsAfterHours = number % 3600;
        int minutes = remainingSecondsAfterHours / 60;
        int remainingSeconds = remainingSecondsAfterHours % 60;


        return String.format("%02dm %02ds", minutes, remainingSeconds);
    }
}

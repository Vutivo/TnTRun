package fr.vutivo.tntrun.game;

import fr.vutivo.tntrun.TntRun;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class TntGame extends BukkitRunnable {
    private TntRun main;
    private int Time;
    public TntGame(TntRun main) {
        this.main = main;
        this.Time = main.Timer;
    }

    @Override
    public void run() {
        Time ++;
        Bukkit.broadcastMessage(Time+"");

    }
}

package fr.vutivo.tntrun.game;

import fr.vutivo.tntrun.TntRun;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Timer;

public class TntStart extends BukkitRunnable {
    private TntRun main;
    private int Timer = 10;
    private TntGame game;
    public TntStart(TntRun main) {
        this.main = main;
        //this.Timer = main.Timerstart;
        this.game = new TntGame(main);


    }

    @Override
    public void run() {
        main.placeholders.put("timer", String.valueOf(Timer)); // %timer%
        for(Player p: Bukkit.getOnlinePlayers()){
            p.setLevel(Timer);
            p.setExp((float) Timer/60);
        }

        if (Timer ==10 ||Timer ==5 ||Timer ==4 ||Timer ==3 ||Timer ==2||Timer ==1){
            Bukkit.broadcastMessage(main.formatMessage(main.getConfig().getString("Message.AnnonceTimerGame"), main.placeholders,true));

        }
        if(Timer ==0){
            Bukkit.broadcastMessage(main.formatMessage(main.getConfig().getString("Message.AnnonceGameStart"), main.placeholders,true));
            Start();
            cancel();

        }
        Timer --;

    }

    public void Start(){
        main.setState(State.GAME);
        for(Player p : Bukkit.getOnlinePlayers()){
            main.PlayerIG.add(p);

        }
        game.runTaskTimer(main,0,20);
    }
    public void Stop(){
        main.setState(State.WAITTING);
        Bukkit.broadcastMessage("MessageStopTimer");
        cancel();
    }
}

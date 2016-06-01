/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.archerlights.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.function.Consumer;
import javax.swing.Timer;

/**
 * MATCH: One shooting of AB or CD
 * ROUND: couple of matches with AB/CD or CD/AB (round = ceil(match/2))
 * 
 * @author arogarth
 */
public class Counter implements ActionListener {

    /**
     * Two Signal    - First group startet
     * One Signal    - After 10 seconds, Start shooting
     * Two Signal    - End of first group, Second group startet
     * One Signal    - After 10 seconds, Start shooting
     * Three Signals - End of shooting
     * 
     * Ten Signals   - Emergency STOP
     */
    public interface CounterListener {
        
        public void startWaiting(); // Two Signals
        public void startShooting(); // One Signal
        public void stopShooting(); // Three Signal
        
        public void emergencyStop(); // Ten Signals
        
        public void setTime(Integer time);
        public void setTeam(TeamMode mode, Integer currentRound, Integer maxRound);
        
//        public void setSignalRed();
//        public void setSignalYellow();
//        public void setSignalGreen();
//        public void setModeAB();
//        public void setModeCD();
//        
//        public void setRound(Integer currentRound, Integer maxRound);
        
    }
    
    private static Counter instance;
    public static Counter getInstance() {
        if(Counter.instance == null)
            Counter.instance = new Counter();
        
        return Counter.instance;
    }
    
    private Counter() {
        t = new Timer(1000, this);
        this.listeners = new ArrayList<>();
        this.reset();
    }
    
    private Boolean isWaiting = true;
    private Boolean isShooting = false;

    private final Integer waitingTime = 10;
    private final Integer shootingTime = 240;
    private final Integer countDownTime = 30;
    

    /**
     * current count down timer value
     */
    private Integer currentTimerValue = 0;
    
    /**
     * Timer
     */
    private static Timer t;

    private final Integer maxMatches = null;
    private Integer match;
    
    private final ArrayList<CounterListener> listeners;
    
    /**
     * Teammode AB or CD
     */
    private TeamMode mode;
    
    
    
    public void addCounterListerner(CounterListener listener) {
        this.listeners.add(listener);
        this.notifyRound();
    }
    
    private void reset() {
        this.mode = TeamMode.AB;
        this.match = 1;
        this.currentTimerValue = 0;
        this.isWaiting = false;
        
        this.notifyRound();
    }
    
    public Integer getRound() {
        Integer round = (int) Math.ceil( getMatch() / 2.0f );
        return round;
    }

    public Integer getMatch() {
        return this.match;
    }

    private void incrementMatch() {
        this.match++;
        this.notifyRound();
    }

    private void incrementRound() {
        this.incrementMatch();
        
        if(!isEndOfRound()) {
            this.incrementMatch();
        }
    }

    private void decrementMatch() {
        this.match--;
    }

    private void decrementRound() {
        if(!isEndOfRound()) {
            this.decrementMatch();
        }

        this.decrementMatch();
    }

    private Boolean isEndOfRound() {
        return ( (getMatch()%2) == 0 );
    }
    
    public Boolean isShooting() {
        return this.isShooting;
    }
    
    public Boolean isWaiting() {
        return this.isWaiting;
    }
    
    /**
     * Start Group1
     * - is Group1 already shooting,
     *   Group2 will start
     * 
     */
    public void start() {
        if(isShooting() && !isEndOfRound()) {
            // End of first Group in round,
            // start of second group
            
            this.incrementMatch();
            this.toggleTeamMode();
            this.startWaiting();
            
        } else if(!isShooting()) {
            
            this.startWaiting();
            
        }
        
    }
    
    
    /**
     * STOP the current round
     * 
     */
    public void stop() {
        if(isShooting()) {
            this.stopShooting();
        }
    }
    
    public void switchTeammode() {
        if(!isShooting()) {
            this.toggleTeamMode();
        }
    }
    
    public void notifyClients() {
        this.notifyRound();
        this.notifyTime();
    }
    
    public void emergencyStop() {
        t.stop();
        
        this.isWaiting = false;
        this.isShooting = false;
        
        this.currentTimerValue = 0;
        this.notifyTime();
        
        this.listeners.stream().forEach((CounterListener lstnr) -> {
            lstnr.emergencyStop();
        });
    }
    
    
    private void notifyRound() {
        System.out.println("#############");
        System.out.println("Match: " + getMatch());
        System.out.println("Round: " + getRound());

        for(CounterListener lstnr : this.listeners) {
            lstnr.setTeam(this.mode, getRound(), maxMatches);
        };
    }
    
    private void notifyTime() {
        this.listeners.stream().forEach((CounterListener lstnr) -> {
            lstnr.setTime(this.currentTimerValue);
        });
    }
    
    private void toggleTeamMode() {
        
        this.mode = this.mode.equals(TeamMode.AB)
                ? TeamMode.CD
                : TeamMode.AB;
        System.out.println("Toggled: " + this.mode.toString());
        
        this.notifyRound();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        this.currentTimerValue--;
        this.notifyTime();

        if (this.currentTimerValue <= 0) {
            
            if (this.isWaiting()) {
                
                this.startShooting();
                
                this.currentTimerValue = this.shootingTime;
                this.notifyTime();
                
            } else {
                
//                if(this.getRound() > 1) {
                    
                    if(!isEndOfRound()) {
                        this.start();
                    } else {
                        this.stop();
                    }
                    
//                }
                
            }
        }
    }
    
    private void startWaiting() {
        this.isWaiting = true;
        this.isShooting = true;
        
        this.currentTimerValue = this.waitingTime;
        this.notifyTime();
        
        t.start();
        
        this.listeners.stream().forEach((CounterListener lstnr) -> {
            lstnr.startWaiting();
        });
    }
    
    private void startShooting() {
        this.isWaiting = false;
        
        this.listeners.stream().forEach((CounterListener lstnr) -> {
            lstnr.startShooting();
        });
    }
    
    private void stopShooting() {
        t.stop();
        
        this.isShooting = false;
        this.isWaiting = false;
        
        if(!isEndOfRound()) {
            this.incrementRound();
            this.toggleTeamMode();
        }
        this.incrementMatch();
        
        this.listeners.stream().forEach((CounterListener lstnr) -> {
            lstnr.stopShooting();
        });
    }

    public Integer getCountDownTime() {
        return this.countDownTime;
    }
}

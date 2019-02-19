package teuton.panel.ui.mode.standalone;

import javafx.concurrent.Task;
import teuton.panel.model.Challenge;
import teuton.panel.model.Goal;

public class ChallengeTask extends Task<Boolean> {
	
	private Challenge challenge;
	private volatile boolean stopped;
	
	public ChallengeTask(Challenge challenge) {
		super();
		this.challenge = challenge;
	}	

	@Override
	protected Boolean call() throws Exception {
		System.out.println("Iniciando reto " + challenge.getName());
		int goalIndex = 0;
		
		// 
		while (!stopped && goalIndex < challenge.getGoals().size() ) {
			
			Goal currentGoal = challenge.getGoals().get(goalIndex);
			
			while (!stopped) {
				System.out.println(challenge.toString(0));
				Thread.sleep(1000L);
			}
			

			goalIndex++;
		}
		System.out.println(challenge.toString(0));
		System.out.println("Reto parado");		
		return true;
	}
	
	public void stop() {
		System.out.println("Parando el reto");
		this.stopped = true;
	}

}

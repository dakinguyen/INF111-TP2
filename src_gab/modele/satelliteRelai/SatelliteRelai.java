package modele.satelliteRelai;

/**
 * Classe simulant le satellite relai
 * 
 * Le satellite ne se contente que de transferer les messages du Rover vers le centre de contrÃ´le
 * et vice-versa.
 * 
 * Le satellite exÃ©cute des cycles Ã  intervale de TEMPS_CYCLE_MS. PÃ©riode Ã 
 * laquelle tous les messages en attente sont transmis. Ceci est implÃ©mentÃ© Ã 
 * l'aide d'une tÃ¢che (Thread).
 * 
 * Le relai satellite simule Ã©galement les interfÃ©rence dans l'envoi des messages.
 * 
 * Services offerts:
 *  - lierCentrOp
 *  - lierRover
 *  - envoyerMessageVersCentrOp
 *  - envoyerMessageVersRover
 * 
 * @author Frederic Simard, ETS
 * @version Hiver, 2019
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;


import modele.centreControle.CentreControle;
import modele.communication.Message;
import modele.rover.Rover;
import utilitaires.FileSimple;

public class SatelliteRelai extends Thread{
	
	static final int TEMPS_CYCLE_MS = 500;
	static final double PROBABILITE_PERTE_MESSAGE = 0.15;
	
	ReentrantLock lock = new ReentrantLock();

	private CentreControle centreControle;
	private Rover rover;
	
	private Random rand = new Random();
	private FileSimple fileCentre = new FileSimple();
	private FileSimple fileRover = new FileSimple();
	
	
	/**
	 * Methode permettant d'envoyer un message vers le centre d'opÃ©ration
	 * @param msg, message a  envoyer
	 */
	public void envoyerMessageVersCentrOp(Message msg) {
		
		lock.lock();
		
		try {
			
			double prob = rand.nextDouble();

			if (prob > PROBABILITE_PERTE_MESSAGE) {
				fileCentre.ajouterElement(msg);

			}
			
		}finally {
			lock.unlock();
		}
	}
	
	/**
	 * Methode permettant d'envoyer un message vers le rover
	 * @param msg, message a  envoyer
	 */
	public void envoyerMessageVersRover(Message msg) {
		lock.lock();
		
		try {
			
			double prob = rand.nextDouble();

			if (prob > PROBABILITE_PERTE_MESSAGE) {
				fileRover.ajouterElement(msg);
			}
			
		}finally {
			lock.unlock();
		}
	}

	@Override
	public void run() {
		
		while(true) {
			
			try{
				// si la file du Rover n'est pas vide, le rover recoit un message du satellite
			    if (!fileRover.estVide()) {
                    Message msgRover = fileRover.enleverElement();
                    rover.receptionMessageDeSatellite(msgRover);
                }
			    // si la file du CentreControle n'est pas vide, le centreControle recoit un message du satellite
				if (!fileCentre.estVide()) {
                    Message msgCentre = fileCentre.enleverElement();
                    centreControle.receptionMessageDeSatellite(msgCentre);
                }

			// exception si la liste est vide
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}


			// attend le prochain cycle
			try {
				Thread.sleep(TEMPS_CYCLE_MS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void lierCentrOp(CentreControle centreControle) {
		this.centreControle = centreControle;
	}

	public void lierRover(Rover rover) {
		this.rover = rover;
	}
	
	

}

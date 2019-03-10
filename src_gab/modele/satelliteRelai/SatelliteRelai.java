package modele.satelliteRelai;

/**
 * Classe simulant le satellite relai
 * 
 * Le satellite ne se contente que de transferer les messages du Rover vers le centre de contrôle
 * et vice-versa.
 * 
 * Le satellite exécute des cycles à intervale de TEMPS_CYCLE_MS. Période à
 * laquelle tous les messages en attente sont transmis. Ceci est implémenté à
 * l'aide d'une tâche (Thread).
 * 
 * Le relai satellite simule également les interférence dans l'envoi des messages.
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
	 * Méthode permettant d'envoyer un message vers le centre d'opération
	 * @param msg, message à envoyer
	 */
	public void envoyerMessageVersCentrOp(Message msg) {
		
		lock.lock();
		
		try {

			/*
			 * (5.1) Insérer votre code ici 
			 */

			double prob = rand.nextDouble();

			if (prob > PROBABILITE_PERTE_MESSAGE) {
				fileCentre.ajouterElement(msg);
				System.out.println("Satellite vers centre: " + msg.getCompte());
			}
			
		}finally {
			lock.unlock();
		}
	}
	
	/**
	 * Méthode permettant d'envoyer un message vers le rover
	 * @param msg, message à envoyer
	 */
	public void envoyerMessageVersRover(Message msg) {
		lock.lock();
		
		try {

			/*
			 * (5.2) Insérer votre code ici 
			 */

			double prob = rand.nextDouble();

			if (prob > PROBABILITE_PERTE_MESSAGE) {
				fileRover.ajouterElement(msg);
				System.out.println("Satellite vers rover: " + msg.getCompte());
			}
			
		}finally {
			lock.unlock();
		}
	}

	@Override
	public void run() {
		
		while(true) {
			
			/*
			 * (5.3) Insérer votre code ici 
			 */
			try{
				Message msgRover = fileRover.enleverElement();
				System.out.println("Satellite : " + msgRover);
				rover.receptionMessageDeSatellite(msgRover);
				Message msgCentre = fileCentre.enleverElement();
				System.out.println("Satellite : " + msgCentre);
				centreControle.receptionMessageDeSatellite(msgCentre);
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

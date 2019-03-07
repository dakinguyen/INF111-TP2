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

import modele.communication.Message;
import utilitaires.Liste;

public class SatelliteRelai extends Thread{
	
	static final int TEMPS_CYCLE_MS = 500;
	static final double PROBABILITE_PERTE_MESSAGE = 0.15;
	
	ReentrantLock lock = new ReentrantLock();
	
	private Random rand = new Random();
	
	
	/**
	 * Méthode permettant d'envoyer un message vers le centre d'opération
	 * @param msg, message à envoyer
	 */
	public void envoyerMessageVersCentrOp(Message msg) {
		
		lock.lock();
		
		try {
			Liste centrOp = new Liste();
			double nbAlea = rand.nextDouble();
			
			if (nbAlea > PROBABILITE_PERTE_MESSAGE) {
				centrOp.ajouterElement(msg);
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
			Liste rover = new Liste();
			double nbAlea = rand.nextDouble();
			
			if (nbAlea > PROBABILITE_PERTE_MESSAGE) {
				rover.ajouterElement(msg);
			}
			
		}finally {
			lock.unlock();
		}
	}

	@Override
	public void run() {
		
		while(true) {
			
			try {
				rover.enleverElement();
			} catch (Exception e) {
				System.out.println("La liste rover est vide!");
			}
			
			try {
				centrOp.enleverElement();
			} catch (Exception e) {
				System.out.println("La liste centrOp est vide!");
			}


			// attend le prochain cycle
			try {
				Thread.sleep(TEMPS_CYCLE_MS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	

}

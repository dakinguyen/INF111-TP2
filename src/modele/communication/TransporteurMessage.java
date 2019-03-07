package modele.communication;
/**
 * Classe qui implémente le protocol de communication entre le Rover
 * et le Centre d'opération.
 * 
 * Il se base sur une interprétation libre du concept de Nack:
 * 	https://webrtcglossary.com/nack/
 *  
 * Les messages envoyés sont mémorisé. À l'aide du compte unique
 * le transporteur de message peut identifier les Messages manquant
 * dans la séquence et demander le renvoi d'un Message à l'aide du Nack.
 * 
 * Pour contourner la situation ou le Nack lui-même est perdu, le Nack
 * est renvoyé periodiquement, tant que le Message manquant n'a pas été reçu.
 * 
 * C'est également cette classe qui gère les comptes unique.
 * 
 * Les messages reçu sont mis en file pour être traité.
 * 
 * La gestion des messages reçu s'effectue comme une tâche s'exécutant indépendamment (Thread)
 * 
 * Quelques détails:
 *  - Le traitement du Nack a priorité sur tout autre message.
 *  - Un message NoOp est envoyé périodiquement pour s'assurer de maintenir
 *    une communication active et identifier les messages manquants en bout de séquence.
 * 
 * Services offerts:
 *  - TransporteurMessage
 *  - receptionMessageDeSatellite
 *  - run
 * 
 * @author Frederic Simard, ETS
 * @version Hiver, 2019
 */

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

public abstract class TransporteurMessage extends Thread {
	
	// compteur de message
	protected CompteurMessage compteurMsg;
	// lock qui protège la liste de messages reçu
	private ReentrantLock lock = new ReentrantLock();
	
	/**
	 * Constructeur, initialise le compteur de messages unique
	 */
	public TransporteurMessage() {
		compteurMsg = new CompteurMessage();		
	}
	
	/**
	 * Méthode gérant les messages reçu du satellite. La gestion se limite
	 * à l'implémentation du Nack, les messages spécialisé sont envoyés
	 * aux classes dérivés
	 * @param msg, message reçu
	 */
	public void receptionMessageDeSatellite(Message msg) {
		lock.lock();
		
		try {
			/* DQ
			ArrayList<Message> msgRecu = new ArrayList<Message>();
			
			if (msg instanceof Nack) {
				msgRecu.add(msg);
			} else {
				
				for (int i = msgRecu.size(); i > msg.getCompte(); i--) {
					msgRecu.add(i, msgRecu.get(i - 1));
				}
				
				msgRecu.add(msg.getCompte(), msg);
			}
			*/
		}finally {
			lock.unlock();
		}
	}

	@Override
	/**
	 * Tâche effectuant la gestion des messages reçu
	 */
	public void run() {
		
		int compteCourant = 0;
		
		while(true) {
			
			lock.lock();
			
			try {
				/*
				Vector<Message> msgAEnvoyer = new Vector<Message>();
				boolean nackEnvoye = false;
				int compteurMsg = 0;
				
				while (msgAEnvoyer.size() > 0 && nackEnvoye == false) {
					Message aGerer = msgAEnvoyer.get(compteCourant);
					
					if (aGerer instanceof Nack) {
						
						for (int i = 0; i < msgAEnvoyer.size(); i++) {
							if (aGerer.getCompte() > (msgAEnvoyer.get(i)).getCompte()) {
								msgAEnvoyer.remove(i);
							}
						}
						
						//peek le message a envoyer
						envoyerMessage(aGerer);
						
					} else if (compteCourant != (msgAEnvoyer.get(compteCourant)).getCompte()) {
						Nack msgManquant = new Nack(compteCourant);
						
						envoyerMessage(msgManquant);
						nackEnvoye = true;
						
					} else if (compteCourant > (msgAEnvoyer.get(compteCourant)).getCompte()) {
						msgAEnvoyer.remove(compteCourant);
						
					} else {
						gestionnaireMessage(aGerer);
						//defile le message
						compteCourant++;
					}
					
					compteurMsg++;
					NoOp msgNoOp = new NoOp(compteurMsg);
					envoyerMessage(msgNoOp);
				}
				*/
			
			}finally{
				lock.unlock();
			}
			
			// pause, cycle de traitement de messages
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * méthode abstraite utilisé pour envoyer un message
	 * @param msg, le message à envoyer
	 */
	abstract protected void envoyerMessage(Message msg);

	/**
	 * méthode abstraite utilisé pour effectuer le traitement d'un message
	 * @param msg, le message à traiter
	 */
	abstract protected void gestionnaireMessage(Message msg);

	

}

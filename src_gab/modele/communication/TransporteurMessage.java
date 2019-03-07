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

	private Vector<Message> messageRecu = new Vector<>();
	private Vector<Message> messageEnvoye = new Vector<>();
	
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
			
			/*
			 * (6.3.3) Insérer votre code ici 
			 */

			if(msg instanceof Nack) {
				messageRecu.add(0, msg);

			} else {
				int size = messageRecu.size();
				boolean placed = false;
				int index =0;

				if (messageRecu.get(size - 1).getCompte() < msg.getCompte()){
					messageRecu.add(msg);
				}

				while (!placed || index < size -1) {
					if (messageRecu.get(index) instanceof Nack) {
						index ++;
					} else if (msg.getCompte() < messageRecu.get(index + 1).getCompte() ){
						placed = true;
						messageRecu.add(index, msg);
					} else {
						index ++;
					}
				}
			}
			
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
		boolean nackSent = false;
		
		while(true) {
			
			lock.lock();
			
			try {

				/*
				 * (6.3.4) Insérer votre code ici 
				 */

				while(!(messageRecu.isEmpty()) && !nackSent) {
					Message msg = messageRecu.firstElement();

					if (msg instanceof Nack) {
						int compteManquant = msg.getCompte();
						boolean found = false;
						int index = 0;

						for (int i = 0; i < messageEnvoye.size(); i++) {
							if (msg.getCompte() == messageEnvoye.get(i).getCompte()) {
								found = true;
								index = i;
							} else if (found) {
								messageEnvoye.remove(i);
							}
						}
						Message toSend = messageEnvoye.get(index);
						envoyerMessage(toSend);
						messageRecu.remove(0);

					} else if (msg.getCompte() > compteCourant) {
						envoyerMessage(new Nack(compteCourant));
						nackSent =true;

					} else if (msg.getCompte() < compteCourant) {
						messageRecu.remove(0);

					} else {
						gestionnaireMessage(msg);
						messageRecu.remove(0);
						compteCourant++;
					}
					int compteNoOp = compteurMsg.getCompteActuel();
					envoyerMessage(new NoOp(compteNoOp));

				}
			
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

	public void ajouteEnvoye(Message msg) {
		messageEnvoye.add(0, msg);
	}

	public void ajouteRecu(Message msg) {
		messageRecu.add(0, msg);
	}

	

}

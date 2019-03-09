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
				int index =size;

				if(messageRecu.isEmpty()) {
					System.out.println("first add");
					messageRecu.add(msg);
					placed = true;
				} else if (messageRecu.get(size - 1).getCompte() < msg.getCompte() &&
						!(messageRecu.get(size -1) instanceof Nack)){
					messageRecu.add(msg);
					placed = true;
				}

				while (!placed || index > 0 ) {
					if (messageRecu.get(index) instanceof Nack) {
						messageRecu.add(index + 1, msg);
					} else if (msg.getCompte() > messageRecu.get(index).getCompte() ){
						placed = true;
						messageRecu.add(index + 1, msg);
					} else {
						index --;
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

				System.out.println("Compte courant: " + compteCourant + " " + this);
				System.out.println(messageRecu.size());

				while(!(messageRecu.isEmpty()) && !nackSent) {
					Message msg = messageRecu.get(0);


					if (msg instanceof Nack) {
						System.out.println("Nack: " + msg.getCompte());
						int compteManquant = msg.getCompte();
						boolean found = false;
						int index = 0;
						int size = messageEnvoye.size();

						for (int i = 0; i < size; i++) {
							if (found) {
								messageEnvoye.remove(i);
							}else if (messageEnvoye.get(i) instanceof Nack) {
								messageEnvoye.remove(i);
							}else if (compteManquant == messageEnvoye.get(i).getCompte()) {
								found = true;
								index = i;
							}
						}
						Message toSend = messageEnvoye.get(index);
						envoyerMessage(toSend);
						messageRecu.remove(0);

					} else if (msg.getCompte() > compteCourant) {
						System.out.println("Missing: " + (msg.getCompte() - 1));
						envoyerMessage(new Nack(compteCourant));
						nackSent =true;

					} else if (msg.getCompte() < compteCourant) {
						System.out.println("Duplicate: " + msg.getCompte());
						messageRecu.remove(0);

					} else {
						System.out.println("Normal: " + msg.getCompte());
						gestionnaireMessage(msg);
						messageRecu.remove(0);
						compteCourant++;
					}
					/*System.out.println("transporteur " + msg.getClass() + ": " + msg.getCompte());
					int compteNoOp = compteurMsg.getCompteActuel();
					envoyerMessage(new NoOp(compteNoOp + 100));*/

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
		messageRecu.add(msg);
	}

	

}

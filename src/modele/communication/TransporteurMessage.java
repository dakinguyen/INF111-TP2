package modele.communication;
/**
 * Classe qui implÃ©mente le protocol de communication entre le Rover
 * et le Centre d'opÃ©ration.
 * 
 * Il se base sur une interprÃ©tation libre du concept de Nack:
 * 	https://webrtcglossary.com/nack/
 *  
 * Les messages envoyÃ©s sont mÃ©morisÃ©. Ã€ l'aide du compte unique
 * le transporteur de message peut identifier les Messages manquant
 * dans la sÃ©quence et demander le renvoi d'un Message Ã  l'aide du Nack.
 * 
 * Pour contourner la situation ou le Nack lui-mÃªme est perdu, le Nack
 * est renvoyÃ© periodiquement, tant que le Message manquant n'a pas Ã©tÃ© reÃ§u.
 * 
 * C'est Ã©galement cette classe qui gÃ¨re les comptes unique.
 * 
 * Les messages reÃ§u sont mis en file pour Ãªtre traitÃ©.
 * 
 * La gestion des messages reÃ§u s'effectue comme une tÃ¢che s'exÃ©cutant indÃ©pendamment (Thread)
 * 
 * Quelques dÃ©tails:
 *  - Le traitement du Nack a prioritÃ© sur tout autre message.
 *  - Un message NoOp est envoyÃ© pÃ©riodiquement pour s'assurer de maintenir
 *    une communication active et identifier les messages manquants en bout de sÃ©quence.
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

import modele.rover.Rover;
import modele.centreControle.CentreControle;

public abstract class TransporteurMessage extends Thread {
	
	// compteur de message
	protected CompteurMessage compteurMsg;
	// lock qui protÃ¨ge la liste de messages reÃ§u
	private ReentrantLock lock = new ReentrantLock();
	
	/**
	 * Constructeur, initialise le compteur de messages unique
	 */
	public TransporteurMessage() {
		compteurMsg = new CompteurMessage();		
	}
	
	/**
	 * MÃ©thode gÃ©rant les messages reÃ§u du satellite. La gestion se limite
	 * Ã  l'implÃ©mentation du Nack, les messages spÃ©cialisÃ© sont envoyÃ©s
	 * aux classes dÃ©rivÃ©s
	 * @param msg, message reÃ§u
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
	 * TÃ¢che effectuant la gestion des messages reÃ§u
	 */
	public void run() {
		
		int compteCourant = 0;
		
		while(true) {
			
			lock.lock();
			
			try {
				/* DQ
				Vector<Message> msgAEnvoyer = new Vector<Message>();
				boolean nackEnvoye = false;
				CompteurMessage compteurMsg = new CompteurMessage();
				int compteur;
				
				while (msgAEnvoyer.size() > 0 && nackEnvoye == false) {
					Message aGerer = msgAEnvoyer.get(compteCourant);
					
					if (aGerer instanceof Nack) {
						
						for (int i = 0; i < msgAEnvoyer.size(); i++) {
							if (aGerer.getCompte() > (msgAEnvoyer.get(i)).getCompte()) {
								msgAEnvoyer.remove(i);
							}
						}
						
						//peek le message a envoyer
						Rover.envoyerMessage(aGerer);
						
					} else if (compteCourant != (msgAEnvoyer.get(compteCourant)).getCompte()) {
						Nack msgManquant = new Nack(compteCourant);
						
						Rover.envoyerMessage(msgManquant);
						nackEnvoye = true;
						
					} else if (compteCourant > (msgAEnvoyer.get(compteCourant)).getCompte()) {
						msgAEnvoyer.remove(compteCourant);
						
					} else {
						Rover.gestionnaireMessage(aGerer);
						//defile le message
						compteCourant++;
					}
					
					compteur = compteurMsg.getCompteActuel();
					NoOp msgNoOp = new NoOp(compteur);
					CentreControle.envoyerMessage(msgNoOp);
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
	 * mÃ©thode abstraite utilisÃ© pour envoyer un message
	 * @param msg, le message Ã  envoyer
	 */
	abstract protected void envoyerMessage(Message msg);

	/**
	 * mÃ©thode abstraite utilisÃ© pour effectuer le traitement d'un message
	 * @param msg, le message Ã  traiter
	 */
	abstract protected void gestionnaireMessage(Message msg);

	

}

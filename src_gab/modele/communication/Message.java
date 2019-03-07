package modele.communication;
/**
 * Classe de base qui définit un message.
 * 
 * Elle contient les informations permettant d'identifier la position 
 * du message dans la séquence et le temps de son envoi
 * 
 * Services offerts:
 *  - Message
 *  - getTempsEnvoi
 *  - setTempsEnvoi
 *  - getCompte
 * 
 * @author Frederic Simard, ETS
 * @version Hiver, 2019
 */


public class Message {

	private int compte;
	private long tempsEnvoi;

    /**
     * Constructeur, requiert un compte unique pour identifier sa position dans
     * la séquence de messages envoyés
     * @param compte, identifiant unique et incrémental
     */
	public Message(int compte) {
		this.compte = compte; 
		tempsEnvoi = System.currentTimeMillis(); // prend en note le temps de l'envoi
	}
	
	/**
	 * Accesseur informateur, pour obtenir le temps de l'envoi
	 * @return long, temps de l'envoi
	 */
	public long getTempsEnvoi() {
		return tempsEnvoi;
	}

	/**
	 * Accesseur mutateur, pour mettre à jours le temps de l'envoi
	 * @param long, temps de la répétition de l'envoi
	 */
	public void setTempsEnvoi(long tempsEnvoi) {
		this.tempsEnvoi = tempsEnvoi;
	}

	/**
	 * Accesseur informateur, pour obtenir le compte unique
	 * @return int, compte unique
	 */
	public int getCompte() {
		return compte;
	}

	
}

package modele.communication;

import utilitaires.*;

public class Commande extends Message {

	private eCommande champ = eCommande.NULLE;
	private Vect2D destination;
	
	public Commande(int compte) {
		super(compte);
	}
	
	public eCommande getCommande() {
		return this.champ;
	}
	
	private void changeDestination() {
		
		switch(this.champ){
			case NULLE:
				this.champ = eCommande.NULLE;
				this.destination = new Vect2D(0,0);
				break;
				
			default:
				this.champ = eCommande.NULLE;
				this.destination = new Vect2D(0,0);
		}
	}
	
	public Vect2D getDestination() {
		
		return this.destination;
	}
}

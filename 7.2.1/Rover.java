package modele.rover;

import modele.satelliteRelai.SatelliteRelai;
import modele.communication.*;
import utilitaires.*;

public class Rover extends TransporteurMessage {

    private SatelliteRelai satellite;
    private Commande cmd;
    private Vect2D posActuel;
    private eCommande typeCommande;
    private Status posActuelStatus;
    
    private final double VITESSE_MparS = 0.5;

    public Rover(SatelliteRelai satellite, Vect2D posActuel) {
        this.satellite = satellite;
        this.posActuel = posActuel;
    }

    @Override
    public void envoyerMessage(Message msg) {
        System.out.println("Rover vers satellite: " + msg.getCompte());
        satellite.envoyerMessageVersCentrOp(msg);
        ajouteEnvoye(msg);
    }

    @Override
    public void gestionnaireMessage(Message msg) {
        System.out.println("Class: " + msg.getClass() + ", numero: " + msg.getCompte());
        cmd = new Commande(msg.getCompte());
        if (cmd.getCommande() == eCommande.DEPLACER_ROVER) {
        	deplacerRover(cmd.getDestination());
        }
    }
    
    private void deplacerRover(Vect2D destination) {
    	Vect2D deplacement = posActuel.calculerDiff(destination);
    	double distance = deplacement.getLongueur();
    	double tempsRequis = distance/VITESSE_MparS;
    	double angleDeplacement = deplacement.getAngle();
    	
    	
    	// envoyer message status satellite.envoyerMessageVersRover(msg);
    	
    	for (int i = 0; i < (int) tempsRequis; i ++) {
    		posActuel.ajouter(Math.cos(angleDeplacement) * VITESSE_MparS, Math.sin(angleDeplacement) * VITESSE_MparS);
    		// envoyer message status
    	}
    	
    	double fractionRequise = tempsRequis - tempsRequis % 1;
    	
    	if (fractionRequise > 0) {
    	
    		posActuel.ajouter(Math.cos(angleDeplacement) * VITESSE_MparS * fractionRequise, Math.sin(angleDeplacement) * VITESSE_MparS * fractionRequise);
       	}
    	
    	// envoyer message statusRover
    }
}

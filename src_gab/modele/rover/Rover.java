package modele.rover;

/**
 * Cette classe definit le Rover
 * 
 * Elle herite de la classe message et contient les informations necessaires a l'envoie
 * d'un message et a la gestion des messages et au deplacement du Rover
 * 
 * Service offert:
 * - envoyerMessage
 * - gestionnaireMessage d'un message
 * - gestionnaire d'une commande
 * - deplacerRover
 * 
 * @author Dat Quang Nguyen, Sara Nguyen, Emerick Paul, Gabriel Somma
 * @version Hiver 2019
 *
 */

import modele.communication.*;
import utilitaires.*;
import modele.satelliteRelai.SatelliteRelai;

public class Rover extends TransporteurMessage {

    private SatelliteRelai satellite;
    private Commande cmd;
    private Vect2D posActuel;
    private eCommande typeCommande;
    private Status posActuelStatus;

    private final double VITESSE_MparS = 0.5;

    /**
     * Constructeur par parametre du Rover
     * @param satellite, le SatelliteRelai
     * @param posActuel, la position actuel du satellite
     */
    public Rover(SatelliteRelai satellite, Vect2D posActuel) {
        this.satellite = satellite;
        this.posActuel = posActuel;
    }


    @Override
    /**
     * Envoyer le message vers le Centre de Controle
     * @param msg, le message
     */
    public void envoyerMessage(Message msg) {
        satellite.envoyerMessageVersCentrOp(msg);
        ajouteEnvoye(msg);
    }

    @Override
    /**
     * Interpreter le message. La fonction garde en memoire le numero du prochain message a traiter
     * @param msg, le message
     */
    public void gestionnaireMessage(Message msg) {
        System.out.println("Class: " + msg.getClass() + ", numero: " + msg.getCompte());
        if (msg instanceof Commande) {
            gestionnaireMessage((Commande) msg);
        }
    }

    /**
     * Verifier la commande et si elle dit de deplacer le rover, le faire
     * @param cmd, la commande
     */
    public void gestionnaireMessage(Commande cmd) {
        this.cmd = new Commande(cmd.getCompte(), cmd.getDestination());
        if (this.cmd.getCommande() == eCommande.DEPLACER_ROVER) {
            deplacerRover(cmd.getDestination());
        }
    }

    /**
     * Deplacer le Rover vers la destination desiree
     * @param destination, la destination desiree
     */
    private void deplacerRover(Vect2D destination) {
    	
    	// trouver le vecteur de placement, la distance de celle-ci, le temps requis pour faire le deplacement et l'angle du vecteur de deplacement
        Vect2D deplacement = posActuel.calculerDiff(destination);
        double distance = deplacement.getLongueur();
        double tempsRequis = distance/VITESSE_MparS;
        double angleDeplacement = deplacement.getAngle();
        
        // envoyer le message du status
        envoyerMessage(new Status(0, posActuel));

        // envoyer le message status tout le long du deplacement
        for (int i = 0; i < (int) tempsRequis; i ++) {
            posActuel.ajouter(Math.cos(angleDeplacement) * VITESSE_MparS, Math.sin(angleDeplacement) * VITESSE_MparS);
            envoyerMessage(new Status((i + 1), posActuel));
        }

        // ajouter la petite partie du temps restant pour completer le trajet
        int tempsInt = (int)(tempsRequis/1);
        double fractionRequise = tempsRequis - tempsInt;

        if (fractionRequise > 0) {

            posActuel.ajouter(Math.cos(angleDeplacement) * VITESSE_MparS * fractionRequise, Math.sin(angleDeplacement) * VITESSE_MparS * fractionRequise);
        }

        System.out.println(posActuel.toString());

        envoyerMessage(new Status(9999, posActuel));
    }



}

package modele.centreControle;

import modele.communication.Message;
import modele.communication.TransporteurMessage;
import modele.satelliteRelai.SatelliteRelai;

/**
 * Cette classe definit le Centre de Controle
 * 
 * Elle herite de la classe message et contient les informations necessaires a l'envoie
 * d'un message et a la gestion des messages
 * 
 * Service offert:
 * - envoyerMessage
 * - gestionnaireMessage
 * 
 * @author Dat Quang Nguyen, Sara Nguyen, Emerick Paul, Gabriel Somma
 * @version Hiver 2019
 *
 */

public class CentreControle extends TransporteurMessage {

    private SatelliteRelai satellite;

    public CentreControle(SatelliteRelai satellite) {
        this.satellite = satellite;

    }

    @Override
    public void envoyerMessage(Message msg) {
        satellite.envoyerMessageVersRover(msg);
        ajouteEnvoye(msg);
    }

    @Override
    public void gestionnaireMessage(Message msg) {
        System.out.println("Class: " + msg.getClass() + ", numero: " + msg.getCompte());
    }
}

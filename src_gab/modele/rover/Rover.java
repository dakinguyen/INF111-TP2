package modele.rover;

import modele.communication.Message;
import modele.communication.TransporteurMessage;
import modele.satelliteRelai.SatelliteRelai;

public class Rover extends TransporteurMessage {

    private SatelliteRelai satellite;

    public Rover(SatelliteRelai satellite) {
        this.satellite = satellite;
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
    }

}

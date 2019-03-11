package modele.centreControle;

import modele.communication.Message;
import modele.communication.TransporteurMessage;
import modele.satelliteRelai.SatelliteRelai;

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

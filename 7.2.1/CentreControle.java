package modele.centreControle;

import modele.communication.Commande;
import modele.communication.Message;
import modele.communication.TransporteurMessage;
import modele.satelliteRelai.SatelliteRelai;
import utilitaires.eCommande;

public class CentreControle extends TransporteurMessage {

    private SatelliteRelai satellite;
    private Commande cmd;
    private eCommande typeCommande;

    public CentreControle(SatelliteRelai satellite) {
        this.satellite = satellite;

    }

    @Override
    public void envoyerMessage(Message msg) {
        System.out.println("Centre vers satellite: " + msg.getCompte());
        satellite.envoyerMessageVersRover(msg);
        ajouteEnvoye(msg);
    }

    @Override
    public void gestionnaireMessage(Message msg) {
        System.out.println("Class: " + msg.getClass() + ", numero: " + msg.getCompte());
        cmd = new Commande(msg.getCompte());
        typeCommande = cmd.getCommande();
    }
}

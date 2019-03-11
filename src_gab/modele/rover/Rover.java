package modele.rover;

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

    public Rover(SatelliteRelai satellite, Vect2D posActuel) {
        this.satellite = satellite;
        this.posActuel = posActuel;
    }

    @Override
    public void envoyerMessage(Message msg) {
        satellite.envoyerMessageVersCentrOp(msg);
        ajouteEnvoye(msg);
    }

    @Override
    public void gestionnaireMessage(Message msg) {
        System.out.println("Class: " + msg.getClass() + ", numero: " + msg.getCompte());
        if (msg instanceof Commande) {
            gestionnaireMessage((Commande) msg);
        }
    }

    public void gestionnaireMessage(Commande cmd) {
        cmd = new Commande(cmd.getCompte(), cmd.getDestination());
        if (cmd.getCommande() == eCommande.DEPLACER_ROVER) {
            deplacerRover(cmd.getDestination());
        }
    }

    private void deplacerRover(Vect2D destination) {
        Vect2D deplacement = posActuel.calculerDiff(destination);
        double distance = deplacement.getLongueur();
        double tempsRequis = distance/VITESSE_MparS;
        double angleDeplacement = deplacement.getAngle();
        

        envoyerMessage(new Status(0, posActuel));

        for (int i = 0; i < (int) tempsRequis; i ++) {
            posActuel.ajouter(Math.cos(angleDeplacement) * VITESSE_MparS, Math.sin(angleDeplacement) * VITESSE_MparS);
            envoyerMessage(new Status((i + 1), posActuel));
        }

        int tempsInt = (int)(tempsRequis/1);
        double fractionRequise = tempsRequis - tempsInt;

        if (fractionRequise > 0) {

            posActuel.ajouter(Math.cos(angleDeplacement) * VITESSE_MparS * fractionRequise, Math.sin(angleDeplacement) * VITESSE_MparS * fractionRequise);
        }

        System.out.println(posActuel.toString());

        envoyerMessage(new Status(9999, posActuel));
    }



}

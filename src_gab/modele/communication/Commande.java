package modele.communication;

import utilitaires.*;

/**
 * Classe qui definit une commande
 *
 * Elle herite de la classe message et contient les informations necessaire a un deplacement
 *
 * Service offert:
 * -getCommande
 * -getDestination
 *
 *
 */

public class Commande extends Message {

    private eCommande champ = eCommande.NULLE;
    private Vect2D destination;

    /**
     * Constructeur par parametre
     * @param compte, le compte du message
     * @param vect2D, le deplacement de la commande
     */
    public Commande(int compte, Vect2D vect2D) {
        super(compte);
        destination = vect2D;
        champ = eCommande.DEPLACER_ROVER;
    }

    /**
     * accesseur de la valeur de champ
     * @return la valeur de champ
     */
    public eCommande getCommande() {
        return this.champ;
    }

    /**
     * Pour arreter le deplacement
     */
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

    /**
     * retourne la destination de la commande
     * @return destination
     */
    public Vect2D getDestination() {

        return this.destination;
    }
}

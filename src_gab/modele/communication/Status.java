package modele.communication;

import utilitaires.*;

/**
 * Classe qui definit un status
 *
 * Elle herite de la classe message et contient les informations necessaire a une position
 *
 * Service offert:
 * -getStatus
 *
 */

public class Status extends Message {

    private Vect2D position;

    /**
     * Constructeur par parametre
     * @param compte, le compte du message
     * @param position, la position actuelle retourne par le message
     */
    public Status(int compte, Vect2D position) {
        super(compte);
        this.position = position;
    }

    /**
     * Retourne l'attribut position
     * @return position
     */
    public Vect2D getStatus() {
        return this.position;
    }
}

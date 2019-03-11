package modele.communication;

/**
 *Classe herite de la classe message, elle definit un message Nack
 * Rien de different qu'un message normal autre que sa classe
 */

public class Nack extends Message {

    public Nack(int compte){
        super(compte);
    }
}

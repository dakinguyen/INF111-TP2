package modele.communication;

/**
 *Classe herite de la classe message, elle definit un message NoOp
 * Rien de different qu'un message normal autre que sa classe
 */

public class NoOp extends Message {

    public NoOp(int compte) {
        super(compte);
    }
}

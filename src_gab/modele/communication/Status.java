package modele.communication;

import utilitaires.*;

public class Status extends Message {

    private Vect2D position;

    public Status(int compte, Vect2D position) {
        super(compte);
        this.position = position;
    }

    public Vect2D getStatus() {
        return this.position;
    }
}

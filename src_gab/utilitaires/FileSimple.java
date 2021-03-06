package utilitaires;

/**
 * Cette classe permet la creation d'une file
 * 
 * Services offerts:
 * - ajouterElement
 * - enleverElement
 * - estVide
 * - getNbElem
 * 
 * @author Dat Quang Nguyen, Sara Nguyen, Emerick Paul, Gabriel Somma
 * @version Hiver 2019
 *
 */

import modele.communication.Message;

public class FileSimple {
    private Noeud tete = null;
    private int nbElement = 0;

    class Noeud {
        private Message element;
        private Noeud suivant = null;

        public Noeud(Message element) {
            this.element = element;
        }
    }

    /**
     * constructeur par defaut
     */
    public FileSimple(){}

    /**
     *Ajoute un element a la file, le nouvel element est maintenant definit par l'attribut tete
     * et ce dernier est son suivant
     * @param element a ajouter
     */
    public void ajouterElement(Message element) {
        Noeud nouveau = new Noeud(element);

        nouveau.suivant = this.tete;
        this.tete = nouveau;
        this.nbElement++;
    }

    /**
     * Enleve le first in, se rend a la fin de la file l'enleve en mettant
     * le pointeur suivant de l'element precedent a null
     * @return l'element enleve
     * @throws Exception si la file est vide
     */
    public Message enleverElement() throws NullPointerException {
        if (estVide()) {
            throw new NullPointerException("La file est vide");
        }

        if (tete.suivant == null) {
            Noeud toReturn = this.tete;
            this.tete = null;
            this.nbElement--;
            return toReturn.element;
        }
        Noeud courant = this.tete;
        Noeud dernier;

        do{
            dernier = courant;
            courant = courant.suivant;
        }while (courant.suivant != null);

        dernier.suivant = null;
        nbElement--;
        return courant.element;

    }

    /**
     * Trouver si la file est vide ou s'il contient des elements
     * @return true si elle est vide, sinon false
     */
    public boolean estVide(){
        return !(this.nbElement > 0);
    }

    /**
     * Trouver le nombre d'elements dans la file
     * @return nbElement, le nombre d'elements
     */
    public int getNbElem() {
        return nbElement;
    }
}

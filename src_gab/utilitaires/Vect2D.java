package utilitaires;
import java.lang.Math;

/**
 *
 */
public class Vect2D {
    private double long_x;
    private double long_y;

    /**
     * Constructeur par defaut
     */
    public Vect2D() {};

    /**
     * Contructeur par parametre
     * @param long_x
     * @param long_y
     */
    public Vect2D(double long_x, double long_y) {
        this.long_x = long_x;
        this.long_y = long_y;
    }

    /**
     * Constructeur par copie
     * @param vect2D
     */
    public Vect2D(Vect2D vect2D) {
        this.long_x = vect2D.long_x;
        this.long_y = vect2D.long_y;
    }

    /**
     * Accesseur de x
     * @return la valeur de la longueur x
     */
    public double getX() {
        return this.long_x;
    }

    /**
     * Accesseur de y
     * @return la valeur de la longueur y
     */
    public double getY() {
        return this.long_y;
    }

    /**
     * Calcule la longueur du vecteur avec les deux attribut x et y
     * @return la longueur
     */
    public double getLongueur() {
        double longueur = Math.sqrt(Math.pow(this.long_x, 2) + Math.pow(this.long_y,2));
        return longueur;
    }

    /**
     * Calcule l'orientation du vecteur avec tan-1
     * @return la valeur de l'angle
     */
    public double getAngle() {
        double angle = Math.atan(this.long_y/this.long_x);
        return angle;
    }

    /**
     * Calcule le vecteur difference entre celui en parametre et le this
     * @param posFin, vecteur soustrait
     * @return le vecteur resultant
     */
    public Vect2D calculerDiff(Vect2D posFin) {
        double newY = posFin.long_y - this.long_y;
        double newX = posFin.long_x - this.long_x;
        return new Vect2D(newX, newY);
    }

    /**
     * Effectue une division scalaire sur les attributs du vecteur
     * @param diviseur
     * @throws ArithmeticException, pour les divisions par zero
     */
    public void diviser(double diviseur) throws ArithmeticException {
        if (diviseur == 0) {
            throw new ArithmeticException("Division par zero");
        }
        this.long_x = this.long_x / diviseur;
        this.long_y = this.long_y / diviseur;
    }

    /**
     * Addition des attributs selon une coordonnee donnee
     * @param x, additionne a l'attribut longX
     * @param y, additionne a l'attribut longY
     */
    public void ajouter(double x, double y) {
        this.long_y += y;
        this.long_x += x;
    }

    /**
     * represente la classe sous forme de chaine de caracteres
     * @return la representation string
     */
    public String toString() {
        String str = "longueur x: " + this.long_x + ", longueur y: " + this.long_y;
        return str;
    }

    /**
     * compare deux vecteurs par leurs attributs
     * @param vect2D, vecteur a compare avec le this
     * @return un booleen de comparaison
     */
    public boolean equals(Vect2D vect2D) {
        if (this.long_x == vect2D.long_x && this.long_y == vect2D.long_y) {
            return true;
        }
        return false;
    }

}














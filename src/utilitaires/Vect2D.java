package utilitaires;

public class Vect2D {
	
	private double x;
	private double y;
	
	/**
	 * Constructeur par defaut
	 */
	public Vect2D() {
		
	}
	
	/**
	 * Constructeur par parametres
	 * @param x : longueur en x
	 * @param y : longueur en y
	 */
	public Vect2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Constructeur par copie
	 * @param vecteur : Vect2D a copier
	 */
	public Vect2D(Vect2D vecteur) {
		this.x = vecteur.x;
		this.y = vecteur.y;
	}
	
	/**
	 * Permet de trouver la valeur de x
	 * @return la valeur de x
	 */
	public double getX() {
		return this.x;
	}
	
	/**
	 * Permet de trouver la valeur de y
	 * @return la valeur de y
	 */
	public double getY() {
		return this.y;
	}
	
	/**
	 * Permet d'obtenir la longueur du vecteur
	 * @return la longueur du vecteur
	 */
	public double getLongueur() {
		return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
	}
	
	/**
	 * Permet d'obtenir l'angle entre les deux longueur du vecteur
	 * @return l'angle du vecteur
	 */
	public double getAngle() {
		return Math.atan2(this.y, this.x);
	}
	
	/**
	 * Calculer la difference entre deux vecteurs
	 * @param posFin : vecteur de la position finale
	 * @return un vecteur qui contient la difference de x et de y entre la position finale et le vecteur initial
	 */
	public Vect2D calculerDiff(Vect2D posFin) {
		Vect2D diff = new Vect2D();
		diff.x = posFin.x - this.x;
		diff.y = posFin.y - this.y;
		
		return diff;
	}
	
	/**
	 * Les variables membres du vecteurs sont divisees par la valeur entree
	 * @param a : le diviseur
	 */
	public void diviser(double a) {
		this.x = this.x / a;
		this.y = this.y / a;
	}
	
	
	/**
	 * Ajoute des valeurs x et y au variables x et y du vecteur
	 * @param x : Valeur a ajouter en x
	 * @param y : Valeur a ajouter en y
	 */
	public void ajouter(double x, double y) {
		this.x = this.x + x;
		this.y = this.y + y;		
	
	}
	
	/**
	 * Transforme le vecteur en chaine de caractere
	 */
	public String toString() {
		return "La valeur de x du vecteur est " + this.x + " et la valeur en y du vecteur est " + this.y + ".";
	}
	
	/**
	 * Compare le vecteur avec un autre vecteur pour voir s'ils sont equals
	 * @param compare : vecteur avec lequel il est compare
	 * @return true si les deux vecteurs sont egaux sinon false
	 */
	public boolean equals(Vect2D compare) {
		boolean valid = false;
		
		if (compare.x == this.x && compare.y == this.y) {
			valid = true;
		}
		
		return valid;
	}
}

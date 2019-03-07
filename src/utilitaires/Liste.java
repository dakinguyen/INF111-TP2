package utilitaires;

public class Liste {

	private Object[] tab;
	private int nbElem = 0;
	
	public Liste() {
		
	}
	
	public Liste(int maxElem) {
		this.tab = new Object[maxElem];
	}
	
	public void ajouterElement(Object element) {
		for (int i = nbElem; i > 0; i --) {
			tab[i] = tab[i - 1];
		}
		
		tab[0] = element;
		nbElem ++;
	}
	
	public Object enleverElement() throws Exception{
		
		if (estVide()) {
			throw new Exception("La liste est vide!");
		}
		Object removed = tab[nbElem - 1];
		
		nbElem --;
		
		return removed;
	}
	
	public boolean estVide() {
		boolean valid = false;
		
		if (this.nbElem == 0) {
			valid = true;
		}
		
		return valid;
	}
	
	
	public void printValues() {
		for (int i = 0; i < nbElem; i ++) {
			System.out.println(this.tab[i]);
		}
	}
}

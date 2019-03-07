package programme;

import java.io.IOException;

import modele.satelliteRelai.SatelliteRelai;
import utilitaires.Liste;

public class ProgrammePrincipal {

	/**
	 * Programme principale, instancie les éléments de la simulation,
	 * les lie entre eux, puis lance la séquence de test.
	 * @param args, pas utilisé
	 */
	public static void main(String[] args){
	
		//SatelliteRelai satellite = new SatelliteRelai();
		//satellite.start();
		
		validation();
	}
	
	public static void validation() {
		Liste tab = new Liste(10);
		
		tab.ajouterElement(1);
		tab.ajouterElement(15);
		tab.ajouterElement(60);
		/*try{
			tab.enleverElement();
		} catch (Exception e) {
			System.out.println("Something went wrong.");
		}*/
		
		tab.printValues();
	}

}

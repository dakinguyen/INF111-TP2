package programme;

import java.io.IOException;

import modele.centreControle.CentreControle;
import modele.communication.Message;
import modele.rover.Rover;
import modele.satelliteRelai.SatelliteRelai;
import utilitaires.*;

public class ProgrammePrincipal {

	/**
	 * Programme principale, instancie les éléments de la simulation,
	 * les lie entre eux, puis lance la séquence de test.
	 * @param args, pas utilisé
	 */
	public static void main(String[] args){
	
		SatelliteRelai satellite = new SatelliteRelai();
		CentreControle centreControle = new CentreControle(satellite);
		Rover rover = new Rover(satellite);
		satellite.lierCentrOp(centreControle);
		satellite.lierRover(rover);

		for (int i =0; i < 10; i++) {
			centreControle.envoyerMessage(new Message(i));


		}

		centreControle.start();
		rover.start();

		satellite.start();







		//testVect2D();
		//testFileSimple();
		
	}

	public static void testVect2D() {
		Vect2D vecteur1 = new Vect2D(3,4);
		Vect2D vecteur2 = new Vect2D(vecteur1);

		System.out.println(vecteur1.getLongueur());
		System.out.println(vecteur1.getAngle());

		vecteur2.ajouter(3,4);
		System.out.println(vecteur2.toString());

		Vect2D resultant = vecteur1.calculerDiff(vecteur2);

		System.out.println(vecteur1.equals(resultant));

		vecteur2.diviser(2);
		System.out.println(vecteur1.equals(vecteur2));
	}

	public static void testFileSimple() {
		FileSimple file = new FileSimple();

		int index = 0;

		while (index < 5) {
			file.ajouterElement(new Message(index));
			index++;
			System.out.println(index);
		}

		while (index > -1) {
			try{
				file.enleverElement();
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

}

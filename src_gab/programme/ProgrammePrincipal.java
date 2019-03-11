/**
 * Le programme principal du projet.
 * 
 * @author Dat Quang Nguyen, Sara Nguyen, Emerick Paul, Gabriel Somma
 * @version Hiver 2019
 *
 */

package programme;

import java.io.IOException;

import modele.centreControle.CentreControle;
import modele.communication.*;
import modele.rover.Rover;
import modele.satelliteRelai.SatelliteRelai;
import utilitaires.*;

public class ProgrammePrincipal {

	/**
	 * Programme principale, instancie les Ã©lÃ©ments de la simulation,
	 * les lie entre eux, puis lance la sÃ©quence de test.
	 * @param args, pas utilisÃ©
	 */
	public static void main(String[] args){

		CompteurMessage compteurMsg = new CompteurMessage();

		SatelliteRelai satellite = new SatelliteRelai();
		CentreControle centreControle = new CentreControle(satellite);
		Rover rover = new Rover(satellite, new Vect2D(0,0));
		satellite.lierCentrOp(centreControle);
		satellite.lierRover(rover);


		Commande msgCmd = null;
		msgCmd = new Commande(compteurMsg.getCompteActuel(),new Vect2D(5,10));
		centreControle.envoyerMessage(msgCmd);

		msgCmd = new Commande(compteurMsg.getCompteActuel(),new Vect2D(3,12));
		centreControle.envoyerMessage(msgCmd);

		msgCmd = new Commande(compteurMsg.getCompteActuel(),new Vect2D(10,10));
		centreControle.envoyerMessage(msgCmd);


		centreControle.start();
		rover.start();

		satellite.start();
		
	}
}

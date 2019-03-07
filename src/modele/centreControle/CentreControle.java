package modele.centreControle;

import modele.communication.*;
import modele.satelliteRelai.*;

public class CentreControle extends TransporteurMessage {
	
	public CentreControle() {
		
	}
	
	public void envoyerMessage(Message msg) {
		SatelliteRelai.envoyerMessageVersRover(msg);
	}
	
	public void gestionnaireMessage(Message msg) {
		
	}
}

package astro;

import model.robot.StartModel;
import controller.gui.AstroController;
import controller.gui.IController;
import controller.gui.SimController;
import controller.gui.SimResultController;
import controller.gui.StartController;

public class Main {

	public static void main(String args[]) {
		IController startController = new StartController();
		
		IController astroController = new AstroController();
		
		IController simController = new SimController();
		
		IController simResultController = new SimResultController();
		
		astroController.addSubController(simController);
		
		startController.addSubController(astroController);
		
		simController.addSubController(simResultController);
		
		startController.display(new StartModel());
	}
}

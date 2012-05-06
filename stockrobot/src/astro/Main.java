package astro;

import model.robot.StartModel;
import controller.gui.AstroController;
import controller.gui.IController;
import controller.gui.MainController;
import controller.gui.StartController;

public class Main {

	public static void main(String args[]) {
		IController startController = new StartController();
		
		IController astroController = new AstroController();
		
		startController.addSubController(astroController);
		
		startController.display(new StartModel());
		
		
	}
}

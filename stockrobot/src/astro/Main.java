package astro;

import model.robot.StartModel;
import controller.gui.IController;
import controller.gui.MainController;
import controller.gui.StartController;

public class Main {

	public static void main(String args[]) {
		IController startController = new StartController();
		
		IController mainMenuController = new MainController();
		
		startController.addSubController(mainMenuController);
		
		startController.display(new StartModel());
		
		
	}
}

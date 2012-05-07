package astro;

import model.robot.StartModel;
import controller.gui.IController;
import controller.gui.StartController;

public class Main {

	public static void main(String args[]) {
		IController startController = new StartController();
		startController.display(new StartModel());
	}
}

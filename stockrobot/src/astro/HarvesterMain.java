package astro;

import java.awt.Dimension;

import view.HarvesterView;
import view.HarvesterViewMVC;

import controller.gui.HarvesterController;
import controller.gui.HarvesterControllerMVC;

import model.scraping.core.Harvester;

/**
 * Parser main program.
 * Use this class to startup the parser GUI.
 * Refer to specific class for more details.
 * <p>
 * Uses MVC-model.
 * <p>
 * @author Erik
 *
 */
public class HarvesterMain {
	/*
    @SuppressWarnings("unused")
	public static void main(String[] args) {
        
    	Harvester      		model      = new Harvester(45000);
        HarvesterView       view       = new HarvesterView(model);
        HarvesterController controller = new HarvesterController(model, view);
        
        view.setSize(new Dimension(385, 430));
        view.setVisible(true);
    }*/
    @SuppressWarnings("unused")
	public static void main(String[] args) {
    	
        HarvesterControllerMVC controller = new HarvesterControllerMVC();
        controller.display(null);
    }
}

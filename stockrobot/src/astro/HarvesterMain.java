package astro;

import java.awt.Dimension;

import view.HarvesterView;

import controller.gui.HarvesterController;

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
	
    @SuppressWarnings("unused")
	public static void main(String[] args) {
    	
        HarvesterController controller = new HarvesterController();
        controller.display(null);
    }
}

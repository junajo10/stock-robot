package astro;
import controller.gui.HarvesterController;


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
	
    public static void main(String[] args) {
    	
        HarvesterController controller = new HarvesterController();
        controller.display(null);
    }
}

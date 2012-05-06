package model.scraping.core;

/**
 * Parser main program.
 * <p>
 * Uses MVC-model.
 * <p>
 * @author Erik
 *
 */
public class HarvesterMain {
    @SuppressWarnings("unused")
	public static void main(String[] args) {
        
    	Harvester      		model      = new Harvester(45000);
        HarvesterView       view       = new HarvesterView(model);
        HarvesterController controller = new HarvesterController(model, view);
        
        view.setVisible(true);
    }

}

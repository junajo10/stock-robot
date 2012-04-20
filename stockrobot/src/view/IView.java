package view;

import controller.gui.IController;

public interface IView  {
	
	public void registerController( IController controller );
	public void init();
}

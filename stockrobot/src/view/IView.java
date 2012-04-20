package view;

import gui.controller.IController;

public interface IView  {
	
	public void registerController( IController controller );
	public void init();
}

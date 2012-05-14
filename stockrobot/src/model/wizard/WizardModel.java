package model.wizard;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import utils.observer.IObservable;

/**
 * 
 * @author Mattias Markehed
 * mattias.markehed@gmail.com
 *
 * Filename: WizardModel.java
 * Description:
 * The model that holds the values for the wizards.
 */
public class WizardModel implements IObservable {

	public final static String EVT_TITLE_CHANGE 			= "evtTitleChange";
	public final static String EVT_SUBTITLE_CHANGE 		= "evtSubtitleChange";
	public final static String EVT_PAGE_CURRENT_CHANGE 	= "evtPageCurrentChange";
	public final static String EVT_PAGE_BACK_CHANGE 		= "evtPageBackChange";
	public final static String EVT_PAGE_NEXT_CHANGE 		= "evtPageNextChange";
	public final static String EVT_CAN_FINISH_CHANGE 	= "evtCanFinishChange";
	
	private String title;
	private String subtitle;
	private Stack<Integer> historyPages;
	private Integer nextPage;
	private Set<Integer> pages;
	private boolean canFinish = false;
	
	private PropertyChangeSupport observers;
	
	public WizardModel(){
		observers = new PropertyChangeSupport(this);
		historyPages = new Stack<Integer>();
		pages = new HashSet<Integer>();
	}
	
	public void setTitle(String title){
		String oldTitle = WizardModel.this.title;
		this.title = title;
		observers.firePropertyChange(EVT_TITLE_CHANGE, oldTitle, title);
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setSubtitle(final String subtitle){
		
        String oldSubtitle = WizardModel.this.subtitle;
        WizardModel.this.subtitle = subtitle;
        observers.firePropertyChange(EVT_SUBTITLE_CHANGE, oldSubtitle, subtitle);
	}
	
	public String getSubtitle() {
		return subtitle;
	}
	
	public void setFinish(boolean canFinish){
		
		boolean tFinish = this.canFinish;
		this.canFinish = canFinish;
		observers.firePropertyChange(EVT_CAN_FINISH_CHANGE, tFinish, canFinish);
	}
	
	public void setNextPage(Integer nextPage){
		if(nextPage == null){
			removeNextPage();
		}else{
			this.nextPage = nextPage;
		}
		observers.firePropertyChange(EVT_PAGE_NEXT_CHANGE, null, nextPage);
	}
	
	public boolean haveNext(){
		
		return nextPage != null;
	}
	
	public void removeNextPage(){
		Integer oldNextPage = nextPage;
		nextPage = null;
		observers.firePropertyChange(EVT_PAGE_NEXT_CHANGE, oldNextPage, nextPage);
	}
	
	public Integer getNextPage(){
		
		return nextPage;
	}
	
	public Integer goNextPage(){
		
		Integer tempNext = nextPage;
		
		if(nextPage != null){
			Integer prev = null;
			if(historyPages.size() > 0){
				prev = historyPages.peek();
			}
			historyPages.add(nextPage);
			nextPage = null;
			observers.firePropertyChange(EVT_PAGE_CURRENT_CHANGE, prev, historyPages.get(historyPages.size()-1));
		}
		
		return tempNext;
	}
	
	public Integer getBackPage(){
		
		Integer prevPage;
		
		if(historyPages.size() > 1){
			prevPage = historyPages.get(historyPages.size()-2);
		}else{
			prevPage = null;
		}
		
		return prevPage;
	}
	
	public boolean haveBack(){
		
		return getBackPage() != null;
	}
	
	public Integer goBackPage(){
		
		Integer prev = null;
		
		if(historyPages.size() > 1){
			Integer current = historyPages.pop();
			prev = historyPages.peek();
			nextPage = current;
			observers.firePropertyChange(EVT_PAGE_CURRENT_CHANGE, current, prev);
		}
		
		return prev;
	}
	
	//TODO determine if it can finished
	public boolean isAllowedFinish(){
		return canFinish;
	}
	
	public Integer getActivePage(){
		return historyPages.get(historyPages.size()-1);
	}

	@Override
	public void addAddObserver(PropertyChangeListener listener) {
		observers.addPropertyChangeListener(listener);
	}

	@Override
	public void removeObserver(PropertyChangeListener listener) {
		observers.removePropertyChangeListener(listener);
	}
	
	public PropertyChangeSupport getObservers() { return observers; }
	
	//TODO: Swing is single threaded, do we really need synchronized here? /kristian
	/**
	 * Adds a page to the wizard and indexes it by its name.
	 * If page with the same name already exists the old page is replaced.
	 * 
	 * @param pageName the name to index the page with
	 * @param page the page to add
	 * @return true if no page with same index was overwritten. Else false
	 */
	public synchronized boolean addPage(int pageID){
		
		boolean pageExisted = false;
		
		pageExisted = pages.contains(pageID);
		pages.add(pageID);
			
		return pageExisted;
	}
	
	
}

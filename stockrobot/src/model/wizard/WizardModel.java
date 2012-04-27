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

	public static String EVT_TITLE_CHANGE 			= "evtTitleChange";
	public static String EVT_SUBTITLE_CHANGE 		= "evtSubtitleChange";
	public static String EVT_PAGE_CURRENT_CHANGE 	= "evtPageCurrentChange";
	public static String EVT_PAGE_BACK_CHANGE 		= "evtPageBackChange";
	public static String EVT_PAGE_NEXT_CHANGE 		= "evtPageNextChange";
	public static String EVT_CAN_FINISH_CHANGE 		= "evtCanFinishChange";
	
	private String title;
	private String subtitle;
	private Stack<Integer> historyPages;
	private Integer nextPage;
	private Set<Integer> pages;
	
	private PropertyChangeSupport observers;
	
	public WizardModel(){
		observers = new PropertyChangeSupport(this);
		historyPages = new Stack<Integer>();
		pages = new HashSet<Integer>();
	}
	
	public void setTitle(String title){
		String oldTitle = title;
		this.title = title;
		observers.firePropertyChange(EVT_TITLE_CHANGE, oldTitle, title);
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setSubtitle(String subtitle){
		String oldSubtitle = subtitle;
		this.subtitle = subtitle;
		observers.firePropertyChange(EVT_SUBTITLE_CHANGE, oldSubtitle, subtitle);
	}
	
	public String getSubtitle() {
		return subtitle;
	}
	
	public void setNextPage(Integer nextPage){
		if(nextPage == null){
			removeNextPage();
		}else{
			//Integer oldNextPage = this.nextPage;
			this.nextPage = nextPage;
		}
		observers.firePropertyChange(EVT_PAGE_NEXT_CHANGE, null, nextPage);
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
	
	public Integer goBackPage(){
		
		Integer prev = null;
		
		if(historyPages.size() > 1){
			Integer current = historyPages.pop();
			prev = historyPages.peek();
			observers.firePropertyChange(EVT_PAGE_CURRENT_CHANGE, current, prev);
		}
		
		return prev;
	}
	
	//TODO determine if it can finished
	public boolean isAllowedFinish(){
		return false;
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

package scraping.scheduler;

/**
 *
 * Abstract interface for the Scheduler.
 * @author Erik
 *
 */
public interface IScheduler {
	public boolean shouldRun();
	public void ignoreTime();
	public boolean pauseParser();
}

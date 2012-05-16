package model.database.jpa;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.openjpa.persistence.OpenJPAEntityManagerFactory;
import org.apache.openjpa.persistence.OpenJPAPersistence;

/**
 * @author Daniel
 *
 * Basically the main JPA system we will use.
 * 
 * It has methods for most of the things we want to accomplish.
 */
public final class JPAHelper extends JPAHelperBase {
	
	private static JPAHelper instance = null;
	
	private static String TMPPERSISTENCE = "<persistence xmlns=\"http://java.sun.com/xml/ns/persistence\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" version=\"1.0\">\n" +

"<persistence-unit name=\"astroportfolio\" transaction-type=\"RESOURCE_LOCAL\">\n" +
"	<class>model.database.jpa.tables.PortfolioHistory</class>\n" +
"	<class>model.database.jpa.tables.PortfolioInvestment</class>\n" +
"	<class>model.database.jpa.tables.PortfolioEntity</class>\n" +
"	<class>model.database.jpa.tables.StockNames</class>\n" +
"	<class>model.database.jpa.tables.StockPrices</class>\n" +
"	<class>model.database.jpa.tables.AlgorithmSettingLong</class>\n" +
"	<class>model.database.jpa.tables.AlgorithmSettingDouble</class>\n" +
"	<class>model.database.jpa.tables.AlgorithmSettings</class>\n" +
"	<properties>\n" +
"		<property name=\"openjpa.Multithreaded\" value=\"true\"/>\n " +
"		<property name=\"openjpa.ConnectionURL\" value=\"jdbc:hsqldb:astrodatabase\"/>\n" +
"		<property name=\"openjpa.ConnectionDriverName\" value=\"org.hsqldb.jdbcDriver\"/> <!--  org.hsqldb.jdbcDriver -->\n" +
"		<property name=\"openjpa.jdbc.SynchronizeMappings\" value=\"buildSchema\"/>\n" +
"		<property name=\"openjpa.Log\" value=\"\"/>\n" +
"		<property name=\"openjpa.ConnectionFactoryProperties\" value=\"printParameters=true\"/>\n" +
"		<property name=\"openjpa.jdbc.MappingDefaults\" value=\"ForeignKeyDeleteAction=restrict,   JoinForeignKeyDeleteAction=restrict\"/>\n" +
"	</properties>\n" +
"</persistence-unit>\n" +

"<persistence-unit name=\"testdb\" transaction-type=\"RESOURCE_LOCAL\">\n" +
"	<class>model.database.jpa.tables.PortfolioHistory</class>\n" +
"	<class>model.database.jpa.tables.PortfolioInvestment</class>\n" +
"	<class>model.database.jpa.tables.PortfolioEntity</class>\n" +
"	<class>model.database.jpa.tables.StockNames</class>\n" +
"	<class>model.database.jpa.tables.StockPrices</class>\n" +
"	<class>model.database.jpa.tables.AlgorithmSettingLong</class>\n" +
"	<class>model.database.jpa.tables.AlgorithmSettingDouble</class>\n" +
"	<class>model.database.jpa.tables.AlgorithmSettings</class>\n" +
"	<properties>\n" +
"		<property name=\"openjpa.Multithreaded\" value=\"true\"/>\n " +
"		<property name=\"openjpa.ConnectionURL\" value=\"jdbc:hsqldb:astrotestdatabase\"/>\n" +
"		<property name=\"openjpa.ConnectionDriverName\" value=\"org.hsqldb.jdbcDriver\"/> <!--  org.hsqldb.jdbcDriver com.mysql.jdbc.Driver -->\n" +
"		<property name=\"openjpa.jdbc.SynchronizeMappings\" value=\"buildSchema\"/>\n" +
"		<property name=\"openjpa.Log\" value=\"\"/>\n" +
"		<property name=\"openjpa.ConnectionFactoryProperties\" value=\"printParameters=true\"/>\n" +
"		<property name=\"openjpa.jdbc.MappingDefaults\" value=\"ForeignKeyDeleteAction=restrict,   JoinForeignKeyDeleteAction=restrict\"/>\n" +
"	</properties>\n" +
"</persistence-unit>\n" +
"</persistence>"; 
	
	/**
	 * Creates an instance of JPAHelper if it doesn't  already exist, and returns the instance of it.
	 * @return An instance of JPAHelper
	 */
	public static IJPAHelper getInstance() {
		synchronized (JPAHelperBase.class) {
			if (instance == null) {	
				instance = new JPAHelper();
			}
		}
		
		if (!instance.getEntityManager().isOpen()) {
			instance.initJPASystem();
		}
		
		return instance;
	}
	
	/**
	 * Inits the jpa system.
	 */
	private void initJPASystem() {
		fixPersistenceXML();
		
		OpenJPAEntityManagerFactory factory = OpenJPAPersistence.createEntityManagerFactory("astroportfolio", "persistence.xml");

		em = factory.createEntityManager();
	}
	
	private void fixPersistenceXML() {
		File f = new File("bin/META-INF/persistence.xml");
		File folder = new File("bin/META-INF");
		System.out.println(f.getAbsolutePath());
		
		if (!folder.exists()) {
			folder.mkdir();
		}
		if (f.exists()) {
			
			System.out.println("exists");
		
		} else {
			System.out.println("dosent exist");
			
			try {
				f.createNewFile();
				
				FileWriter fw = new FileWriter(f);
				
				fw.write(TMPPERSISTENCE);
				fw.flush();
				fw.close();
				
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	private JPAHelper() {
		initJPASystem();
	}
}
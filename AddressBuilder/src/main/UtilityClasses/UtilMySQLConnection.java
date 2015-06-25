package main.UtilityClasses;

import main.SQLDatabaseClasses.RBSData;
import main.SQLDatabaseClasses.RBSSubnetDataAbis;
import main.SQLDatabaseClasses.RBSSubnetDataIub;
import main.SQLDatabaseClasses.RBSSubnetDataOM;
import main.SQLDatabaseClasses.RBSSubnetDataS1;
import main.SQLDatabaseClasses.SubnetData;
import main.SQLDatabaseClasses.Superclasses.RBSSubnetData;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class UtilMySQLConnection {
	
	public UtilMySQLConnection(){
		this.initializeHibernateConfiguration();
	}
	
	private String database = "AddressStorage";
	
	private Configuration configuration = null;
	
	private String serverIPAddress = "127.0.0.1:3306";
	
	private String serverUsername = "root";
	
	private String serverPassword = "Vegas123";
	
	private SessionFactory sessionFactory = null;
	
	public void openSessions(){
		
		this.initializeHibernateConfiguration();
		
		setSessionFactory(getConfiguration().buildSessionFactory());
		
		
	}
	
	//close SessionFactory session	
	public void closeSessions(){
		
		getSessionFactory().close();
		setSessionFactory(null);
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public String getServerIPAddress() {
		return serverIPAddress;
	}

	public void setServerIPAddress(String serverIPAddress) {
		this.serverIPAddress = serverIPAddress;
	}

	public String getServerUsername() {
		return serverUsername;
	}

	public void setServerUsername(String serverUsername) {
		this.serverUsername = serverUsername;
	}

	public String getServerPassword() {
		return serverPassword;
	}

	public void setServerPassword(String serverPassword) {
		this.serverPassword = serverPassword;
	}

	private void setConfiguration (Configuration configuration){
		this.configuration = configuration;
	}
	
	public Session getNewSession(){
		
		if(getSessionFactory() == null)
			this.openSessions();
		
		return getSessionFactory().openSession();
	}
	
	private Configuration getConfiguration() {
		
		return this.configuration;
		
	}
	
	private String getDatabase(){
		return this.database;
	}
	
	private void setDatabase(String database){
		this.database = database;
	}
	
	private void initializeHibernateConfiguration(){
		
		if(getConfiguration()==null && getDatabase()!=null){
			
		setConfiguration(new Configuration());
		
		getConfiguration().setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		getConfiguration().setProperty("hibernate.connection.url","jdbc:mysql://" + getServerIPAddress() + "/" + database);
		getConfiguration().setProperty("hibernate.connection.username", getServerUsername());
		getConfiguration().setProperty("hibernate.connection.password", getServerPassword());
		getConfiguration().setProperty("hibernate.c3p0.min_siz", "5");
		getConfiguration().setProperty("hibernate.c3p0.max_size", "20");
		getConfiguration().setProperty("hibernate.c3p0.timeout", "1800");
		getConfiguration().setProperty("hibernate.c3p0.max_statements", "50");
		getConfiguration().setProperty("connection.pool_size", "10");
		getConfiguration().setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect");
		getConfiguration().setProperty("hibernate.search.default.directory_provider", "filesystem");
		getConfiguration().setProperty("hibernate.search.default.indexBase", "/var/lucene/indexes");
		getConfiguration().addAnnotatedClass(RBSData.class);
		getConfiguration().addAnnotatedClass(SubnetData.class);
		getConfiguration().addAnnotatedClass(RBSSubnetDataIub.class);
		getConfiguration().addAnnotatedClass(RBSSubnetDataAbis.class);
		getConfiguration().addAnnotatedClass(RBSSubnetDataOM.class);
		getConfiguration().addAnnotatedClass(RBSSubnetDataS1.class);
		getConfiguration().addAnnotatedClass(RBSSubnetData.class);
		}
		
		
	}

}

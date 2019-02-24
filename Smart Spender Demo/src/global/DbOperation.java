package global;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import vo.UserVo;

public class DbOperation {

	static AnnotationConfiguration configuration;
	public static SessionFactory factory;
	public Session session;
	public Transaction transaction;
	public Query query;
	
	public DbOperation()
	{
		//getConnection();
		session=factory.openSession();
	}

	static {
		configuration = new AnnotationConfiguration();
		configuration.configure("hibernate.cfg.xml");
		factory = configuration.buildSessionFactory();
	}

	private void getConnection() {
		// TODO Auto-generated method stub
		configuration=new AnnotationConfiguration();
		configuration.configure("hibernate.cfg.xml");
		factory=configuration.buildSessionFactory();
		session=factory.openSession();
	}
	
	
	public void insert(Object object)
	{
		transaction=session.beginTransaction();
		session.save(object);
		transaction.commit();
		session.close();
	}
	
	public void update(Object object)
	{
		transaction=session.beginTransaction();
		session.update(object);
		transaction.commit();
		session.close();
	}
	
	public void delete(Object object)
	{
		transaction=session.beginTransaction();
		session.delete(object);
		transaction.commit();
		session.close();
	}
}
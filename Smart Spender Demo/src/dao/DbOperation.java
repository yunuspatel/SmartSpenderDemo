package dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import vo.UserVo;

public class DbOperation {

	static AnnotationConfiguration configuration;
	static SessionFactory factory;
	Session session;
	Transaction transaction;
	Query query;
	
	public DbOperation()
	{
		getConnection();
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

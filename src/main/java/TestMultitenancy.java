import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.caelum.lt.multitenancy.DB;
import br.com.caelum.lt.multitenancy.models.Product;

public class TestMultitenancy {
	private static ApplicationContext context;
	private static SessionFactory factory;

	public static void main(String[] args) {
		
		//get "spring"
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
//		DB.setDb("second");
		
		Session session = getSession();
		
		//lets save some products
		Product p1 = new Product(100.0,"some product", "product #1");
		Product p2 = new Product(100.0,"some product", "product #2");
		
		session.getTransaction().begin();
		session.save(p1);
		session.save(p2);
		session.getTransaction().commit();
		
		List allProducts = getAllProducts(session);
		System.out.println(allProducts.size()); // 2
		
		session.close();
		factory.close();
		
		//////////////////////////////////////////////////////////////////////////////
		// *** change the db ****
		//////////////////////////////////////////////////////////////////////////////

		DB.setDb("second");
		
		session = getSession();
		
		//select all...
		System.out.println(getAllProducts(session).size()); // ops, is empty... because we are in another database!!
		
		//save one product
		session.getTransaction().begin();
		session.save(new Product(100.0,"some product", "product #3"));
		session.getTransaction().commit();
		
		//select again
		System.out.println(getAllProducts(session).size()); // 1
		
		session.close();
		factory.close();
		
		//change again....
		
		DB.setDb("first");
		
		session = getSession();

		System.out.println(getAllProducts(session).size()); // 2 again.... 
		
		session.close();
		factory.close();
	}

	private static List getAllProducts(Session session) {
		return session.createQuery("from Product").list();
	}

	private static Session getSession() {
		factory = context.getBean(SessionFactory.class);
		return factory.openSession();
	}
}

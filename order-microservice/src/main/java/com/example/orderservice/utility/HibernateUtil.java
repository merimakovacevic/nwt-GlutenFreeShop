package com.example.orderservice.utility;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil
{
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory()
    {
        try
        {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }

    public static void startSessionFactoryExample() {
        //		Session session = HibernateUtil.getSessionFactory().openSession();
//		session.beginTransaction();
//
//		Product product1 = new Product();
//		product1.setId(23423L);
//		product1.setPrice(1232L);
//		session.save(product1);
//		session.save(product2);
//
//		User user1 = new User();
//		User user2 = new User();
//		User user3 = new User();
//		session.save(user1);
//		session.save(user2);
//		session.save(user3);
//
//		Item item1 = new Item();
//		item1.setProduct(product1);
//		item1.setAmount(200);
//		item1.setOrder(new Order());
//		Item item2 = new Item();
//		item2.setProduct(product2);
//		item2.setAmount(300);
//		item2.setOrder(new Order());
//		session.save(item1);
//		session.save(item2);
//
//		Order order = new Order();
//		order.setDeliveryAddress("Address1");
//		order.setItems(List.of(item1, item2));
//		order.setUserId(user1);
//		session.save(order);
//
//		session.getTransaction().commit();
//		HibernateUtil.shutdown();
    }
}
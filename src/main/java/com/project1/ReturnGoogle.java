package com.project1;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.db.Connector;
import com.db.User;


/**
 * Servlet implementation class ReturnGoogle
 */
public class ReturnGoogle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ReturnGoogle() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/**
		 * 
		 * 
		 * DO YOUR OPENID MAGIC HERE
		 * 
		 * 
		 */
		
		/* ---- CHECK TO OUR DB BEGIN ----*/
		String name = request.getParameter("user");
		String email = name;
		//String name = "Stewart Sentanoe"; //Change this parameter based what you need
		//String email = "ss@gmail.com"; //Change this parameter based what you need
		String realm = "GOOGLE"; //Change this parameter based what you need
		int uid=0;
		
		Session session = null;
        Transaction tx = null;
		try 
		{
			Connector conn = new Connector();
            session = conn.configureSessionFactory().openSession();
            tx = session.beginTransaction();
            
            String hsql = "from User where email = :user_email and realm = :login_realm";
            Query query = session.createQuery(hsql);
            query.setParameter("user_email", email);
            query.setParameter("login_realm", realm);
            List<User> result = query.list();
            
            if(result.size() != 0)
            {
            	//USER IS EXIST, DO THE AUTHORIZATION METHOD
            	for(User u : result)
            	{
            		 System.out.println("Id: " + u.getId() + " | Name:"  + u.getName() + " | Email:" + u.getEmail() + " | Realm:" + u.getRealm());
            		 uid=u.getId();
        		 	/**
        			 * 
        			 * 
        			 * DO YOUR AUTHORIZATION MAGIC HERE
        			 * 
        			 * 
        			 */
            	}
            }
            else
            {
            	//USER IS NOT EXIST, STORE IN DB
            	try
            	{
            		User newUser = new User(0, name, email,realm);
            		session.save(newUser);
            		uid=newUser.getId();  // does this work?
            	}
            	catch(Exception ex)
            	{
            		ex.printStackTrace();
            		tx.rollback();
            	}
            	
            	/**
    			 * 
    			 * 
    			 * DO YOUR AUTHORIZATION MAGIC HERE
    			 * 
    			 * 
    			 */
            }
        } 
		catch (Exception ex) 
		{
			
            ex.printStackTrace();
        } 
		finally
		{
			if(session != null && session.isOpen())
			{
				tx.commit();
				session.flush();
				session.close();
			}
			response.sendRedirect("messageBoard.jsp?userid="+uid);
        }
		/* ---- CHECK TO OUR DB END ----*/
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}

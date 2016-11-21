package com.project1;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.db.Connector;
import com.db.User;

/**
 * Servlet implementation class DeleteChat
 */
public class DeleteChat extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public DeleteChat() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uid = request.getParameter("userid");
		HttpSession session1 = request.getSession(false);
		String uid1=session1.getAttribute("uid").toString();
		if(uid.equals(uid1)==false){
			response.sendRedirect("http://localhost:8080/project1/Error.jsp");
			return;
		}
		int uid2 = Integer.parseInt(uid1);
		int chatId = Integer.parseInt(request.getParameter("chatID"));
		Session session = null;
        Transaction tx = null;
        
        try 
        {
        	Connector conn = new Connector();
            session = conn.configureSessionFactory().openSession();
            tx = session.beginTransaction();
            
            String hsql = "delete from Chat where id = :chatID and user_id = :uid";
            Query query = session.createQuery(hsql);
            query.setParameter("chatID", chatId);
            query.setParameter("uid", uid2);
            int res = query.executeUpdate();
            
            
        } 
        catch (Exception ex) 
        {
        	tx.rollback();
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
        	
        	response.sendRedirect("messageBoard.jsp");
        }
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

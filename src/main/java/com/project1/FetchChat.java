package com.project1;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.db.Chat;
import com.db.Connector;
import com.db.User;

/**
 * Servlet implementation class FetchChat
 */
public class FetchChat extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FetchChat() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uid = request.getParameter("userid");
		
		int loggedInUserId = uid != null ? Integer.parseInt(uid) : 1;

		String loggedInUserName = "";

		PrintWriter out = response.getWriter();
		Session session = null;
		SessionFactory sf = null;
		Transaction tx = null;
		try {
			Connector conn = new Connector();
			sf = conn.configureSessionFactory();
			session = sf.openSession();
			tx = session.beginTransaction();
			String uid1 = null;
			try {
				uid1 = request.getSession().getAttribute("uid").toString();
				if (uid1 == null) {
					response.sendRedirect("/index.jsp");
					return;
				}
			} catch (Exception ex) {
				response.sendRedirect("/index.jsp");
				return;
			}
			if (uid.equals(uid1) == false) {
				response.sendRedirect("/Error.jsp");
				return;
			}

			{
				String hsql = "from User where id = :user_id";
				Query query = session.createQuery(hsql);
				query.setParameter("user_id", loggedInUserId);
				List<User> result = query.list();
				for (User u : result) {
					loggedInUserName = u.getName();
				}
			}

			List<Chat> chats = session.createQuery("from Chat").list();
			for (Chat c : chats) {
				// System.out.println(c.getUser_id()+ " : "+c.getChat());
				if (c.getUser_id() == loggedInUserId) {
					out.println("<li class=\"right clearfix\">");
					out.println("<span class=\"chat-img pull-right\">");
					out.println(
							"<img src=\"http://placehold.it/50/FA6F57/fff\" alt=\"User Avatar\" class=\"img-circle\" />");
					out.println("</span>");
					out.println("<div class=\"chat-body clearfix\">");
					out.println("<div class=\"header\">");
					out.println("<small class=\" text-muted\">");
					out.println("<i class=\"fa fa-clock-o fa-fw\"></i></small>");
					out.println("<strong class=\"pull-right primary-font\">" + loggedInUserName + "</strong>");
					out.println("</div>");
					out.println("<p class=\"pull-right\">");
					out.println(c.getChat() + " " + "<a href='DeleteChat?chatID=" + c.getId() + "&userid=" + uid
							+ "'><i class='fa fa-times'></i></a>");
					out.println("</p>");
					out.println("</div>");
					out.println("</li>");
				} else {
					String userName = "";

					String hsql = "from User where id = :user_id";
					Query query = session.createQuery(hsql);
					query.setParameter("user_id", c.getUser_id());
					List<User> result = query.list();
					for (User u : result) {
						userName = u.getName();
					}

					out.println("<li class=\"left clearfix\">");
					out.println("<span class=\"chat-img pull-left\">");
					out.println(
							"<img src=\"http://placehold.it/50/55C1E7/fff\" alt=\"User Avatar\" class=\"img-circle\" />");
					out.println("</span>");
					out.println("<div class=\"chat-body clearfix\">");
					out.println("<div class=\"header\">");
					out.println("<strong class=\"primary-font\">" + userName + "</strong>");
					out.println("<small class=\"pull-right text-muted\">");
					out.println("<i class=\"fa fa-clock-o fa-fw\"></i>");
					out.println("</small>");
					out.println("</div>");
					out.println("<p>");
					out.println(c.getChat());
					out.println("</p>");
					out.println("</div>");
					out.println("</li>");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			if (session != null && session.isOpen()) {
				tx.commit();
				session.flush();
				session.close();
				sf.close();
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

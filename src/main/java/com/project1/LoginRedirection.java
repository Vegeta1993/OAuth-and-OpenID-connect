package com.project1;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.SecureRandom;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nimbusds.oauth2.sdk.ResponseType;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.openid.connect.sdk.AuthenticationRequest;
import com.nimbusds.openid.connect.sdk.Nonce;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.scope.ExtendedPermissions;
import com.restfb.scope.ScopeBuilder;
import com.restfb.scope.UserDataPermissions;
import java.net.URI;
import java.net.URL;

/**
 * Servlet implementation class LoginRedirection
 */
public class LoginRedirection extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginRedirection() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String realm = request.getParameter("direction");
		if (realm.equalsIgnoreCase("Google")) {
			// REDIRECT THEM TO GOOGLE
			URI redirectURI = null;
			try {
				redirectURI = new URI("http://localhost:8080/project1/ReturnGoogle");
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			URI authURI = null;
			try {
				authURI = new URI("https://accounts.google.com/o/oauth2/v2/auth");
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ResponseType rt = new ResponseType("code");
			Scope scope = new Scope("openid", "email", "profile");
			//replace xxx with your cliendID
			ClientID clientID = new ClientID(
					"xxx.apps.googleusercontent.com");
			//to mitigate CSRF attack
			String state1 = new BigInteger(130, new SecureRandom()).toString(32);
			request.getSession().setAttribute("state", state1);
			State state = new State(state1);
			Nonce nonce = null;
			AuthenticationRequest authRequest = new AuthenticationRequest(redirectURI, rt, scope, clientID, redirectURI,
					state, nonce);
			URI parameterizedRedirectURI = null;
			try {
				parameterizedRedirectURI = new URI(authURI.toString() + "?" + authRequest.toQueryString());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			String redirectString = parameterizedRedirectURI.toString();
			response.sendRedirect(redirectString);

		} else if (realm.equalsIgnoreCase("Facebook")) {

			String redirectUrl = "http://localhost:8080/project1/ReturnFacebook";
			//replace with your appId
			String appId = "xxx";

			ScopeBuilder scopeBuilder = new ScopeBuilder();
			scopeBuilder.addPermission(ExtendedPermissions.EMAIL);

			FacebookClient client = new DefaultFacebookClient(Version.VERSION_2_5);
			String loginDialogUrlString = client.getLoginDialogUrl(appId, redirectUrl, scopeBuilder);

			response.sendRedirect(loginDialogUrlString);

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

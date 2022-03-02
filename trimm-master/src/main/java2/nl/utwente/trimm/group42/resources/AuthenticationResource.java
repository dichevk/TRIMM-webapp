package nl.utwente.trimm.group42.resources;

import java.util.Base64;

import javax.validation.constraints.Email;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import nl.utwente.trimm.group42.dao.AuthDao;
import nl.utwente.trimm.group42.dao.RunnerDataDao;
import nl.utwente.trimm.group42.dao.SessionTokenDao;
import nl.utwente.trimm.group42.dao.UserDao;
import nl.utwente.trimm.group42.dao.VerificationDao;
import nl.utwente.trimm.group42.extras.EncryptionPass;
import nl.utwente.trimm.group42.extras.EncryptionPassStronger;
import nl.utwente.trimm.group42.models.SendEmail;

@Path("/authentication")
public class AuthenticationResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	/**
	 * A resource method for the login service
	 * @param username
	 * @param password
	 * @return success/fail
	 */
	// GET-localhost//trimmapp/rest/authetication/login?username=Boris&password=something
	@Path("login")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response loginAuth(@QueryParam("username") String username, @QueryParam("password") String password) {
		byte[] salt = AuthDao.getSalt(username);
		byte[] hashedpass = EncryptionPassStronger.HashPassStr(password, salt);
		String answer = AuthDao.login(username, hashedpass);
		System.out.println(answer);
		return Response.status(Response.Status.OK).entity(answer).type(MediaType.APPLICATION_JSON).build();
	}
	/**
	 * A resource method for the reg service before verifying the email
	 * @param email
	 * @return status 200/204 and Please check you email if it is not a valid email/status 404 and Not a valid email!
	 */
	// POST-localhost//trimmapp/rest/authetication/reg?email=something
	@Path("reg")
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response regSendEmail(@QueryParam("email")String email) {
		if(UserDao.validEmail(email)) {
			return Response.status(Response.Status.NOT_FOUND).entity("Not a valid email!").build();
		} else {
			SendEmail send=new SendEmail();
			String token =send.generateSafeToken();
			VerificationDao.AddEmailnToken(email, token);
			send.setSubject("Verify your email Trimm_runner_app");
			send.setText("You need to verify your email before continuing. Please copy this token in field on the page: "+token+"\r\n"+"If you have not made this request,please ignore this email and contact us back.");
			send.setRecepient(email);
			send.send();
			return Response.status(Response.Status.OK).entity("Please check your email").build();
		}
	}
	/**
	 * A resource method for the reg service after verify token is given by the user
	 * @param token sent to the user's email
	 * @param username
	 * @param password
	 * @param email
	 * @return success and status 200/204 if registering this use is legal/status 404 if it is not legal to register the user
	 */

	// POST-localhost//trimmapp/rest/authetication/reg/sometoken?username=Boris&password=something&email=someemail
	@Path("reg/{verifytoken}")
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response regAuth(@PathParam("verifytoken")String token,@QueryParam("username") String username, @QueryParam("password") String password,@QueryParam("email") String email) {
		if(VerificationDao.checkToken(token, email)) {
		byte[] salt = EncryptionPassStronger.generateSalt();
	    byte[] hashedpass = EncryptionPassStronger.HashPassStr(password, salt);
		String answer = AuthDao.register(username, hashedpass,salt); //add password and salt
		UserDao.addUser(username, email);//add the account

		return Response.status(Response.Status.OK).entity(answer).type(MediaType.APPLICATION_JSON).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("Not a valid token").build();
		}
	}
	/**
	 * A resource method for the forget password service/requesting change of passwords
	 * @param email
	 * @return 200/204 if it is a valid email and "Please check your email"/404 "Not a valid email!"
	 */
	//POST-localhost//trimmapp/rest/authetication/forgetpassword(email encoded in the body as JSON)
	@Path("forgetpassword")
	@POST
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_JSON)
	public Response forgetPass(String email) {
		if(UserDao.validEmail(email)) {
			SendEmail send=new SendEmail();
			String token =send.generateSafeToken();
			UserDao.addToken(token, email);
			send.setSubject("forgotten password Trimm_runner_app");
			send.setText("You forgot your password. Please copy this token in field on the page: "+token+"\r\n"+"If you have not made this request,please ignore this email and contact us back.");
			send.setRecepient(email);
			send.send();
			return Response.status(Response.Status.OK).entity("Please check your email").build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("Not a valid email!").build();
		}
		
	}
	/**
	 * A resource method for verifying the new passwords
	 * @param token sent to the user's email for security
	 * @param email
	 * @param pass
	 * @return 200/204 and "Password changed"/404 and "Not a valid email or token"
	 */
	//POST-localhost//trimmapp/rest/authetication/forgetpassword/sometoken?email=someemail&password=somepass
	@Path("forgetpassword/{token}")
	@POST
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response forgetPass2(@PathParam("token") String token,@QueryParam("email")String email,@QueryParam("password")String pass) {
		
		if(UserDao.validToken(token, email)) {
			byte[] salt = EncryptionPassStronger.generateSalt();
			byte[] hashedpass = EncryptionPassStronger.HashPassStr(pass, salt);
			AuthDao.changePass(UserDao.getUsername(token),hashedpass, salt);
			return Response.status(Response.Status.OK).entity("Password changed").build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("Not a valid email or token").build();
		}
		
	}
	/**
	 * A resource method for the log out service 
	 * @param sessiontoken
	 */
	//DELETE-localhost//trimmapp/rest/authetication/logout/sessiontoken
	@Path("logout/{sessiontoken}")
	@DELETE
	public void logout(@PathParam("sessiontoken")String sessiontoken) {
		SessionTokenDao.deleteToken(sessiontoken);
		System.out.println("User logged out");
	}
	
	

}

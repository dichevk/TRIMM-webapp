package nl.utwente.trimm.group42.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.google.gson.Gson;

import nl.utwente.trimm.group42.dao.SessionTokenDao;
import nl.utwente.trimm.group42.dao.UserDao;
import nl.utwente.trimm.group42.models.User;

@Path("/user")
public class UserResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	/**
	 * A resource method for fetching the user service
	 * @param sessiontoken
	 * @return
	 */
	//GET-localhost//trimmapp/rest/user/sessiontoken
	@Path("{sessiontoken}")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getAccUser(@PathParam("sessiontoken") String sessiontoken) {
		User user = UserDao.getAcc(sessiontoken);
		UserDao.addstepsnKM(user, sessiontoken);
		Gson gson = new Gson();
		String json = gson.toJson(user);
		return json;
	}
	/**
	 * A resource method for updating the user service
	 * @param sessiontoken identifies the user's session
	 * @param user
	 */
	//PUT-localhost//trimmapp/rest/user/sessiontoken(json encoding of the user in the body)
	@Path("{sessiontoken}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateUser(@PathParam("sessiontoken") String sessiontoken,User user) {
		User userA = user;
		UserDao.updateUser(userA);
		userA=UserDao.getAcc(sessiontoken);
	}
	/**
	 * A resource method for deleting the user account
	 * @param sessiontoken identifies the user's session
	 */
	@Path("{sessiontoken}")
	@DELETE
	public void deleteUser(@PathParam("sessiontoken") String sessiontoken) {
		String username = SessionTokenDao.getUser(sessiontoken);
		//delete the session token
		SessionTokenDao.deleteToken(sessiontoken);
		UserDao.deleteUser(username);
		System.out.println("Done");
		
	}

}

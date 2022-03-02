package nl.utwente.trimm.group42.testauthclient;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

public class AuthClient { //TODO DELETE?

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(getBaseURI());
		String url_pattern = "rest";
		String path = "authentication";
		String path2 = "login";
		String path3 = "reg";
		//login user JF with correct pass//expected:{answer:success}
		System.out.println("answer: "+target.path(url_pattern).path(path)
				.path(path2).queryParam("username", "JF").queryParam("password", "secondrunner18").request()
		.accept(MediaType.APPLICATION_JSON).get(String.class));
		//login user JF with incorrect pass//expected:{answer:fail}
		System.out.println("answer: "+target.path(url_pattern).path(path)
				.path(path2).queryParam("username", "JF").queryParam("password", "test").request()
		.accept(MediaType.APPLICATION_JSON).get(String.class));
		//register user//expected{answer:success}
		System.out.println("register user:{answer:fail}");
		System.out.println("answer: "+target.path(url_pattern).path(path)
				.path(path3).queryParam("username", "John").queryParam("password", "test").request()
		.accept(MediaType.APPLICATION_JSON).post(Entity.json(null)).readEntity(String.class));
		System.out.println("register with existing username/expected:{answer:fail}");//register with existing username//expected:{answer:fail}
		System.out.println("answer: "+target.path(url_pattern).path(path)
				.path(path3).queryParam("username", "JF").queryParam("password", "test").request()
		.accept(MediaType.APPLICATION_JSON).post(Entity.json(null)).readEntity(String.class));
	}
	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/TRIMM_visualizing_runners_data_42").build();
	}
}

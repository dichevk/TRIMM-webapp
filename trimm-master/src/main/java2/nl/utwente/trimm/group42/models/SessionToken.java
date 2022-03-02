package nl.utwente.trimm.group42.models;

import java.security.SecureRandom;
import java.sql.Time;
import java.util.Base64;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import nl.utwente.trimm.group42.dao.SessionTokenDao;

public class SessionToken {

	public String user;
	public String sessiontoken;

	/**
	 * Creates a SessionToken and puts it in a static map
	 * 
	 * @param user the user for which the token will be
	 */
	public SessionToken(String username) {
		sessiontoken = createToken();
		this.user = username;
	}

	/**
	 * @return a random SessionToken
	 */
	private String createToken() {
		while (true) {
			SecureRandom random = new SecureRandom();
			Base64.Encoder encoder = Base64.getUrlEncoder();
			byte[] randomBytes = new byte[24];
			random.nextBytes(randomBytes);
			String token = encoder.encodeToString(randomBytes);
			if (SessionTokenDao.getUser(token) == null) {
				return token;
			}
		}
	}

}

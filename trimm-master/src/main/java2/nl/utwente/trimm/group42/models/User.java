package nl.utwente.trimm.group42.models;



import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class User {
	private String username;
	private String firstname;
	private String lastname;
	private String email;
	private int height;
	private int weight;
	private double distanceran;
	private int steps;
	
	public User(String username, String firstname, String lastname,String email,int weight,int height) {
		this.username = username;
		this.firstname=firstname;
		this.lastname=lastname;
		this.email = email;
		this.height=height;
		this.weight=weight;
	}
	public User() {
		
	}
	public String getfirstname() {
		return this.firstname;
	}
	public String getEmail() {
		return this.email;
	}
	public String getUsername() {
		return this.username;
	}
	public String getlastname() {
		return this.lastname;
	}
	public int getheight() {
		return this.height;
	}
	public int getweight() {
		return this.weight;
	}
	public double getRankm() {
		return this.distanceran;
	}
	public int getsteps() {
		return this.steps;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setfirstname(String firstname) {
		this.firstname = firstname;
	}
	public void setlastname(String lastname) {
		this.lastname=lastname;
	}
	public void setRankm(double distanceran) {
		this.distanceran=distanceran;
	}
	public void setSteps(int steps) {
		this.steps=steps;
	}
	public void setEmail(String email) {
		this.email=email;
	}
	public void setheight(int height) {
		this.height = height;
	}
	public void setweght(int weight) {
		this.weight=weight;
	}

}

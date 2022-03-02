package nl.utwente.trimm.group42.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BodyPack {
	double distance;
	String time;
	Shoes shoe;
	String description;
	String date;

	public BodyPack(double distance, String time, Shoes shoe,String description,String date) {
		this.distance = distance;
		this.time = time;
		this.shoe = shoe;
		this.description = description;
		this.date = date;
	}
	public BodyPack() {
		
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getTime() {
		return time;
	}
	public String getDate() {
		return date;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Shoes getShoe() {
		return shoe;
	}

	public void setShoe(Shoes shoe) {
		this.shoe = shoe;
	}
	
}

package nl.utwente.trimm.group42.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Shoes {
	String brand;
	String name;
	int heelmm;
	int forefootmm;
	int dropmm;
	int weightgr;
	final static String MIXED = "mixed";

	public Shoes(String brand, String name, int heelmm, int forefootmm, int dropmm, int weightgr) {
		this.brand = brand;
		this.name = name;
		this.heelmm = heelmm;
		this.forefootmm = forefootmm;
		this.dropmm = dropmm;
		this.weightgr = weightgr;
	}
	public Shoes() {
		
	}
	

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHeelmm() {
		return heelmm;
	}

	public void setHeelmm(int heelmm) {
		this.heelmm = heelmm;
	}

	public int getForefootmm() {
		return forefootmm;
	}

	public void setForefootmm(int forefootmm) {
		this.forefootmm = forefootmm;
	}

	public int getDropmm() {
		return dropmm;
	}

	public void setDropmm(int dropmm) {
		this.dropmm = dropmm;
	}

	public int getWeightgr() {
		return weightgr;
	}

	public void setWeightgr(int weightgr) {
		this.weightgr = weightgr;
	}
}

package nl.utwente.trimm.group42.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StepData {
	String time;
	int step;
	String username;
	int run;
	int surface;
	String ic_right;
	String to_right;
	double axtibacc_right;
	double tibimpact_right;
	double axsacacc_right;
	double sacimpact_right;
	double brakingforce_right;
	double pushoffpower_right;
	double tibintrot_right;
	double vll_right;
	String ic_left;
	String to_left;
	double axtibacc_left;
	double tibimpact_left;
	double axsacacc_left;
	double sacimpact_left;
	double brakingforce_left;
	double pushoffpower_left;
	double tibintrot_left;
	double vll_left;

	public StepData(String time, int step, String username, int run, int surface, String ic_right, String to_right,
			double axtibacc_right, double tibimpact_right, double axsacacc_right, double sacimpact_right,
			double brakingforce_right, double pushoffpower_right, double tibintrot_right, double vll_right,
			String ic_left, String to_left, double axtibacc_left, double tibimpact_left, double axsacacc_left,
			double sacimpact_left, double brakingforce_left, double pushoffpower_left, double tibintrot_left,
			double vll_left) {

		this.time = time;
		this.step = step;
		this.username = username;
		this.run = run;
		this.surface = surface;
		this.ic_right = ic_right;
		this.to_right = to_right;
		this.axtibacc_right = axtibacc_right;
		this.tibimpact_right = tibimpact_right;
		this.axsacacc_right = axsacacc_right;
		this.sacimpact_right = sacimpact_right;
		this.brakingforce_right = brakingforce_right;
		this.pushoffpower_right = pushoffpower_right;
		this.tibintrot_right = tibintrot_right;
		this.vll_right = vll_right;
		this.ic_left = ic_left;
		this.to_left = to_left;
		this.axtibacc_left = axtibacc_left;
		this.tibimpact_left = tibimpact_left;
		this.axsacacc_left = axsacacc_left;
		this.sacimpact_left = sacimpact_left;
		this.brakingforce_left = brakingforce_left;
		this.pushoffpower_left = pushoffpower_left;
		this.tibintrot_left = tibintrot_left;
		this.vll_left = vll_left;
	}
	public StepData() {
		
	}

	public String get(String type) {
		switch (StepDataType.valueOf(type)) {
		case axsacacc_left:
			return "" + axsacacc_left;
		case axsacacc_right:
			return "" + axsacacc_right;
		case axtibacc_left:
			return "" + axtibacc_left;
		case axtibacc_right:
			return "" + axtibacc_right;
		case brakingforce_left:
			return "" + brakingforce_left;
		case brakingforce_right:
			return "" + brakingforce_right;
		case ic_left:
			return "" + ic_left;
		case ic_right:
			return "" + ic_right;
		case pushoffpower_left:
			return "" + pushoffpower_left;
		case pushoffpower_right:
			return "" + pushoffpower_right;
		case sacimpact_left:
			return "" + sacimpact_left;
		case sacimpact_right:
			return "" + sacimpact_right;
		case step:
			return "" + step;
		case surface:
			return "" + surface;
		case tibimpact_left:
			return "" + tibimpact_left;
		case tibimpact_right:
			return "" + tibimpact_right;
		case tibintrot_left:
			return "" + tibintrot_left;
		case tibintrot_right:
			return "" + tibintrot_right;
		case time:
			return "" + time;
		case to_left:
			return "" + to_left;
		case to_right:
			return "" + to_right;
		case vll_left:
			return "" + vll_left;
		case vll_right:
			return "" + vll_right;
		default:
			return null;
		}
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getRun() {
		return run;
	}

	public void setRun(int run) {
		this.run = run;
	}

	public int getSurface() {
		return surface;
	}

	public void setSurface(int surface) {
		this.surface = surface;
	}

	public String getIc_right() {
		return ic_right;
	}

	public void setIc_right(String ic_right) {
		this.ic_right = ic_right;
	}

	public String getTo_right() {
		return to_right;
	}

	public void setTo_right(String to_right) {
		this.to_right = to_right;
	}

	public double getAxtibacc_right() {
		return axtibacc_right;
	}

	public void setAxtibacc_right(double axtibacc_right) {
		this.axtibacc_right = axtibacc_right;
	}

	public double getTibimpact_right() {
		return tibimpact_right;
	}

	public void setTibimpact_right(double tibimpact_right) {
		this.tibimpact_right = tibimpact_right;
	}

	public double getAxsacacc_right() {
		return axsacacc_right;
	}

	public void setAxsacacc_right(double axsacacc_right) {
		this.axsacacc_right = axsacacc_right;
	}

	public double getSacimpact_right() {
		return sacimpact_right;
	}

	public void setSacimpact_right(double sacimpact_right) {
		this.sacimpact_right = sacimpact_right;
	}

	public double getBrakingforce_right() {
		return brakingforce_right;
	}

	public void setBrakingforce_right(double brakingforce_right) {
		this.brakingforce_right = brakingforce_right;
	}

	public double getPushoffpower_right() {
		return pushoffpower_right;
	}

	public void setPushoffpower_right(double pushoffpower_right) {
		this.pushoffpower_right = pushoffpower_right;
	}

	public double getTibintrot_right() {
		return tibintrot_right;
	}

	public void setTibintrot_right(double tibintrot_right) {
		this.tibintrot_right = tibintrot_right;
	}

	public double getVll_right() {
		return vll_right;
	}

	public void setVll_right(double vll_right) {
		this.vll_right = vll_right;
	}

	public String getIc_left() {
		return ic_left;
	}

	public void setIc_left(String ic_left) {
		this.ic_left = ic_left;
	}

	public String getTo_left() {
		return to_left;
	}

	public void setTo_left(String to_left) {
		this.to_left = to_left;
	}

	public double getAxtibacc_left() {
		return axtibacc_left;
	}

	public void setAxtibacc_left(double axtibacc_left) {
		this.axtibacc_left = axtibacc_left;
	}

	public double getTibimpact_left() {
		return tibimpact_left;
	}

	public void setTibimpact_left(double tibimpact_left) {
		this.tibimpact_left = tibimpact_left;
	}

	public double getAxsacacc_left() {
		return axsacacc_left;
	}

	public void setAxsacacc_left(double axsacacc_left) {
		this.axsacacc_left = axsacacc_left;
	}

	public double getSacimpact_left() {
		return sacimpact_left;
	}

	public void setSacimpact_left(double sacimpact_left) {
		this.sacimpact_left = sacimpact_left;
	}

	public double getBrakingforce_left() {
		return brakingforce_left;
	}

	public void setBrakingforce_left(double brakingforce_left) {
		this.brakingforce_left = brakingforce_left;
	}

	public double getPushoffpower_left() {
		return pushoffpower_left;
	}

	public void setPushoffpower_left(double pushoffpower_left) {
		this.pushoffpower_left = pushoffpower_left;
	}

	public double getTibintrot_left() {
		return tibintrot_left;
	}

	public void setTibintrot_left(double tibintrot_left) {
		this.tibintrot_left = tibintrot_left;
	}

	public double getVll_left() {
		return vll_left;
	}

	public void setVll_left(double vll_left) {
		this.vll_left = vll_left;
	}

	public String toString() {
		return time + "  |  " + step + "  |  " + username + "  |  " + run + "  |  " + surface + "  |  " + ic_right
				+ "  |  " + to_right + "  |  " + axsacacc_right + "  |  " + tibimpact_right + "  |  " + axsacacc_right
				+ "  |  " + sacimpact_right + "  |  " + brakingforce_right + "  |  " + pushoffpower_right + "  |  "
				+ tibimpact_right + "  |  " + vll_right + "  |  " + ic_left + "  |  " + to_left + "  |  "
				+ axtibacc_left + "  |  " + tibimpact_left + "  |  " + axsacacc_left + "  |  " + sacimpact_left
				+ "  |  " + brakingforce_left + "  |  " + pushoffpower_left + "  |  " + tibimpact_left + "  |  "
				+ vll_left;
	}
}

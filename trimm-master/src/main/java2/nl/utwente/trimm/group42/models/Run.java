package nl.utwente.trimm.group42.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Run {
	int run_no;
	List<StepData> data;
	BodyPack metadata;
	// averages asked by TRIMM from the project description
	static final String[] AVG_V = { "brakingforce_right", "brakingforce_left", "pushoffpower_left",
			"pushoffpower_right", "tibimpact_right", "tibimpact_left" };

	public Run() {
		data = new ArrayList<StepData>();
	}

	public void addStepData(StepData sd) {
		data.add(sd);
	}

	public List<StepData> getStepData() {
		return data;
	}

	public void addMetaData(BodyPack meta) {
		this.metadata = meta;
	}

	public BodyPack getMeta() {
		return this.metadata;
	}

	/**
	 * Calculates the difference of the time in the first and last stepdata.
	 * 
	 * @Requires the run to have at least one stepdata
	 * @Requires the stepdata to be sorted
	 * @return The duration of the run in minutes
	 */
	public int getDuration() {
		int duration = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
			Date firstDate = sdf.parse(data.get(0).time);
			Date secondDate = sdf.parse(data.get(data.size() - 1).time);
			long differenceInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
			duration = (int) TimeUnit.MINUTES.convert(differenceInMillies, TimeUnit.MILLISECONDS);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return (duration);
	}

	/**
	 * This method calculates the pace of the run.
	 * 
	 * @Requires the run to have at least one stepdata
	 * @Requires the stepdata to be sorted
	 * @return the pace of the run in steps per minute
	 */
	public int getAveragePace() {
		int steps = data.get(data.size() - 1).step;
		int pace = steps / getDuration();
		return pace;
	}

	/**
	 * A method that calculates and returns the different values of the pace through
	 * the run.
	 * 
	 * @return a list of pace values
	 */
	public List<String> getPace() {
		List<String> result = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		try {
			for (int i = 0; i < data.size(); i++) {
				List<Date> dataset = new ArrayList<>();
				int j = -5;
				while (dataset.size() < 10 && i + j < data.size() && j < 5) {
					try {
						dataset.add(sdf.parse(data.get(i - j++).time));
					} catch (IndexOutOfBoundsException e) {
					}
				}
				Long differenceInMillies = Math
						.abs(dataset.get(0).getTime() - dataset.get(dataset.size() - 1).getTime()) / dataset.size();
				double pace;
				pace = 60 / (differenceInMillies / (double) 1000.0);
				result.add(pace + "");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * A method for the total steps of a run
	 * 
	 * @return number of step for the run object
	 */
	public int getTotalSteps() {
		int totalSteps = data.get(data.size() - 1).step;
		return totalSteps;
	}
	/**
	 * A method that calculates the averages of the variables in the beggining of the class  so that
	 * they are easilly and fastly accessed by the frontend
	 * @return a list that contains lists of average of values
	 */

	public List<List<Double>> getVariable_avg() {
		List<Double> br_avg = getOverview(AVG_V[0], AVG_V[1]);
		List<Double> psh_avg = getOverview(AVG_V[2], AVG_V[3]);
		List<Double> impact_avg = getOverview(AVG_V[4], AVG_V[5]);
		List<List<Double>> variable_avg = new ArrayList<List<Double>>();
		variable_avg.add(br_avg);
		variable_avg.add(psh_avg);
		variable_avg.add(impact_avg);
		return variable_avg;

	}

	public List<String> getVariable(String variable) {
		List<String> list = new ArrayList<String>();
		for (StepData stepdata : data) {
			list.add("" + stepdata.get(variable));
		}
		return list;
	}

	private List<Double> convertDataTodouble(List<String> data) {
		List<Double> list = new ArrayList<Double>();
		double converted = 0.0;
		for (String piece : data) {
			converted = Double.parseDouble(piece);
			list.add(converted);
		}
		return list;

	}
	/**
	 * Calculates the averages of right and left leg
	 * @param data1
	 * @param data2
	 * @return list with average values
	 */
	private List<Double> calculateAVG(List<String> data1, List<String> data2) {
		List<Double> list1 = convertDataTodouble(data1);
		List<Double> list2 = convertDataTodouble(data2);
		List<Double> avg = new ArrayList<Double>();
		Iterator<Double> it1 = list1.iterator();
		Iterator<Double> it2 = list2.iterator();
		double avgPiece = 0.0;
		while (it1.hasNext() && it2.hasNext()) {
			avgPiece = (it1.next() + it2.next()) / 2;
			avg.add(avgPiece);
		}

		return avg;

	}

	public List<Double> getOverview(String variable1, String variable2) {
		List<String> list1 = getVariable(variable1);
		List<String> list2 = getVariable(variable2);
		List<Double> avg = calculateAVG(list1, list2);
		return avg;

	}

}

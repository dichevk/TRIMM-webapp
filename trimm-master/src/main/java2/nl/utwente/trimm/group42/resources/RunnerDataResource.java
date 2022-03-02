package nl.utwente.trimm.group42.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;

import nl.utwente.trimm.group42.dao.BodyPackDao;
import nl.utwente.trimm.group42.dao.RunnerDataDao;
import nl.utwente.trimm.group42.models.BodyPack;
import nl.utwente.trimm.group42.models.Run;
@Path("/data")
public class RunnerDataResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	/**
	 * A resource method for the duration of a run
	 * @param sessiontoken
	 * @param run
	 * @return duration of the run in minutes
	 */

	// GET-localhost//trimmapp/rest/Data/duration?sessiontoken=TOKEN&run=1
	@Path("duration")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Integer getDuration(@QueryParam("sessiontoken") String sessiontoken, @QueryParam("run") int run) {
		int duration =RunnerDataDao.getRunWithoutMeta(sessiontoken, run).getDuration();
		return  duration ;
	}
	/**
	 * A resource method for the average pace of a run
	 * @param sessiontoken
	 * @param run
	 * @return average pace of the run steps/min
	 */
	// GET-localhost//trimmapp/rest/Data/pace?sessiontoken=TOKEN&run=1
	@Path("average_pace")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Integer getAveragePace(@QueryParam("sessiontoken") String sessiontoken, @QueryParam("run") int run) {
		int pace=RunnerDataDao.getRunWithoutMeta(sessiontoken, run).getAveragePace();
		return  pace ;
	}
	/**
	 * A resource method for the steps of a run by given run number and sessiontoken which identifies the user session
	 * @param sessiontoken
	 * @param run
	 * @return total steps of the run
	 */
	// GET-localhost//trimmapp/rest/Data/steps?sessiontoken=TOKEN&run=1
	@Path("steps")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Integer getTotalSteps(@QueryParam("sessiontoken") String sessiontoken, @QueryParam("run") int run) {
		return  RunnerDataDao.getRunWithoutMeta(sessiontoken, run).getTotalSteps();
	}
	/**
	 * A resoource method for the pace throughout the run
	 * @param sessiontoken
	 * @param run
	 * @return list of pace values for a run
	 */
	// GET-localhost//trimmapp/rest/Data/pace?sessiontoken=Boris&run=1&variable=@axtibacc_left
	@Path("pace")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getpace(@QueryParam("sessiontoken") String sessiontoken, @QueryParam("run") int run) {
		String json = new Gson().toJson(RunnerDataDao.getRunWithoutMeta(sessiontoken, run).getPace());
		return json;
	}
	/**
	 * A resource method for a variable of a run(e.g pushoffpower_left)
	 * @param sessiontoken
	 * @param run
	 * @param variable
	 * @return list of variable values for a run
	 */
	
	// GET-localhost//trimmapp/rest/Data/variable?sessiontoken=Boris&run=1&variable=@axtibacc_left
	@Path("variable")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getVariable(@QueryParam("sessiontoken") String sessiontoken, @QueryParam("run") int run, @QueryParam("variable") String variable) {
		String json = new Gson().toJson(RunnerDataDao.getRun(sessiontoken, run).getVariable(variable));
		return json;
	}
	/**
	 * A resource method for the average of two values of a run(e.g. pushoffpower_left and pushoffpower_right)
	 * @param sessiontoken
	 * @param run
	 * @param variable1
	 * @param variable2
	 * @return list of average values for a run
	 */
	// GET-localhost//trimmapp/rest/Data/overview_variable?sessiontoken=Boris&run=1&variable1=axtibacc_left&variable2=axtibacc_right
	@Path("overview_variable")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getOverviewOfVar(@QueryParam("sessiontoken") String sessiontoken, @QueryParam("run") int run, @QueryParam("variable1") String variable1, @QueryParam("variable2") String variable2) {
		String json = new Gson().toJson(RunnerDataDao.getRunWithoutMeta(sessiontoken, run).getOverview(variable1, variable2));
		return json;
	}
	/**
	 * A resource method for fetching the meta data of run
	 * @param sessiontoken
	 * @param run
	 * @return json representation of a BodyPack object
	 */
	// GET-localhost//trimmapp/rest/data/overview_run?sessiontoken=Boris&run=1
	@Path("overview_run")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getBasiciOverview(@QueryParam("sessiontoken") String sessiontoken, @QueryParam("run") int run) {
		Gson gson = new Gson();
		String json = gson.toJson(BodyPackDao.getBodyPackData(sessiontoken, run));
		return json;
	}
	/**
	 * A resource method for getting the count of the user's runs
	 * @param sessiontoken
	 * @return number of runs
	 */
	// GET-localhost//trimmapp/rest/data/count?sessiontoken=Bsfsdfsd34
	@Path("count")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getRunCount(@QueryParam("sessiontoken")String sessiontoken) {
		int count = RunnerDataDao.getCount(sessiontoken);
		Gson gson =new Gson();
		String json= gson.toJson(count);
		return json;
	}
	/**
	 * A resource method for getting a whole run
	 * @param sessiontoken
	 * @param run
	 * @return Run object instance
	 */
	// GET-localhost//trimmapp/rest/data/run?sessiontoken=Bsfsdfsd34&run=1
	@Path("run")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Run fetchRun(@QueryParam("sessiontoken") String sessiontoken, @QueryParam("run") int run) {
		Run run1=RunnerDataDao.getRun(sessiontoken, run);
		return run1;
	}
	/**
	 * 	A resource method for getting the dates for the user's runs
	 * @param sessiontoken identifies the user's session
	 * @return
	 */
	@Path("dates")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getDates(@QueryParam("sessiontoken") String sessiontoken){
		ArrayList<String> dates=BodyPackDao.RunDate(sessiontoken);
		String json = new Gson().toJson(dates);
		return json;
	}
	
	

}

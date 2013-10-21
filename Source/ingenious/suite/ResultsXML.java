package ingenious.suite;

import java.util.List;

import ingenious.suite.network.IAdminResponse;

public class ResultsXML {
	private final List<IAdminResponse> responses;
	private final String bad;
	
	public ResultsXML(List<IAdminResponse> responses, String bad){
		this.responses = responses;
		this.bad = bad;
	}
	
	public ResultsXML(List<IAdminResponse> responses){
		this.responses = responses;
		this.bad = null;
	}
	
	/**
	 * Does this result contain a bad
	 */
	public boolean isBad(){
		return (bad != null);		
	}

	public List<IAdminResponse> getResponses() {
		return responses;
	}

	public String getBad() {
		return bad;
	}
}

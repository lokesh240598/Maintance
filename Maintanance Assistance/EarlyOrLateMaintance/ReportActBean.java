package EarlyOrLateMaintance;

public class ReportActBean {
	String Activity_ID;
	String Description;
	int Frequency;
	int Tolerance;
	
	
	public ReportActBean(String activity_ID, String description, int frequency, int tolerance) {
		super();
		Activity_ID = activity_ID;
		Description = description;
		Frequency= frequency;
		Tolerance=tolerance;
		
	}
	public String getActivity_ID() {
		return Activity_ID;
	}
	public void setActivity_ID(String activity_ID) {
		Activity_ID = activity_ID;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public int getFrequency() {
		return Frequency;
	}
	public void setFrequency(int frequency) {
		Frequency = frequency;
	}
	public int getTolerance() {
		return Tolerance;
	}
	public void setTolerance(int tolerance) {
		Tolerance = tolerance;
	}

}

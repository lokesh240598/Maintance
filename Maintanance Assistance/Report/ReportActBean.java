package Report;

public class ReportActBean {
	String Activity_ID;
	String Description;
	
	
	public ReportActBean(String activity_ID, String description) {
		super();
		Activity_ID = activity_ID;
		Description = description;
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

}

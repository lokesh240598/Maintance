package Report;

public class ReportBean {
	String Machine_ID;
	String Machine_Model;
	String Machine_Type;
	String Activity_ID;
	String Activity;
	String Date;
	String Unit;
	
	
	public ReportBean(String machine_ID, String machine_Model, String machine_Type, String activity_ID, String activity,
			String date, String unit) {
		super();
		Machine_ID = machine_ID;
		Machine_Model = machine_Model;
		Machine_Type = machine_Type;
		Activity_ID = activity_ID;
		Activity = activity;
		Date = date;
		Unit = unit;
	}
	public String getMachine_ID() {
		return Machine_ID;
	}
	public void setMachine_ID(String machine_ID) {
		Machine_ID = machine_ID;
	}
	public String getMachine_Model() {
		return Machine_Model;
	}
	public void setMachine_Model(String machine_Model) {
		Machine_Model = machine_Model;
	}
	public String getMachine_Type() {
		return Machine_Type;
	}
	public void setMachine_Type(String machine_Type) {
		Machine_Type = machine_Type;
	}
	public String getActivity_ID() {
		return Activity_ID;
	}
	public void setActivity_ID(String activity_ID) {
		Activity_ID = activity_ID;
	}
	public String getActivity() {
		return Activity;
	}
	public void setActivity(String activity) {
		Activity = activity;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getUnit() {
		return Unit;
	}
	public void setUnit(String unit) {
		Unit = unit;
	}
	
	
}

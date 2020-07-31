package EarlyOrLateMaintance;

public class ReportEarlyBean {
	String Machine_ID;
	String Machine_Model;
	String Machine_Type;
	String Activity_ID;
	String Activity;
	String Date;
	String Schedule_Date;
	String Unit;
	String Status;
	
	
	public ReportEarlyBean(String machine_ID, String machine_Model, String machine_Type, String activity_ID, String activity,
			String date, String schedule_date, String unit, String status ) {
		super();
		Machine_ID = machine_ID;
		Machine_Model = machine_Model;
		Machine_Type = machine_Type;
		Activity_ID = activity_ID;
		Activity = activity;
		Date = date;
		Schedule_Date=schedule_date;
		Unit = unit;
		Status=status;
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
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getSchedule_Date() {
		return Schedule_Date;
	}
	public void setSchedule_Date(String schedule_Date) {
		Schedule_Date = schedule_Date;
	}
	
	
	
	
}

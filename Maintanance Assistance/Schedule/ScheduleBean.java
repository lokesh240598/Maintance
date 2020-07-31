package Schedule;

import java.sql.Date;
import java.time.LocalDate;

public class ScheduleBean {
	String Machine_ID;
	String Machine_Model;
	String Machine_Type;
	String Unit;
	String Activity_ID;
	String Activity;
	String Last_Date;
	String Frequency;
	
	
	
	public ScheduleBean(String machine_ID, String machine_Model, String machine_Type, String unit, String activity_ID,
			String activity, String last_Date, String frequency) {
		super();
		Machine_ID = machine_ID;
		Machine_Model = machine_Model;
		Machine_Type = machine_Type;
		Unit = unit;
		Activity_ID = activity_ID;
		Activity = activity;
		Last_Date = last_Date;
		Frequency = frequency;
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
	public String getUnit() {
		return Unit;
	}
	public void setUnit(String unit) {
		Unit = unit;
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
	public String getLast_Date() {
		return Last_Date;
	}
	public void setLast_Date(String last_Date) {
		Last_Date = last_Date;
	}

	public String getFrequency() {
		return Frequency;
	}

	public void setFrequency(String frequency) {
		Frequency = frequency;
	}
	
	
	
}

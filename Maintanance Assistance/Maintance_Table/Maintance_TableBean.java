package Maintance_Table;

public class Maintance_TableBean {
	String Machine_ID;
	String Machine_Model;
	String Machine_Type;
	String Unit;
	String Activity;
	String Date;
	String Remarks;
	
	
	
	public Maintance_TableBean(String machine_ID, String machine_Model, String machine_Type, String unit,
			String activity, String date ,String remarks) {
		super();
		Machine_ID = machine_ID;
		Machine_Model = machine_Model;
		Machine_Type = machine_Type;
		Unit = unit;
		Activity = activity;
		Date = date;
		Remarks = remarks;
		
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
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	
	
	
}

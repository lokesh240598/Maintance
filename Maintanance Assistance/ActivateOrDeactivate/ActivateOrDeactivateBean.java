package ActivateOrDeactivate;

public class ActivateOrDeactivateBean {
	String Machine_ID;
	String Machine_Model;
	String Machine_Type;
	String Unit;
	public ActivateOrDeactivateBean(String machine_ID, String machine_Model, String machine_Type, String unit) {
		super();
		Machine_ID = machine_ID;
		Machine_Model = machine_Model;
		Machine_Type = machine_Type;
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
	public String getUnit() {
		return Unit;
	}
	public void setUnit(String unit) {
		Unit = unit;
	}
	
	

}

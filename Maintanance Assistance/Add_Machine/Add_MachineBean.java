package Add_Machine;

public class Add_MachineBean {
	String Machine_Type;
	String Activity_ID;
	String Description;
	String Frequency;
	String Tolerance;
	public Add_MachineBean(String machine_Type, String activity_ID, String description, String frequency,
			String tolerance) {
		super();
		Machine_Type = machine_Type;
		Activity_ID = activity_ID;
		Description = description;
		Frequency = frequency;
		Tolerance = tolerance;
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
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getFrequency() {
		return Frequency;
	}
	public void setFrequency(String frequency) {
		Frequency = frequency;
	}
	public String getTolerance() {
		return Tolerance;
	}
	public void setTolerance(String tolerance) {
		Tolerance = tolerance;
	}
	
	
}

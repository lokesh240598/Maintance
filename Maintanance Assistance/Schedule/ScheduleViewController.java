package Schedule;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Schedule.ScheduleBean;

import java.util.Calendar;
import java.util.ResourceBundle;


import Schedule.Data;
import Schedule.ScheduleBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ScheduleViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private Button btnAdd;

    @FXML
    private TextField txtMachineID;

    @FXML
    private TextField txtMachineModel;

    @FXML
    private TextField MaintanceDate1;

    @FXML
    private TextField txtUnit;

    @FXML
    private TableView<ScheduleBean> table;

    @FXML
    private TextField txtActivity;

    @FXML
    private TextField txtActivityID;

    @FXML
    private DatePicker toDate;

    @FXML
    private DatePicker fromDate;

    @FXML
    private TextField txtMachineType;

    @FXML
    private DatePicker MaintanceDate2;

    @FXML
    private Label lblResult;
    
    @FXML
    private TextField txtFrequency;
    
    PreparedStatement pst;
    Connection con;
    ObservableList<ScheduleBean> list;
    
    void doconnect() throws ClassNotFoundException, SQLException
    {
    	Class.forName("com.mysql.jdbc.Driver"); 
    	con=DriverManager.getConnection("jdbc:mysql://localhost/maintance", Data.uid, Data.pwd);
    	System.out.println("Connected");
    }

    @FXML
    void doAddDatabase(ActionEvent event) throws SQLException {
    	if(txtMachineID.getText().equals("")|| txtMachineModel.getText().equals("") ||
    			txtMachineType.getText().equals(null) || txtUnit.getText().equals("") ||
    			txtActivityID.getText().equals("") || txtActivity.getText().equals("") ||
    			MaintanceDate1.getText().equals("") || MaintanceDate2.getValue().equals("") )
		{
			lblResult.setText("FILL DATA CORRECTLY");
		}
    	else
    	{
    		pst=con.prepareStatement("Insert into schedule values(?,?,?,?,?,?,?,?,?,?,?)");
        	
    		
    		pst.setString(1,txtMachineID.getText());
    		pst.setString(2,txtMachineModel.getText());
        	pst.setString(3,txtMachineType.getText());
        	pst.setString(4, txtUnit.getText());
        	pst.setString(5,txtActivityID.getText());
        	pst.setString(6,txtActivity.getText());
        	pst.setString(7, MaintanceDate1.getText());
        	LocalDate local=MaintanceDate2.getValue();
    		java.sql.Date d=java.sql.Date.valueOf(local);
    		pst.setDate(8,d);
    		int m1 = local.getMonthValue();
			int y1 = local.getYear();
			int d111 =local.getDayOfMonth();
			pst.setInt(9, d111);
			pst.setInt(10, m1);
			pst.setInt(11, y1);
    		pst.executeUpdate();
    		
    		
			
			 pst=con.prepareStatement("Update machine_master set Date=? where Machine_ID=? AND Machine_Model=? AND Machine_Type=? AND Unit=? AND Activity_ID=? AND Activity=?");
			 LocalDate local1=MaintanceDate2.getValue();
			 java.sql.Date d1=java.sql.Date.valueOf(local1); 
			 pst.setDate(1,d1); 
			 pst.setString(2,txtMachineID.getText());
			 pst.setString(3,txtMachineModel.getText());
			 pst.setString(4,txtMachineType.getText()); 
			 pst.setString(5,txtUnit.getText()); 
			 pst.setString(6,txtActivityID.getText());
			 pst.setString(7,txtActivity.getText());
			 
			 pst.executeUpdate(); 
			 lblResult.setText("SAVED SUCCESSFULLY");
			
    		
    		txtActivityID.setText(null);
    		txtActivity.setText(null);
    		txtMachineID.setText(null);
        	txtMachineModel.setText(null);
        	txtMachineType.setText(null);
        	txtUnit.setText(null);
        	MaintanceDate1.setText(null);
        	MaintanceDate2.setValue(null);
        	txtFrequency.setText(null);
        	
        	
    	}
    }

    

    @FXML
    void doShow(ActionEvent event) {
    	TableColumn<ScheduleBean, String> machidCol=new TableColumn<ScheduleBean, String>("MACHINE ID");
    	machidCol.setCellValueFactory(new PropertyValueFactory<>("Machine_ID"));//field name in bean
    	machidCol.setMinWidth(100);
    	
    	TableColumn<ScheduleBean, String> machmodCol=new TableColumn<ScheduleBean, String>("MACHINE MODEL");
    	machmodCol.setCellValueFactory(new PropertyValueFactory<>("Machine_Model"));//field name in bean
    	machmodCol.setMinWidth(130);
    	
    	TableColumn<ScheduleBean, String> mtypeCol=new TableColumn<ScheduleBean, String>("MACHINE TYPE");
    	mtypeCol.setCellValueFactory(new PropertyValueFactory<>("Machine_Type"));//field name in bean
    	mtypeCol.setMinWidth(120);
    	
    	TableColumn<ScheduleBean, String> uniCol=new TableColumn<ScheduleBean, String>("UNIT");
    	uniCol.setCellValueFactory(new PropertyValueFactory<>("Unit"));//field name in bean
    	uniCol.setMinWidth(40);
    	
    	TableColumn<ScheduleBean, String> actidCol=new TableColumn<ScheduleBean, String>("ACTIVITY ID");
    	actidCol.setCellValueFactory(new PropertyValueFactory<>("Activity_ID"));//field name in bean
    	actidCol.setMinWidth(100);
    	    	
    	TableColumn<ScheduleBean, String> actCol=new TableColumn<ScheduleBean, String>("ACTIVITY");
    	actCol.setCellValueFactory(new PropertyValueFactory<>("Activity"));//field name in bean
    	actCol.setMinWidth(195);
    	    	
    	TableColumn<ScheduleBean, String> tolCol=new TableColumn<ScheduleBean, String>("LAST MAINTANCE DATE");
    	tolCol.setCellValueFactory(new PropertyValueFactory<>("Last_Date"));//field name in bean
    	tolCol.setMinWidth(150);
    	
    	TableColumn<ScheduleBean, String> freqCol=new TableColumn<ScheduleBean, String>("FREQUENCY");
    	freqCol.setCellValueFactory(new PropertyValueFactory<>("Frequency"));//field name in bean
    	freqCol.setMinWidth(150);
    	
    	table.getColumns().clear();
    	table.getColumns().addAll(machidCol,machmodCol,mtypeCol,uniCol,actidCol,actCol,tolCol,freqCol);
    	list=getRecordsFromTableSome(fromDate.getValue(),toDate.getValue());
    	table.setItems(list);

    	
    }
    ObservableList<ScheduleBean> getRecordsFromTableSome(LocalDate m1,LocalDate m2)
   	{
   		ObservableList<ScheduleBean> list=FXCollections.observableArrayList();
   		
   		try {
   			  Date m11=java.sql.Date.valueOf(m1);
   			  Date m22=java.sql.Date.valueOf(m2);
   			  System.out.println(m1+""+m2);
   			  pst=con.prepareStatement("select Machine_ID, Machine_Model, Machine_Type, Unit, Activity_ID, Activity, Date, Frequency from machine_master where Date Between ? AND ? AND Status = ? ");
   			  pst.setDate(1, m11); 
   			  pst.setDate(2, m22);
   			  pst.setInt(3, 1);
   			  ResultSet rs=  pst.executeQuery();
   			  while(rs.next())
   			  {
   				String machineidCol=rs.getString("Machine_ID");
   				String machinemodelCol=rs.getString("Machine_Model");
   				String machinetype=rs.getString("Machine_Type");
   				String unit=rs.getString("Unit");
   				String activityid=rs.getString("Activity_ID");
   				String activity=rs.getString("Activity");
   				String ldate=rs.getString("Date");
   				String freq=rs.getString("Frequency");
   				
   				
   				ScheduleBean bean=new ScheduleBean(machineidCol,machinemodelCol,machinetype,unit,activityid,activity,ldate,freq);
   				list.add(bean);
   			  }
   			
   			} 
   		catch (SQLException e) 
   		{
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
   		
   		return list;
    }
    

    @FXML
    void doShowData(MouseEvent event) {
    	ScheduleBean A1=table.getSelectionModel().getSelectedItem();
    	if(A1==null)
    	{
    		lblResult.setText("PLEASE SELECT ACTIVITY");
    	}
		
		 else { 
			 String a1=A1.getMachine_ID(); 
			 String a2=A1.getMachine_Model(); 
			 String a3=A1.getMachine_Type(); 
			 String a4=A1.getUnit(); 
			 String a5=A1.getActivity_ID(); 
			 String a6=A1.getActivity(); 
			 String a7=A1.getLast_Date();
			 String a8=A1.getFrequency();
			 txtMachineID.setText(a1);
			 txtMachineModel.setText(a2);
			 txtMachineType.setText(a3);
			 txtUnit.setText(a4);
			 txtActivityID.setText(a5);
			 txtActivity.setText(a6);
			 MaintanceDate1.setText(a7);
			 txtFrequency.setText(a8);
			 btnAdd.setDisable(true);
			 }
		 lblResult.setText(null);
    }
    
    @FXML
    void doDate(ActionEvent event) {
    	String date=MaintanceDate1.getText();
    	String freq=txtFrequency.getText();
    	int frequency=Integer.parseInt(freq);    	 
    	System.out.println("Date before Addition: "+date);
    	//Specifying date format that matches the given date
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar c = Calendar.getInstance();
    	try{
    	   //Setting the date to the given date
    	   c.setTime(sdf.parse(date));
    	}catch(ParseException e){
    		e.printStackTrace();
    	 }
    	   
    	//Number of Days to add
    	c.add(Calendar.DAY_OF_MONTH, frequency);  
    	
    	String newDate = sdf.format(c.getTime());  
    	
    	System.out.println("Date after Addition: "+newDate);  
    	
    	LocalDate localDate = null;
        DateTimeFormatter formatter = null;
    	
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        localDate = LocalDate.parse(newDate, formatter);
        System.out.println("Input Date?= "+ newDate);
        System.out.println("Converted Date?= " + localDate + "\n");
    	MaintanceDate2.setValue(localDate);
    	btnAdd.setDisable(false);
    }    

    @FXML
    void initialize() throws ClassNotFoundException, SQLException {
        doconnect();
    }
}

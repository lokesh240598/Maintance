package EarlyOrLateMaintance;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import EarlyOrLateMaintance.Data;
import EarlyOrLateMaintance.ReportActBean;


import EarlyOrLateMaintance.ReportEarlyBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;


public class EarlyOrLateMaintanceViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtMachineID;

    @FXML
    private ComboBox<String> comboActivity;

    @FXML
    private DatePicker currentdate;

    @FXML
    private DatePicker scheduledate;
    
    @FXML
    private Label lblResult;

    @FXML
    private TableView<ReportEarlyBean> table;

    @FXML
    private DatePicker toDate;
    
    @FXML
    private Button btnAdd;

    @FXML
    private DatePicker fromDate;
    
    @FXML
    private TableView<ReportActBean> actTable;
    
    PreparedStatement pst;
    Connection con;
    ObservableList<ReportEarlyBean> list;
    ObservableList<ReportActBean> list1;
    
    String machineid;
    String machinemodel;
    String machinetype;
    String activityid;
    String activity;
    String unit;
    String date;
    String schedule;
    String status;
    int frequency;
    int tolerance;
    LocalDate localDate = null;
    int flag;
    
    void doconnect() throws ClassNotFoundException, SQLException
    {
    	Class.forName("com.mysql.jdbc.Driver"); 
    	con=DriverManager.getConnection("jdbc:mysql://localhost/maintance", Data.uid, Data.pwd);
    	System.out.println("Connected");
    }


    @FXML
    void doExport(ActionEvent event) {
    	try {
			writeExcel();
			lblResult.setText("EXPORTED SUCCESSFULLY");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void writeExcel() throws Exception {
        Writer writer = null;
        try {
        	FileChooser chooser=new FileChooser();
	    	
        	chooser.setTitle("Select Path:");
        	
        	chooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Files", "*.*")
                    
                );
        	File file=chooser.showSaveDialog(null);
        	String filePath=file.getAbsolutePath();
        	if(!(filePath.endsWith(".csv")||filePath.endsWith(".CSV")))
        	{
        		lblResult.setText(".csv EXTENSION REQUIRED");
        		return;
        	}
        	 file = new File(filePath);
        	 
        	 
        	 
            writer = new BufferedWriter(new FileWriter(file));
            String text="Machine ID, Machine Model, Machine Type, Activity ID, Activity, Maintance Date, Schedule Date, Unit, Status\n";
            writer.write(text);
            for (ReportEarlyBean p : list)
            {
				text = p.getMachine_ID()+ " , " + p.getMachine_Model()+ " , " + p.getMachine_Type()+ " , " + p.getActivity_ID() +" , " +p.getActivity() +" , " + p.getDate()+" , " + p.getSchedule_Date()+ " , "+ p.getUnit()+" , "+ p.getStatus()+"\n";
                writer.write(text);
                System.out.println(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
           
            writer.flush();
             writer.close();
        }
    }
    
    @FXML
    void doSelectActivty(ActionEvent event) {

    	TableColumn<ReportActBean, String> actIDCol=new TableColumn<ReportActBean, String>("ACTIVITY ID");
     	actIDCol.setCellValueFactory(new PropertyValueFactory<>("Activity_ID"));//field name in bean
     	actIDCol.setMinWidth(120);
     	
     	TableColumn<ReportActBean, String> actCol=new TableColumn<ReportActBean, String>("ACTIVITY");
     	actCol.setCellValueFactory(new PropertyValueFactory<>("Description"));//field name in bean
     	actCol.setMinWidth(160);
     	
     	TableColumn<ReportActBean, Integer> freCol=new TableColumn<ReportActBean, Integer>("FREQUENCY");
     	freCol.setCellValueFactory(new PropertyValueFactory<>("Frequency"));//field name in bean
     	freCol.setMinWidth(120);
     	
     	TableColumn<ReportActBean, Integer> tolCol=new TableColumn<ReportActBean, Integer>("Tolerance");
     	tolCol.setCellValueFactory(new PropertyValueFactory<>("Tolerance"));//field name in bean
     	tolCol.setMinWidth(120);
     	
     	actTable.getColumns().clear();
    	actTable.getColumns().addAll(actIDCol,actCol,freCol,tolCol);
    	
    	list1=getRecordsFromTableSome4(comboActivity.getValue());
    	actTable.setItems(list1);
     	
		
	    }
    

    private ObservableList<ReportActBean> getRecordsFromTableSome4(String mactype) {
    	ObservableList<ReportActBean> list1=FXCollections.observableArrayList();
    	try {
 			  
 			  pst=con.prepareStatement("select Activity_ID, Description, Frequency, Tolerance from activity_master where Machine_Type=?");
 			  pst.setString(1, mactype);
 	
 			  ResultSet rs=  pst.executeQuery();
 			  while(rs.next())
 			  {
 				String actid=rs.getString("Activity_ID");
 				String act=rs.getString("Description");
 				int fre=rs.getInt("Frequency");
 				int tol=rs.getInt("Tolerance");
 	 				
 				ReportActBean bean=new ReportActBean(actid,act,fre,tol);
 				list1.add(bean);
 			  }
 			
 			} 
 		catch (SQLException e) 
 		{
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		
 		return list1;


	}



    @FXML
    void doAddToDatabase(ActionEvent event) throws SQLException {
    	LocalDate current=currentdate.getValue();
		LocalDate addDate=current.plusDays(frequency);
		
    	
    	pst=con.prepareStatement("Insert into earlyorlatemaintance values(?,?,?,?,?,?,?,?,?)");
    	
		
		pst.setString(1,machineid);
		pst.setString(2,machinemodel);
    	pst.setString(3,machinetype);
    	pst.setString(4, unit);
    	pst.setString(5,activityid);
    	pst.setString(6,activity);
    	
    	LocalDate local=currentdate.getValue();
		java.sql.Date d=java.sql.Date.valueOf(local);
		pst.setDate(7,d);
		pst.setString(8, date);
		pst.setString(9, status);
		pst.executeUpdate();
		
		
		
		
		
		pst=con.prepareStatement("Update machine_master set Date=? where Machine_ID=? AND Activity_ID=? AND Activity=?");
		 LocalDate local1=addDate;
		 java.sql.Date d1=java.sql.Date.valueOf(local1); 
		 
		 pst.setDate(1,d1);
		 System.out.println(local1);
		 pst.setString(2,machineid); 
		 pst.setString(3,activityid);
		 pst.setString(4,activity);
		 System.out.println(machineid+activityid+activity+"successful");
		 pst.executeUpdate();
		 
		 
		 pst=con.prepareStatement("Update schedule set Last_Date=?, Next_Date=? where Machine_ID=? AND Activity_ID=? AND Activity=? AND Next_Date=? ");
		 LocalDate local2=currentdate.getValue();
		 java.sql.Date d22= java.sql.Date.valueOf(local2);
		 System.out.println(local2);
		 pst.setDate(1, d22);
		 LocalDate local3=addDate;
		 java.sql.Date d11=java.sql.Date.valueOf(local3); 
		 System.out.println(d11);
		 pst.setDate(2,d11); 
		 pst.setString(3,machineid); 
		 pst.setString(4,activityid);
		 pst.setString(5,activity);
		 LocalDate local4=scheduledate.getValue();
		 java.sql.Date d33= java.sql.Date.valueOf(local4);
		 System.out.println(d33);
		 pst.setDate(6, d33);
		 pst.setDate(6, d33);
		 
		 pst.executeUpdate();
		 
		 lblResult.setText("SAVED SUCCESSFULLY");
		
    }

		
		
    

    @FXML
    void doCheck(ActionEvent event) {
    	
    	LocalDate current=currentdate.getValue();
    	LocalDate subDate=localDate.minusDays(tolerance);
    	LocalDate addDate=localDate.plusDays(tolerance);
    	
    	System.out.println(subDate+"    "+addDate);
    	if(current.compareTo(subDate)<0) {
    		
    		status="EARLY MAINTANCE";
    		System.out.println(status);
    		flag=1;
    		lblResult.setText(status);
      	}
    	else if(current.compareTo(addDate)>0) {
    		
    		status="LATE MAINTANCE";
    		System.out.println(status);
    		flag=2;
    		lblResult.setText(status);
    		
  	}
    	else 
    	{
    		status="ON TIME MAINTANCE";
    		System.out.println(status);
    		lblResult.setText(status);
    	}
  }
    	
    	
    
    
    
    @FXML
    void doSearch(ActionEvent event) {
    	
    	try {
 			  machineid=txtMachineID.getText();
 			  machinetype=comboActivity.getValue();
 			  
 			  pst=con.prepareStatement("select * from machine_master where Machine_ID=? and Machine_Type=? and Activity=? ");
 			 
 			  pst.setString(1,machineid);
 			  pst.setString(2, machinetype);
 			  pst.setString(3, activity);
 	
 			  ResultSet rs=  pst.executeQuery();
 			  while(rs.next())
 			  {
 				machineid=rs.getString("Machine_ID");
 				machinemodel=rs.getString("Machine_Model");
 				machinetype=rs.getString("Machine_Type");
 				activityid=rs.getString("Activity_ID");
 				activity=rs.getString("Activity");
 				unit=rs.getString("Unit");
 				date=rs.getString("Date");
 				status=rs.getString("Status");
 			  }
 			  
 			
 			} 
 		catch (SQLException e) 
 		{
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
    	
        DateTimeFormatter formatter = null;
    	
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        localDate = LocalDate.parse(date, formatter);
        
        scheduledate.setValue(localDate);
        
    	

    }

    @FXML
    void doShowData(MouseEvent event) {
    	ReportActBean A1=actTable.getSelectionModel().getSelectedItem();
		 activity=A1.getDescription();
		 frequency=A1.getFrequency();
		 tolerance=A1.getTolerance();
		 System.out.println(activity);
    }


    @FXML
    void doShow(ActionEvent event) {

    	if(fromDate.getValue().equals(null)|| toDate.getValue().equals(null))
    	{
    		lblResult.setText("Fill Data Correctly...");
    	}

    	
    		TableColumn<ReportEarlyBean, String> machidCol=new TableColumn<ReportEarlyBean, String>("MACHINE ID");
        	machidCol.setCellValueFactory(new PropertyValueFactory<>("Machine_ID"));//field name in bean
        	machidCol.setMinWidth(100);
        	
        	TableColumn<ReportEarlyBean, String> machmodCol=new TableColumn<ReportEarlyBean, String>("MACHINE MODEL");
        	machmodCol.setCellValueFactory(new PropertyValueFactory<>("Machine_Model"));//field name in bean
        	machmodCol.setMinWidth(100);
        	
        	TableColumn<ReportEarlyBean, String> mtypeCol=new TableColumn<ReportEarlyBean, String>("MACHINE TYPE");
        	mtypeCol.setCellValueFactory(new PropertyValueFactory<>("Machine_Type"));//field name in bean
        	mtypeCol.setMinWidth(120);
        	
        	TableColumn<ReportEarlyBean, String> actidCol=new TableColumn<ReportEarlyBean, String>("ACTIVITY ID");
        	actidCol.setCellValueFactory(new PropertyValueFactory<>("Activity_ID"));//field name in bean
        	actidCol.setMinWidth(100);
        	
        	TableColumn<ReportEarlyBean, String> actCol=new TableColumn<ReportEarlyBean, String>("ACTIVITY");
        	actCol.setCellValueFactory(new PropertyValueFactory<>("Activity"));//field name in bean
        	actCol.setMinWidth(195);
        	
        	TableColumn<ReportEarlyBean, String> freCol=new TableColumn<ReportEarlyBean, String>("MAINTANCE DATE");
        	freCol.setCellValueFactory(new PropertyValueFactory<>("Date"));//field name in bean
        	freCol.setMinWidth(130);
        	
        	TableColumn<ReportEarlyBean, String> datCol=new TableColumn<ReportEarlyBean, String>("SCHEDULE DATE");
        	datCol.setCellValueFactory(new PropertyValueFactory<>("Schedule_Date"));//field name in bean
        	datCol.setMinWidth(110);
        	
        	TableColumn<ReportEarlyBean, String> tolCol=new TableColumn<ReportEarlyBean, String>("UNIT");
        	tolCol.setCellValueFactory(new PropertyValueFactory<>("Unit"));//field name in bean
        	tolCol.setMinWidth(100);
        	
        	TableColumn<ReportEarlyBean, String> statCol=new TableColumn<ReportEarlyBean, String>("STATUS");
        	statCol.setCellValueFactory(new PropertyValueFactory<>("Status"));//field name in bean
        	statCol.setMinWidth(150);
        	
        	table.getColumns().clear();
        	table.getColumns().addAll(machidCol,machmodCol,mtypeCol,actidCol,actCol,freCol,datCol,tolCol,statCol);
        	
        	list=getRecordsFromTableSome(fromDate.getValue(),toDate.getValue());
        	table.setItems(list);
    	

    }

    private ObservableList<ReportEarlyBean> getRecordsFromTableSome(LocalDate m1, LocalDate m2) {
		
        ObservableList<ReportEarlyBean> list=FXCollections.observableArrayList();
   		
   		try {
   			  Date m11=java.sql.Date.valueOf(m1);
			  Date m22=java.sql.Date.valueOf(m2);
   			  pst=con.prepareStatement("select * from earlyorlatemaintance where Date Between ? AND ? ");
   			 
   			  pst.setDate(1,m11);
   			  pst.setDate(2, m22);
   			  
   			  ResultSet rs=  pst.executeQuery();
   			  while(rs.next())
   			  {
   				String mid=rs.getString("Machine_ID");
   				String mmod=rs.getString("Machine_Model");
   				String mtype=rs.getString("Machine_Type");
   				String actid=rs.getString("Activity_ID");
   				String act=rs.getString("Activity");
   				String fre=rs.getString("Date");
   				String dat=rs.getString("Schedule_Date");
   				String tol=rs.getString("Unit");
   				String stat=rs.getString("Status");
   				
   				
   				ReportEarlyBean bean=new ReportEarlyBean(mid,mmod,mtype,actid,act,fre,dat,tol,stat);
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
    void initialize() throws ClassNotFoundException, SQLException {
        doconnect();
        filluname();
    }
	
    ArrayList<String>uname;
    void filluname()
    {
    	uname=new ArrayList<String>();
    	comboActivity.getItems().clear();
    	try {
    		
    		//?: in parameters
			pst=con.prepareStatement("select distinct Machine_Type from activity_master ");
			ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
				String r=rs.getString("Machine_Type");
				System.out.println(r);
				uname.add(String.valueOf(r));
			}
			comboActivity.getItems().addAll(uname);
			
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    
    }
    
}

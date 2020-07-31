package Report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Add_Machine.Add_MachineBean;
import Report.ReportBean;
import Report.ReportActBean;

import java.sql.PreparedStatement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class ReportViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<ReportBean> table;

    @FXML
    private RadioButton rbMachineType;

    @FXML
    private ToggleGroup Option;

    @FXML
    private ComboBox<String> comboMachineType1;

    @FXML
    private Label lblResult;

    @FXML
    private DatePicker toDate;

    @FXML
    private DatePicker fromDate;

    @FXML
    private RadioButton rbMachineID;

    @FXML
    private TextField lblMachineID;

    @FXML
    private RadioButton rbMachineType1;

    @FXML
    private ComboBox<String> comboMachineType2;

    @FXML
    private ComboBox<String> comboActivity;
    
    @FXML
    private TableView<ReportActBean> actTable;
    
    
    PreparedStatement pst;
    Connection con;
    ObservableList<ReportBean> list;
    ObservableList<ReportActBean> list1;
    
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
            String text="Machine ID, Machine Model, Machine Type, Activity ID, Activity, Date, Unit\n";
            writer.write(text);
            for (ReportBean p : list)
            {
				text = p.getMachine_ID()+ " , " + p.getMachine_Model()+ " , " + p.getMachine_Type()+ " , " + p.getActivity_ID() +" , " +p.getActivity() +" , " + p.getDate()+" , " + p.getUnit()+" , "+"\n";
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
    void doSearch(ActionEvent event) {
    	
    	if(fromDate.getValue().equals(null)|| toDate.getValue().equals(null))
    	{
    		lblResult.setText("Fill Data Correctly...");
    	}

    	if(rbMachineID.isSelected()==true)
    	{
    		TableColumn<ReportBean, String> machidCol=new TableColumn<ReportBean, String>("MACHINE ID");
        	machidCol.setCellValueFactory(new PropertyValueFactory<>("Machine_ID"));//field name in bean
        	machidCol.setMinWidth(100);
        	
        	TableColumn<ReportBean, String> machmodCol=new TableColumn<ReportBean, String>("MACHINE MODEL");
        	machmodCol.setCellValueFactory(new PropertyValueFactory<>("Machine_Model"));//field name in bean
        	machmodCol.setMinWidth(100);
        	
        	TableColumn<ReportBean, String> mtypeCol=new TableColumn<ReportBean, String>("MACHINE TYPE");
        	mtypeCol.setCellValueFactory(new PropertyValueFactory<>("Machine_Type"));//field name in bean
        	mtypeCol.setMinWidth(120);
        	
        	TableColumn<ReportBean, String> actidCol=new TableColumn<ReportBean, String>("ACTIVITY ID");
        	actidCol.setCellValueFactory(new PropertyValueFactory<>("Activity_ID"));//field name in bean
        	actidCol.setMinWidth(100);
        	
        	TableColumn<ReportBean, String> actCol=new TableColumn<ReportBean, String>("ACTIVITY");
        	actCol.setCellValueFactory(new PropertyValueFactory<>("Activity"));//field name in bean
        	actCol.setMinWidth(195);
        	
        	TableColumn<ReportBean, String> freCol=new TableColumn<ReportBean, String>("DATE");
        	freCol.setCellValueFactory(new PropertyValueFactory<>("Date"));//field name in bean
        	freCol.setMinWidth(100);
        	
        	TableColumn<ReportBean, String> tolCol=new TableColumn<ReportBean, String>("UNIT");
        	tolCol.setCellValueFactory(new PropertyValueFactory<>("Unit"));//field name in bean
        	tolCol.setMinWidth(100);
        	
        	table.getColumns().clear();
        	table.getColumns().addAll(machidCol,machmodCol,mtypeCol,actidCol,actCol,freCol,tolCol);
        	
        	list=getRecordsFromTableSome1(fromDate.getValue(),toDate.getValue(),lblMachineID.getText());
        	table.setItems(list);
    	}
    
    	else if(rbMachineType.isSelected()==true)
    	{
    		TableColumn<ReportBean, String> machidCol=new TableColumn<ReportBean, String>("MACHINE ID");
        	machidCol.setCellValueFactory(new PropertyValueFactory<>("Machine_ID"));//field name in bean
        	machidCol.setMinWidth(100);
        	
        	TableColumn<ReportBean, String> machmodCol=new TableColumn<ReportBean, String>("MACHINE MODEL");
        	machmodCol.setCellValueFactory(new PropertyValueFactory<>("Machine_Model"));//field name in bean
        	machmodCol.setMinWidth(100);
        	
        	TableColumn<ReportBean, String> mtypeCol=new TableColumn<ReportBean, String>("MACHINE TYPE");
        	mtypeCol.setCellValueFactory(new PropertyValueFactory<>("Machine_Type"));//field name in bean
        	mtypeCol.setMinWidth(120);
        	
        	TableColumn<ReportBean, String> actidCol=new TableColumn<ReportBean, String>("ACTIVITY ID");
        	actidCol.setCellValueFactory(new PropertyValueFactory<>("Activity_ID"));//field name in bean
        	actidCol.setMinWidth(100);
        	
        	TableColumn<ReportBean, String> actCol=new TableColumn<ReportBean, String>("ACTIVITY");
        	actCol.setCellValueFactory(new PropertyValueFactory<>("Activity"));//field name in bean
        	actCol.setMinWidth(195);
        	
        	TableColumn<ReportBean, String> freCol=new TableColumn<ReportBean, String>("DATE");
        	freCol.setCellValueFactory(new PropertyValueFactory<>("Date"));//field name in bean
        	freCol.setMinWidth(100);
        	
        	TableColumn<ReportBean, String> tolCol=new TableColumn<ReportBean, String>("UNIT");
        	tolCol.setCellValueFactory(new PropertyValueFactory<>("Unit"));//field name in bean
        	tolCol.setMinWidth(100);
        	
        	table.getColumns().clear();
        	table.getColumns().addAll(machidCol,machmodCol,mtypeCol,actidCol,actCol,freCol,tolCol);
        	
        	list=getRecordsFromTableSome2(fromDate.getValue(),toDate.getValue(),comboMachineType1.getValue());
        	table.setItems(list);
    	}
    	
    	else if(rbMachineType1.isSelected()==true)
    	{
    		TableColumn<ReportBean, String> machidCol=new TableColumn<ReportBean, String>("MACHINE ID");
        	machidCol.setCellValueFactory(new PropertyValueFactory<>("Machine_ID"));//field name in bean
        	machidCol.setMinWidth(100);
        	
        	TableColumn<ReportBean, String> machmodCol=new TableColumn<ReportBean, String>("MACHINE MODEL");
        	machmodCol.setCellValueFactory(new PropertyValueFactory<>("Machine_Model"));//field name in bean
        	machmodCol.setMinWidth(100);
        	
        	TableColumn<ReportBean, String> mtypeCol=new TableColumn<ReportBean, String>("MACHINE TYPE");
        	mtypeCol.setCellValueFactory(new PropertyValueFactory<>("Machine_Type"));//field name in bean
        	mtypeCol.setMinWidth(120);
        	
        	TableColumn<ReportBean, String> actidCol=new TableColumn<ReportBean, String>("ACTIVITY ID");
        	actidCol.setCellValueFactory(new PropertyValueFactory<>("Activity_ID"));//field name in bean
        	actidCol.setMinWidth(100);
        	
        	TableColumn<ReportBean, String> actCol=new TableColumn<ReportBean, String>("ACTIVITY");
        	actCol.setCellValueFactory(new PropertyValueFactory<>("Activity"));//field name in bean
        	actCol.setMinWidth(195);
        	
        	TableColumn<ReportBean, String> freCol=new TableColumn<ReportBean, String>("DATE");
        	freCol.setCellValueFactory(new PropertyValueFactory<>("Date"));//field name in bean
        	freCol.setMinWidth(100);
        	
        	TableColumn<ReportBean, String> tolCol=new TableColumn<ReportBean, String>("UNIT");
        	tolCol.setCellValueFactory(new PropertyValueFactory<>("Unit"));//field name in bean
        	tolCol.setMinWidth(100);
        	
        	table.getColumns().clear();
        	table.getColumns().addAll(machidCol,machmodCol,mtypeCol,actidCol,actCol,freCol,tolCol);
        	
        	list=getRecordsFromTableSome5(fromDate.getValue(),toDate.getValue(),activity);
        	table.setItems(list);
    	}
    	
    	
    	else
    	{
    		
        		TableColumn<ReportBean, String> machidCol=new TableColumn<ReportBean, String>("MACHINE ID");
            	machidCol.setCellValueFactory(new PropertyValueFactory<>("Machine_ID"));//field name in bean
            	machidCol.setMinWidth(100);
            	
            	TableColumn<ReportBean, String> machmodCol=new TableColumn<ReportBean, String>("MACHINE MODEL");
            	machmodCol.setCellValueFactory(new PropertyValueFactory<>("Machine_Model"));//field name in bean
            	machmodCol.setMinWidth(100);
            	
            	TableColumn<ReportBean, String> mtypeCol=new TableColumn<ReportBean, String>("MACHINE TYPE");
            	mtypeCol.setCellValueFactory(new PropertyValueFactory<>("Machine_Type"));//field name in bean
            	mtypeCol.setMinWidth(120);
            	
            	TableColumn<ReportBean, String> actidCol=new TableColumn<ReportBean, String>("ACTIVITY ID");
            	actidCol.setCellValueFactory(new PropertyValueFactory<>("Activity_ID"));//field name in bean
            	actidCol.setMinWidth(100);
            	
            	TableColumn<ReportBean, String> actCol=new TableColumn<ReportBean, String>("ACTIVITY");
            	actCol.setCellValueFactory(new PropertyValueFactory<>("Activity"));//field name in bean
            	actCol.setMinWidth(195);
            	
            	TableColumn<ReportBean, String> freCol=new TableColumn<ReportBean, String>("DATE");
            	freCol.setCellValueFactory(new PropertyValueFactory<>("Date"));//field name in bean
            	freCol.setMinWidth(100);
            	
            	TableColumn<ReportBean, String> tolCol=new TableColumn<ReportBean, String>("UNIT");
            	tolCol.setCellValueFactory(new PropertyValueFactory<>("Unit"));//field name in bean
            	tolCol.setMinWidth(100);
            	
            	table.getColumns().clear();
            	table.getColumns().addAll(machidCol,machmodCol,mtypeCol,actidCol,actCol,freCol,tolCol);
            	
            	list=getRecordsFromTableSome3(fromDate.getValue(),toDate.getValue());
            	table.setItems(list);
        
    	}	
    }
    
    
	private ObservableList<ReportBean> getRecordsFromTableSome5(LocalDate m1, LocalDate m2, String activity) {
    	ObservableList<ReportBean> list=FXCollections.observableArrayList();
   		
   		try {
   			  Date m11=java.sql.Date.valueOf(m1);
			  Date m22=java.sql.Date.valueOf(m2);
   			  pst=con.prepareStatement("select * from schedule where Last_Date Between ? AND ? And Activity = ?");
   			 
   			  pst.setDate(1,m11);
   			  pst.setDate(2, m22);
   			  pst.setString(3, activity);
   			  ResultSet rs=  pst.executeQuery();
   			  while(rs.next())
   			  {
   				String mid=rs.getString("Machine_ID");
   				String mmod=rs.getString("Machine_Model");
   				String mtype=rs.getString("Machine_Type");
   				String actid=rs.getString("Activity_ID");
   				String act=rs.getString("Activity");
   				String fre=rs.getString("Last_Date");
   				String tol=rs.getString("Unit");
   				
   				
   				ReportBean bean=new ReportBean(mid,mmod,mtype,actid,act,fre,tol);
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


	private ObservableList<ReportBean> getRecordsFromTableSome1(LocalDate m1, LocalDate m2, String macid) {
		
    	ObservableList<ReportBean> list=FXCollections.observableArrayList();
   		
   		try {
   			  Date m11=java.sql.Date.valueOf(m1);
			  Date m22=java.sql.Date.valueOf(m2);
   			  pst=con.prepareStatement("select * from schedule where Last_Date Between ? AND ? And Machine_ID= ?");
   			 
   			  pst.setDate(1,m11);
   			  pst.setDate(2, m22);
   			  pst.setString(3, macid);
   			  ResultSet rs=  pst.executeQuery();
   			  while(rs.next())
   			  {
   				String mid=rs.getString("Machine_ID");
   				String mmod=rs.getString("Machine_Model");
   				String mtype=rs.getString("Machine_Type");
   				String actid=rs.getString("Activity_ID");
   				String act=rs.getString("Activity");
   				String fre=rs.getString("Last_Date");
   				String tol=rs.getString("Unit");
   				
   				
   				ReportBean bean=new ReportBean(mid,mmod,mtype,actid,act,fre,tol);
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


	private ObservableList<ReportBean> getRecordsFromTableSome2(LocalDate m1, LocalDate m2, String mactype) {
		// TODO Auto-generated method stub
		ObservableList<ReportBean> list=FXCollections.observableArrayList();
   		
   		try {
   			  Date m11=java.sql.Date.valueOf(m1);
			  Date m22=java.sql.Date.valueOf(m2);
   			  pst=con.prepareStatement("select * from schedule where Last_Date Between ? AND ? And Machine_Type= ?");
   			 
   			  pst.setDate(1,m11);
   			  pst.setDate(2, m22);
   			  pst.setString(3, mactype);
   			  ResultSet rs=  pst.executeQuery();
   			  while(rs.next())
   			  {
   				String mid=rs.getString("Machine_ID");
   				String mmod=rs.getString("Machine_Model");
   				String mtype=rs.getString("Machine_Type");
   				String actid=rs.getString("Activity_ID");
   				String act=rs.getString("Activity");
   				String fre=rs.getString("Last_Date");
   				String tol=rs.getString("Unit");
   				
   				
   				ReportBean bean=new ReportBean(mid,mmod,mtype,actid,act,fre,tol);
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


	private ObservableList<ReportBean> getRecordsFromTableSome3(LocalDate m1, LocalDate m2) {
		ObservableList<ReportBean> list=FXCollections.observableArrayList();
   		
   		try {
   			  Date m11=java.sql.Date.valueOf(m1);
			  Date m22=java.sql.Date.valueOf(m2);
   			  pst=con.prepareStatement("select * from schedule where Last_Date Between ? AND ?");
   			 
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
   				String fre=rs.getString("Last_Date");
   				String tol=rs.getString("Unit");
   				
   				
   				ReportBean bean=new ReportBean(mid,mmod,mtype,actid,act,fre,tol);
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
    void doClear(ActionEvent event) {
    	lblMachineID.setText(null);
    	comboMachineType1.setValue(null);
    	lblResult.setText(null);
		Option.selectToggle(null); 
		fromDate.setValue(null);
		toDate.setValue(null);
		table.getColumns().clear();
		actTable.getColumns().clear();
		comboMachineType2.setValue(null);
    }
	
	String activity;
	
	 @FXML
	    void doShowData(MouseEvent event) {
		 ReportActBean A1=actTable.getSelectionModel().getSelectedItem();
		 activity=A1.getDescription();
		 System.out.println(activity);
	    }


	 @FXML
	    void doSelect(ActionEvent event) {
		 System.out.println(comboMachineType2.getValue());
		 
		TableColumn<ReportActBean, String> actIDCol=new TableColumn<ReportActBean, String>("ACTIVITY ID");
     	actIDCol.setCellValueFactory(new PropertyValueFactory<>("Activity_ID"));//field name in bean
     	actIDCol.setMinWidth(120);
     	
     	TableColumn<ReportActBean, String> actCol=new TableColumn<ReportActBean, String>("ACTIVITY");
     	actCol.setCellValueFactory(new PropertyValueFactory<>("Description"));//field name in bean
     	actCol.setMinWidth(200);
     	
     	actTable.getColumns().clear();
    	actTable.getColumns().addAll(actIDCol,actCol);
    	
    	list1=getRecordsFromTableSome4(comboMachineType2.getValue());
    	actTable.setItems(list1);
     	
		
	    }
    

    private ObservableList<ReportActBean> getRecordsFromTableSome4(String mactype) {
    	ObservableList<ReportActBean> list1=FXCollections.observableArrayList();
    	try {
 			  
 			  pst=con.prepareStatement("select Activity_ID, Description from activity_master where Machine_Type=?");
 			  pst.setString(1, mactype);
 	
 			  ResultSet rs=  pst.executeQuery();
 			  while(rs.next())
 			  {
 				String actid=rs.getString("Activity_ID");
 				String act=rs.getString("Description");
 	 				
 				ReportActBean bean=new ReportActBean(actid,act);
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
    void initialize() throws ClassNotFoundException, SQLException {
    	doconnect();
    	filluname1();
    	filluname2();
    	      
    }
    
    ArrayList<String>uname;
    void filluname1()
    {
    	uname=new ArrayList<String>();
    	comboMachineType1.getItems().clear();
    	try {
    		
    		//?: in parameters
			pst=con.prepareStatement("select distinct Machine_Type from activity_master");
			ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
				String r=rs.getString("Machine_Type");
				System.out.println(r);
				uname.add(String.valueOf(r));
			}
			comboMachineType1.getItems().addAll(uname);
			
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    
    }
    
    ArrayList<String>uname1;
    void filluname2()
    {
    	uname1=new ArrayList<String>();
    	comboMachineType2.getItems().clear();
    	try {
    		
    		//?: in parameters
			pst=con.prepareStatement("select distinct Machine_Type from activity_master");
			ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
				String r=rs.getString("Machine_Type");
				System.out.println(r);
				uname1.add(String.valueOf(r));
			}
			comboMachineType2.getItems().addAll(uname1);
			
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    
    }
}
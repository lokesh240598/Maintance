package Maintance_Table;

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
import java.util.ResourceBundle;


import Maintance_Table.Data;
import Maintance_Table.Maintance_TableBean;
import Schedule.ScheduleBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Maintance_TableViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Maintance_TableBean> table;
    

    @FXML
    private DatePicker toDate;

    @FXML
    private DatePicker fromDate;

    @FXML
    private Label lblResult;
    
   
    
    PreparedStatement pst;
    Connection con;
    ObservableList<Maintance_TableBean> list;
    
    
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
            String text="Machine ID, Machine Model, Machine Type, Unit, Activity, Date, Remarks\n";
            writer.write(text);
            for (Maintance_TableBean p : list)
            {
				text = p.getMachine_ID()+ " , " + p.getMachine_Model()+ " , " + p.getMachine_Type() +" , " + p.getUnit()+" , " + p.getActivity()+" , " + p.getDate()+" , "+  p.getRemarks()+"\n";
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
    void doShow(ActionEvent event) {
    	    	if(fromDate.getValue().equals(null)|| toDate.getValue().equals(null))
    	    	{
    	    		lblResult.setText("Fill Data Correctly...");
    	    	}
    	    	else {
    	TableColumn<Maintance_TableBean, String> machidCol=new TableColumn<Maintance_TableBean, String>("MACHINE ID");
    	machidCol.setCellValueFactory(new PropertyValueFactory<>("Machine_ID"));//field name in bean
    	machidCol.setMinWidth(100);
    	
    	TableColumn<Maintance_TableBean, String> machmodCol=new TableColumn<Maintance_TableBean, String>("MACHINE MODEL");
    	machmodCol.setCellValueFactory(new PropertyValueFactory<>("Machine_Model"));//field name in bean
    	machmodCol.setMinWidth(130);
    	
    	TableColumn<Maintance_TableBean, String> mtypeCol=new TableColumn<Maintance_TableBean, String>("MACHINE TYPE");
    	mtypeCol.setCellValueFactory(new PropertyValueFactory<>("Machine_Type"));//field name in bean
    	mtypeCol.setMinWidth(120);
    	
    	TableColumn<Maintance_TableBean, String> uniCol=new TableColumn<Maintance_TableBean, String>("UNIT");
    	uniCol.setCellValueFactory(new PropertyValueFactory<>("Unit"));//field name in bean
    	uniCol.setMinWidth(40);
    	    	
    	TableColumn<Maintance_TableBean, String> actCol=new TableColumn<Maintance_TableBean, String>("ACTIVITY");
    	actCol.setCellValueFactory(new PropertyValueFactory<>("Activity"));//field name in bean
    	actCol.setMinWidth(195);
    	
    	TableColumn<Maintance_TableBean, String> dateCol=new TableColumn<Maintance_TableBean, String>("DATE");
    	dateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));//field name in bean
    	dateCol.setMinWidth(200);
    	    	
    	TableColumn<Maintance_TableBean, String> tolCol=new TableColumn<Maintance_TableBean, String>("REMARKS");
    	tolCol.setCellValueFactory(new PropertyValueFactory<>("Remarks"));//field name in bean
    	tolCol.setMinWidth(200);
    	
    	table.getColumns().clear();
    	table.getColumns().addAll(machidCol,machmodCol,mtypeCol,uniCol,actCol,dateCol,tolCol);
    	list=getRecordsFromTableSome(fromDate.getValue(),toDate.getValue());
    	table.setItems(list);
    }
    }
    

    ObservableList<Maintance_TableBean> getRecordsFromTableSome(LocalDate m1,LocalDate m2)
   	{
   		ObservableList<Maintance_TableBean> list=FXCollections.observableArrayList();
   		
   		try {
   			  Date m11=java.sql.Date.valueOf(m1);
   			  Date m22=java.sql.Date.valueOf(m2);
   			  System.out.println(m1+""+m2);
   			  pst=con.prepareStatement("select Machine_ID, Machine_Model, Machine_Type, Unit, Activity, Date, Remarks from machine_master where Date Between ? AND ? AND Status= ? ");
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
   				String activity=rs.getString("Activity");
   				String date=rs.getString("Date");
   				String remarks=rs.getString("Remarks");   				
   				
   				Maintance_TableBean bean=new Maintance_TableBean(machineidCol,machinemodelCol,machinetype,unit,activity,date,remarks);
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
    }
}

package ActivateOrDeactivate;

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
import java.util.ResourceBundle;

import Add_Activity.Data;
import Add_Machine.Add_MachineBean;
import ActivateOrDeactivate.ActivateOrDeactivateBean;
import Maintance_Table.Maintance_TableBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class ActivateOrDeactivateViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblStatus;

    @FXML
    private TextField txtMachineID;
    
    @FXML
    private TableView<ActivateOrDeactivateBean> table;
    
    PreparedStatement pst;
    Connection con;
    ObservableList<ActivateOrDeactivateBean> list;
    
    void doconnect() throws ClassNotFoundException, SQLException
    {
    	Class.forName("com.mysql.jdbc.Driver"); 
    	con=DriverManager.getConnection("jdbc:mysql://localhost/maintance", Data.uid, Data.pwd);
    	System.out.println("Connected");
    }

    @FXML
    void doActivate(ActionEvent event) throws SQLException {
    	
    	if(txtMachineID.getText().equals(""))
		{
			lblStatus.setText("FILL DATA CORRECTLY");
		}
    	else 
    	{
    		pst=con.prepareStatement("Update machine_master set Status=? where Machine_ID=? ");
    		pst.setInt(1, 1);
    		pst.setString(2,txtMachineID.getText());
    		pst.executeUpdate(); 
			 lblStatus.setText("ACTIVATE SUCCESSFULLY");
    	}
    }

    @FXML
    void doDeactivate(ActionEvent event) throws SQLException {
    	
    	if(txtMachineID.getText().equals(""))
		{
			lblStatus.setText("FILL DATA CORRECTLY");
		}
    	else 
    	{
    		pst=con.prepareStatement("Update machine_master set Status=? where Machine_ID=? ");
    		pst.setInt(1, 0);
    		pst.setString(2,txtMachineID.getText());
    		pst.executeUpdate(); 
			 lblStatus.setText("DEACTIVATE SUCCESSFULLY");
    	}
    }
    
    @FXML
    void doExport(ActionEvent event) {

    	try {
			writeExcel();
			lblStatus.setText("EXPORTED SUCCESSFULLY");
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
        		lblStatus.setText(".csv EXTENSION REQUIRED");
        		return;
        	}
        	 file = new File(filePath);
        	 
        	 
        	 
            writer = new BufferedWriter(new FileWriter(file));
            String text="Machine ID, Machine Model, Machine Type, Unit\n";
            writer.write(text);
            for (ActivateOrDeactivateBean p : list)
            {
				text = p.getMachine_ID()+ " , " + p.getMachine_Model()+ " , " + p.getMachine_Type() +" , " + p.getUnit()+"\n";
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

    	TableColumn<ActivateOrDeactivateBean, String> machidCol=new TableColumn<ActivateOrDeactivateBean, String>("MACHINE ID");
    	machidCol.setCellValueFactory(new PropertyValueFactory<>("Machine_ID"));//field name in bean
    	machidCol.setMinWidth(120);
    	
    	TableColumn<ActivateOrDeactivateBean, String> machmodCol=new TableColumn<ActivateOrDeactivateBean, String>("MACHINE MODEL");
    	machmodCol.setCellValueFactory(new PropertyValueFactory<>("Machine_Model"));//field name in bean
    	machmodCol.setMinWidth(130);
    	
    	TableColumn<ActivateOrDeactivateBean, String> mtypeCol=new TableColumn<ActivateOrDeactivateBean, String>("MACHINE TYPE");
    	mtypeCol.setCellValueFactory(new PropertyValueFactory<>("Machine_Type"));//field name in bean
    	mtypeCol.setMinWidth(120);
    	
    	TableColumn<ActivateOrDeactivateBean, String> uniCol=new TableColumn<ActivateOrDeactivateBean, String>("UNIT");
    	uniCol.setCellValueFactory(new PropertyValueFactory<>("Unit"));//field name in bean
    	uniCol.setMinWidth(70);
    	
    	table.getColumns().clear();
    	table.getColumns().addAll(machidCol,machmodCol,mtypeCol,uniCol);
    	list=getRecordsFromTableSome();
    	table.setItems(list);
    	
    }


    private ObservableList<ActivateOrDeactivate.ActivateOrDeactivateBean> getRecordsFromTableSome() {
    	ObservableList<ActivateOrDeactivateBean> list=FXCollections.observableArrayList();
   		
   		try {
   			  
   			  pst=con.prepareStatement("select Machine_ID, Machine_Model, Machine_Type, Unit from machine_master where  Status= 0 ");
   			 
   			  ResultSet rs=  pst.executeQuery();
   			  while(rs.next())
   			  {
   				String machineidCol=rs.getString("Machine_ID");
   				String machinemodelCol=rs.getString("Machine_Model");
   				String machinetype=rs.getString("Machine_Type");
   				String unit=rs.getString("Unit");
   				  				
   				
   				ActivateOrDeactivateBean bean=new ActivateOrDeactivateBean(machineidCol,machinemodelCol,machinetype,unit);
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

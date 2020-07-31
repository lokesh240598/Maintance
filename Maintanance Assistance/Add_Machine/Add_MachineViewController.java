package Add_Machine;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Add_Activity.Data;
import Add_Machine.Add_MachineBean;
import Add_Machine.Add_MachineBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class Add_MachineViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtMachineID;

    @FXML
    private TextField txtMachineModel;

    @FXML
    private Label lblStatus1;

    @FXML
    private DatePicker MaintanceDate;

    @FXML
    private TextField txtUnit;

    @FXML
    private TableView<Add_MachineBean> table;

    @FXML
    private TextField txtActivity;

    @FXML
    private TextField txtActivityID;

    @FXML
    private ComboBox<String> comboMachineType;

    @FXML
    private TextField txtFrequency;

    @FXML
    private TextField txtTolerance;
    
    PreparedStatement pst;
    Connection con;
    ObservableList<Add_MachineBean> list;
    
    void doconnect() throws ClassNotFoundException, SQLException
    {
    	Class.forName("com.mysql.jdbc.Driver"); 
    	con=DriverManager.getConnection("jdbc:mysql://localhost/maintance", Data.uid, Data.pwd);
    	System.out.println("Connected");
    }

    
    @FXML
    void doAddNewActivity(ActionEvent event) throws SQLException {
    	if(txtMachineID.getText().equals("")|| txtMachineModel.getText().equals("") ||
    			comboMachineType.getValue().equals(null) || txtUnit.getText().equals("") ||
    			txtActivityID.getText().equals("") || txtActivity.getText().equals("") ||
    			txtFrequency.getText().equals("") || txtTolerance.getText().equals("") )
		{
			lblStatus1.setText("FILL DATA CORRECTLY");
		}
    	else
    	{
    		pst=con.prepareStatement("Insert into machine_master values(?,?,?,?,?,?,?,?,?,?,?)");
        	
    		
    		pst.setString(1,txtMachineID.getText());
    		pst.setString(2,txtMachineModel.getText());
        	pst.setString(3,comboMachineType.getValue());
        	pst.setString(4,txtActivityID.getText());
        	pst.setString(5,txtActivity.getText());
        	pst.setInt(6,Integer.parseInt(txtFrequency.getText()));
        	pst.setInt(7,Integer.parseInt(txtTolerance.getText()));
        	LocalDate local=MaintanceDate.getValue();
    		java.sql.Date d=java.sql.Date.valueOf(local);
    		pst.setDate(8,d);
    		pst.setString(9,txtUnit.getText());
    		pst.setInt(10, 1);
    		pst.setString(11, null);
    		
    		pst.executeUpdate();
    		lblStatus1.setText("SAVED SUCCESSFULLY");
    		
    		txtActivityID.setText(null);
    		txtActivity.setText(null);
    		txtFrequency.setText(null);
        	txtTolerance.setText(null);
        	MaintanceDate.setValue(null);
        	
    	}
    }

    @FXML
    void doNew(ActionEvent event) {
    	txtActivityID.setText(null);
    	txtMachineID.setText(null);
    	txtMachineModel.setText(null);
    	txtUnit.setText(null);
    	txtActivity.setText(null);
    	comboMachineType.setValue(null);
    	txtFrequency.setText(null);
    	txtTolerance.setText(null);
    	lblStatus1.setText(null);
    	MaintanceDate.setValue(null);
    	table.getItems().clear();
    }

    @FXML
    void doSelect(ActionEvent event) {
    	
    	TableColumn<Add_MachineBean, String> mtypeCol=new TableColumn<Add_MachineBean, String>("MACHINE TYPE");
    	mtypeCol.setCellValueFactory(new PropertyValueFactory<>("Machine_Type"));//field name in bean
    	mtypeCol.setMinWidth(120);
    	
    	TableColumn<Add_MachineBean, String> actidCol=new TableColumn<Add_MachineBean, String>("ACTIVITY ID");
    	actidCol.setCellValueFactory(new PropertyValueFactory<>("Activity_ID"));//field name in bean
    	actidCol.setMinWidth(100);
    	
    	TableColumn<Add_MachineBean, String> actCol=new TableColumn<Add_MachineBean, String>("DESCRIPTION");
    	actCol.setCellValueFactory(new PropertyValueFactory<>("Description"));//field name in bean
    	actCol.setMinWidth(195);
    	
    	TableColumn<Add_MachineBean, String> freCol=new TableColumn<Add_MachineBean, String>("FREQUENCY");
    	freCol.setCellValueFactory(new PropertyValueFactory<>("Frequency"));//field name in bean
    	freCol.setMinWidth(100);
    	
    	TableColumn<Add_MachineBean, String> tolCol=new TableColumn<Add_MachineBean, String>("TOLERANCE");
    	tolCol.setCellValueFactory(new PropertyValueFactory<>("Tolerance"));//field name in bean
    	tolCol.setMinWidth(100);
    	
    	table.getColumns().clear();
    	table.getColumns().addAll(mtypeCol,actidCol,actCol,freCol,tolCol);
    	
    	list=getRecordsFromTableSome(comboMachineType.getValue());
    	table.setItems(list);
    }
    ObservableList<Add_MachineBean> getRecordsFromTableSome(String mactype)
   	{
   		ObservableList<Add_MachineBean> list=FXCollections.observableArrayList();
   		
   		try {
   			  pst=con.prepareStatement("select * from activity_master where Machine_Type=?");
   			  pst.setString(1,mactype);  			 
   			  ResultSet rs=  pst.executeQuery();
   			  while(rs.next())
   			  {
   				String mtype=rs.getString("Machine_Type");
   				String actid=rs.getString("Activity_ID");
   				String act=rs.getString("Description");
   				String fre=rs.getString("Frequency");
   				String tol=rs.getString("Tolerance");
   				
   				
   				Add_MachineBean bean=new Add_MachineBean(mtype,actid,act,fre,tol);
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
    	Add_MachineBean A1=table.getSelectionModel().getSelectedItem();
    	if(A1==null)
    	{
    		lblStatus1.setText("PLEASE SELECT ACTIVITY");
    	}
		
		 else { 
			 String a1=A1.getMachine_Type(); 
			 String a2=A1.getActivity_ID(); 
			 String a3=A1.getDescription(); 
			 String a4=A1.getFrequency(); 
			 String a5=A1.getTolerance(); 
			 txtActivityID.setText(a2);
			 txtActivity.setText(a3);
			 txtFrequency.setText(a4);
			 txtTolerance.setText(a5);
			 }
		 lblStatus1.setText(null);
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
    	comboMachineType.getItems().clear();
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
			comboMachineType.getItems().addAll(uname);
			
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    
    }
}

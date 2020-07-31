package Add_Activity;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Add_Activity.Data;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class Add_ActivityViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtActivity;

    @FXML
    private TextField txtFrequency;

    @FXML
    private TextField txtTolerance;

    @FXML
    private TextField txtActivityID;

    @FXML
    private RadioButton FrequencyDay;

	
	@FXML private ToggleGroup Frequency;
	 
    @FXML
    private RadioButton FrequencyMonth;

    @FXML
    private RadioButton FrequencyYear;

    @FXML
    private RadioButton ToleranceDay;

	
	@FXML private ToggleGroup Tolerance;
	 

    @FXML
    private RadioButton ToleranceMonth;

    @FXML
    private RadioButton ToleranceYear;

    @FXML
    private Label lblStatus2;

    @FXML
    private ComboBox<String> comboMachineType;
    
    PreparedStatement pst;
    Connection con;
    
    void doconnect() throws ClassNotFoundException, SQLException
    {
    	Class.forName("com.mysql.jdbc.Driver"); 
    	con=DriverManager.getConnection("jdbc:mysql://localhost/maintance", Data.uid, Data.pwd);
    	System.out.println("Connected");
    }

    @FXML
    void doAddNewActivity(ActionEvent event) throws SQLException {
    	if(txtActivityID.getText().equals("")|| comboMachineType.getValue().equals("") || txtActivity.getText().equals("") || txtFrequency.getText().equals("") || txtTolerance.getText().equals("") )
		{
			lblStatus2.setText("FILL DATA CORRECTLY");
		}
    	else
    	{
    		pst=con.prepareStatement("Insert into activity_master values(?,?,?,?,?)");
    		pst.setString(1,comboMachineType.getValue());
    		pst.setString(2,txtActivityID.getText());
    		pst.setString(3,txtActivity.getText());
    		if(FrequencyDay.isSelected()==true)
    		{
    			String x1=txtFrequency.getText();
    			int x11=(Integer.parseInt(x1));
    			pst.setInt(4, x11);
    		}
    		if(FrequencyMonth.isSelected()==true)
    		{
    			String x2=txtFrequency.getText();
    			int x22=(Integer.parseInt(x2))*31;
    			pst.setInt(4, x22);
    		}
    		if(FrequencyYear.isSelected()==true)
    		{
    			String x3=txtFrequency.getText();
    			int x33=(Integer.parseInt(x3))*365;
    			pst.setInt(4, x33);
    		}
    		if(ToleranceDay.isSelected()==true)
    		{
    			String y1=txtTolerance.getText();
    			int y11=(Integer.parseInt(y1));
    			pst.setInt(5, y11);
    		}
    		if(ToleranceMonth.isSelected()==true)
    		{
    			String y2=txtTolerance.getText();
    			int y22=(Integer.parseInt(y2))*31;
    			pst.setInt(5, y22);
    		}
    		if(ToleranceYear.isSelected()==true)
    		{
    			String y3=txtTolerance.getText();
    			int y33=(Integer.parseInt(y3))*365;
    			pst.setInt(5, y33);
    		}
    		
			pst.executeUpdate();
			lblStatus2.setText("SAVED SUCCESSFULLY");
    	}
    }

    @FXML
    void doNew(ActionEvent event) {
    	txtActivityID.setText(null);
    	txtActivity.setText(null);
    	comboMachineType.setValue(null);
    	txtFrequency.setText(null);
    	txtTolerance.setText(null);
    	lblStatus2.setText(null);
		Frequency.selectToggle(null); 
		Tolerance.selectToggle(null);
		
    }
    
    @FXML
    void doNewActivity(ActionEvent event) {
    	txtActivityID.setText(null);
    	txtActivity.setText(null);
    	
    	txtFrequency.setText(null);
    	txtTolerance.setText(null);
    	lblStatus2.setText(null);
		Frequency.selectToggle(null); 
		Tolerance.selectToggle(null);
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


    @FXML
    void initialize() throws ClassNotFoundException, SQLException {
    	 doconnect();
    	 filluname();
    }
}

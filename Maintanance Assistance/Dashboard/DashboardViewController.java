package Dashboard;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DashboardViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private Button btn5;

    @FXML
    private Button btn4;
    
    @FXML
    private Button btn6;

    @FXML
    private Button btn7;

    @FXML
    void doOpenAddActivity(MouseEvent event) {
    	try {
    		if(event.getClickCount()==1)
    		{
    	Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("Add_Activity/Add_ActivityView.fxml")); 
		Scene scene = new Scene(root);
		
		Stage stage=new Stage();

		stage.setScene(scene);
		
		stage.show();
    		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}

    }

    

    @FXML
    void doOpenAddMachine(MouseEvent event) {
    	try {
    		if(event.getClickCount()==1)
    		{
    	Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("Add_Machine/Add_MachineView.fxml")); 
		Scene scene = new Scene(root);
		
		Stage stage=new Stage();

		stage.setScene(scene);
		
		stage.show();
    		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}

    }

    
    @FXML
    void doOpenMaintanceLog(MouseEvent event) {
    	try {
    		if(event.getClickCount()==1)
    		{
    	Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("Maintance_Table/Maintance_TableView.fxml")); 
		Scene scene = new Scene(root);
		
		Stage stage=new Stage();

		stage.setScene(scene);
		
		stage.show();
    		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}

    }

        @FXML
    void doOpenReport(MouseEvent event) {
    	try {
    		if(event.getClickCount()==1)
    		{
    	Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("Report/ReportView.fxml")); 
		Scene scene = new Scene(root);
		
		Stage stage=new Stage();

		stage.setScene(scene);
		
		stage.show();
    		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}

    }



    @FXML
    void doOpenSchedule(MouseEvent event) {
    	try {
    		if(event.getClickCount()==1)
    		{
    	Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("Schedule/ScheduleView.fxml")); 
		Scene scene = new Scene(root);
		
		Stage stage=new Stage();

		stage.setScene(scene);
		
		stage.show();
    		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}

    }
    
    @FXML
    void doOpenDeactivate(MouseEvent event) {
    	try {
    		if(event.getClickCount()==1)
    		{
    	Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("ActivateOrDeactivate/ActivateOrDeactivateView.fxml")); 
		Scene scene = new Scene(root);
		
		Stage stage=new Stage();

		stage.setScene(scene);
		
		stage.show();
    		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}


    }

    @FXML
    void doOpenMaintance(MouseEvent event) {
    	try {
    		if(event.getClickCount()==1)
    		{
    	Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("EarlyOrLateMaintance/EarlyOrLateMaintanceView.fxml")); 
		Scene scene = new Scene(root);
		
		Stage stage=new Stage();

		stage.setScene(scene);
		
		stage.show();
    		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}


    }

    

    @FXML
    void initialize() {
        
    }
}


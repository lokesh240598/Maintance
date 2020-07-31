package Add_Activity;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Add_ActivityMain extends Application
	{

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception
	{
		Parent root= FXMLLoader.load(getClass().getResource("Add_ActivityView.fxml"));
		  Scene scene= new Scene(root);
		  stage.setScene(scene);
		  stage.show();
		
	}

}

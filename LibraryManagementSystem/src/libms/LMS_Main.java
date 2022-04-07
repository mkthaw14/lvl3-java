package libms;
	
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import libms.controller.MemberAddController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class LMS_Main extends Application 
{
	
	private static Stage primaryStage;

	@Override
	public void start(Stage stage) 
	{
		try {
			
			URL url = new File("src/libms/view/Login.fxml").toURI().toURL();
			AnchorPane root = (AnchorPane)FXMLLoader.load(url);

			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage = stage;
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Library Management System");
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void changeScene(String path) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(LMS_Main.class.getResource(path));
		Parent root = loader.load();
		
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.hide();
		primaryStage.show();
	}
	
	public static <T>T openNewWindow(String path, String title) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(LMS_Main.class.getResource(path));

		Parent root = loader.load();
		Scene scene = new Scene(root);
		
		Stage primaryStage = new Stage();
		
		primaryStage.setScene(scene);
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.setTitle(title);
		primaryStage.show();
		
		T controller = loader.getController();
		return controller;

	}
	
	public static void closeWindow(Button btn_close)
	{
    	System.out.println("Close");
        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();
	}
	
	
	public static <T>T getController(String path) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(LMS_Main.class.getResource(path));		
		loader.load();
		
		T controller = loader.getController();
		
		return controller;
	}
	
}

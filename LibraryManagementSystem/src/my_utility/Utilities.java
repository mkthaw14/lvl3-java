package my_utility;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Utilities {
	
	public static void showWarning(String text, AlertType type)
	{
		Alert alert = new Alert(type);
		alert.setTitle("Message");
		alert.setContentText(text);
		alert.setHeaderText(null);
		alert.showAndWait();
	}
	
	public static Alert showConfirmation(String text, AlertType type)
	{
		Alert alert = new Alert(type);
		alert.setTitle("Confirmation");
		alert.setContentText(text);
		alert.setHeaderText(null);
		
		return alert;
	}
	
	public static int stringToInt(String num)
	{
		int val = 0;
		try
		{
			if(!num.isEmpty())
			{
				val = Integer.parseInt(num);
			}
		} catch (NumberFormatException e)
		{
			System.err.println(e.getMessage());
			val = 0;
		}
		
		return val;
	}
}

package web.data.starter;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import web.data.pull.jango.*;

public class Start  extends Application {
    @Override
    public void start(Stage primaryStage){
    	
    	
    	
    	
    	
    	
        System.out.println("Started ...!");
        try {
			HTMLReader htmlReader = new HTMLReader();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
        Button btnStart = new Button();
        btnStart.setText("Start");
        btnStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Started ...!");
                try {
					HTMLReader htmlReader = new HTMLReader();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btnStart);

 Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Web data import export");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

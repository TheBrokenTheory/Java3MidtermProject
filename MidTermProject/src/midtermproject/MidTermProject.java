package midtermproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Jonathan Anders
 */
public class MidTermProject extends Application {
    
    private BorderPane main = new BorderPane();
    private Statement queryStatement;
    
    @Override
    public void start(Stage primaryStage)
    {
        main.setTop(getMenuBar());
        Scene scene = new Scene(main, 675, 550);
        
        primaryStage.setTitle("Northwind Database Query Tool");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //Connect to DB
        initializeDataBase();
    }

    //Returns the top menubar and all the controls
    private MenuBar getMenuBar()
    {
        MenuBar topMenu = new MenuBar();
        
        //Create file menu and populate with Exit button
        Menu menuFile = new Menu("File");
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t)
            {
               System.exit(0);
            }
        });   
        
        menuFile.getItems().addAll(exit);
        
        
        
        
        topMenu.getMenus().addAll(menuFile);
        return topMenu;
    }
    
    //Connect to the database
    private void initializeDataBase()
    {
        try
        {
            //Load driver from JDBC
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            
            //Establish connection
            Connection dbConnection = DriverManager.getConnection("jdbc:ucanaccess://C:/Data/Northwind.mdb");
            
            //Create a statement
            queryStatement = dbConnection.createStatement();   
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("error");
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    
}

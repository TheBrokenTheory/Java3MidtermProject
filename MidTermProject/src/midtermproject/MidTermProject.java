package midtermproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


/**
 *
 * @author Jonathan Anders
 */
public class MidTermProject extends Application {
    
    private ObservableList<ObservableList> data;
    
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
         displayResults();
    }

    //Returns the top menubar and all the controls
    private MenuBar getMenuBar()
    {
        MenuBar topMenu = new MenuBar();        
        topMenu.getMenus().addAll(getFileMenu(), getOrderMenu(), getCustomerMenu(), getEmployeeMenu());
        return topMenu;
    }
    
    //Returns file menu
    private Menu getFileMenu()
    {
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
        
        return menuFile;
    }
    
    //Returns menu for all order queries
    private Menu getOrderMenu()
    {
       Menu menuQuery = new Menu("Order");
        MenuItem orderTotal = new MenuItem("Order Total");
        orderTotal.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t)
            {
               main.setCenter(getOrderDetailsDesign());
            }
        }); 
        
        MenuItem orderDetail = new MenuItem("Order Details");
        orderDetail.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t)
            {
               main.setCenter(getOrderDetailsDesign());
            }
        }); 
        
        menuQuery.getItems().addAll(orderTotal, orderDetail);
        
        return menuQuery;
    }
    
    //Returns menu for all customer queries
    private Menu getCustomerMenu()
    {
        Menu menuCustomer = new Menu("Customer");
        MenuItem customerDetails = new MenuItem("Customer Details");
        customerDetails.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t)
            {
               main.setCenter(getCustomerDetailBox());
            }
        });
        
        menuCustomer.getItems().addAll(customerDetails);
        
        return menuCustomer;
    }
    
    //Returns menu for all employee queries
    private Menu getEmployeeMenu()
    {
        Menu menuEmployee = new Menu("Employee");
        
        MenuItem employeeBirthdays = new MenuItem("Employee Birthdays");
        employeeBirthdays.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t)
            {
               main.setCenter(getEmployeeDetailsBox());
            }
        }); 
        
        menuEmployee.getItems().addAll(employeeBirthdays);
        
        return menuEmployee;
    }
    
    //Returns hbox for all input for getting order details
    private HBox getOrderDetailsDesign()
    {
        HBox orderTotalBox = new HBox();
        
        Label orderNumLabel = new Label("Enter Order Number");
        TextField orderNumberField = new TextField();
        Button submitQueryButton = new Button("Submit");
        submitQueryButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t)
            {

            }
        });         
        
        orderTotalBox.getChildren().addAll(orderNumLabel, orderNumberField, submitQueryButton);
        orderTotalBox.setSpacing(5);
        orderTotalBox.setPadding(new Insets(5));
        
        return orderTotalBox;
    }
    
    private HBox getCustomerDetailBox()
    {
        HBox customerDetailBox = new HBox();
        
        Label stateCodeLabel = new Label("Enter State Code");
        TextField stateCodeInputBox = new TextField();
        Button submitQueryButton = new Button("Submit");
        submitQueryButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t)
            {

            }
        }); 
        
        customerDetailBox.getChildren().addAll(stateCodeLabel, stateCodeInputBox, submitQueryButton);
        customerDetailBox.setSpacing(5);
        customerDetailBox.setPadding(new Insets(5));
        
        return customerDetailBox;
    }

    private HBox getEmployeeDetailsBox()
    {
        HBox EmployeeDetailBox = new HBox();
        
        Label yearLabel = new Label("Enter Year");
        TextField yearInputBox = new TextField();
        Button submitQueryButton = new Button("Submit");
        submitQueryButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t)
            {

            }
        }); 
        
        EmployeeDetailBox.getChildren().addAll(yearLabel, yearInputBox, submitQueryButton);
        EmployeeDetailBox.setSpacing(5);
        EmployeeDetailBox.setPadding(new Insets(5));
        
        return EmployeeDetailBox;
    }
    
    private void displayResults()
    {
        TableView resultsTable = new TableView();
        data = FXCollections.observableArrayList();
        
        try
        {
            String queryString = "select * from Employees";
          
            ResultSet resultSet = queryStatement.executeQuery(queryString);
            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            
            for (int i = 1; i <= rsMetaData.getColumnCount(); i++)
            {
                TableColumn test = new TableColumn(rsMetaData.getColumnName(i));
                System.out.println(rsMetaData.getColumnName(i) + "    ");
                test.setResizable(true);
                resultsTable.getColumns().addAll(test);
            }
            // Iterate through the result and print the student names
            while (resultSet.next()) 
            {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rsMetaData.getColumnCount(); i++)
                {
                    row.add(resultSet.getString(i));
                }
                data.add(row);
            }
            
            resultsTable.setItems(data);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
       
         
        main.setCenter(resultsTable);
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

package midtermproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;


/**
 *
 * @author Jonathan Anders
 */
public class MidTermProject extends Application {
    
    private BorderPane main = new BorderPane();
    private Statement queryStatement;
    
    @Override
    public void start(Stage primaryStage) throws SQLException
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
               main.setCenter(getOrderTotalsDesign());
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
        
        Label reviewInputLabel = new Label("");
        Label orderNumLabel = new Label("Enter Order Number");
        TextField orderNumberField = new TextField();
        Button submitQueryButton = new Button("Submit");
        submitQueryButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t)
            {
                if(validateOrderNumber(orderNumberField))
                {
                    String query = "select Orders.OrderID, Orders.OrderDate, Orders.Freight, "
                            + "[Order Details].ProductID, [Order Details].Quantity, [Order Details].UnitPrice, "
                            + "[Order Details].Discount "
                            + "from [Order Details] inner join Orders "
                            + "on Orders.OrderID=[Order Details].OrderID" + " where OrderID =" + orderNumberField.getText(); 
                       

                    getResults(query);
                }
                else
                {
                    reviewInputLabel.setText("Please Input Valid Order Number");
                    orderTotalBox.getChildren().add(reviewInputLabel);
                }
            }
        });         
        
        orderTotalBox.getChildren().addAll(orderNumLabel, orderNumberField, submitQueryButton);
        orderTotalBox.setSpacing(5);
        orderTotalBox.setPadding(new Insets(5));
        
        return orderTotalBox;
    }
    
    //Returns hbox for all input for getting order details
    private HBox getOrderTotalsDesign()
    {
        HBox orderTotalBox = new HBox();
        
        Label reviewInputLabel = new Label("");
        Label orderNumLabel = new Label("Enter Order Number");
        TextField orderNumberField = new TextField();
        Button submitQueryButton = new Button("Submit");
        submitQueryButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t)
            {
                if(validateOrderNumber(orderNumberField))
                {
                    String query = "select OrderID, UnitPrice, Quantity, Discount, (UnitPrice*Quantity)-(UnitPrice*Quantity*Discount) as Total "
                            + "from [Order Details]"
                            + "where OrderID =" + orderNumberField.getText();

                    getResults(query);
                }
                else
                {
                    reviewInputLabel.setText("Please Input Valid Order Number");
                    orderTotalBox.getChildren().add(reviewInputLabel);
                }
            }
        });         
        
        orderTotalBox.getChildren().addAll(orderNumLabel, orderNumberField, submitQueryButton);
        orderTotalBox.setSpacing(5);
        orderTotalBox.setPadding(new Insets(5));
        
        return orderTotalBox;
    }
    
    private boolean validateOrderNumber(TextField order)
    {
        boolean validates = true;
        Integer orderNum = null;
        try
        {
            orderNum = Integer.parseInt(order.getText());   
            if(orderNum >= 11078 || orderNum <= 10247)
            {
                validates = false;
            }
        }
        catch(NumberFormatException e)
        {
            validates = false;
        }
        
        return validates;
    }
    
    private HBox getCustomerDetailBox()
    {
        HBox customerDetailBox = new HBox();
        
        Label reviewInputLabel = new Label("");
        Label stateCodeLabel = new Label("Enter State Code");
        TextField stateCodeInputBox = new TextField();
        Button submitQueryButton = new Button("Submit");
        submitQueryButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t)
            {
                if(validateStateCode(stateCodeInputBox))
                {
                    String query = "select ContactName, City from Customers where Region LIKE '%" + 
                    stateCodeInputBox.getText() + "%' order by City";

                    getResults(query);
                }
                else
                {
                    reviewInputLabel.setText("Please Input Valid State Code");
                    customerDetailBox.getChildren().add(reviewInputLabel);
                }
            }
        }); 
        
        customerDetailBox.getChildren().addAll(stateCodeLabel, stateCodeInputBox, submitQueryButton);
        customerDetailBox.setSpacing(5);
        customerDetailBox.setPadding(new Insets(5));
        
        return customerDetailBox;
    }
    
    //Validates that the user entered in a right state code
    private boolean validateStateCode(TextField stateCode)
    {
        boolean validates = true;
        
        String state = stateCode.getText();
        char[] chars = state.toCharArray();
        
        for (char c : chars)
        {
            if(!Character.isLetter(c))
            {
                return false;
            }
        }
        
        if(state.length() > 2)
        {
            validates = false;
        }
     
        return validates;
    }
    
    //Controls and design to enter year of birth for employee
    private HBox getEmployeeDetailsBox()
    {
        HBox EmployeeDetailBox = new HBox();
        
        Label reviewInputLabel = new Label("");
        Label yearLabel = new Label("Enter Year");
        TextField yearInputBox = new TextField();
        Button submitQueryButton = new Button("Submit");
        submitQueryButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t)
            {
                if(validateBirthYear(yearInputBox))
                {
                    String query = "select FirstName, LastName from Employees where BirthDate LIKE '%" + 
                    yearInputBox.getText() + "%' order by LastName";

                    getResults(query);
                }
                else
                {
                    reviewInputLabel.setText("Please Input Valid Year");
                    EmployeeDetailBox.getChildren().add(reviewInputLabel);
                }
            }
        }); 
        
        EmployeeDetailBox.getChildren().addAll(yearLabel, yearInputBox, submitQueryButton);
        EmployeeDetailBox.setSpacing(5);
        EmployeeDetailBox.setPadding(new Insets(5));
        
        return EmployeeDetailBox;
    }
    
    //Validates user entered a valid year
    private boolean validateBirthYear(TextField birthYear)
    {
        boolean validates = true;
        Integer year = null;
        try
        {
            year = Integer.parseInt(birthYear.getText());   
            if(year > 2018 || year < 1900)
            {
                validates = false;
            }
        }
        catch(NumberFormatException e)
        {
            validates = false;
        }

        return validates;
    }
    
    //Grabs a resultset from db using a query string
    private void getResults(String query)
    {
        try
        {
            ResultSet resultSet = queryStatement.executeQuery(query);
            displayResults(resultSet);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    //Displays the results from DB on a table view
    //I spent a while trying to get this to work on my own.
    //I could not figure it out so I used code from the sample project
    //you posted on Ask the Instructor.
    private void displayResults(ResultSet rs) 
    {
        ScrollPane resultsPane = new ScrollPane();
        TableView resultsTable = new TableView();
        ObservableList<ObservableList> data = FXCollections.observableArrayList();

        try
        {
            resultsTable.getColumns().clear();
            //Dynamically add table columns
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++)
            {
                final int j = i;
                System.out.println(rs.getMetaData().getColumnName(i + 1));
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(
                    new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() 
                    {
                        @Override
                        public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) 
                        {
                            if (param == null || param.getValue() == null || param.getValue().get(j) == null) 
                            {
                                return null;
                            }
                            return new SimpleStringProperty(param.getValue().get(j).toString());
                        }
                    }
                );

               resultsTable.getColumns().addAll(col);
            }
            
            //Add data to rows
            while (rs.next())
            {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
                {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
            
            //Add data to tableview
            resultsTable.setItems(data);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
       
        //Add the scrollpane to the center of page
        resultsPane.setContent(resultsTable);
        resultsPane.setFitToHeight(true);
        resultsPane.setFitToWidth(true);
        main.setCenter(resultsPane);
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

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Interfaz extends Application{
    TableView<Employee> table;
    ObservableList<Employee> employees;
    int idEmployee;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Creamos un panel en donde se agregaran los elementos
        Pane root = new Pane();
        //Cambiamos el color del panel 
        root.setStyle("-fx-background-color: rgba(130, 140, 146, 0.5);");

        //Agregamos una imagen al panel
        Image img = new Image(getClass().getResourceAsStream("IMAGEN.png"));
        ImageView imag1 = new ImageView(img);
        imag1.setFitWidth(250);
        imag1.setFitHeight(250);
        imag1.setX(50);
        imag1.setY(30);
        root.getChildren().add(imag1);

        //Creamos la etiqueta del nombre
        Label nameLabel = new Label("Name");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        nameLabel.setLayoutX(60);
        nameLabel.setLayoutY(300);
        root.getChildren().addAll(nameLabel);

        //Creamos el cuadro de texto donde se ingresara el nombre
        TextField namTextField = new TextField();
        namTextField.setLayoutX(20);
        namTextField.setLayoutY(330);
        root.getChildren().add(namTextField);

        //Creamos la etiqueta de la direccion
        Label addressLabel = new Label("Address");
        addressLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        addressLabel.setLayoutX(60);
        addressLabel.setLayoutY(370);
        root.getChildren().add(addressLabel);

        //Creamos el cuadro de texto donde se ingresara la direccion
        TextField addressTextField = new TextField();
        addressTextField.setLayoutX(20);
        addressTextField.setLayoutY(400);
        root.getChildren().add(addressTextField);

        //Creamos la etiqueta del numero de telefono
        Label phoneLabel = new Label("Phone");
        phoneLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        phoneLabel.setLayoutX(60);
        phoneLabel.setLayoutY(440);
        root.getChildren().add(phoneLabel);

        //Creamos el cuadro de texto donde se ingresara el telefono
        TextField phoneTextField = new TextField();
        phoneTextField.setLayoutX(20);
        phoneTextField.setLayoutY(470);
        root.getChildren().add(phoneTextField);

        //Creamos el boton para guardar los datos ingresados
        Button saveButton = new Button("Save");
        saveButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        saveButton.setLayoutX(30);
        saveButton.setLayoutY(530);

        //Vehiculos
        Label labelVehicle = new Label("Vehicles");
        labelVehicle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        labelVehicle.setLayoutX(220);
        labelVehicle.setLayoutY(300);
        root.getChildren().add(labelVehicle);

        CheckBox carBox = new CheckBox("Car");
        carBox.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        carBox.setLayoutX(220);
        carBox.setLayoutY(330);
        root.getChildren().add(carBox);

        CheckBox truckBox = new CheckBox("Truck");
        truckBox.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        truckBox.setLayoutX(220);
        truckBox.setLayoutY(360);
        root.getChildren().add(truckBox);

        CheckBox shipBox = new CheckBox("Ship");
        shipBox.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        shipBox.setLayoutX(220);
        shipBox.setLayoutY(390);
        root.getChildren().add(shipBox);

        CheckBox motorCycleBox = new CheckBox("MotorCycle");
        motorCycleBox.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        motorCycleBox.setLayoutX(220);
        motorCycleBox.setLayoutY(420);
        root.getChildren().add(motorCycleBox);

        CheckBox bicycleBox = new CheckBox("Bicycle");
        bicycleBox.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        bicycleBox.setLayoutX(220);
        bicycleBox.setLayoutY(450);
        root.getChildren().add(bicycleBox);

        //Accion del boton
        // saveButton.setOnAction(e -> {
        //     String name = namTextField.getText();
        //     String address = addressTextField.getText();
        //     String phone = phoneTextField.getText();

        //     if (name.isEmpty() || address.isEmpty() || phone.isEmpty()){
        //         showAlert("Error", "All data is necessary");
        //         return;
        //     }

        //     Database.insertEmployee(name, address, phone);
        //     table.setItems(Database.getList());
        //     showAlert(":D", "Data was saved seccessfully");
        // });

        saveButton.setOnAction(e -> {
            String name = namTextField.getText();
            String address = addressTextField.getText();
            String phone = phoneTextField.getText();
        
            if (name.isEmpty() || address.isEmpty() || phone.isEmpty()){
                showAlert("Error", "All data is necessary");
                return;
            }
        
            // Insertamos el empleado en la base de datos
            Database.insertEmployee(name, address, phone);
            int employeeId = Database.getIdEmployeed(name, address, phone); // Obtener el ID del empleado recién insertado
        
            // Ahora, insertamos los vehículos asociados
            if (carBox.isSelected()) {
                Database.insertVehicle("Car", employeeId);
            }
            if (truckBox.isSelected()) {
                Database.insertVehicle("Truck", employeeId);
            }
            if (shipBox.isSelected()) {
                Database.insertVehicle("Ship", employeeId);
            }
            if (motorCycleBox.isSelected()) {
                Database.insertVehicle("MotorCycle", employeeId);
            }
            if (bicycleBox.isSelected()) {
                Database.insertVehicle("Bicycle", employeeId);
            }
        
            // Actualizar la tabla de empleados
            table.setItems(Database.getList());
            // showAlert(":D", "Data was saved successfully");
        });

        root.getChildren().add(saveButton);        

        //Boton para eliminar a un empleado
        Button deleteButton = new Button("Delete");
        deleteButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        deleteButton.setLayoutX(125);
        deleteButton.setLayoutY(530);

        deleteButton.setOnAction(e -> {
            Employee employee = table.getSelectionModel().getSelectedItem();
            if (employee != null){
                employees = Database.deleteEmployeeSelect(employee, employees);

                employees = Database.getList();
                table.setItems(employees);
            } else {
                showAlert("Error", "Select an employee");
            }
        });
        root.getChildren().add(deleteButton);

        //Boton para actualizar la informacion de un empleado
        Button updateButton = new Button("Update");
        updateButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        updateButton.setLayoutX(230);
        updateButton.setLayoutY(530);

        updateButton.setOnAction(e -> {
            Employee employee = table.getSelectionModel().getSelectedItem();
            String newName = namTextField.getText();
            String newAddress = addressTextField.getText();
            String newPhone = phoneTextField.getText();
            if (employee != null){
                Database.updateEmployee(newName, newAddress, newPhone, idEmployee);

                employees = Database.getList();
                table.setItems(employees);
            } else {
                showAlert("Error", "Select an employee");
            }
        });

        root.getChildren().add(updateButton);

        //Creamos la tabla para mostrar la informacion
        table = new TableView<>();
        //Asignamos posiciones a la tabla
        table.setLayoutX(400);
        table.setLayoutY(40);
        table.setPrefSize(450, 400);

        table.setOnMouseClicked(event -> {
            Employee employee = table.getSelectionModel().getSelectedItem();
            if (employee != null){
                namTextField.setText(employee.getName());
                addressTextField.setText(employee.getAddress());
                phoneTextField.setText(employee.getPhone());
                idEmployee = Database.getIdEmployeed(employee.getName(), employee.getAddress(), employee.getPhone());
                System.out.println(idEmployee);
            }
        });

        //Creamos la columna para el nombre
        TableColumn<Employee, String> names = new TableColumn<Employee, String>("Name");
        names.setCellValueFactory(data -> data.getValue().nameProperty());

        //Creamos la columna para la direccion
        TableColumn<Employee, String> address = new TableColumn<Employee, String>("Address");
        address.setCellValueFactory(data -> data.getValue().addressProperty());

        //Creamos la columna para el numero de telefono
        TableColumn<Employee, String> phone = new TableColumn<Employee, String>("Phone");
        phone.setCellValueFactory(data -> data.getValue().phoneProperty());
        
        TableColumn<Employee, String> vehiclesColumn = new TableColumn<>("Vehicles");
        vehiclesColumn.setCellValueFactory(data -> {
            Employee employee = data.getValue();
            // Obtener el ID del empleado usando los datos disponibles (nombre, dirección, teléfono)
            int employeeId = Database.getIdEmployeed(employee.getName(), employee.getAddress(), employee.getPhone());
    
            // Obtener los vehículos usando el ID
            String vehicles = Database.getEmployeeVehicles(employeeId);
    
            // Devolver los vehículos como un SimpleStringProperty para la columna
            return new SimpleStringProperty(vehicles);
        });

        //Agregamos las columnas a la tabla
        table.getColumns().addAll(names, address, phone, vehiclesColumn);
        //Hacemos que todo el tamano de la tabla se use entre las columnas totales
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        root.getChildren().add(table);

        //Obtenemos los datos de MySQL
        employees = Database.getList();
        System.out.println(Database.getEmployeeVehicles(1));
        table.setItems(employees);
        
        Scene scene = new Scene(root, 900, 600);
    
        primaryStage.setTitle("CRUD");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main (String[] args){
        launch(args);
    }
} 


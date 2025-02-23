import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Database {
    //Atributos necesarios para la conexion entre java y MySQL
    private static final String url = "jdbc:mysql://localhost:3306/company"; //Base de datos
    private static final String user = "root"; //Usuario
    private static final String password = "12345678"; //Contrasenia

    //Metodo para conectarse a la base de datos
    public static Connection connect(){
        try {
            //Hacemos la conexion con el driver manager
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    } 

    // //Metodo para agregar a un empleado a la clase de datos
    public static void insertEmployee(String name, String address, String phone){
        //String de la consulta de MySQL, los parametros se ponene con ?
        String sql = "insert into employees (name, address, phone) values (?, ?, ?)";

        //Creamos la conexion y hacemos una sentencia de SQL
        try (Connection conn = Database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, name); //El primer parametro es el nombre
            pstmt.setString(2, address); //El segundo parametro es la direccion
            pstmt.setString(3, phone); //El tercer parametro es el numero
            pstmt.executeUpdate(); //Ejecutamos la sentencia en SQL
            System.out.println("Employee added seccessfully");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void insertVehicle(String vehicleName, int employeeId) {
        String query = "INSERT INTO vehicles (vehicle_name, employee_id) VALUES (?, ?)";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, vehicleName);
            stmt.setInt(2, employeeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Metodo para mostrar la tabla de empleados
    public static void showEmployees(){
        //String de la consulta de MySQL
        String sql = "select * from employees";

        //Hacemos la conexion, creamos la sentencia y el resultado obtenido de la consulta se guarda en un objeto resultset
        try (Connection conn = Database.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + '\n' +
                                   "Name: " + rs.getString("name") + '\n' +
                                   "Adrress: " + rs.getString("address") + '\n' +
                                   "Phone: " + rs.getString("phone") + '\n' );
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Metodo para actualizar los datos de un empleado
    public static void updateEmployee(int id, String name, String address, String phone){
        String sql = "update employees set name = ?, address = ?, phone = ? where id = ?";

        try (Connection conn = Database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.setString(3, phone);
            pstmt.setInt(4, id);
            int rowsUpdate = pstmt.executeUpdate();
            if (rowsUpdate > 0){
                System.out.println("Employee epdated");
            } else {
                System.out.println("Employee not found");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = Database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Employee deleted successfully!");
            } else {
                System.out.println("Employee not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Employee> getList(){
        ObservableList<Employee> list = FXCollections.observableArrayList();

        String sql = "select name, address, phone from employees";

        try(Connection conn = Database.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()) {
                Employee employee = new Employee(rs.getString("name"), rs.getString("address"), rs.getString("phone"));
                list.add(employee);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return list;
    }

    public static ObservableList<Employee> deleteEmployeeSelect(Employee employee, ObservableList<Employee> list){
        String sql = "delete from employees where name = ? and address = ? and phone = ?";
        try (Connection conn = Database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getAddress());
            pstmt.setString(3, employee.getPhone());
            pstmt.executeUpdate();

            list.remove(employee);
        } catch (SQLException e){
            e.printStackTrace();
        }

        return list;
    }

    public static int getIdEmployeed(String name, String address, String phone){
        String sql = "select id from employees where name = ? and address = ? and phone = ?";
        try (Connection conn = Database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.setString(3, phone);

            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()){
                return rs.getInt("id");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    public static void updateEmployee(String name, String address, String phone, int id){
        String sql = "update employees set name = ?, address = ?, phone = ? where id = ?";

        try (Connection conn = Database.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.setString(3, phone);
            pstmt.setInt(4, id);

            pstmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static String getEmployeeVehicles(int employeeId) {
        // Consulta para obtener los vehículos asociados al empleado
        String query = "SELECT vehicle_name FROM vehicles WHERE employee_id = ?";
        StringBuilder vehicles = new StringBuilder();
        
        try (Connection conn = Database.connect();
             PreparedStatement statement = conn.prepareStatement(query)) {
            
            statement.setInt(1, employeeId);
            
            ResultSet resultSet = statement.executeQuery();
            
            // Recorre los resultados y agrega los vehículos a la lista
            while (resultSet.next()) {
                if (vehicles.length() > 0) {
                    vehicles.append(", ");  // Separador de vehículos
                }
                vehicles.append(resultSet.getString("vehicle_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return vehicles.toString();
    }
    
}
// public class Employee {
//     //Atributos de la clase
//     public String name;
//     public String address;
//     public String phone;

//     //Constructor
//     public Employee(String name, String address, String phone){
//         this.name = name;
//         this.address = address;
//         this.phone = phone;
//     }

//     //Getters y Setters
//     public String getName(){
//         return name;
//     }

//     public void setName(String name){
//         this.name = name;
//     }

//     public String getAddress(){
//         return address;
//     }

//     public void setaddress(String address){
//         this.address = address;
//     }

//     public String getPhone(){
//         return phone;
//     }

//     public void setphone(String phone){
//         this.phone = phone;
//     }
// }

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Employee {
    private final StringProperty name;
    private final StringProperty address;
    private final StringProperty phone;

    public Employee(String name, String address, String phone) {
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.phone = new SimpleStringProperty(phone);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public StringProperty phoneProperty() {
        return phone;
    }
}

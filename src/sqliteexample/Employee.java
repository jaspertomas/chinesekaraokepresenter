/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sqliteexample;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author jaspertomas
 */
public class Employee {

    int id;
    String name;
    int age;
    String address;
    float salary;

    Employee(ResultSet rs) throws SQLException {

        id = rs.getInt("id");
        name = rs.getString("name");
        age = rs.getInt("age");
        address = rs.getString("address");
        salary = rs.getFloat("salary");

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }
}

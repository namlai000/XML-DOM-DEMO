/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author thegu
 */
public class Student {
    private String Id;
    private String sClass;
    private String Lastname;
    private String Middlename;
    private String Firstname;
    private String Sex;
    private String Password;
    private String Address;
    private String Status;

    public Student() {
    }

    public Student(String Id, String sClass, String Lastname, String Middlename, String Firstname, String Sex, String Password, String Address, String Status) {
        this.Id = Id;
        this.sClass = sClass;
        this.Lastname = Lastname;
        this.Middlename = Middlename;
        this.Firstname = Firstname;
        this.Sex = Sex;
        this.Password = Password;
        this.Address = Address;
        this.Status = Status;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getsClass() {
        return sClass;
    }

    public void setsClass(String sClass) {
        this.sClass = sClass;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String Lastname) {
        this.Lastname = Lastname;
    }

    public String getMiddlename() {
        return Middlename;
    }

    public void setMiddlename(String Middlename) {
        this.Middlename = Middlename;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String Firstname) {
        this.Firstname = Firstname;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String Sex) {
        this.Sex = Sex;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
    
    
}

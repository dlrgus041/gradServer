package org.grad.project.form;

public class EmployeeForm {

    private String name, phone, address;
    private Long id;
    private int vaccine = -1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVaccine() {
        return vaccine;
    }

    public void setVaccine(int vaccine) {
        this.vaccine = vaccine;
    }
}

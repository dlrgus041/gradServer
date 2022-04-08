package org.grad.project.visitor;

public class VisitorForm {

    private Long id;
    private String name, phone;
    private int address1, address2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAddress1() {
        return address1;
    }

    public void setAddress1(int address1) {
        this.address1 = address1;
    }

    public int getAddress2() {
        return address2;
    }

    public void setAddress2(int address2) {
        this.address2 = address2;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Dương
 */
public class SupplierDTO {

    private int supplier_id;
    private String name;
    private String address;
    private String phone;

    public SupplierDTO() {
    }

    public SupplierDTO(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public SupplierDTO(int supplier_id, String name, String address, String phone) {
        this.supplier_id = supplier_id;
        this.name = name;
        this.address = address;
        this.phone = phone;

    }

    public int getId() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}

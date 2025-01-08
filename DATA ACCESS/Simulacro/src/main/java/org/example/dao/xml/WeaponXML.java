package org.example.dao.xml;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
@XmlAccessorType(XmlAccessType.FIELD)
public class WeaponXML {
    @XmlElement
    private String name;
    @XmlElement
    private int price;

    public WeaponXML() {
    }

    public WeaponXML(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "WeaponXML{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
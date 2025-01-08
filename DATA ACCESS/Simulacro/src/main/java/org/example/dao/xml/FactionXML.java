package org.example.dao.xml;

import lombok.*;
import org.example.dao.mappers.xml.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class FactionXML {
    @XmlElement
    private String name;
    @XmlElement
    private String contact;
    @XmlElement
    private String planet;
    @XmlElement
    private int numberCS;
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateLastPurchase;
    @XmlElementWrapper(name = "weapons")
    @XmlElement(name = "weapon")
    private List<WeaponXML> weapons;

    public FactionXML() {
    }

    public FactionXML(String name, String contact, String planet, int numberCS, LocalDate dateLastPurchase, List<WeaponXML> weapons) {
        this.name = name;
        this.contact = contact;
        this.planet = planet;
        this.numberCS = numberCS;
        this.dateLastPurchase = dateLastPurchase;
        this.weapons = weapons;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public int getNumberCS() {
        return numberCS;
    }

    public void setNumberCS(int numberCS) {
        this.numberCS = numberCS;
    }

    public LocalDate getDateLastPurchase() {
        return dateLastPurchase;
    }

    public void setDateLastPurchase(LocalDate dateLastPurchase) {
        this.dateLastPurchase = dateLastPurchase;
    }

    public List<WeaponXML> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<WeaponXML> weapons) {
        this.weapons = weapons;
    }


    @Override
    public String toString() {
        return "FactionXML{" +
                "name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", planet='" + planet + '\'' +
                ", numberCS=" + numberCS +
                ", dateLastPurchase=" + dateLastPurchase +
                ", weapons=" + weapons +
                '}';
    }
}

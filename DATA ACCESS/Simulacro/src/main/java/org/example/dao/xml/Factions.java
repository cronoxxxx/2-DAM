package org.example.dao.xml;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement(name = "factions")
@XmlAccessorType(XmlAccessType.FIELD)
public class Factions {
    @XmlElement(name = "faction")
    private List<FactionXML> factions;

    public Factions() {
    }

    public Factions(List<FactionXML> factions) {
        this.factions = factions;
    }

    public List<FactionXML> getFactions() {
        return factions;
    }

    public void setFactions(List<FactionXML> factions) {
        this.factions = factions;
    }

    @Override
    public String toString() {
        return "Factions{" +
                "factions=" + factions +
                '}';
    }
}
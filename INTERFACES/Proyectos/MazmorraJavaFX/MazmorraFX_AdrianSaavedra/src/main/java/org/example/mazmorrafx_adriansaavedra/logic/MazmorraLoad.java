package org.example.mazmorrafx_adriansaavedra.logic;

import javafx.stage.FileChooser;
import lombok.Getter;
import org.example.mazmorrafx_adriansaavedra.model.Dungeon;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
@Getter
public class MazmorraLoad {
    private Dungeon dungeon;

    public void loadXMLFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
            File xmlFile = fileChooser.showOpenDialog(null);
            if (xmlFile != null) {
                JAXBContext jaxbContext = JAXBContext.newInstance(Dungeon.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                this.dungeon = (Dungeon) jaxbUnmarshaller.unmarshal(xmlFile);
            }
        } catch (Exception e) {
            this.dungeon = null;
        }
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.InputStream;
import javafx.scene.image.Image;

/**
 *
 * @author jasperdesmet
 */
public class ImageCharacter extends Image{
    private final String character;

    public ImageCharacter(InputStream url,String character) {
        super(url);
        this.character = character;
    }

    public String getCharacter() {
        return character;
    }
}

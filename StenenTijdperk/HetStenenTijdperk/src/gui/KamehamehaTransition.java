/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 *
 * @author jasperdesmet
 */
public class KamehamehaTransition extends Transition {
    
    private final ImageView imageView;
    private final int count;
    private final int columns;
    private final int width;
    private final int height;

    private int lastIndex;
    private int lastX;
    private int lastY;
    
    
    public KamehamehaTransition(
            ImageView imageView, 
            Duration duration, 
            int count,   int columns,
            int width,   int height) {
        this.imageView = imageView;
        this.count     = count;
        this.columns   = columns;
        this.width     = width;
        this.height    = height;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    @Override
    protected void interpolate(double k) {
        final int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex) {
            final int x = (index % columns) * width;
            final int y = (index / columns) * height;
            imageView.setViewport(new Rectangle2D(x, y, width, height));
            lastIndex = index;
            lastX = x;
            lastY = y;
        }
    }
}

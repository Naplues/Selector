package nju.gzq.gui.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Image Component
 */
public class ImageComponent extends JComponent {
    BufferedImage image;

    public ImageComponent(String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (Exception e) {

        }
        setZoom(1);
    }

    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Dimension dimension = getPreferredSize();
        graphics.drawImage(image, 0, 0, dimension.width, dimension.height, this);
    }

    public void setZoom(double zoom) {
        int width = (int) (zoom * image.getWidth());
        int height = (int) (zoom * image.getHeight());
        setPreferredSize(new Dimension(width, height));
        revalidate();
        repaint();
    }
}

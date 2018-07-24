package nju.gzq.gui.image;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class ImageViewer extends JComponent {
    JSlider slider;
    ImageComponent image;
    JScrollPane scrollPane;

    public ImageViewer(String path) {
        slider = new JSlider(0, 1000, 500);
        image = new ImageComponent(path);
        scrollPane = new JScrollPane(image);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                image.setZoom(2. * slider.getValue() / slider.getMaximum());
            }
        });

        this.setLayout(new BorderLayout());
        this.add(slider, BorderLayout.NORTH);
        this.add(scrollPane);
    }
}
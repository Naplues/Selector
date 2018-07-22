package nju.gzq;

import nju.gzq.gui.GUI;

import javax.swing.*;

public class Main {
    /**
     * Program entry
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });
    }
}

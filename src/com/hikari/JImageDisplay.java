package com.hikari;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JImageDisplay extends JComponent {
    private BufferedImage image;

    /** Это конструктор инициализации, он задает выводимому изображению ширину и высоту **/
    public JImageDisplay(int width, int height) {
        /** TYPE_INT_RGB задает тип отображения пикселей **/
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        /** Создаем объект типа Dimension(измерение) **/
        Dimension dimension = new Dimension(width, height);
        /** Вызываем метод родительского класса JComponent и передаем входным аргументом
         * объект dimension, который содержит высоту и ширину изображения **/
        super.setPreferredSize(dimension);
    }

    /** Метод отрисовки изображения. Входной аргумент - объект класса Graphics. **/
    public void paintComponent(Graphics graphics) {
        /** Вызов метода суперкласса paintComponent для правильного отображения объектов. **/
        super.paintComponent(graphics);
        /** Объект graphics вызывает метод drawImage, который отрисовывает заданное изображение. **/
        graphics.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    }

    /** Метод закрашивает все пиксели изображения в черный цвет. Очистка изображения. **/
    public void clearImage() {
        /** В двух циклах перебираются все пискели по координатам x, y. **/
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                /** Затем каждый пиксель изображения окрашивается в черный цвет. **/
                drawPixel(i, j, 0);
            }
        }
    }

    /** Метод устанавлиевает цвет rgbColor у конкретного пикселя по ширине и высоте. **/
    public void drawPixel(int x, int y, int rgbColor) {
        /** Объеккт image вызвает метод setRGB, который присваивает конкретный цвет пикселю. **/
        image.setRGB(x, y, rgbColor);
    }

    public BufferedImage getImage() {
        return image;
    }
}

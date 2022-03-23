package com.hikari;

import java.awt.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class FractalExplorer {
    /** JFrame отображает фрактал **/
    private JImageDisplay imageDisplay;
    /** displaySize задает размеры окна. Оно будет (displaySize x displaySize). **/
    private int displaySize;
    /** Инициализация объекта fractalGenerator соответствующего класса. **/
    private FractalGenerator fractalGenerator;
    /** Инициализация объекта range, указывающий диапазон комплексной
     плоскости, которая выводится на экран  **/
    private Rectangle2D.Double range;


    /** Конструктор инициализации. **/
    private FractalExplorer(int displaySize){
        /** Задает размеры окна, которые были переданы как входной аргумент. **/
        this.displaySize = displaySize;
        /** Создание объекта, принадлежащему классу Mandelbrot. **/
        this.fractalGenerator = new Mandelbrot();
        /** Создание объекта, принадлежащему классу Mandelbrot. **/
        this.range = new Rectangle2D.Double(0,0,0,0);
        /** Объект fractalGenerator вызывает метод getInitialRange.
         * Задается рассматриваемый участок комплексной плоскости **/
        fractalGenerator.getInitialRange(this.range);
    }

    /** Точка входа в программу, метод main(). **/
    public static void main(String[] args) {

        FractalExplorer fractalExplorer = new FractalExplorer(600);
        fractalExplorer.createAndShowGUI();
        fractalExplorer.drawFractal();
    }



    /** Настраивает интерфейс (GUI). **/
    public void createAndShowGUI() {
        /** Создает окно(frame) и его название. **/
        JFrame frame = new JFrame("Fractal Generator");
        /** Создает кнопку и её название. **/
        JButton button = new JButton("Reset");

        /** Задает отображение экрана с заданными размерами. **/
        imageDisplay = new JImageDisplay(displaySize, displaySize);
        imageDisplay.addMouseListener(new MouseListener());

        button.addActionListener(new ActionHandler());

        /** setLayout добавляет макет, который поделен на 5 зон.
         * Его используем для правильного расположения элементов(экрана и кнопки) на экране **/
        frame.setLayout(new java.awt.BorderLayout());
        /** Добавляет экран отображения с заданными размерами. **/
        frame.add(imageDisplay, BorderLayout.CENTER);
        /** Добавляет кнопку и привязывает ее снизу интерфейса. **/
        frame.add(button, BorderLayout.SOUTH);
        /** Задает операцию закрытия окна по умолчанию. Выход по закрытию окна. **/
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /** Размечает окно для правильного отображения элементов. **/
        frame.pack();
        /** Делает окно ВИДИМЫМ. Изначально окна невидимы. **/
        frame.setVisible(true);
        /** Запрет на изменение размеров окна. **/
        frame.setResizable(false);
    }

    /** Этот метод отрисовывает фрактал. **/
    private void drawFractal() {
        /** Создаем два цикла, чтобы пройти все пиксели на экране. **/
        for (int x = 0; x < displaySize; x++) {
            for (int y = 0; y < displaySize; y++) {
                /** Вычисляет кол-во итераций для отрисовки фрактала по заданным координатам. **/
                int counter = fractalGenerator.numIterations(FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x),
                        fractalGenerator.getCoord(range.y, range.y + range.width, displaySize, y));
                /** Если точка не выходит за границы, то ставим пикселю черный цвет. **/
                if (counter == -1) {
                    imageDisplay.drawPixel(x, y, 0);
                }
                /** В противном случае цвет рассчитывается на основе счетчика итераций. **/
                else {
                    float hue = 0.7f + (float) counter / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    /** Вызывается метод drawPixel, который окрашивает заданный пиксель в заданный цвет. **/
                    imageDisplay.drawPixel(x, y, rgbColor);
                }
            }
        }
        /** Теперь, когда все пиксели раскрашены(циклы завершили свою работу),
         * необходимо ОБНОВИТЬ ИЗОБРАЖЕНИЕ, иначе оно так и останется прежним. **/
        imageDisplay.repaint();
    }

    /** Этот класс обрабатывает события от кнопки сброса(reset).
     * <implements ActionListener> реализует соответствующий интерфейс .**/
    public class ActionHandler implements ActionListener {
        @Override
        /** Метод сбрасывает диапазон(range) к начальному
         * и перерисовывает фрактал. **/
        public void actionPerformed(ActionEvent e) {
            fractalGenerator.getInitialRange(range);
            drawFractal();
        }
    }

    /** Класс обрабатывает события от мыши. Наследуется от MouseAdapter. **/
    public class MouseListener extends MouseAdapter {
        @Override
        /** При получении события о щелчке мышью, класс должен
         отобразить пиксельные кооринаты щелчка в область фрактала **/
        public void mouseClicked(MouseEvent e) {
            double x = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, e.getX());
            double y = FractalGenerator.getCoord(range.y, range.y + range.width, displaySize, e.getY());
            /** Вызов метода recenterAndZoomRange() с указанными координатами
             *  и приближением масштаба на 0.5. **/
            fractalGenerator.recenterAndZoomRange(range, x, y, 0.5);
            /** Перерисовка фрактала. **/
            drawFractal();
        }
    }
}
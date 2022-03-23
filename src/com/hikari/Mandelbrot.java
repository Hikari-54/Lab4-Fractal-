package com.hikari;

import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator {
    /** Определяю константу максимального кол-ва итераций для отрисовки фракталов. **/
    public static final int MAX_ITERATIONS = 2000;

    /** Переопределяем абстрактный метод getInitialRange, который определяет
     *  рассматриваемую область комплексной плоскости **/
    @Override
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -1.5;
        range.height = 3;
        range.width = 3;
    }

    /** Метод numIterations реализует итеративную функцию для фрактала Мандельброта. **/
    @Override
    public int numIterations(double x, double y) {
        double r = x;
        double i = y;
        int counter = 0;
        while (counter < MAX_ITERATIONS) {
            counter++;
            double k = r * r - i*i+x;
            double m = 2 * r * i + y;
            r = k;
            i = m;
            /** Проверка условия (|z| > 2), при котором цикл заканчивается. **/
            if (r*r+i*i > 4)
                break;
        }
        /** Проверка условия (counter должен быть меньше <MAX_ITERATIONS(2000)>), при котором цикл заканчивается. **/
        if (counter == MAX_ITERATIONS)
            return -1;
        return counter;
    }
}

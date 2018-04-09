/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebasAleatoridad;

/**
 *
 * @author Bruno
 */
public class ChiCuadrado {
    private double[] serie;     //Serie a probar
    private int k;              //Cantidad de Intervalos
    private int v;              //Grados de Libertad
    private double tabla[][];   //Tabla...
    private double precision;   //Precision con la que quiero trabajar (0.1, 0.01, 0.001, 0.0001)
    private double[] tablaChiCuadrado95;        //valores de tabla chi2 nivel de confianza 0.95

    public ChiCuadrado(double[] serie, int k, double precision) {
        this.serie = serie;
        this.k = k;
        if (serie.length/k < 5) { //Para Distribucion Uniforme...
            k=k/2;
        }
        this.v = k - 1 - 0;
        this.tabla = new double[k][5];
        for (int i = 0; i < k; i++) {
            tabla[i][0] = i + 1;
        }
        for (int i = 0; i < k; i++) {
            tabla[i][1] = 0;
        }
        for (int i = 0; i < k; i++) {
            tabla[i][2] = serie.length / k; //Para Distribucion Uniforme...
        }
        this.precision = precision;
        this.tablaChiCuadrado95 = new double[]{3.84, 5.99, 7.81, 9.49, 11.1, 12.6, 14.1, 15.5, 16.9, 18.3, 19.7, 21.0, 22.4, 23.7, 25.0, 26.3, 27.6, 28.9, 30.1, 31.4, 32.7, 33.9, 35.2, 36.4, 37.7, 38.9, 40.1, 41.3, 42.6, 43.8, 55.8, 67.5, 79.1, 90.5, 101.9, 113.1, 124.3};
    }
    
    public void distribucion(){
        for (int i = 0; i < serie.length; i++) {
            for (double[] tabla1 : tabla) {
                double li = 0 + (1/k + precision) * (tabla1[0] - 1);
                double ls = (1/k) * (tabla1[0]);
                if (li< serie[i] && serie[i] <ls) {
                    tabla1[1]++;
                    break;
                }
            }
        }
    }
    
    public double chiCalculada() {
        for (double[] tabla1 : tabla) {
            tabla1[3] = Math.pow((tabla1[1] - tabla1[2]), 2);
        }
        for (double[] tabla1 : tabla) {
            tabla1[4] = tabla1[3] / tabla1[2];
        }
        double r=0;
        for (int i = 0; i < 10; i++) {
            r += tabla[i][4];
        }
        return r;
    }

    public boolean hipotesis() {
        distribucion();
        return chiCalculada() <= tablaChiCuadrado95[v + 1];

    }

}

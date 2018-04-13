/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebasAleatoridad;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

    public ChiCuadrado(double[] serie, int k, int op) {
        this.serie = serie;
        this.k = k;
        if (serie.length/k < 5) { //Para Distribucion Uniforme...
            this.k=k/2;
        }
        this.v = this.k - 1 - 0;
        this.tabla = new double[5][this.k];
        for (int i = 0; i < this.k; i++) {
            tabla[0][i] = i + 1;
        }
        for (int i = 0; i < this.k; i++) {
            tabla[1][i] = 0;
        }
        for (int i = 0; i < this.k; i++) {
            tabla[2][i] = serie.length / this.k; //Para Distribucion Uniforme...
        }
        switch(op){
            case 1:
                precision = 0.1;
                break;
            case 2:
                precision = 0.01;
                break;
            case 3:
                precision = 0.001;
                break;
            case 4:
                precision = 0.0001;
                break;
        }
        this.tablaChiCuadrado95 = new double[]{3.84, 5.99, 7.81, 9.49, 11.1, 12.6, 14.1, 15.5, 16.9, 18.3, 19.7, 21.0, 22.4, 23.7, 25.0, 26.3, 27.6, 28.9, 30.1, 31.4, 32.7, 33.9, 35.2, 36.4, 37.7, 38.9, 40.1, 41.3, 42.6, 43.8, 55.8, 67.5, 79.1, 90.5, 101.9, 113.1, 124.3};
    }
    
    public void distribucion(){
        for (int i = 0; i < serie.length; i++) {
            for (int j = 0; j < k; j++) {
                double auxiliar = 1.00;
                double li = 0 + (auxiliar/k + precision) * (tabla[0][j] - 1);
                double ls = (auxiliar/k) * tabla[0][j];
                if (li< serie[i] && serie[i] <ls) {
                    tabla[1][j]++;
                    break;
                }
            }
        }
    }
    
    public double chiCalculada() {
        for (int i = 0; i < k; i++) {
            tabla[3][i] = Math.pow((tabla[1][i] - tabla[2][i]), 2);
        }
        for (int i = 0; i < k; i++) {
            tabla[4][i] = tabla[3][i] / tabla[2][i];
        }
        double r=0;
        for (int i = 0; i < k; i++) {
            r += tabla[4][i];
        }
        return r;
    }

    public boolean hipotesis() {
        distribucion();
        return chiCalculada() <= tablaChiCuadrado95[v + 1];

    }

    public double[] getSerie() {
        return serie;
    }

    public void setSerie(double[] serie) {
        this.serie = serie;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public double[][] getTabla() {
        return tabla;
    }

    public void setTabla(double[][] tabla) {
        this.tabla = tabla;
    }

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }

    public double[] getTablaChiCuadrado95() {
        return tablaChiCuadrado95;
    }

    public void setTablaChiCuadrado95(double[] tablaChiCuadrado95) {
        this.tablaChiCuadrado95 = tablaChiCuadrado95;
    }
    
    public String getIntervalo(double intervalo){
        double auxiliar = 1.00;
        double limi = (double)0 + (auxiliar/k) * (intervalo) + precision;
        if (intervalo == 0) {
            limi = 0;
        }
        double lims = (auxiliar/(double)k) * (intervalo + auxiliar);
        
        String li = new BigDecimal(limi)
                .setScale(4, RoundingMode.DOWN)
                .stripTrailingZeros()
                .toString();
        
        String ls = new BigDecimal(lims)
                .setScale(4, RoundingMode.DOWN)
                .stripTrailingZeros()
                .toString();
        
        return (li + " - " + ls);
    }
    
    public double getFrecuenciaObs(int intervalo){
        return tabla[1][intervalo];
    }
    
    public double getFrecuenciaEsp(int intervalo){
        return tabla[2][intervalo];
    }
    

}

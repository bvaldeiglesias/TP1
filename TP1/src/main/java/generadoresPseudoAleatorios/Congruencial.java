/*
 * Clase generadora de numeros Pseudo Aleatorios 
 * a traves de Metodo Congruencial Lineal Mixto o Congruencial Multiplicativo
 * -La diferencia depende del valor de C.
 */
package generadoresPseudoAleatorios;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Bruno
 */
public class Congruencial {

    private double a;       //Constante Multiplicativa
    private double c;       //Constante Aditiva
    private double m;       //Modulo
    private double seed;    //Semilla o Valor Inicial
    private double xi;      //Valor Calculado
    private double r;       //nro Pseudo Aleatorio
    private boolean first;  //Utilizado para determinar si ya se genero primer numero.
    private int incluirUno; //Se incluye 1 como valor posible?
    private int precision;  //Define la precision con la que trabajar
    

    public Congruencial() {
        this.a = 71561;
        this.c = 56822;
        this.m = 341157;
        this.seed = 31767;
        this.precision=4;
    }

    public Congruencial(double c) {
        this.c = c;     //Debe ser 0 para ser "Congruencial Multiplicativo"
        this.a = 71561;
        this.m = 341157;
        this.seed = 31767;
        this.precision=4;
        this.first = false;
    }
    

    public Congruencial(double a, double m, double c, double seed, boolean ideal, boolean incluirUno, int prec) {
        if (ideal) {
            if (c==0) {
                this.a = 5 + 8 * a;         //a = k
            } else {
                this.a = 1 + 4 * a;         //a = k
            }   
            this.m = Math.pow(2, m);        //m = g
        } else {
            this.a = a;
            this.m = m;
        }
        if (incluirUno) {
            this.incluirUno = 1;
        } else {
            this.incluirUno = 0;
        }
        this.c = c;                         //Debe ser 0 para ser "Congruencial Multiplicativo"
        this.seed = seed;
        this.first = false;
        this.precision=prec;
    } 
    
    //Genera nro RND y lo devuelve sin mas.
    public double RND (){
        if (!first) {
            xi = (a*seed + c)%(m);
            first = true;
            return r = (xi)/(m-incluirUno);
        } else {
            xi = (a*xi + c)%(m);
            return r = (xi)/(m-incluirUno);
        }
    }
    
    //A parte de generar nro RND lo trunca dependiendo la precision y lo devuelve
    public String truncateRND() {
        return new BigDecimal(RND())
                .setScale(precision, RoundingMode.DOWN)
                .stripTrailingZeros()
                .toString();
    }

//////////////////////////////////////////////////////Getter y Setter/////////////////////////////////////////////    
    
    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
    }

    public double getSeed() {
        return seed;
    }

    public void setSeed(double seed) {
        this.seed = seed;
    }

    public double getXi() {
        return xi;
    }

    public void setXi(double xi) {
        this.xi = xi;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public int getIncluirUno() {
        return incluirUno;
    }

    public void setIncluirUno(int incluirUno) {
        this.incluirUno = incluirUno;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }
    
}

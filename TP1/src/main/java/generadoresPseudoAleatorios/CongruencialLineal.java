/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generadoresPseudoAleatorios;

/**
 *
 * @author Bruno
 */
public class CongruencialLineal {

    private double a;      //Constante Multiplicativa
    private double c;      //Constante Aditiva
    private double m;      //Modulo
    private double seed;   //Semilla o Valor Inicial
    private double xi;     //Valor Calculado
    private double r;   //nro Pseudo Aleatorio
    private boolean first;  //Utilizado para determinar si ya se genero primer numero.
    private int incluirUno; //Se incluye 1 como valor posible?
    private double precision; //Define la precision con la que trabajar
    

    public CongruencialLineal() {
        this.a = 5;
        this.c = 7;
        this.m = 2;
        this.seed = 0;
    }
    
    public CongruencialLineal(double a, double m, double c, double seed, boolean ideal, boolean incluirUno, double precision) {
        if (ideal) {
            this.a = 1 + 4 * a;     //a = k
            this.m = Math.pow(2, m);         //m = g
        } else {
            this.a = a;
            this.m = m;
        }
        if (incluirUno) {
            this.incluirUno = 1;
        } else {
            this.incluirUno = 0;
        }
        this.c = c;
        this.seed = seed;
        this.first = false;
    } 
    
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
}

package pruebasAleatoridad;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class ChiCuadradoB
{

    private int cantidadIntervalos;         //Cantidad de intervalos ingresados por teclado
    private int cantidadNumeros;            //Cantidad de numeros a generar, ingresados x teclado
    private double resultado;               //Resultado de la prueba de chi cuadrado
    private double[] tabulado;              //Vector con los valores a comparar de chi-cuadrado
    private List<String> numerosGenerados;  //Lista con los números generados aleatoriamente
    double[] tablaRandom;

    //Constructor de la clase Chi-Cuadrado que tiene como parámetros a la cantidad de intervalos 
    //ingresados por teclado y la cantidad de números a generar por el generador de series.
    public ChiCuadradoB(int cantidadIntervalos, int cantidadNumeros)
    {
        this.cantidadIntervalos = cantidadIntervalos;
        this.cantidadNumeros = cantidadNumeros;
        this.tabulado = new double[100];
        this.tabulado = chiCuadrado();
    }

    //Método que devuelve un vector con las frecuencias en las que aparecen los distintos valores
    public int[] generarTablaFrecuencias()
    {
        int[] tablaFrencuencias = new int[cantidadIntervalos];
        Random rnd = new Random();
        float numeroAleatorio = rnd.nextFloat();
        numerosGenerados = new ArrayList<String>();
        String aleatorioTxt;

        for (int i = 0; i < cantidadNumeros; i++)
        {
            for (int j = 0; j < cantidadIntervalos; j++)
            {
                double limiteSuperior = ((double) (1 + j) / (double) cantidadIntervalos);

                if (numeroAleatorio < limiteSuperior)
                {
                    tablaFrencuencias[j]++;
                    break;
                }
            }
            double redondeado = (double) Math.round(numeroAleatorio * 100d) / 100d;
            aleatorioTxt = "" + redondeado;
            numerosGenerados.add(aleatorioTxt); //add() --> método que agrega los números a la lista
            numeroAleatorio = rnd.nextFloat(); //reetea el numero aleatorio a generar
        }
        return tablaFrencuencias;
    }

    public int[] generarTablaFrecuencias2()
    {
        tablaRandom = new double[cantidadNumeros];
        for (int i = 0; i < tablaRandom.length; i++)
        {
            Random rnd = new Random();
            float numeroAleatorio = rnd.nextFloat();
            double redondeado = (double) Math.round(numeroAleatorio * 100d) / 100d;
            tablaRandom[i] = redondeado;
        }

        Arrays.sort(tablaRandom);

        //Obtener el primero de la lista
        double decimal_menor_lista = tablaRandom[0];

        //Obtener el ultimo de la lista
        double decimal_mayor_lista = tablaRandom[tablaRandom.length - 1];

        //Recorrido...
        double recorrido = decimal_mayor_lista - decimal_menor_lista;

        //Recorrido / cantidad de intervalos
        double amplitud_intervalo = recorrido / cantidadIntervalos;

        //Primer intervalo = decimal_menor_lista
        //Segundo intervalo..
        double segundo_intervalo = decimal_menor_lista + amplitud_intervalo;

        //Vector donde se van a almacenar todas las frecuencias
        int[] tablaFrecuencias = new int[cantidadIntervalos];

        for (int i = 0; i < cantidadNumeros; i++)
        {
            for (int j = 0; j < cantidadIntervalos; j++)
            {
                if (tablaRandom[i] >= decimal_menor_lista && tablaRandom[i] < segundo_intervalo)
                {
                    tablaFrecuencias[j]++;
                    break;
                } else
                {
                    if (tablaRandom[i] == tablaRandom[cantidadNumeros - 1])
                    {
                        tablaFrecuencias[tablaFrecuencias.length - 1]++;
                        break;
                    } else
                    {
                        while (tablaRandom[i] >= decimal_menor_lista && tablaRandom[i] > segundo_intervalo)
                        {
                            decimal_menor_lista = decimal_menor_lista + amplitud_intervalo;
                            segundo_intervalo = segundo_intervalo + amplitud_intervalo;
                            j++;
                        }
                        tablaFrecuencias[j]++;
                        //SE RESETEAN LAS VARIABLES PARA LA PROXIMA VERIFICACIÓN
                        decimal_menor_lista = tablaRandom[0];
                        segundo_intervalo = decimal_menor_lista + amplitud_intervalo;
                        break;
                    }

                }
            }
        }
        llenar_lista_con_vector(tablaRandom);
        return tablaFrecuencias;
    }

    public void llenar_lista_con_vector(double[] Vector)
    {
        numerosGenerados = new ArrayList<String>();
        for (int i = 0; i < Vector.length; i++)
        {
            double redondeado = (double) Math.round(Vector[i] * 100d) / 100d;
            String valor = String.valueOf(redondeado);
            numerosGenerados.add(valor);
        }

    }

    //Método que te devuelve la frecuencia esperada para el cálculo de Chi-Cuadrado
    public double frecuenciaEsperada()
    {
        double fe = cantidadNumeros / cantidadIntervalos;
        return fe;
    }
    
    public int getK()
    {
        return cantidadIntervalos;
    }

    //Método que calcula el valor de chi cuadrado para la serie
    public double valorTestChiCuadrado(int[] tablaFrecuencias)
    {
        double fe = frecuenciaEsperada();
        for (int i = 0; i < tablaFrecuencias.length; i++)
        {
            resultado += (Math.pow(tablaFrecuencias[i] - fe, 2)) / fe;
        }
        return resultado;
    }

    //Método que verifica si la frecuencia acumulada "resultado" es menor a la frecuencia tabulada en chi cuadrado
    public boolean testChiCuadrado(int[] tablaFrecuencias)
    {
        if (cantidadIntervalos > 1)
        {
            if (tabulado[cantidadIntervalos - 2] > resultado)  //cantidadIntervalos - 1 = k - 1 = V (grados de libertad)
            {
                return true;
            }
        }
        return false;
    }

    public double tabuladoChiCuadrado()
    {
        return tabulado[cantidadIntervalos - 2];
    }

    public double[] chiCuadrado()
    {
        tabulado[0] = 3.841;
        tabulado[1] = 5.991;
        tabulado[2] = 7.815;
        tabulado[3] = 9.488;
        tabulado[4] = 11.070;
        tabulado[5] = 12.592;
        tabulado[6] = 14.067;
        tabulado[7] = 15.507;
        tabulado[8] = 16.919;
        tabulado[9] = 18.307;
        tabulado[10] = 19.675;
        tabulado[11] = 21.026;
        tabulado[12] = 22.362;
        tabulado[13] = 23.685;
        tabulado[14] = 24.996;
        tabulado[15] = 26.296;
        tabulado[16] = 27.587;
        tabulado[17] = 28.869;
        tabulado[18] = 30.144;
        tabulado[19] = 31.410;
        tabulado[20] = 32.671;
        tabulado[21] = 33.924;
        tabulado[22] = 35.172;
        tabulado[23] = 36.415;
        tabulado[24] = 37.652;
        tabulado[25] = 38.885;
        tabulado[26] = 40.113;
        tabulado[27] = 41.337;
        tabulado[28] = 42.557;
        tabulado[29] = 43.773;
        tabulado[39] = 53.7;
        tabulado[49] = 66.8;
        tabulado[59] = 79.5;
        tabulado[69] = 104.2;
        tabulado[79] = 116.3;
        tabulado[89] = 128.3;
        tabulado[99] = 140.2;
        return tabulado;
    }

    public List<String> getNumerosGenerados()
    {
        return numerosGenerados;
    }
    
    public String getIntervalo(double intervalo){
        double auxiliar = 1.00;
        double limi = (double)0 + (auxiliar/cantidadIntervalos) * (intervalo) + 0.1;
        if (intervalo == 0) {
            limi = 0;
        }
        double lims = (auxiliar/(double)cantidadIntervalos) * (intervalo + auxiliar);
        
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

}

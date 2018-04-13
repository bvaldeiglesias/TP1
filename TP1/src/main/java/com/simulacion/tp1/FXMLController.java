package com.simulacion.tp1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import generadoresPseudoAleatorios.*;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import pruebasAleatoridad.ChiCuadrado;
import pruebasAleatoridad.ChiCuadradoB;

public class FXMLController implements Initializable
{

    ///Ejercicio A
    @FXML
    private RadioButton radCongruencialMixto;
    @FXML
    private ToggleGroup gMetodo;
    @FXML
    private RadioButton radCongruencialMultiplicativo;
    @FXML
    private TextField txtSemilla;
    @FXML
    private TextField txtCteC;
    @FXML
    private TextField txtAoG;
    @FXML
    private ComboBox cmbPrecision;
    @FXML
    private CheckBox chkIncluir1;
    @FXML
    private Button btnGenerar;
    @FXML
    private Button btnSiguiente;
    @FXML
    private Button btnReiniciar;
    @FXML
    private TextField txtMoK;
    @FXML
    private Pane paneParametros;
    @FXML
    private Label lblA;
    @FXML
    private Label lblM;
    @FXML
    private CheckBox chkIdeal;
    @FXML
    private TextField txtCantNros;
    @FXML
    private CheckBox chkMod;
    @FXML
    private TableView tblTabla = new TableView();

    private Congruencial rndGenerator;

    ///Ejercicio B
    private Button btn_generar;
    private Button btn_graficar;
    @FXML
    private Label lbl_acepta_prueba;
    private TextField txt_cant_intervalos;
    private TextField txt_cant_numeros;
    @FXML
    private Label lbl_sumatoria_chi_cuadrado;
    @FXML
    private TableView tbl_Tabla = new TableView();
    @FXML
    private TextArea txt_serie;
    private Label lbl_alerta;
    private NumberAxis yFrecBc_B;
    private BarChart chrtFrecuenciaC_B;
    private final ObservableList<Row_B> data_B = FXCollections.observableArrayList();
    private ChiCuadradoB chi_B;

    ///Ejercicio C
    @FXML
    private Button btnProbar;
    @FXML
    private TextField txtCantNrosC;
    @FXML
    private Button btnReiniciarC;
    @FXML
    private BarChart chrtFrecuenciaC;
    @FXML
    private CategoryAxis xIntervalo;
    @FXML
    private TextField txtCantIntervalosC;
    @FXML
    private ListView lsvSerie;
    @FXML
    private LineChart lnchrEsperadaC;
    @FXML
    private NumberAxis yFrecBc;
    @FXML
    private NumberAxis yFrecLn;
    @FXML
    private TextArea txtAResultados;

    private double[] serie;
    private ChiCuadrado pruebaChi;
    private NumberAxis yFrecLn_B;
    private LineChart<?, ?> lnchrEsperadaC_B;
    @FXML
    private CheckBox chkCMixto;
    @FXML
    private TextField txtSemillaC;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

//////////////////////////////////////////////////////Ejercicio A/////////////////////////////////////////////
        //Cargar valores en Combobox
        ObservableList<String> options
                = FXCollections.observableArrayList(
                        "1",
                        "2",
                        "3",
                        "4"
                );
        cmbPrecision.getItems().addAll(options);

        //Chekear si hay que habilitar o deshabilitar la edicion de parametros
        if (chkMod.isSelected())
        {
            paneParametros.setDisable(false);
        } else
        {
            paneParametros.setDisable(true);
        }

        //Crear y definir columnas de tabla
        tblTabla.setEditable(true);

        TableColumn colIndice = new TableColumn("Indice");
        TableColumn colRND = new TableColumn("RND");

        tblTabla.getColumns().addAll(colIndice, colRND);

        colIndice.setCellValueFactory(new PropertyValueFactory("indice"));
        colRND.setCellValueFactory(new PropertyValueFactory("rnd"));
        
        tblTabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //Tooltips de ayuda
        final Tooltip tooltip = new Tooltip();
        tooltip.setText(
                "Ideal:\n"
                + "Modulo M =  2^g\n"
                + "Constante A =  1 + 4 * k\n");

        chkIdeal.setTooltip(tooltip);

        final Tooltip tooltipIncluir1 = new Tooltip();
        tooltipIncluir1.setText(
                "Incluir al valor 1 como \n"
                + "parte del conjunto de numeros aceptados\n");

        chkIncluir1.setTooltip(tooltipIncluir1);

//////////////////////////////////////////////////////Ejercicio B/////////////////////////////////////////////
        tbl_Tabla.setEditable(true);

        TableColumn colIntervalo = new TableColumn("Intervalo");
        TableColumn colFrec_obt = new TableColumn("Frecuencia Obtenida");
        TableColumn colFrec_esp = new TableColumn("Frecuencia Esperada");
        TableColumn col3 = new TableColumn("(fo-fe)^2");
        TableColumn col4 = new TableColumn("(col 3)/fe");

        colIntervalo.setCellValueFactory(new PropertyValueFactory<>("intervalo"));
        colFrec_obt.setCellValueFactory(new PropertyValueFactory<>("frecuencia_obtenida"));
        colFrec_esp.setCellValueFactory(new PropertyValueFactory<>("frecuencia_esperada"));
        col3.setCellValueFactory(new PropertyValueFactory<>("col3"));
        col4.setCellValueFactory(new PropertyValueFactory<>("col4"));
        
        

        tbl_Tabla.getColumns().addAll(colIntervalo, colFrec_obt, colFrec_esp, col3, col4);
        
        tbl_Tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//////////////////////////////////////////////////////Ejercicio C/////////////////////////////////////////////
        //No se necesita inicializar nada.
    }

//////////////////////////////////////////////////////Ejercicio A/////////////////////////////////////////////
    @FXML
    //Generar numeros RND iniciales con los parametros ingresados, validando datos de entrada
    private void handleButtonActionGenerar(ActionEvent event)
    {

        //Modificar habilitacion en  base de que se permite cambiar o no una vez precionado el boton generar
        btnSiguiente.setDisable(false);
        btnGenerar.setDisable(true);
        radCongruencialMixto.setDisable(true);
        radCongruencialMultiplicativo.setDisable(true);
        txtCantNros.setDisable(true);
        chkMod.setDisable(true);
        txtSemilla.setDisable(true);
        txtCteC.setDisable(true);
        chkIncluir1.setDisable(true);
        chkIdeal.setDisable(true);
        txtAoG.setDisable(true);
        txtMoK.setDisable(true);
        cmbPrecision.setDisable(true);

        int cantNros = Integer.parseInt(txtCantNros.getText());

        //Se ingresaron parametros manualmente o se utilizan los predefinidos?
        if (!chkMod.isSelected())
        {
            if (radCongruencialMixto.isSelected())
            {

                rndGenerator = new Congruencial();

            } else
            {

                rndGenerator = new Congruencial(0);

            }
        } else
        {
            if (true)
            { //Validar que los txtBox no sean nulos o tengan valores erroneos, probar con try catch
                double seed = Double.parseDouble(txtSemilla.getText());
                double aOg = Double.parseDouble(txtAoG.getText());
                double mOk = Double.parseDouble(txtMoK.getText());
                String test = cmbPrecision.getSelectionModel().getSelectedItem().toString();
                int precision = Integer.parseInt(test);
                boolean incluir1 = chkIncluir1.isSelected();
                boolean ideal = chkIdeal.isSelected();
                double cteC;
                if (radCongruencialMixto.isSelected())
                {
                    cteC = Double.parseDouble(txtCteC.getText());
                } else
                {
                    cteC = 0;
                }

                //Instanciar clase generadora de nros pseudoaleatorios
                rndGenerator = new Congruencial(aOg, mOk, cteC, seed, ideal, incluir1, precision);

            }
        }
        //Generar "cantNros" de RND y cargar en tabla
        final ObservableList<Row> data = FXCollections.observableArrayList();
        for (int i = 1; i < cantNros + 1; i++)
        {

            String auxIndice = String.valueOf(i);
            String auxRND = String.valueOf(rndGenerator.truncateRND());
            data.add(new Row(auxIndice, auxRND));

        }
        tblTabla.setItems(data);
    }

    @FXML
    //Generar siguente valor de serie
    private void Siguiente(ActionEvent event)
    {
        final ObservableList<Row> data = FXCollections.observableArrayList();

        int auxin = tblTabla.getItems().size() + 1;
        String auxIndice;
        auxIndice = String.valueOf(auxin);
        String auxRND = String.valueOf(rndGenerator.truncateRND());
        data.add(new Row(auxIndice, auxRND));
        tblTabla.getItems().addAll(data);

    }

    @FXML
    //Restaurar Valores del programa en Tab A
    private void Reiniciar(ActionEvent event)
    {

        btnSiguiente.setDisable(true);
        btnGenerar.setDisable(false);
        radCongruencialMixto.setDisable(false);
        radCongruencialMultiplicativo.setDisable(false);
        txtCantNros.setDisable(false);
        txtCantNros.setText("");
        chkMod.setDisable(false);
        txtSemilla.setDisable(false);
        if (!radCongruencialMultiplicativo.isSelected())
        {
            txtCteC.setDisable(false);
        }
        chkIncluir1.setDisable(false);
        chkIdeal.setDisable(false);
        txtAoG.setDisable(false);
        txtMoK.setDisable(false);
        cmbPrecision.setDisable(false);

        rndGenerator = null;

        tblTabla.getItems().clear();
    }

    @FXML
    //CheckBox de Ideal, modifica Labels dependiendo seleccion
    private void handleChkIdeal(ActionEvent event)
    {
        //Chekear 
        if (chkIdeal.isSelected())
        {
            lblA.setText("Valor de G");
            lblM.setText("Valor de K");
        } else
        {
            lblA.setText("Valor de A");
            lblM.setText("Modulo M");
        }
    }

    @FXML
    //CheckBox de Modificacion, permite modificar o no parametros de entrada
    private void handleChkMod(ActionEvent event)
    {
        if (chkMod.isSelected())
        {
            paneParametros.setDisable(false);
        } else
        {
            paneParametros.setDisable(true);
        }

    }

    @FXML
    //RadialBox de Metodo, Habilita o deshabilita ingreso de "C" dependiendo si es Cong Mixto o Lineal
    private void handleRadChangeMetodo(ActionEvent event)
    {
        if (radCongruencialMultiplicativo.isSelected())
        {
            txtCteC.setDisable(true);
        }
        if (radCongruencialMixto.isSelected())
        {
            txtCteC.setDisable(false);
        }
    }

    

    //Clase PUBLICA interna para la creacion de filas de la tabla, con valor de indice y nro RND generado
    public static class Row
    {

        private final SimpleStringProperty indice;
        private final SimpleStringProperty rnd;

        public Row(String indice, String rnd)
        {
            this.indice = new SimpleStringProperty(indice);
            this.rnd = new SimpleStringProperty(rnd);
        }

        public String getIndice()
        {
            return indice.get();

        }

        public String getRnd()
        {
            return rnd.get();

        }

        public void setIndice(String fName)
        {
            indice.set(fName);
        }

        public void setRnd(String fName)
        {
            rnd.set(fName);
        }
    }

//////////////////////////////////////////////////////Ejercicio B/////////////////////////////////////////////
    public void cargatTabTabla()
    {

//        int numeros = Integer.parseInt(txt_cant_numeros.getText());
//        int intervalos = Integer.parseInt(txt_cant_intervalos.getText());
//
//        
//            lbl_alerta.setText("");
//            ChiCuadradoB chi_B = new ChiCuadradoB(intervalos, numeros);
//
//            int[] tabla_frecuencias = chi_B.generarTablaFrecuencias2();
//
//            List<String> lista = chi_B.getNumerosGenerados();
//            txt_serie.setText(lista.toString());
//
//            txt_cant_intervalos.setDisable(true);
//            txt_cant_numeros.setDisable(true);
//            btn_generar.setDisable(true);
//
//            StringBuilder sumatoria = new StringBuilder();
//            sumatoria.append(chi_B.valorTestChiCuadrado(tabla_frecuencias));
//            lbl_sumatoria_chi_cuadrado.setText(sumatoria.toString());
//
//            if (chi_B.testChiCuadrado(tabla_frecuencias))
//            {
//                lbl_acepta_prueba.setText("Prueba aceptada");
//            } else
//            {
//                lbl_acepta_prueba.setText("La prueba no fue aceptada");
//            }
//
//            Creación de los intervalos
//            List<String> l = chi_B.getNumerosGenerados();
//            Collections.sort(l);
//
//            Obtener el menor
//            double intervalo_menor = Double.parseDouble(l.get(0));
//
//            Obtener el mayor
//            double intervalo_mayor = Double.parseDouble(l.get(l.size() - 1));
//
//            Armo el recorrido
//            double recorrido = intervalo_mayor - intervalo_menor;
//
//            Armo lo que se le suma a cada intervalo
//            double suma_a_intervalo = recorrido / intervalos;
//
//            Armo la primer pasada
//            String auxIntervaloMenor_primeraPasada = String.valueOf(intervalo_menor);
//
//            double siguiente_intervalo = (double) Math.round((intervalo_menor + suma_a_intervalo) * 100d) / 100d;
//            String auxHasta = String.valueOf(siguiente_intervalo);

            //Relleno de la tabla
            for (int i = 0; i < pruebaChi.getK() ; i++)
            {
                String Frecuencia_observada = String.valueOf(pruebaChi.getFrecuenciaObs(i));
                String Frecuencia_esperada = String.valueOf(pruebaChi.getFrecuenciaEsp(i));
                String valueCol3 = pruebaChi.getPosTabla(3, i);
                String valueCol4 = pruebaChi.getPosTabla(4, i);
                data_B.add(new Row_B(pruebaChi.getIntervalo(i), Frecuencia_observada, Frecuencia_esperada, valueCol3, valueCol4));

//                //Calculo del "Desde"
//                double siguienteDesde = (double) Math.round((intervalo_menor + suma_a_intervalo) * 100d) / 100d;
//                String siguiente_menor = String.valueOf(siguienteDesde);
//
//                //Se va a sumar el valor anterior para poder tener los valores siguientes de "Hasta"
//                double siguienteHasta = (double) Math.round((siguienteDesde + suma_a_intervalo) * 100d) / 100d;
//                String fila_siguiente_hasta = String.valueOf(siguienteHasta);
//
//                //Sirve para poder seguir sumando el valor anterior y no se quede el principal
//                intervalo_menor = Double.parseDouble(siguiente_menor);
//
//                //Los valores en String para agregar en la proxima pasada, del valor "Desde" siguiente y el valor "Hasta" que le sigue
//                auxIntervaloMenor_primeraPasada = siguiente_menor;
//                auxHasta = fila_siguiente_hasta;
            }
            tbl_Tabla.getItems().addAll(data_B);
            
            

        
        

//        //Parametros para definir escala de eje Y
//        double unit_B = chi_B.frecuenciaEsperada() / (double) 10;
//        if (unit_B < 1)
//        {
//            unit_B = 1;
//        }
//        double upperLimit_B = chi_B.frecuenciaEsperada() + unit_B + 5;
//
//        // base bar chart
//        chrtFrecuenciaC_B.setLegendVisible(false);
//        chrtFrecuenciaC_B.setAnimated(false);
//        chrtFrecuenciaC_B.setCategoryGap(10);
//
//        // overlay line chart
//        lnchrEsperadaC_B.setLegendVisible(false);
//        lnchrEsperadaC_B.setAnimated(false);
//        lnchrEsperadaC_B.setCreateSymbols(true);
//        lnchrEsperadaC_B.setAlternativeRowFillVisible(false);
//        lnchrEsperadaC_B.setAlternativeColumnFillVisible(false);
//        lnchrEsperadaC_B.setHorizontalGridLinesVisible(false);
//        lnchrEsperadaC_B.setVerticalGridLinesVisible(false);
//        lnchrEsperadaC_B.getXAxis().setVisible(false);
//        lnchrEsperadaC_B.getYAxis().setVisible(false);
//        //Carga stylesheet a linechart
//        lnchrEsperadaC_B.getStylesheets().addAll(getClass().getResource("/styles/chart.css").toExternalForm());
//
//        //Sincronizar rango de eje Y de ambas graficas 
//        yFrecBc_B.setAutoRanging(false);
//        yFrecBc_B.setLowerBound(0);
//        yFrecBc_B.setUpperBound(upperLimit_B);
//        yFrecBc_B.setTickUnit(unit_B);
//
//        yFrecLn_B.setAutoRanging(false);
//        yFrecLn_B.setLowerBound(0);
//        yFrecLn_B.setUpperBound(upperLimit_B);
//        yFrecLn_B.setTickUnit(unit_B);
//
//        //Cargar datos de distribucion de frecuencia en graficos
//        XYChart.Series set3_B = new XYChart.Series<>();
//        for (int i = 0; i < chi_B.getK(); i++)
//        {
//            double aux_B = (double) i;
//            set3_B.getData().add(new XYChart.Data(chi_B.getIntervalo(aux_B), chi_B.frecuenciaEsperada()));
//        }
//        chrtFrecuenciaC_B.getData().addAll(set3_B);
//
//        XYChart.Series set2_B = new XYChart.Series<>();
//        for (int i = 0; i < chi_B.getK(); i++)
//        {
//            double aux_B = (double) i;
//            set2_B.getData().add(new XYChart.Data(chi_B.getIntervalo(aux_B), chi_B.frecuenciaEsperada()));
//        }
//        lnchrEsperadaC_B.getData().addAll(set2_B);

    }


//    private void handleButtonReiniciar(ActionEvent event)
//    {
//        txt_cant_intervalos.setDisable(false);
//        txt_cant_numeros.setDisable(false);
//        txt_cant_intervalos.setText("");
//        txt_cant_numeros.setText("");
//        lbl_acepta_prueba.setText("");
//        lbl_sumatoria_chi_cuadrado.setText("");
//        btn_generar.setDisable(false);
//        tbl_Tabla.getItems().clear();
//        txt_serie.clear();
//        btn_graficar.setDisable(true);
//        lbl_alerta.setText("");
//    }

    public static class Row_B
    {

        private final SimpleStringProperty intervalo; 
        private final SimpleStringProperty frecuencia_obtenida;
        private final SimpleStringProperty frecuencia_esperada;
        private final SimpleStringProperty col3;
        private final SimpleStringProperty col4;

        private Row_B(String intervalo, String frecuencia_obtenida, String frecuencia_esperada, String col3, String col4)
        {
            this.intervalo = new SimpleStringProperty(intervalo);
            this.frecuencia_esperada = new SimpleStringProperty(frecuencia_esperada);
            this.frecuencia_obtenida = new SimpleStringProperty(frecuencia_obtenida);
            this.col3 =new SimpleStringProperty(col3);
            this.col4 =new SimpleStringProperty(col4);
        }

        public void setDesde(String asd)
        {
            intervalo.set(asd);
        }

        public void setFrec_esp(String asd)
        {
            frecuencia_esperada.set(asd);
        }

        public void setFrec_obt(String asd)
        {
            frecuencia_obtenida.set(asd);
        }

        public void setCol3(String asd)
        {
            col3.set(asd);
        }
        
        public void setCol4(String asd)
        {
            col4.set(asd);
        }

        public String getIntervalo()
        {
            return intervalo.get();
        }

        public String getFrecuencia_obtenida()
        {
            return frecuencia_obtenida.get();
        }

        public String getFrecuencia_esperada()
        {
            return frecuencia_esperada.get();
        }
        
        public String getCol3()
        {
            return col3.get();
        }
        public String getCol4()
        {
            return col4.get();
        }

    }

//////////////////////////////////////////////////////Ejercicio C/////////////////////////////////////////////
    @FXML
    //Efectuar prueba de frecuencia en serie generada por congruencial mixto
    private void handleButtonProbar(ActionEvent event)
    {

        btnProbar.setDisable(true);
        btnReiniciarC.setDisable(false);
        txtCantIntervalosC.setDisable(true);
        txtCantNrosC.setDisable(true);
//        Reiniciar(event);

        //Genera serie aleatoria con generadorRND congruencial mixto para probar
        
        serie = new double[Integer.parseInt(txtCantNrosC.getText())];
        
        if (chkCMixto.isSelected()) {
            rndGenerator = new Congruencial(71561, 341157, 56822, Double.parseDouble(txtSemillaC.getText()), false, false, 4);
            for (int i = 0; i < serie.length; i++) {
                serie[i] = Double.parseDouble(rndGenerator.truncateRND());
                lsvSerie.getItems().add(serie[i]);
            }
        } else {
            for (int i = 0; i < serie.length; i++) {
                Random rnd = new Random();
                double numeroAleatorio = rnd.nextDouble();
                double redondeado =  Math.round(numeroAleatorio * 10000d) / 10000d;
                serie[i] = redondeado;
                        
                lsvSerie.getItems().add(serie[i]);
            }
            
        }
        
        int intervalos = Integer.parseInt(txtCantIntervalosC.getText());
        for (int i = 3; i < 7; i++) {
            if (intervalos > (10*i + 1)) {
                intervalos = 10*i + 1;
            }
        }
        
        
        //Calculo de chicuadrado y verificacion de hipotesis
        pruebaChi = new ChiCuadrado(serie, intervalos , 4);
        if (pruebaChi.hipotesis())
        {
            txtAResultados.setText("Se aprueba hipotesis \nFrecuencia esperada: " + pruebaChi.getFrecuenciaEsp(1));
            txtAResultados.setVisible(true);
            lbl_acepta_prueba.setText("Se acepta hipotesis. " + pruebaChi.getR() + " < " + pruebaChi.getValueTablaChi());
        } else
        {
            txtAResultados.setText("Se rechaza hipotesis \nFrecuencia esperada: " + pruebaChi.getFrecuenciaEsp(1));
            txtAResultados.setVisible(true);
            lbl_acepta_prueba.setText("Se rechaza hipotesis. " + pruebaChi.getR() + " > " + pruebaChi.getValueTablaChi());
        }

        //Parametros para definir escala de eje Y
        double unit = pruebaChi.getFrecuenciaEsp(1) / (double) 10;
        if (unit < 1)
        {
            unit = 1;
        }
        double upperLimit = pruebaChi.getFrecuenciaEsp(1) + unit + 5;

        // base bar chart
        chrtFrecuenciaC.setLegendVisible(false);
        chrtFrecuenciaC.setAnimated(false);
        chrtFrecuenciaC.setCategoryGap(10);

        // overlay line chart
        lnchrEsperadaC.setLegendVisible(false);
        lnchrEsperadaC.setAnimated(false);
        lnchrEsperadaC.setCreateSymbols(true);
        lnchrEsperadaC.setAlternativeRowFillVisible(false);
        lnchrEsperadaC.setAlternativeColumnFillVisible(false);
        lnchrEsperadaC.setHorizontalGridLinesVisible(false);
        lnchrEsperadaC.setVerticalGridLinesVisible(false);
        lnchrEsperadaC.getXAxis().setVisible(false);
        lnchrEsperadaC.getYAxis().setVisible(false);
        //Carga stylesheet a linechart
        lnchrEsperadaC.getStylesheets().addAll(getClass().getResource("/styles/chart.css").toExternalForm());

        //Sincronizar rango de eje Y de ambas graficas 
        yFrecBc.setAutoRanging(false);
        yFrecBc.setLowerBound(0);
        yFrecBc.setUpperBound(upperLimit);
        yFrecBc.setTickUnit(unit);

        yFrecLn.setAutoRanging(false);
        yFrecLn.setLowerBound(0);
        yFrecLn.setUpperBound(upperLimit);
        yFrecLn.setTickUnit(unit);

        //Cargar datos de distribucion de frecuencia en graficos
        XYChart.Series set3 = new XYChart.Series<>();
        for (int i = 0; i < pruebaChi.getK(); i++)
        {
            double aux = (double) i;
            set3.getData().add(new XYChart.Data(pruebaChi.getIntervalo(aux), pruebaChi.getFrecuenciaObs(i)));
        }
        chrtFrecuenciaC.getData().addAll(set3);

        XYChart.Series set2 = new XYChart.Series<>();
        for (int i = 0; i < pruebaChi.getK(); i++)
        {
            double aux = (double) i;
            set2.getData().add(new XYChart.Data(pruebaChi.getIntervalo(aux), pruebaChi.getFrecuenciaEsp(i)));
        }
        lnchrEsperadaC.getData().addAll(set2);
        
        //Cargar datos en tab Tabla
        cargatTabTabla();
        lbl_sumatoria_chi_cuadrado.setText(String.valueOf(pruebaChi.getR()));

    }

    @FXML
    //Restaurar Valores del programa en Tab C
    private void handleButtonReiniciarC(ActionEvent event)
    {
        btnProbar.setDisable(false);
        btnReiniciarC.setDisable(true);
        txtCantIntervalosC.setDisable(false);
        txtCantNrosC.setDisable(false);
        txtAResultados.setVisible(false);
        txtAResultados.setText("");
        txtCantNrosC.setText("");
        txtSemillaC.setText("");
        txtCantIntervalosC.setText("");
            

        chrtFrecuenciaC.getData().clear();
        lnchrEsperadaC.getData().clear();
        
        lsvSerie.getItems().clear();
    }
    
    @FXML
    private void handleChkCMixto(ActionEvent event) {
        if (chkCMixto.isSelected()) {
            txtSemillaC.setDisable(false);
        } else {
            txtSemillaC.setDisable(true);
        }
    }
}

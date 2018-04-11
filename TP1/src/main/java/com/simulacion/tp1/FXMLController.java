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
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class FXMLController implements Initializable {
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
    
    ///Ejercicio C
    


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
        if (chkMod.isSelected()) {
            paneParametros.setDisable(false);
        } else {
            paneParametros.setDisable(true);
        }
        
        //Crear y definir columnas de tabla
        tblTabla.setEditable(true);
        
        TableColumn colIndice = new TableColumn("Indice");
        TableColumn colRND = new TableColumn("RND");
        
        tblTabla.getColumns().addAll(colIndice, colRND);
     
        colIndice.setCellValueFactory(new PropertyValueFactory("indice"));
        colRND.setCellValueFactory(new PropertyValueFactory("rnd"));
        
        //Tooltips de ayuda
        final Tooltip tooltip = new Tooltip();
        tooltip.setText(
                "\nIdeal:\n"
                + "Modulo M =  2^g\n"  
                + "Constante A =  1 + 4 * k\n"        );
        
        chkIdeal.setTooltip(tooltip);
        
        final Tooltip tooltipIncluir1 = new Tooltip();
        tooltipIncluir1.setText(
                "\nIncluir al valor 1 como \n"
                + "parte del conjunto de numeros aceptados\n" );
        
        chkIncluir1.setTooltip(tooltipIncluir1); 
        
//////////////////////////////////////////////////////Ejercicio B/////////////////////////////////////////////



//////////////////////////////////////////////////////Ejercicio C/////////////////////////////////////////////


    }
    
    
    
    
    
//////////////////////////////////////////////////////Ejercicio A/////////////////////////////////////////////
    @FXML
    //Generar numeros RND iniciales con los parametros ingresados, validando datos de entrada
    private void handleButtonActionGenerar(ActionEvent event) {
        
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
        if (!chkMod.isSelected()) {
            if (radCongruencialMixto.isSelected()) {

                rndGenerator = new Congruencial();

            } else {

                rndGenerator = new Congruencial(0);

            }
        } else {
            if (true) { //Validar que los txtBox no sean nulos o tengan valores erroneos, probar con try catch
                double seed = Double.parseDouble(txtSemilla.getText());
                double aOg = Double.parseDouble(txtAoG.getText());
                double mOk = Double.parseDouble(txtMoK.getText());
                String test = cmbPrecision.getSelectionModel().getSelectedItem().toString();
                int precision = Integer.parseInt(test);
                boolean incluir1 = chkIncluir1.isSelected();
                boolean ideal = chkIdeal.isSelected();
                double cteC;
                if (radCongruencialMixto.isSelected()) {
                    cteC = Double.parseDouble(txtCteC.getText());
                } else {
                    cteC = 0;
                }
                
                //Instanciar clase generadora de nros pseudoaleatorios
                rndGenerator = new Congruencial(aOg, mOk, cteC, seed, ideal, incluir1, precision);

            }
            
            //Generar "cantNros" de RND y cargar en tabla
            final ObservableList<Row> data = FXCollections.observableArrayList();
            for (int i = 1; i < cantNros + 1; i++) {

                String auxIndice = String.valueOf(i);
                String auxRND = String.valueOf(rndGenerator.truncateRND());
                data.add(new Row(auxIndice, auxRND));

            }
            tblTabla.setItems(data);
        }

    }

    @FXML
    //Generar siguente valor de serie
    private void Siguiente(ActionEvent event) {
        final ObservableList<Row> data = FXCollections.observableArrayList();
        
        int auxin = tblTabla.getItems().size() +1;
        String auxIndice;
        auxIndice = String.valueOf(auxin);
        String auxRND = String.valueOf(rndGenerator.truncateRND());
        data.add(new Row(auxIndice, auxRND));
        tblTabla.getItems().addAll(data);
        
    }

    @FXML
    //Restaurar Valores del programa
    private void Reiniciar(ActionEvent event) {
        btnSiguiente.setDisable(true);
        btnGenerar.setDisable(false);
        radCongruencialMixto.setDisable(false);
        radCongruencialMultiplicativo.setDisable(false);
        txtCantNros.setDisable(false);
        txtCantNros.setText("");
        chkMod.setDisable(false);
        txtSemilla.setDisable(false);
        if (!radCongruencialMultiplicativo.isSelected()) {
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
    private void handleChkIdeal(ActionEvent event) {
        //Chekear 
        if (chkIdeal.isSelected()) {
            lblA.setText("Valor de G");
            lblM.setText("Valor de K");
        } else {
            lblA.setText("Valor de A");
            lblM.setText("Modulo M");
        }
    }

    @FXML
    //CheckBox de Modificacion, permite modificar o no parametros de entrada
    private void handleChkMod(ActionEvent event) {
        if (chkMod.isSelected()) {
            paneParametros.setDisable(false);
        } else {
            paneParametros.setDisable(true);
        }

    }

    @FXML
    //RadialBox de Metodo, Habilita o deshabilita ingreso de "C" dependiendo si es Cong Mixto o Lineal
    private void handleRadChangeMetodo(ActionEvent event) {
        if (radCongruencialMultiplicativo.isSelected()) {
            txtCteC.setDisable(true);
        } 
        if(radCongruencialMixto.isSelected()) {
            txtCteC.setDisable(false);
        }
    }
    
    //Clase privada interna para la creacion de filas de la tabla, con valor de indice y nro RND generado
    private static class Row{
         private final SimpleStringProperty indice;
         private final SimpleStringProperty rnd;

        public Row(String indice, String rnd) {
            this.indice = new SimpleStringProperty(indice);
            this.rnd = new SimpleStringProperty(rnd);
        }

        public String getIndice() {
            return indice.get();

        }

        public String getRnd() {
            return rnd.get();

        }
         
        public void setIndice(String fName) {
            indice.set(fName);
        }
        public void setRnd(String fName) {
            rnd.set(fName);
        }
    }
    
//////////////////////////////////////////////////////Ejercicio B/////////////////////////////////////////////
    
    
//////////////////////////////////////////////////////Ejercicio C/////////////////////////////////////////////
}


    


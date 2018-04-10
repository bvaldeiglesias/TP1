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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import generadoresPseudoAleatorios.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class FXMLController implements Initializable {

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
    
    private CongruencialLineal rndGenerator;
    


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Cargar valores en Combobox
        ObservableList<String> options
                = FXCollections.observableArrayList(
                        "0.1",
                        "0.01",
                        "0.001",
                        "0.0001"
                );
        cmbPrecision.getItems().addAll(options);

        //Chekear que si hay que habilitar o deshabilitar la edicion de parametros
        if (chkMod.isSelected()) {
            paneParametros.setDisable(false);
        } else {
            paneParametros.setDisable(true);
        }
        
        
        tblTabla.setEditable(true);
        
        TableColumn colIndice = new TableColumn("Indice");
        TableColumn colRND = new TableColumn("RND");
        
        tblTabla.getColumns().addAll(colIndice, colRND);
        
        
        
//        final ObservableList<Row> data = FXCollections.observableArrayList(
//                new Row( "1", "mmmmmmm"),
//                new Row("2", "aaaaaaaaa")
//        );
        
        colIndice.setCellValueFactory(new PropertyValueFactory("indice"));
        colRND.setCellValueFactory(new PropertyValueFactory("rnd"));
        
 //       tblTabla.setItems(data);
        
    }

    @FXML
    //Generar numeros RND iniciales con los parametros ingresados, chekeando nulls
    private void handleButtonActionGenerar(ActionEvent event) {
        
        int cantNros = Integer.parseInt(txtCantNros.getText());
        
        if (!chkMod.isSelected()) {
            if (radCongruencialMixto.isSelected()) {
                
                rndGenerator = new CongruencialLineal();
                final ObservableList<Row> data = FXCollections.observableArrayList();             
                for (int i = 1; i < cantNros+1; i++) {
                    
                    String auxIndice = String.valueOf(i);
                    String auxRND = String.valueOf(rndGenerator.RND());
                    data.add(new Row( auxIndice, auxRND));
                    
                }
                tblTabla.setItems(data);
                
                

            } else {
                
                rndGenerator = new CongruencialLineal(0);
                final ObservableList<Row> data = FXCollections.observableArrayList();             
                for (int i = 1; i < cantNros+1; i++) {
                    
                    String auxIndice = String.valueOf(i);
                    String auxRND = String.valueOf(rndGenerator.RND());
                    data.add(new Row( auxIndice, auxRND));
                    
                }
                tblTabla.setItems(data);
            }
        } else {
            if (true) {

                double seed = Double.parseDouble(txtSemilla.getText());
                double cteC = Double.parseDouble(txtCteC.getText());
                double aOg = Double.parseDouble(txtAoG.getText());
                double mOk = Double.parseDouble(txtMoK.getText());
                String test = cmbPrecision.getSelectionModel().getSelectedItem().toString();
                double precision = Double.parseDouble(test);
                boolean incluir1 = chkIncluir1.isSelected();
                boolean ideal = chkIdeal.isSelected();
                if (radCongruencialMixto.isSelected()) {
                    
                    rndGenerator = new CongruencialLineal(aOg, mOk, cteC, seed, ideal, incluir1, precision);
                    final ObservableList<Row> data = FXCollections.observableArrayList();
                    for (int i = 1; i < cantNros + 1; i++) {

                        String auxIndice = String.valueOf(i);
                        String auxRND = String.valueOf(rndGenerator.RND());
                        data.add(new Row(auxIndice, auxRND));

                    }
                    tblTabla.setItems(data);

                } else {
                    
                    rndGenerator = new CongruencialLineal(aOg, mOk, 0, seed, ideal, incluir1, precision);
                    final ObservableList<Row> data = FXCollections.observableArrayList();
                    for (int i = 1; i < cantNros + 1; i++) {

                        String auxIndice = String.valueOf(i);
                        String auxRND = String.valueOf(rndGenerator.RND());
                        data.add(new Row(auxIndice, auxRND));

                    }
                    tblTabla.setItems(data);
                }

            }
        }

    }

    @FXML
    private void Siguiente(ActionEvent event) {
        final ObservableList<Row> data = FXCollections.observableArrayList();
        
        int auxin = tblTabla.getItems().size() +1;
        String auxIndice;
        auxIndice = String.valueOf(auxin);
        String auxRND = String.valueOf(rndGenerator.RND());
        data.add(new Row(auxIndice, auxRND));
        tblTabla.getItems().addAll(data);
        
    }

    @FXML
    private void Reiniciar(ActionEvent event) {
    }

    @FXML
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
    private void handleChkMod(ActionEvent event) {
        if (chkMod.isSelected()) {
            paneParametros.setDisable(false);
        } else {
            paneParametros.setDisable(true);
        }

    }

    @FXML
    private void handleRadChangeMetodo(ActionEvent event) {
        if (radCongruencialMultiplicativo.isSelected()) {
            txtCteC.setDisable(true);
        } 
        if(radCongruencialMixto.isSelected()) {
            txtCteC.setDisable(false);
        }
    }
    
    public static class Row{
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
}


    


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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FXMLController implements Initializable {
    
    private Label label;
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
    private RadioButton radAyM;
    @FXML
    private ToggleGroup gParametros;
    @FXML
    private RadioButton radGyK;
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
    private TextArea txtArea;
    @FXML
    private TextField txtMoK;
    
//    private void handleButtonAction(ActionEvent event) {
//        System.out.println("You clicked me!");
//        label.setText("Hello World!");
//    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> options
                = FXCollections.observableArrayList(
                        "0.1",
                        "0.01",
                        "0.001",
                        "0.0001"
                );

        cmbPrecision.getItems().addAll(options);
//        cmbPrecision.getItems().removeAll(cmbPrecision.getItems());
//        cmbPrecision.getItems().addAll("Option A", "Option B", "Option C");
//        cmbPrecision.getSelectionModel().select("Option B");
        
    }    

    @FXML
    private void handleButtonActionGenerar(ActionEvent event) {
        if (true ) {
        
            double seed = Double.parseDouble(txtSemilla.getText());
            double cteC = Double.parseDouble(txtCteC.getText());
            double aOg = Double.parseDouble(txtAoG.getText());
            double mOk = Double.parseDouble(txtMoK.getText());
            String test = cmbPrecision.getSelectionModel().getSelectedItem().toString();
            double precision = Double.parseDouble(test);
            boolean incluir1 = chkIncluir1.isSelected();
            boolean ideal = radGyK.isSelected();
            if (radCongruencialMixto.isSelected()) {
                CongruencialLineal rndGenerator;
                rndGenerator = new CongruencialLineal(aOg, mOk, cteC, seed, ideal, incluir1, precision);
                txtArea.appendText("Serie: ");
                for (int i = 0; i < 20; i++) {
                    txtArea.appendText(String.valueOf(rndGenerator.RND()));
                    txtArea.appendText(" - ");
                }

            } else {
                MultiplicativoCongruente rndGenerator;
                rndGenerator = new MultiplicativoCongruente(aOg, mOk, cteC, seed, ideal, incluir1, precision);
                for (int i = 0; i < 20; i++) {
                    txtArea.appendText(String.valueOf(rndGenerator.RND()));
                }
            }
            
            
        }
    }

            


    @FXML
    private void Siguiente(ActionEvent event) {
    }

    @FXML
    private void Reiniciar(ActionEvent event) {
    }
}

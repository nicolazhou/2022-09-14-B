/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.itunes.model.Album;
import it.polito.tdp.itunes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnComponente"
    private Button btnComponente; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnSet"
    private Button btnSet; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA1"
    private ComboBox<Album> cmbA1; // Value injected by FXMLLoader

    @FXML // fx:id="txtDurata"
    private TextField txtDurata; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML
    void doComponente(ActionEvent event) {
    	
    	this.txtResult.clear();
        
    	if(!this.model.isGrafoLoaded()) {
    		
    		this.txtResult.setText("Crea grafo prima!");
    		return;
    		
    	}
    	
    	
    	Album a1 = this.cmbA1.getValue();
    	
    	if(a1 == null) {
    		
    		this.txtResult.appendText("Scegli un album A1 prima! \n");
    		return;
    		
    	}
    	
    	List<Album> connessi = this.model.getListaConnessa(a1);
    	
    	int numBrani = this.model.getNumBraniTot(connessi);
    	
    	this.txtResult.appendText("Componente connesa - " + a1 + "\n");
    	this.txtResult.appendText("Dimensione componente = " + connessi.size() + "\n");
    	this.txtResult.appendText("# Album componente = " + numBrani + "\n");
    	
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	this.txtResult.clear();
        
    	String input = this.txtDurata.getText();
    	
    	Integer d = 0; // secondi
    	
    	try {
    		
    		d = Integer.parseInt(input);
    		
    	} catch(NumberFormatException e) {
    		
    		this.txtResult.setText("Inserisci un valore intero alla durata !");
    		return;
    		
    	}
    	
    	this.model.creaGrafo(d);
    	
    	this.txtResult.appendText("Grafo creato! \n");
    	this.txtResult.appendText("# Vertici: " + this.model.getNNodes() + "\n");
    	this.txtResult.appendText("# Archi: " + this.model.getNArchi() + "\n");
        
    	
    	this.cmbA1.getItems().clear();
    	this.cmbA1.getItems().addAll(this.model.getVertici());
    	
    	
    }

    @FXML
    void doEstraiSet(ActionEvent event) {

    	this.txtResult.clear();
    	
    	Album a1 = this.cmbA1.getValue();
    	
    	if(a1 == null) {
    		
    		this.txtResult.appendText("Scegli un album A1 prima! \n");
    		return;
    		
    	}

    	String input = this.txtX.getText();
    	
    	Integer dTOT = 0; // minuti
    	
    	try {
    		
    		dTOT = Integer.parseInt(input);
    		
    	} catch(NumberFormatException e) {
    		
    		this.txtResult.setText("Inserisci un valore intero a dTOT !");
    		return;
    		
    	}
    	
    	Set<Album> soluzione = this.model.trova(a1, dTOT);
    	
    	if(soluzione == null) {
    		
    		this.txtResult.appendText("Impossibile, la durata di " + a1 + " supera dTOT !\n");
    		
    	} else {
    		
        	
        	this.txtResult.appendText("Ricorsione: lista di album: \n");
        	
        	for(Album a : soluzione) {
        		
        		this.txtResult.appendText(a+"\n");
        		
        	}
    		
    	}
    	
    	
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnComponente != null : "fx:id=\"btnComponente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSet != null : "fx:id=\"btnSet\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA1 != null : "fx:id=\"cmbA1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDurata != null : "fx:id=\"txtDurata\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }

}

package it.polito.tdp.porto;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Adiacenza;
import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {
	Model model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleCoautori(ActionEvent event) {
    	
    	if(this.boxPrimo.getValue()==null) {
    		this.txtResult.appendText("Attenzione! Devi selezionare un Autore.\n");
    	}else {
    		model.creaGrafo();
    		List<Author> autori = model.coAutori(this.boxPrimo.getValue());
    		this.txtResult.clear();
    		this.txtResult.appendText("Autore Selezionato: "+this.boxPrimo.getValue()+". Lista di Co-Autori: \n");
    		for(Author a : autori) {
    			this.txtResult.appendText(a+"\n");
    		}
    		
    		this.boxSecondo.getItems().clear();
    		List<Author> nonCoAutori = model.noCoautori(this.boxPrimo.getValue());
        	this.boxSecondo.getItems().addAll(nonCoAutori);
        	
    	}
    	
    	
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	if(this.boxPrimo.getValue()==null || this.boxSecondo.getValue()==null) {
    		this.txtResult.appendText("Attenzione! Devi selezionare due Autori.\n");
    	}else {
    		model.creaGrafo();
    		this.txtResult.appendText("\n");
    		model.trovaCamminoMinimo(this.boxPrimo.getValue(), this.boxSecondo.getValue());
    		this.txtResult.appendText("Trovo una serie di articoli che colleghino l'autore: "+this.boxPrimo.getValue()+" "
    				+ "e: "+this.boxSecondo.getValue()+"\n");
    		
    		for(Adiacenza a: model.trovaCamminoMinimo(this.boxPrimo.getValue(), this.boxSecondo.getValue())) {
    			this.txtResult.appendText("Autore: "+a.getAutore1()+" e Autore: "+a.getAutore2()+" per la pubblicazione: "+a.getPubblicazione().toString()+"\n");
    		}
    	}
    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.boxPrimo.getItems().addAll(model.listaAutori());
		
	}
}

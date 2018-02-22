package org.mobibank.payme.ui.view.utilisateur.form;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.mobibank.payme.backend.numerate.Jours;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.HorizontalLayout;

public class HorraireLine {

	private HorizontalLayout line;
	private ComboBox<Jours> jours;
	private DateTimeField debut;
	private DateTimeField fin;
	private Button add;
	
	public HorraireLine(HorizontalLayout line, ComboBox<Jours> jours, DateTimeField debut, DateTimeField fin,
			Button add) {
		super();
		this.line = line;
		this.jours = jours;
		this.debut = debut;
		this.fin = fin;
		this.add = add;
	}
	
	public LocalTime debut(){
		LocalDateTime date = debut.getValue();
		if(date == null) return LocalTime.of(0, 0);
		return date.toLocalTime();
	}
	
	public LocalTime fin(){
		LocalDateTime date = fin.getValue();
		if(date == null) return LocalTime.of(0, 0);
		return date.toLocalTime();
	}
	
	public Jours jours(){
		return jours.getValue();
	}
	
	public HorizontalLayout getLine() {
		return line;
	}
	public void setLine(HorizontalLayout line) {
		this.line = line;
	}
	public ComboBox<Jours> getJours() {
		return jours;
	}
	public void setJours(ComboBox<Jours> jours) {
		this.jours = jours;
	}
	public DateTimeField getDebut() {
		return debut;
	}
	public void setDebut(DateTimeField debut) {
		this.debut = debut;
	}
	public DateTimeField getFin() {
		return fin;
	}
	public void setFin(DateTimeField fin) {
		this.fin = fin;
	}
	public Button getAdd() {
		return add;
	}
	public void setAdd(Button add) {
		this.add = add;
	}
	
	
}

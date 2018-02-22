package org.mobibank.payme.ui.view.admin.pays;

import org.mobibank.payme.backend.data.entity.Pays;
import org.mobibank.payme.backend.data.entity.Structure;
import org.mobibank.payme.backend.data.entity.Zone;
import org.mobibank.payme.backend.numerate.Devise;
import org.mobibank.payme.ui.AbstractCrudViewDesign;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
/**
 * 
 * @author BEYE
 *
 */
public class PaysViewDesign extends AbstractCrudViewDesign<Pays>{

	/**
	 * 
	 */
	protected Grid<Pays> list;
	protected TextField nom;
	protected TextField codeIso;
	protected TextField indicatif;
	protected TextField numberSize;
	protected TextField ipAdresse;
	protected CheckBox disponible;
	protected TextField taf;
	protected ComboBox<Devise> devise;
	protected ComboBox<Zone> zone;
	protected ComboBox<Structure> localProvider;
	protected ComboBox<Structure> mainBanque;
	
	public PaysViewDesign() {
		
		list = new Grid<Pays>(Pays.class);
		list.setSizeFull();
		list.setColumnResizeMode(ColumnResizeMode.ANIMATED);
		list.setColumns("id", "nom", "codeIso", "numberSize", "ipAdresse", "disponible", "devise", "localProvider", "mainBanque");
		addGrid(list);
		
		nom = new TextField("Nom");
		nom.setValueChangeMode(ValueChangeMode.LAZY);
		nom.setValueChangeTimeout(500);
		
		codeIso = new TextField("Code Pays");
		codeIso.setValueChangeMode(ValueChangeMode.LAZY);
		codeIso.setValueChangeTimeout(500);
		
		indicatif = new TextField("Indicatif Pays");
		indicatif.setValueChangeMode(ValueChangeMode.LAZY);
		indicatif.setValueChangeTimeout(500);
		
		numberSize = new TextField("Number Size");
		numberSize.setValueChangeMode(ValueChangeMode.LAZY);
		numberSize.setValueChangeTimeout(500);
		
		ipAdresse = new TextField("Adresse IP");
		ipAdresse.setValueChangeMode(ValueChangeMode.LAZY);
		ipAdresse.setValueChangeTimeout(500);
		
		taf = new TextField("Taf");
		taf.setValueChangeMode(ValueChangeMode.LAZY);
		taf.setValueChangeTimeout(500);
		
		disponible = new CheckBox("Activer");
		devise = new ComboBox<Devise>("Devise");
		devise.setItems(Devise.asList());
		
		zone = new ComboBox<Zone>("Zone");
		
		localProvider = new ComboBox<Structure>("Provider");
		mainBanque = new ComboBox<Structure>("Main Banque");
		
		addField(disponible);
		addField(nom);
		addField(codeIso);
		addField(indicatif);
		addField(numberSize);
		addField(ipAdresse);
		addField(taf);
		addField(devise);
		addField(zone);
		addField(localProvider);
		addField(mainBanque);
	}
	
	public void bindForm(BeanValidationBinder<Pays> binder){
		binder.forField(nom).bind(Pays::getNom, Pays::setNom);
		binder.forField(codeIso).bind(Pays::getCodeIso, Pays::setCodeIso);
		binder.forField(indicatif).bind(Pays::getIndicatif, Pays::setIndicatif);
		binder.forField(numberSize).
		withConverter(new StringToIntegerConverter("Doit etre un nombre entier")).
		bind(Pays::getNumberSize, Pays::setNumberSize);
		binder.forField(ipAdresse).bind(Pays::getIpAdresse, Pays::setIpAdresse);
		binder.forField(disponible).bind(Pays::getDisponible, Pays::setDisponible);
		binder.forField(devise).bind(Pays::getDevise, Pays::setDevise);
		binder.forField(taf).
		withConverter(new StringToIntegerConverter("Doit etre un nombre entier"))
		.bind(Pays::getTaf, Pays::setTaf);
		binder.forField(zone).bind(Pays::getZone, Pays::setZone);
		binder.forField(localProvider).bind(Pays::getLocalProvider, Pays::setLocalProvider);
		binder.forField(mainBanque).bind(Pays::getMainBanque, Pays::setMainBanque);
	}
}

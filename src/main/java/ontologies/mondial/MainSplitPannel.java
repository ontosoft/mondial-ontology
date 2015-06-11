package ontologies.mondial;

import ontologies.mondial.view.ContinentLayout;
import ontologies.mondial.view.CountryLayout;
import ontologies.mondial.view.EconomyLayout;
import ontologies.mondial.view.ProvinceLayout;

import com.vaadin.ui.HorizontalSplitPanel;

public class MainSplitPannel extends HorizontalSplitPanel implements
		ValueSubmittedListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1030484021469740942L;

	@Override
	public void onSubmitted(String value) {
		// TODO Auto-generated method stub
		ValueSubmittedListener.super.onSubmitted(value);
		switch(value){
		case "Provinces": 
			ProvinceLayout provinceL = new ProvinceLayout();
			this.setSecondComponent(provinceL);
			break;
		case "Countries": 
			CountryLayout country = new CountryLayout();
			this.setSecondComponent(country);
			break;
		case "Continents": 
			ContinentLayout continent = new ContinentLayout();
			this.setSecondComponent(continent);
			break;
		case "Economic data": 
			EconomyLayout economy = new EconomyLayout();
			this.setSecondComponent(economy);
			break;
		default:
			break;
		}
	}

}

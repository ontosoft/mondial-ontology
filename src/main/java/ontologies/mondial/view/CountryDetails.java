package ontologies.mondial.view;

import ontologies.mondial.ValueSubmittedListener;
import ontologies.mondial.dao.Country;
import ontologies.mondial.view.EconomyLayout;

import com.vaadin.ui.HorizontalSplitPanel;

public class CountryDetails extends HorizontalSplitPanel implements
		ValueSubmittedListener {

	public void alternatingSearchForm(){
		if(this.isVisible()) {
			this.setVisible(false);
			//parentForm.getSearchFormHide().setIcon(new ThemeResource("../../icons/down.svg"));
		}
		else{
			this.setVisible(true);
			//parentForm.getSearchFormHide().setIcon(new ThemeResource("../../icons/up.svg"));
		}
			
		
	}

	private Country country;
	
	public CountryDetails(Country country) {
		super();
		this.country = country;
	}

	private static final long serialVersionUID = 1030484021469740942L;

	
	
	
	@Override
	public void onSubmitted(String value) {
		// TODO Auto-generated method stub
		ValueSubmittedListener.super.onSubmitted(value);
		switch(value){
		case "Provinces": 
			ProvinceLayout provinceL = new ProvinceLayout(country);
			this.setSecondComponent(provinceL);
			break;
		case "Hidrology": 
			WaterLayout waterL = new WaterLayout(country);
			this.setSecondComponent(waterL);
			break;
		case "Geo-morphology": 
			SurfaceLayout surfaceL = new SurfaceLayout(country);
			this.setSecondComponent(surfaceL);
			break;
		case "Economy": 
			EconomyLayout economy = new EconomyLayout(country);
			this.setSecondComponent(economy);
			break;
		case "Religion": 
			ReligionLayout religion = new ReligionLayout(country);
			this.setSecondComponent(religion);
			break;
		case "Borders": 
			CountryBLayout countryB = new CountryBLayout(country);
			this.setSecondComponent(countryB);
			break;
		default:
			break;
		}
	}

}

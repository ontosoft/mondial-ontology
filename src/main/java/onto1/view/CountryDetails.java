package onto1.view;

import onto1.ValueSubmittedListener;

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
			
		
	}/**
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

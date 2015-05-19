package onto1;

import onto1.view.ContinentLayout;
import onto1.view.CountryLayout;
import onto1.view.ProvinceLayout;

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
		case "Province1": 
			ProvinceLayout provinceL = new ProvinceLayout();
			this.setSecondComponent(provinceL);
			break;
		case "Country": 
			CountryLayout country = new CountryLayout();
			this.setSecondComponent(country);
			break;
		case "Continent": 
			ContinentLayout continent = new ContinentLayout();
			this.setSecondComponent(continent);
			break;
		default:
			break;
		}
	}

}

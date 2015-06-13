package ontologies.mondial.view;

import ontologies.mondial.ValueSubmittedListener;
import ontologies.mondial.dao.River;

import com.vaadin.ui.HorizontalSplitPanel;

public class RiverDetails extends HorizontalSplitPanel implements
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

	private River river;
	
	public RiverDetails(River r) {
		super();
		this.river = r;
	}

	private static final long serialVersionUID = 1030484021469740942L;

	
	
	
	@Override
	public void onSubmitted(String value) {
		// TODO Auto-generated method stub
		ValueSubmittedListener.super.onSubmitted(value);
		switch(value){
		case "Provinces": 
			ProvinceLayout provinceL = new ProvinceLayout(river);
			this.setSecondComponent(provinceL);
			break;
		case "FlowsTo": 
			WaterLayout waterL = new WaterLayout(river);
			this.setSecondComponent(waterL);
			break;
		default:
			break;
		}
	}

}

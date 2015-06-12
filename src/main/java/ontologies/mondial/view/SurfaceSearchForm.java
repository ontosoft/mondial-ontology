package ontologies.mondial.view;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class SurfaceSearchForm extends VerticalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 851894446243275846L;
	Button search= new Button("Search", this::search);
	Button cancel = new Button ("Clean", this::cancel);
    TextField countryString = new TextField("Country");
    TextField provinceString = new TextField("Province");
    TextField nameString = new TextField("Name");
    TextField typeString = new TextField("Type");
    
    SurfaceLayout parentForm = null;
    
    
    public SurfaceSearchForm(SurfaceLayout p){
    	parentForm = p;
    	configureComponents();
    	buildLayout();
		if (p.getCountry() != null) 
			countryString.setVisible(false);
		else
			countryString.setVisible(true);
		if (p.getProvince() != null) 
			provinceString.setVisible(false);
		else
			provinceString.setVisible(true);
    }
    
    private void buildLayout() {
		setSizeUndefined();
		setMargin(true);
		//HorizontalLayout actions = new HorizontalLayout(search,cancel);
		//actions.setSpacing(true);
		GridLayout searchFields = new GridLayout(4,2);
		
		searchFields.addComponent(nameString,0,0);
		searchFields.addComponent(typeString,1,0);
		searchFields.addComponent(countryString,2,0);
		searchFields.addComponent(provinceString,3,0);
		searchFields.addComponent(search,0,1);
		searchFields.addComponent(cancel,1,1);
		
		searchFields.setSpacing(true);
		addComponents(searchFields);
//		populationLess.addValidator(new IndtegerRangeValidator(
//	            "The value must be integer between 0-120 (was {0})",
//	            0, 120));
		
	}
    
	private void configureComponents() {
		
		search.setStyleName(ValoTheme.BUTTON_PRIMARY);
		search.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		setVisible(true);

	}
	
	public void search(Button.ClickEvent event){

            parentForm.reloadTuples( countryString.getValue(), provinceString.getValue(), nameString.getValue(), typeString.getValue());

    }
	//Making form visible or invisible -changing state hide, unhide
	public void alternatingSearchForm(){
		if(this.isVisible()) {
			this.setVisible(false);
			parentForm.getSearchFormHide().setIcon(new ThemeResource("../../icons/down.svg"));
		}
		else{
			this.setVisible(true);
			parentForm.getSearchFormHide().setIcon(new ThemeResource("../../icons/up.svg"));
		}
			
		
	}
	
	public void cancel(Button.ClickEvent event) {
		nameString.setValue("");
		typeString.setValue("");
		countryString.setValue("");
		provinceString.setValue("");
    }
	

}

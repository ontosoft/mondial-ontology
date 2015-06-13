package ontologies.mondial.view;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ReligionSearchForm extends VerticalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 851894446243275846L;
	Button search= new Button("Search", this::search);
	Button cancel = new Button ("Clean", this::cancel);	
	TextField religionName = new TextField("Religion name");
    TextField percentageLess = new TextField("Percentage less than");
    TextField percentageGreater = new TextField("Percentage greater than");
    TextField countryString = new TextField("Country");    
    ReligionLayout parentForm = null;
    
    
    public ReligionSearchForm(ReligionLayout p){
    	parentForm = p;
    	configureComponents();
    	buildLayout();
    }
    
    private void buildLayout() {
		setSizeUndefined();
		setMargin(true);
		//HorizontalLayout actions = new HorizontalLayout(search,cancel);
		//actions.setSpacing(true);
		GridLayout searchFields = new GridLayout(6,3);
		searchFields.addComponent(religionName,0,0);
		searchFields.addComponent(percentageGreater,1,0);
		searchFields.addComponent(percentageLess,2,0);
		
		searchFields.addComponent(countryString,3,0);
		
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

            parentForm.reloadTuples( countryString.getValue(), religionName.getValue(), 
            		percentageLess.getValue().toString(), percentageGreater.getValue().toString());

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
		countryString.setValue("");
		religionName.setValue(""); 
		percentageLess.setValue(""); 
		percentageGreater.setValue("");
		
    }
	

}

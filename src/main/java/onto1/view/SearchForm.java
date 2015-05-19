package onto1.view;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class SearchForm extends FormLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 851894446243275846L;
	Button search= new Button("Search", this::search);
	Button cancel = new Button ("Clean", this::cancel);
	TextField populationLess = new TextField("Population less then");
	TextField populationGreater = new TextField("Population greater then");
    TextField areaLess = new TextField("Area less then");
    TextField areaGreater = new TextField("Area greater than");
    TextField continentString = new TextField("Continent");
    
    ContinentLayout parentForm = null;
    
    
    public SearchForm(ContinentLayout p){
    	parentForm = p;
    	configureComponents();
    	buildLayout();
    }
    
    private void buildLayout() {
		setSizeUndefined();
		setMargin(true);
		HorizontalLayout actions = new HorizontalLayout(search,cancel);
		actions.setSpacing(true);
		addComponents(populationLess, populationGreater, areaLess, areaGreater, continentString, actions );
//		populationLess.addValidator(new IntegerRangeValidator(
//	            "The value must be integer between 0-120 (was {0})",
//	            0, 120));
		
	}
    
	private void configureComponents() {
		
		search.setStyleName(ValoTheme.BUTTON_PRIMARY);
		search.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		setVisible(true);

	}
	
	public void search(Button.ClickEvent event){

            parentForm.reloadTuples(continentString.getValue(), populationLess.getValue().toString(), 
            		populationGreater.getValue().toString(), 
            		areaLess.getValue().toString(), areaGreater.getValue().toString());

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
        parentForm.getContactList().select(null);
    }
	

}

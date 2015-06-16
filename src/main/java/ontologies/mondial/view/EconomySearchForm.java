package ontologies.mondial.view;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class EconomySearchForm extends VerticalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 851894446243275846L;
	Button search= new Button("Search", this::search);
	Button cancel = new Button ("Clean", this::cancel);
	TextField gdpLess = new TextField("GDP less than  (mil. $)");
	TextField gdpGreater = new TextField("GDP greater than (mil. $)");
    TextField agricultureLess = new TextField("Agriculture less than (%)");
    TextField agricultureGreater = new TextField("Agriculture greater than (%)");
    TextField serviceLess = new TextField("Service less than (%)");
    TextField serviceGreater = new TextField("Service greater than (%)");
    TextField industryLess = new TextField("Industry less than (%)");
    TextField industryGreater = new TextField("Industry greater than (%)");
    TextField inflationLess = new TextField("Inflation less than (%)");
    TextField inflationGreater = new TextField("Inflation greater than (%)");
    TextField continentString = new TextField("Continent");
    
    EconomyLayout parentForm = null;
    
    
    public EconomySearchForm(EconomyLayout p){
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
		searchFields.addComponent(gdpGreater,0,0);
		searchFields.addComponent(gdpLess,1,0);
		searchFields.addComponent(agricultureGreater,2,0);
		searchFields.addComponent(agricultureLess,3,0);
		searchFields.addComponent(serviceGreater,4,0);
		searchFields.addComponent(serviceLess,5,0);
		searchFields.addComponent(industryGreater,0,1);
		searchFields.addComponent(industryLess,1,1);
		searchFields.addComponent(inflationGreater,2,1);
		searchFields.addComponent(inflationLess,3,1);
		
		searchFields.addComponent(continentString,4,1);
		
		searchFields.addComponent(search,0,2);
		searchFields.addComponent(cancel,1,2);
		
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

            parentForm.reloadTuples(continentString.getValue(), gdpLess.getValue().toString(), 
            		gdpGreater.getValue().toString(), 
            		agricultureLess.getValue().toString(), agricultureGreater.getValue().toString(),
            		industryLess.getValue().toString(), industryGreater.getValue().toString(),
            		serviceLess.getValue().toString(), serviceGreater.getValue().toString(),
            		inflationLess.getValue().toString(), inflationGreater.getValue().toString());

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
		continentString.setValue("");
		gdpLess.setValue(""); 
		gdpGreater.setValue(""); 
		agricultureLess.setValue("");
		agricultureGreater.setValue("");
		industryLess.setValue("");
		industryGreater.setValue("");
		serviceLess.setValue("");
		serviceGreater.setValue("");
		inflationLess.setValue("");
		inflationGreater.setValue("");
    }
	

}

package onto1.view;

import onto1.dao.Country;
import onto1.services.CountryService;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class CountryLayout extends VerticalLayout{

	SearchForm searchForm = new SearchForm(this);
	/**
	 * 
	 */
	private static final long serialVersionUID = -4679449649489092925L;

	/**
	 * 
	 */
	private Grid contactList = new Grid();
	private Button searchFormHide = new Button();
	private TextField filter = new TextField();
	private CountryService service = CountryService.createDemoService();
	
	public CountryLayout(){
		buildLayout();
		configureComponents();
	}
	
    public Grid getContactList() {
		return contactList;
	}

	public void setContactList(Grid contactList) {
		this.contactList = contactList;
	}

	public void onSubmitted(String value) {
        // to be implemented
    }

	public Button getSearchFormHide() {
		return searchFormHide;
	}

	public void setSearchFormHide(Button searchFormHide) {
		this.searchFormHide = searchFormHide;
	}

	private void buildLayout() {
		GridLayout title = new GridLayout(1,1);
		title.setMargin(false);
		title.setWidth("100%");
		Label lblTitle = new Label("General data about countries within continent");
		lblTitle.addStyleName( "h2" );
		lblTitle.setSizeUndefined();
		title.addComponent(lblTitle,0,0);
		title.setComponentAlignment(lblTitle, Alignment.MIDDLE_CENTER);

		this.addComponent(title);
		this.addComponent(searchForm);
		HorizontalLayout actions = new HorizontalLayout(filter, searchFormHide);
		searchFormHide.setIcon(new ThemeResource("../../icons/up.svg"));
		actions.setWidth("100%");
		filter.setWidth("100%");
		actions.setExpandRatio(filter, 1);

		this.setSizeFull();
		this.setMargin(false);
		this.addComponent(actions);
		this.addComponent(contactList);
		
		this.setExpandRatio(contactList, 1);
		contactList.setSizeFull();
		
	}



	public void configureComponents() {

		searchFormHide.addClickListener(e -> searchForm.alternatingSearchForm());
		filter.setInputPrompt("Filter contacts...");
		filter.addTextChangeListener(e -> refreshContacts(e.getText()));

		contactList.setContainerDataSource(new BeanItemContainer<>(
				Country.class));
		contactList.setColumnOrder("country","area","population","continent");
		contactList.removeColumn("id");

		contactList.setSelectionMode(Grid.SelectionMode.SINGLE);
		//contactList.addSelectionListener(e -> contactForm
		//		.edit((Continent) contactList.getSelectedRow()));
		refreshContacts();
	}

	void refreshContacts() {
		refreshContacts(filter.getValue());
		
		
	}
	
	void reloadTuples(String continentString, String populationLess,
			String populationGreater,String areaLess, String areaGreater) {
		service = CountryService.reloadService(continentString, populationLess,
				populationGreater, areaLess, areaGreater);
		refreshContacts(filter.getValue());
	}
	
	private void refreshContacts(String stringFilter) {
		contactList.setContainerDataSource(new BeanItemContainer<>(
				Country.class, service.findAll(stringFilter)));
		
	}

}
	



package ontologies.mondial.view;

import java.text.NumberFormat;
import java.util.Locale;

import ontologies.mondial.dao.Country;
import ontologies.mondial.dao.Economy;
import ontologies.mondial.services.EconomyService;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.CellReference;
import com.vaadin.ui.Grid.CellStyleGenerator;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.renderers.NumberRenderer;

public class EconomyLayout extends VerticalLayout{

	EconomySearchForm searchForm = new EconomySearchForm(this);
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
	private EconomyService service;
	
	public EconomyLayout(){
		service = EconomyService.createDemoService();
		buildLayout();
		configureComponents();
		//formatCells();
	}
	
   	public EconomyLayout(Country c){
		buildLayout();
		configureComponents();
		searchFormHide.setVisible(false);
		//formatCells();
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
		Label lblTitle = new Label("Economic data");
		lblTitle.addStyleName( "h3" );
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
				Economy.class));
		contactList.setColumnOrder("country","gdp","agriculture","service","industry","inflation","continent");
		contactList.removeColumn("id");

		contactList.setSelectionMode(Grid.SelectionMode.SINGLE);
		//contactList.addSelectionListener(e -> contactForm
		//		.edit((Continent) contactList.getSelectedRow()));
		refreshContacts();
	}

	void refreshContacts() {
		refreshContacts(filter.getValue());
		
		
	}
	
	void reloadTuples(String continentString, String gdpLess, 
			String gdpGreater, String agricultureLess, String agricultureGreater,String industryLess, 
			String industryGreater, String serviceLess, String serviceGreater, String inflationLess, 
			String inflationGreater) {
		service = EconomyService.reloadService(continentString, gdpLess, 
        		gdpGreater, agricultureLess, agricultureGreater,industryLess, industryGreater,
        		serviceLess, serviceGreater, inflationLess, inflationGreater);
		refreshContacts(filter.getValue());
	}
	
	private void refreshContacts(String stringFilter) {
		contactList.setContainerDataSource(new BeanItemContainer<>(
				Economy.class, service.findAll(stringFilter)));
		
	}
	
	 @SuppressWarnings("unused")
	private void formatCells() {
		 contactList.setCellStyleGenerator(new CellStyleGenerator() {
	            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

				@Override
	            public String getStyle(CellReference cellReference) {
	                if ("amount".equals(cellReference.getPropertyId())) {
	                    Double value = (Double) cellReference.getValue();
	                    if (value.doubleValue() == Math.round(value.doubleValue())) {
	                        return "integer";
	                    }
	                }
	                return null;
	            }
	        });

	        //getPage().getStyles().add(".integer { color: blue; }");

	        NumberFormat poundformat = NumberFormat.getCurrencyInstance(Locale.UK);
	        NumberRenderer poundRenderer = new NumberRenderer(poundformat);
	        contactList.getColumn("amount").setRenderer(poundRenderer);



	        contactList.getColumn("count").setRenderer(new HtmlRenderer());
			
		}

}
	



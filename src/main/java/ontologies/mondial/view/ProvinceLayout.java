package ontologies.mondial.view;


import ontologies.mondial.dao.Country;
import ontologies.mondial.dao.Province;
import ontologies.mondial.services.ProvinceService;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ProvinceLayout extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4679449649489092925L;
	ProvinceSearchForm searchForm = new ProvinceSearchForm(this);
	Country country = null;
	private Table list = new Table();
	private Button searchFormHide = new Button();
	private TextField filter = new TextField();
	private ProvinceService service;
	
	public ProvinceLayout(){
		service = ProvinceService.createDemoService();
		buildLayout();
		configureComponents();
	}
	
    public ProvinceLayout(Country c) {
		this.country=c;
		service = ProvinceService.reloadService(this.country.getUri(), "", "", "", "", "");
		buildLayout();
		configureComponents();

	}

	private void buildLayout() {
		GridLayout title = new GridLayout(1, 1);
		title.setMargin(false);
		title.setWidth("100%");
		Label lblTitle = new Label(
				"Provinces");
		lblTitle.addStyleName("h2");
		lblTitle.setSizeUndefined();
		title.addComponent(lblTitle, 0, 0);
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
		this.addComponent(list);
		
		this.setExpandRatio(list, 1);
		list.setSizeFull();
		
	}



	public void configureComponents() {

		searchFormHide
		.addClickListener(e -> searchForm.alternatingSearchForm());
		filter.setInputPrompt("Filter contacts...");
		filter.addTextChangeListener(e -> refreshContacts(e.getText()));

		list.setContainerDataSource(new BeanItemContainer<>(
				Province.class));
		list.setMultiSelect(false);
		list.setSizeFull();
		list.setSelectable(true);
		list.setImmediate(true);
		
		list.addShortcutListener(new ShortcutListener("Details",
				KeyCode.ARROW_RIGHT, null) {

			private static final long serialVersionUID = 1L;

			@Override
			public void handleAction(final Object sender, final Object target) {
				if (list.getValue() != null) {
					// here send event to your presenter to remove it physically
					// in database
					// and then refresh the table
					// or just call tableContainer.removeItem(itemId)
				}
			}
		});

		list.addGeneratedColumn("details", new ColumnGenerator() {
			private static final long serialVersionUID = 1L;

			@Override
			public Object generateCell(final Table source, final Object itemId,
					Object columnId) {
				Button btn = new Button("More information");
				btn.addClickListener(new ClickListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(ClickEvent event) {
//						ProvinceSubWindow sub = new ProvinceSubWindow((Province)itemId);
				        
				        // Add it to the root component
//				        UI.getCurrent().addWindow(sub);
					}
				});
				return btn;
			}
		});
		
//		contactList.addSelectionListener(e -> contactForm
//				.edit((Province) contactList.getSelectedRow()));
		refreshContacts();
	}

	void refreshContacts() {
		refreshContacts(filter.getValue());
	}

	private void refreshContacts(String stringFilter) {
		list.setContainerDataSource(new BeanItemContainer<>(
				Province.class, service.findAll(stringFilter)));
		list.setVisibleColumns("country", "province", "city", "population", "area", "details");
	}
	
	void reloadTuples(String countryString, String populationLess,
			String populationGreater, String areaLess, String areaGreater) {
		if (this.country != null)
			service = ProvinceService.reloadService(this.country.getUri(), countryString, populationLess,
					populationGreater, areaLess, areaGreater);
		else
			service = ProvinceService.reloadService("", countryString, populationLess,
				populationGreater, areaLess, areaGreater);
		refreshContacts(filter.getValue());
	}
	
	public Button getSearchFormHide() {
		return searchFormHide;
	}

	public void setSearchFormHide(Button searchFormHide) {
		this.searchFormHide = searchFormHide;
	}

}

package ontologies.mondial.view;

import ontologies.mondial.dao.Country;
import ontologies.mondial.dao.Religion;
import ontologies.mondial.services.CountryService;
import ontologies.mondial.services.ReligionService;

import com.vaadin.data.util.BeanItem;
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
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ReligionLayout extends VerticalLayout {

	ReligionSearchForm searchForm = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = -4679449649489092925L;

	/**
	 * 
	 */
	private Table list = new Table() {

		private static final long serialVersionUID = 1L;

		@Override
		public Align getColumnAlignment(Object propertyId) {
			Class<?> t = getContainerDataSource().getType(propertyId);
			if (t == Double.class)
				return Table.Align.RIGHT;

			if (t == Integer.class)
				return Table.Align.RIGHT;

			return super.getColumnAlignment(propertyId);
		}
	};
	private Button searchFormHide = new Button();
	private TextField filter = new TextField();
	private ReligionService service;
	private Country country;

	// for table formating
	String PERCENT_PROPERTY = "%";

	public ReligionLayout() {
		service = ReligionService.createDemoService();
		buildLayout();
		configureComponents();
		this.country = null;
	}

	public ReligionLayout(Country c) {
		this.country = c;
		service = ReligionService.reloadService(this.country.getUri(), "", "", "", "");
		buildLayout();
		configureComponents();
		searchFormHide.setVisible(false);
		// formatCells();
	}

	public Button getSearchFormHide() {
		return searchFormHide;
	}

	public void setSearchFormHide(Button searchFormHide) {
		this.searchFormHide = searchFormHide;
	}

	private void buildLayout() {
		searchForm = new ReligionSearchForm(this);

		GridLayout title = new GridLayout(1, 1);
		title.setMargin(false);
		title.setWidth("100%");
		Label lblTitle = new Label("Religious groups");
		
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

		list.setContainerDataSource(new BeanItemContainer<>(Religion.class));
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
					CountrySubWindow sub = new CountrySubWindow((Country)list.getValue());
			        
			        // Add it to the root component
			        UI.getCurrent().addWindow(sub);
				}
			}
		});
		//click on Enter
		list.addShortcutListener(new ShortcutListener("Default key",
				KeyCode.ENTER, null) {

			private static final long serialVersionUID = 1L;

			@Override
			public void handleAction(final Object sender, final Object target) {
				if (list.getValue() != null) {
					CountrySubWindow sub = new CountrySubWindow((Country)list.getValue());
			        
			        // Add it to the root component
			        UI.getCurrent().addWindow(sub);
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
						BeanItem<?> item = (BeanItem<?>) source.getItem(itemId);
						CountryService countryService = CountryService.reloadService(((Religion)item.getBean()).getCountryUri(), "", "","", "", "");
						Country c = (Country)countryService.getFirst();
						CountrySubWindow sub = new CountrySubWindow(c);
				        
				        // Add it to the root component
				        UI.getCurrent().addWindow(sub);
					}
				});
				return btn;
			}
		});
		// contactList.addSelectionListener(e -> contactForm
		// .edit((Continent) contactList.getSelectedRow()));
		refreshContacts();
	}

	void refreshContacts() {
		refreshContacts(filter.getValue());

	}

	void reloadTuples(String countryString, String religionName,
			String percentageLess, String percentageGreater) {
		if (this.country != null)
			service = ReligionService.reloadService(this.country.getUri(),
					countryString, religionName, percentageLess, percentageGreater);
		else
			service = ReligionService.reloadService("",countryString, religionName, percentageLess, percentageGreater);
		refreshContacts(filter.getValue());
	}

	private void refreshContacts(String stringFilter) {
		list.setContainerDataSource(new BeanItemContainer<>(Religion.class,
				service.findAll(stringFilter)));
		list.setVisibleColumns("country", "name", "percentage", "details");
		if (this.country!=null)
			list.setVisibleColumns("country", "name", "percentage");

	}

}

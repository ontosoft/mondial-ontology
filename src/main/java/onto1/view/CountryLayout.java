package onto1.view;

import onto1.MainSplitPannel;
import onto1.NavigatorTree;
import onto1.dao.Country;
import onto1.services.CountryService;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class CountryLayout extends VerticalLayout {

	SearchForm searchForm = new SearchForm(this);
	CountryDetails details = new CountryDetails();
	
	private static final long serialVersionUID = -4679449649489092925L;

	private Table contactList = new Table();
	private Button searchFormHide = new Button();
	private TextField filter = new TextField();
	private CountryService service = CountryService.createDemoService();

	// FooterRow footer = contactList.appendFooterRow();

	public CountryLayout() {
		buildLayout();
		configureComponents();
	}

	public Table getContactList() {
		return contactList;
	}

	public void setContactList(Table contactList) {
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
		GridLayout title = new GridLayout(1, 1);
		title.setMargin(false);
		title.setWidth("100%");
		Label lblTitle = new Label(
				"General data about countries within continent");
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
		this.addComponent(contactList);

		this.setExpandRatio(contactList, 1);
		contactList.setSizeFull();

	}

	public void configureComponents() {

		searchFormHide
				.addClickListener(e -> searchForm.alternatingSearchForm());
		filter.setInputPrompt("Filter contacts...");
		filter.addTextChangeListener(e -> refreshContacts(e.getText()));

		contactList.setContainerDataSource(new BeanItemContainer<>(
				Country.class));

		contactList.setVisibleColumns(new Object[] { "country", "area",
				"population", "continent" });
		contactList.setMultiSelect(false);
		contactList.setSizeFull();
		contactList.setSelectable(true);
		contactList.setImmediate(true);
 
		contactList.addShortcutListener(new ShortcutListener("Delete",
				KeyCode.ARROW_RIGHT, null) {

			private static final long serialVersionUID = 1L;

			@Override
			public void handleAction(final Object sender, final Object target) {
				if (contactList.getValue() != null) {
					// here send event to your presenter to remove it physically
					// in database
					// and then refresh the table
					// or just call tableContainer.removeItem(itemId)
				}
			}
		});

		contactList.addGeneratedColumn("Action", new ColumnGenerator() {
			private static final long serialVersionUID = 1L;

			@Override
			public Object generateCell(final Table source, final Object itemId,
					Object columnId) {
				Button btn = new Button("More information");
				btn.addClickListener(new ClickListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(ClickEvent event) {
						CountrySubWindow sub = new CountrySubWindow();
				        
				        // Add it to the root component
				        UI.getCurrent().addWindow(sub);
					}
				});
				return btn;
			}
		});

		refreshContacts();
	}

	void refreshContacts() {
		refreshContacts(filter.getValue());

	}

	void reloadTuples(String continentString, String populationLess,
			String populationGreater, String areaLess, String areaGreater) {
		service = CountryService.reloadService(continentString, populationLess,
				populationGreater, areaLess, areaGreater);
		refreshContacts(filter.getValue());
	}

	private void refreshContacts(String stringFilter) {
		contactList.setContainerDataSource(new BeanItemContainer<>(
				Country.class, service.findAll(stringFilter)));
		// footer.getCell("continent").setText("Number of items: "+
		// service.findAll(stringFilter).size());
	}
	
}

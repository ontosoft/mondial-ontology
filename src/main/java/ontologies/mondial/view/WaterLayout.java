package ontologies.mondial.view;


import ontologies.mondial.dao.Country;
import ontologies.mondial.dao.Province;
import ontologies.mondial.dao.River;
import ontologies.mondial.dao.Water;
import ontologies.mondial.services.RiverService;
import ontologies.mondial.services.WaterService;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
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

public class WaterLayout extends VerticalLayout {

	WaterSearchForm searchForm = null;
	
	private static final long serialVersionUID = -4679449649489092925L;

	private Table list = new Table();
	private Button searchFormHide = new Button();
	private TextField filter = new TextField();
	private WaterService service;

	private Country country = null;
	private Province province = null;
	private River river = null;
	

	public WaterLayout() {
		service = WaterService.createDemoService();
		buildLayout();
		configureComponents();
		this.country = null;
		this.province = null;
		this.setRiver(null);
	}
	
	public WaterLayout(Country c) {
		this.country = c;
		service = WaterService.reloadService("", this.country.getUri(), "", "",
				"", "", "");
		buildLayout();
		configureComponents();
		this.province = null;
		this.setRiver(null);
	}
	//Water elements in which this River flows to 
	public WaterLayout(River r) {
		this.setRiver(r);
		service = WaterService.reloadByRiverDestination(this.river.getUri());
		buildLayout();
		configureComponents();
		this.province = null;
		this.country = null;
	}

	public Table getContactList() {
		return list;
	}

	public void setContactList(Table contactList) {
		this.list = contactList;
	}


	public Button getSearchFormHide() {
		return searchFormHide;
	}

	public void setSearchFormHide(Button searchFormHide) {
		this.searchFormHide = searchFormHide;
	}

	private void buildLayout() {
		searchForm =  new WaterSearchForm(this);
		
		GridLayout title = new GridLayout(1, 1);
		title.setMargin(false);
		title.setWidth("100%");
		Label lblTitle = new Label(
				"Hydrology elements in provinces");
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
				Water.class));

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
					openWaterInSubWindow((Water)list.getValue());
					
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
					openWaterInSubWindow((Water)list.getValue());
				}
			}
		});
		
		list.addItemClickListener(new ItemClickListener() {
			private static final long serialVersionUID = 1L;


			@Override
			public void itemClick(ItemClickEvent event) {
                if (event.isDoubleClick()){
                	BeanItem<?> item = (BeanItem<?>) event.getItem();
                	openWaterInSubWindow((Water)item.getBean());
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
						openWaterInSubWindow((Water)item.getBean());
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

	void reloadTuples(String countryString, String provinceString, String nameString, String typeString) {
		if (this.country != null)
			service = WaterService.reloadService("", this.country.getUri(), "",countryString, provinceString, nameString, typeString);
		else if(this.province !=null)
			service = WaterService.reloadService("", "" , this.province.getUri(),countryString, provinceString, nameString, typeString);
		else
			service = WaterService.reloadService("", "" , "",countryString, provinceString, nameString, typeString);
		refreshContacts(filter.getValue());
	}

	private void refreshContacts(String stringFilter) {
		list.setContainerDataSource(new BeanItemContainer<>(
				Water.class, service.findAll(stringFilter)));
		// footer.getCell("continent").setText("Number of items: "+
		// service.findAll(stringFilter).size());
		list.setVisibleColumns( "province", "name", "type","details");
		Object [] properties={"type", "name"};
		boolean [] ordering={true,true};
		list.sort(properties, ordering);
	}
	//Opens selected water element in new SubWindow 
	private void openWaterInSubWindow(Water w){
		//First gethering information of the selected river
		if (w.getType().equals("River")){
			RiverService riverService = RiverService.retrieveSecificRiver(w.getUri());
			River r = (River)riverService.getFirst();
			RiverSubWindow sub = new RiverSubWindow(r);

		    UI.getCurrent().addWindow(sub);
		}
		
	}
	
	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public River getRiver() {
		return river;
	}

	public void setRiver(River river) {
		this.river = river;
	}
	
}

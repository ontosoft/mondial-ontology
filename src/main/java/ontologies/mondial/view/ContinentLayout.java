package ontologies.mondial.view;


import ontologies.mondial.dao.Continent;
import ontologies.mondial.services.ContinentService;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ContinentLayout extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4679449649489092925L;

	/**
	 * 
	 */
	private Grid contactList = new Grid();
	private Button newContact = new Button();
	private TextField filter = new TextField();
	private ContinentService service = ContinentService.createDemoService();
	
	public ContinentLayout(){
		buildLayout();
		configureComponents();
	}
	
    public void onSubmitted(String value) {
        // to be implemented
    }

	private void buildLayout() {
		GridLayout title = new GridLayout(1, 1);
		title.setMargin(false);
		title.setWidth("100%");
		Label lblTitle = new Label("Continent");
		lblTitle.addStyleName("h2");
		lblTitle.setSizeUndefined();
		title.addComponent(lblTitle, 0, 0);
		title.setComponentAlignment(lblTitle, Alignment.MIDDLE_CENTER);
		this.addComponent(title);
		
		HorizontalLayout actions = new HorizontalLayout(filter, newContact);
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

		//newContact.addClickListener(e -> contactForm.edit(new Country()));
		filter.setInputPrompt("Filter contacts...");
		filter.addTextChangeListener(e -> refreshContacts(e.getText()));

		contactList.setContainerDataSource(new BeanItemContainer<>(
				Continent.class));
		contactList.setColumnOrder("name","area");
		contactList.removeColumn("id");
		contactList.setSelectionMode(Grid.SelectionMode.SINGLE);
		//contactList.addSelectionListener(e -> contactForm
		//		.edit((Country) contactList.getSelectedRow()));
		refreshContacts();
	}

	void refreshContacts() {
		refreshContacts(filter.getValue());

	}

	private void refreshContacts(String stringFilter) {
		contactList.setContainerDataSource(new BeanItemContainer<>(
				Continent.class, service.findAll(stringFilter)));
		
	}

}
	



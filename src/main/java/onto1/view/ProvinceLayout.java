package onto1.view;

import onto1.dao.Province;
import onto1.services.ProvinceService;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ProvinceLayout extends VerticalLayout{

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
	//private ContactForm contactForm = new ContactForm();
	private ProvinceService service = ProvinceService.createDemoService();
	
	public ProvinceLayout(){
		buildLayout();
		configureComponents();
	}
	
    public void onSubmitted(String value) {
        // to be implemented
    }

	private void buildLayout() {
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

		//newContact.addClickListener(e -> contactForm.edit(new Province()));
		filter.setInputPrompt("Filter contacts...");
		filter.addTextChangeListener(e -> refreshContacts(e.getText()));

		contactList.setContainerDataSource(new BeanItemContainer<>(
				Province.class));
		contactList.setColumnOrder("country", "province","city");
		contactList.removeColumn("id");
		contactList.setSelectionMode(Grid.SelectionMode.SINGLE);
//		contactList.addSelectionListener(e -> contactForm
//				.edit((Province) contactList.getSelectedRow()));
		refreshContacts();
	}

	void refreshContacts() {
		refreshContacts(filter.getValue());

	}

	private void refreshContacts(String stringFilter) {
		contactList.setContainerDataSource(new BeanItemContainer<>(
				Province.class, service.findAll(stringFilter)));
		//contactForm.setVisible(false);
	}

}
	



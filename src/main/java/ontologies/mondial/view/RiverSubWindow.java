package ontologies.mondial.view;


import ontologies.mondial.dao.River;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class RiverSubWindow extends Window {
	 /**
	 * Sub windows for Country Details
	 */
	private static final long serialVersionUID = 1L;

	public RiverSubWindow(River river) {
	        super(river.getName() + " - river details"); // Set window caption
	        center();
	        buildCountryDetailsLayout(river);
	        setWidth("75%");
	        setHeight("70%");
	}
	private void buildCountryDetailsLayout(River river) {

		GridLayout data = new GridLayout(2,4);
		data.setMargin(true);
		data.setSpacing(true);
		Label lblTitle = new Label("River details");
		lblTitle.setStyleName("center1");
		lblTitle.setStyleName("h2");
		data.addComponent(lblTitle,0,0,1,0);
		TextField txtName = new TextField("Name");
		txtName.setValue(river.getName());		
		data.addComponent(txtName,0,1);
		TextField txtLength = new TextField("Length");
		txtLength.setValue(river.getLength() == null ? "": ((river.getLength()).toString()));
		data.addComponent(txtLength,1,1);
		TextField txtSource = new TextField("Source Coordinates");
		txtSource.setValue(river.getSource());		
		data.addComponent(txtSource,0,2);
		TextField txtEstuary = new TextField("Estuary Coordinates");
		txtEstuary.setValue(river.getSource());		
		data.addComponent(txtEstuary,1,2);
		
		HorizontalLayout empty = new HorizontalLayout();
		VerticalLayout right = new VerticalLayout();
		right.addComponent(empty);
		right.setSizeFull();
		
		RiverDetailsNavigatorTree navTree = new RiverDetailsNavigatorTree();
		navTree.setSizeFull();

		RiverDetails vsplit = new RiverDetails(river);
		vsplit.setSplitPosition(12);
		vsplit.setSizeFull();
		vsplit.setFirstComponent(navTree);
		vsplit.setSecondComponent(right);
		
		navTree.addListener(vsplit);		
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addComponent(data);
		mainLayout.addComponent(vsplit);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(vsplit, 1);
		mainLayout.setStyleName("grayback");
		setContent(mainLayout);

	}
}

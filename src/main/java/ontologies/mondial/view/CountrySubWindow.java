package ontologies.mondial.view;


import ontologies.mondial.dao.Country;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class CountrySubWindow extends Window {
	 /**
	 * Sub windows for Country Details
	 */
	private static final long serialVersionUID = 1L;

	public CountrySubWindow(Country country) {
	        super(country.getCountry() + " - country details"); // Set window caption
	        center();
	        buildCountryDetailsLayout(country);
	        setWidth("80%");
	        setHeight("80%");
	}
	private void buildCountryDetailsLayout(Country country) {

		HorizontalLayout empty = new HorizontalLayout();
		VerticalLayout right = new VerticalLayout();
		right.addComponent(empty);
		right.setSizeFull();
		
		CountryDetailsNavigatorTree navTree = new CountryDetailsNavigatorTree();
		navTree.setSizeFull();

		CountryDetails vsplit = new CountryDetails(country);
		vsplit.setSplitPosition(12);
		vsplit.setSizeFull();
		vsplit.setFirstComponent(navTree);
		vsplit.setSecondComponent(right);
		
		navTree.addListener(vsplit);		
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addComponent(vsplit);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(vsplit, 1);
		
		setContent(mainLayout);

	}
}

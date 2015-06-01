package onto1.view;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class CountrySubWindow extends Window {
	 /**
	 * Sub windows for Country Details
	 */
	private static final long serialVersionUID = 1L;

	public CountrySubWindow() {
	        super("Country Details"); // Set window caption
	        center();
	        buildCountryDetailsLayout();
	      
	    }
	private void buildCountryDetailsLayout() {

		HorizontalLayout empty = new HorizontalLayout();
		empty.setWidth("100%");
		VerticalLayout right = new VerticalLayout();
		right.addComponent(empty);
		right.setSizeFull();
		
		CountryDetailsNavigatorTree navTree = new CountryDetailsNavigatorTree();

		CountryDetails vsplit = new CountryDetails();
		vsplit.setSplitPosition(15);
		vsplit.setSizeFull();
		vsplit.setFirstComponent(navTree);
		vsplit.setSecondComponent(right);
		
		navTree.addListener(vsplit);
		
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addComponent(navTree);
		
		setContent(mainLayout);

	}
}

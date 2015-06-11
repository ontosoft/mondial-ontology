package ontologies.mondial;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

public class NavigatorTree extends VerticalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tree t;

	private List<ValueSubmittedListener> listeners = new ArrayList<ValueSubmittedListener>();

	public void addListener(ValueSubmittedListener listener) {
		listeners.add(listener);
	}

	private void notifyListeners(String s) {
		for (ValueSubmittedListener listener : listeners) {
			listener.onSubmitted(s);
		}
	}

	public NavigatorTree() {
		// TODO Auto-generated constructor stub
		
		this.setHeight(null);
		this.setWidth(null);
		
		
		
		t = new Tree();
		t.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("deprecation")
			public void itemClick(ItemClickEvent event) {
				// Pick only left mouse clicks
				if (event.getButton() == ItemClickEvent.BUTTON_LEFT)
					Notification.show(event.getItemId().toString(),
							Notification.Type.TRAY_NOTIFICATION);
				switch (event.getItemId().toString()) {
				case "Provinces":
					notifyListeners("Provinces");
					break;
				case "Countries":
					notifyListeners("Countries");
					
					break;
				case "Economic data":
					notifyListeners("Economic data");

					break;
				case "Continents":
					notifyListeners("Continents");
					break;
				default:
					break;
				}

			}
		});
		t.setSizeFull();
		t.setImmediate(true);

		t.addItem("General");
		t.addItem("Economy");
		t.addItem("Economy");
		t.addItem("Advanced queries");
		t.addItem("Countries");
		t.addItem("Continents");
		t.addItem("Provinces");
		t.addItem("Economic data");

		// Set the hierarchy
		t.setParent("Continents", "General");
		t.setParent("Countries", "General");
		t.setParent("Provinces", "General");
		t.setParent("Economic data","Economy");
		

		t.setChildrenAllowed("Country", false);
		t.setChildrenAllowed("Continent", false);
		t.setChildrenAllowed("Provinces", false);
		t.setChildrenAllowed("Economic data", false);
		
		this.addComponent(t);

	}

}

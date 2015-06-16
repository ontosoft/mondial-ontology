package ontologies.mondial;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

public class NavigatorTree extends VerticalLayout {
	private static final String ADVANCED_QUERIES = "Advanced queries";
	private static final String SOCIETY = "Society";
	private static final String ECONOMY = "Economy";
	private static final String GENERAL = "General";
	private static final String CONTINENTS = "Continents";
	private static final String RELIGION = "Religion";
	private static final String ECONOMIC_DATA = "Economic data";
	private static final String COUNTRIES = "Countries";
	private static final String PROVINCES = "Provinces";
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
				case PROVINCES:
					notifyListeners(PROVINCES);
					break;
				case COUNTRIES:
					notifyListeners(COUNTRIES);
					
					break;
				case ECONOMIC_DATA:
					notifyListeners(ECONOMIC_DATA);

					break;
				case RELIGION:
					notifyListeners(RELIGION);

					break;
				case CONTINENTS:
					notifyListeners(CONTINENTS);
					break;
				default:
					break;
				}

			}
		});
		t.setSizeFull();
		t.setImmediate(true);

		t.addItem(GENERAL);
		t.addItem(ECONOMY);
		t.addItem(SOCIETY);
		t.addItem(ADVANCED_QUERIES);
		t.addItem(COUNTRIES);
		t.addItem(CONTINENTS);
		t.addItem(PROVINCES);
		t.addItem(ECONOMIC_DATA);
		t.addItem(RELIGION);

		// Set the hierarchy
		t.setParent(CONTINENTS, GENERAL);
		t.setParent(COUNTRIES, GENERAL);
		t.setParent(PROVINCES, GENERAL);
		t.setParent(ECONOMIC_DATA,ECONOMY);
		t.setParent(RELIGION, SOCIETY);

		t.setChildrenAllowed(COUNTRIES, false);
		t.setChildrenAllowed(CONTINENTS, false);
		t.setChildrenAllowed(PROVINCES, false);
		t.setChildrenAllowed(ECONOMIC_DATA, false);
		t.setChildrenAllowed(RELIGION, false);
		
		this.addComponent(t);

	}

}

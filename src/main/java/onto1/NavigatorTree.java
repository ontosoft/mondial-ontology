package onto1;

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
				case "Provinces1":
					notifyListeners("Province1");
					Notification.show("Chosen",
							Notification.Type.TRAY_NOTIFICATION);
					break;
				case "Country":
					notifyListeners("Country");
					Notification.show("Chosen",
							Notification.Type.TRAY_NOTIFICATION);
					break;
				case "Continent":
					notifyListeners("Continent");
					break;
				default:
					break;
				}

			}
		});
		t.setSizeFull();
		t.setImmediate(true);

		t.addItem("Basic queries");
		t.addItem("Advanced queries");
		t.addItem("Country");
		t.addItem("Continent");
		t.addItem("Provinces1");

		// Set the hierarchy
		t.setParent("Country", "Basic queries");
		t.setParent("Continent", "Basic queries");
		t.setParent("Provinces1", "Advanced queries");

		t.setChildrenAllowed("Country", false);
		t.setChildrenAllowed("Continent", false);
		t.setChildrenAllowed("Provinces1", false);
		
		this.addComponent(t);

	}

}

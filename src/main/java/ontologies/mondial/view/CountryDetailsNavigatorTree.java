package ontologies.mondial.view;

import java.util.ArrayList;
import java.util.List;

import ontologies.mondial.ValueSubmittedListener;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

public class CountryDetailsNavigatorTree extends VerticalLayout {
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

	public CountryDetailsNavigatorTree() {
		// TODO Auto-generated constructor stub
		
		this.setSizeUndefined();
		this.setHeight("100%");
		
		
		
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
				case "Hidrology":
					notifyListeners("Hidrology");
					
					break;
				case "Geo-morphology":
					notifyListeners("Geo-morphology");

					break;
				case "Economy":
					notifyListeners("Economy");
					break;
				default:
					break;
				}

			}
		});
		t.setSizeFull();
		t.setImmediate(true);

		t.addItem("Provinces");
		t.addItem("Hidrology");
		t.addItem("Geo-morphology");
		t.addItem("Economy");


		t.setChildrenAllowed("Provinces", false);
		t.setChildrenAllowed("Hidrology", false);
		t.setChildrenAllowed("Geo-morphology", false);
		t.setChildrenAllowed("Economy", false);
		
		this.addComponent(t);

	}

}

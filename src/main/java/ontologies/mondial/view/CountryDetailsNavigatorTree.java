package ontologies.mondial.view;

import java.util.ArrayList;
import java.util.List;

import ontologies.mondial.ValueSubmittedListener;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

public class CountryDetailsNavigatorTree extends VerticalLayout {
	private static final String RELIGION = "Religion";
	private static final String ECONOMY = "Economy";
	private static final String GEO_MORPHOLOGY = "Geo-morphology";
	private static final String HIDROLOGY = "Hidrology";
	private static final String PROVINCES = "Provinces";
	private static final String BORDERS = "Borders";
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
				case PROVINCES:
					notifyListeners(PROVINCES);
					break;
				case HIDROLOGY:
					notifyListeners(HIDROLOGY);
					
					break;
				case GEO_MORPHOLOGY:
					notifyListeners(GEO_MORPHOLOGY);

					break;
				case BORDERS:
					notifyListeners(BORDERS);

					break;
				case ECONOMY:
					notifyListeners(ECONOMY);
					break;
				case RELIGION:
					notifyListeners(RELIGION);
					break;	
					
				default:
					break;
				}

			}
		});
		t.setSizeFull();
		t.setImmediate(true);

		t.addItem(PROVINCES);
		t.addItem(HIDROLOGY);
		t.addItem(GEO_MORPHOLOGY);
		t.addItem(BORDERS);
		t.addItem(ECONOMY);
		t.addItem(RELIGION);


		t.setChildrenAllowed(PROVINCES, false);
		t.setChildrenAllowed(HIDROLOGY, false);
		t.setChildrenAllowed(GEO_MORPHOLOGY, false);
		t.setChildrenAllowed(BORDERS, false);
		t.setChildrenAllowed(ECONOMY, false);
		t.setChildrenAllowed(RELIGION, false);
		
		this.addComponent(t);
		this.addStyleName("roseback");
	}

}

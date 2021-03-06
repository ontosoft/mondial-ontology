package ontologies.mondial.view;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ProvinceSearchForm extends VerticalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 851894446243275846L;
	Button search = new Button("Search", this::search);
	Button cancel = new Button("Clean", this::cancel);
	TextField populationLess = new TextField("Population less than");
	TextField populationGreater = new TextField("Population greater than");
	TextField areaLess = new TextField("Area less than (km²)");
	TextField areaGreater = new TextField("Area greater than (km²)");
	TextField countryString = new TextField("Country");

	ProvinceLayout parentForm = null;

	public ProvinceSearchForm(ProvinceLayout p) {
		parentForm = p;
		configureComponents();
		buildLayout();
		if (p.getCountry() != null) 
				countryString.setVisible(false);
		else
			countryString.setVisible(true);
		if (p.getRiver() != null) 
			alternatingSearchForm();
		
	}

	private void buildLayout() {
		setSizeUndefined();
		setMargin(true);
		// HorizontalLayout actions = new HorizontalLayout(search,cancel);
		// actions.setSpacing(true);
		GridLayout searchFields = new GridLayout(5, 2);
		searchFields.addComponent(populationGreater, 0, 0);
		populationGreater.addStyleName("numerical");
		searchFields.addComponent(populationLess, 1, 0);
		populationLess.addStyleName("numerical");
		searchFields.addComponent(areaGreater, 2, 0);
		areaGreater.addStyleName("numerical");
		searchFields.addComponent(areaLess, 3, 0);
		areaLess.addStyleName("numerical");
		searchFields.addComponent(countryString, 4, 0);

		searchFields.addComponent(search, 0, 1);
		searchFields.addComponent(cancel, 1, 1);

		searchFields.setSpacing(true);
		addComponents(searchFields);
		// populationLess.addValidator(new IndtegerRangeValidator(
		// "The value must be integer between 0-120 (was {0})",
		// 0, 120));

	}

	private void configureComponents() {

		search.setStyleName(ValoTheme.BUTTON_PRIMARY);
		search.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		setVisible(true);

	}

	public void search(Button.ClickEvent event) {

		parentForm.reloadTuples(countryString.getValue(), populationLess
				.getValue().toString(),
				populationGreater.getValue().toString(), areaLess.getValue()
						.toString(), areaGreater.getValue().toString());

	}

	// Making form visible or invisible -changing state hide, unhide
	public void alternatingSearchForm() {
		if (this.isVisible()) {
			this.setVisible(false);
			parentForm.getSearchFormHide().setIcon(
					new ThemeResource("../../icons/down.svg"));
		} else {
			this.setVisible(true);
			parentForm.getSearchFormHide().setIcon(
					new ThemeResource("../../icons/up.svg"));
		}

	}

	public void cancel(Button.ClickEvent event) {
		countryString.setValue("");
		populationLess.setValue("");
		populationGreater.setValue("");
		areaLess.setValue("");
		areaGreater.setValue("");
	}

}

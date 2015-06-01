package onto1;

import java.util.Arrays;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.client.ui.menubar.MenuItem;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 *3
 */
@SuppressWarnings("deprecation")
@Title("OntoUI")
@Theme("reindeer")
@Widgetset("onto1.MyAppWidgetset")
public class MyUI extends UI {

	
	/**
	 * 
	 */
	private String[] themes = { "valo", "reindeer", "runo", "chameleon" };
	
	private static final long serialVersionUID = -3273531387190391203L;
	private  final VerticalLayout right = new VerticalLayout();
	private MenuBar mainMenu;
	ComboBox themePicker = new ComboBox("Theme", Arrays.asList(themes));
	
	@Override
	protected void init(VaadinRequest vaadinRequest) {

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		buildLayout();
		
	}

	private ComboBox getThemePicking(){
        themePicker.setValue(getTheme());
        themePicker.setNullSelectionAllowed(false);
        themePicker.setTextInputAllowed(false);
        themePicker.setSizeUndefined();
        themePicker.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			@Override
            public void valueChange(ValueChangeEvent event) {
                String theme = (String) event.getProperty().getValue();
                setTheme(theme);
            }
        });
        return themePicker;
	}

	private void buildLayout() {

		HorizontalLayout empty = new HorizontalLayout();
		empty.setWidth("100%");
		
		right.addComponent(empty);
		right.setSizeFull();
		
		NavigatorTree navTree = new NavigatorTree();

		MainSplitPannel vsplit = new MainSplitPannel();
		vsplit.setSplitPosition(18);
		vsplit.setSizeFull();
		vsplit.setFirstComponent(navTree);
		vsplit.setSecondComponent(right);
		
		navTree.addListener(vsplit);
		
		mainMenu = menu();
		FormLayout themeLayout = new FormLayout(getThemePicking());
		themeLayout.setSizeUndefined();
		themeLayout.setMargin(false);
		
		HorizontalLayout menuLayout = new HorizontalLayout(mainMenu,themeLayout);
		menuLayout.setWidth("100%");
		menuLayout.setComponentAlignment(themeLayout, Alignment.MIDDLE_RIGHT);
		menuLayout.setExpandRatio(mainMenu, 0);
		menuLayout.setMargin(false);

		
		//menuLayout.addComponent(themeLayout);
		menuLayout.setWidth("100%");
		
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.setMargin(false);
		//mainLayout.setSpacing(true);
		mainLayout.addComponent(menuLayout);
		mainLayout.addComponent(vsplit);
		mainLayout.setExpandRatio(vsplit, 1);
		
		setContent(mainLayout);

	}



	public MenuBar menu() {
		MenuBar barMenu = new MenuBar();
		// A top-level menu item that opens a submenu
		com.vaadin.ui.MenuBar.MenuItem file = barMenu.addItem("File", null, null);
		com.vaadin.ui.MenuBar.MenuItem help = barMenu.addItem("Help", null, null);
		return barMenu;
	}

	void refreshContacts() {
	
	}


	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}

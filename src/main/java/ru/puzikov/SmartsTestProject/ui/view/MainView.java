package ru.puzikov.SmartsTestProject.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import ru.puzikov.SmartsTestProject.backend.entity.Bank;
import ru.puzikov.SmartsTestProject.backend.service.BankService;
import ru.puzikov.SmartsTestProject.ui.form.BankForm;

@Route("")
public class MainView extends VerticalLayout {

    BankForm form;
    Grid<Bank> grid = new Grid<>(Bank.class);
    Label label;

    private final BankService bankService;
    private String selectedBankName;

    public MainView(BankService bankService) {
        this.bankService = bankService;

        addClassName("banks-view");
        setSizeFull();
        configureGrid();
        configureLabel();

        form = new BankForm(bankService.findAll());
        form.addListener(BankForm.SaveEvent.class, this::saveBank);
        form.addListener(BankForm.DeleteEvent.class, this::deleteBank);
        form.addListener(BankForm.CloseEvent.class, e -> closeEditor());


        VerticalLayout gridLayout = new VerticalLayout(grid);
        gridLayout.setWidth("500px");
        gridLayout.setHeight("500px");
        VerticalLayout formLayout = new VerticalLayout(form);
        formLayout.setWidth("300px");
        formLayout.setHeight("500px");

        HorizontalLayout content = new HorizontalLayout(gridLayout, formLayout);

        add(label, getToolBar(), content);
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassName("bank-grid");
        grid.setSizeFull();
        grid.setColumns("bankName");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.addItemDoubleClickListener(event -> editBank(event.getItem()));
    }

    private void configureLabel() {
        String text = "Welcome to our loan calculator app. " +
                "On this page you can see the list of available banks. " +
                "You can also add a new bank or edit an existing one. " +
                "To edit a bank, double-click on the bank you want to edit.";
        label = new Label(text);
    }


    private void deleteBank(BankForm.DeleteEvent event) {
        bankService.delete(event.getBank());
        updateList();
        closeEditor();
    }

    private void saveBank(BankForm.SaveEvent event) {
        bankService.save(event.getBank());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        Button addButton = new Button("Add new Bank", new Icon(VaadinIcon.PLUS),
                click -> addBank());

        Button goToButton = new Button("Go to the selected bank", new Icon(VaadinIcon.ARROW_RIGHT));
        goToButton.setIconAfterText(true);
        goToButton.setEnabled(false);

        grid.addSelectionListener(selectionEvent -> {
            if (!grid.asSingleSelect().isEmpty()) {
                goToButton.setEnabled(true);

                selectedBankName = grid.asSingleSelect().getValue().getBankName();
            } else {
                goToButton.setEnabled(false);
            }
        });

        goToButton.addClickListener(click ->
                goToButton
                        .getUI()
                        .ifPresent(ui -> ui.navigate(BankView.class, selectedBankName)));


        HorizontalLayout toolbar = new HorizontalLayout(addButton, goToButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }


    private void addBank() {
        grid.asSingleSelect().clear();
        editBank(new Bank());
    }


    private void editBank(Bank bank) {
        if (bank == null) {
            closeEditor();
        } else {
            form.setBank(bank);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setBank(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(bankService.findAll());
    }

}

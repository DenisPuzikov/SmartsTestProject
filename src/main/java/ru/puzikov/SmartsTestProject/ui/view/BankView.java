package ru.puzikov.SmartsTestProject.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import ru.puzikov.SmartsTestProject.backend.service.BankService;
import ru.puzikov.SmartsTestProject.backend.service.ClientService;
import ru.puzikov.SmartsTestProject.backend.service.CreditService;
import ru.puzikov.SmartsTestProject.ui.view.layout.ClientLayout;
import ru.puzikov.SmartsTestProject.ui.view.layout.CreditLayout;

@Route("bank")
public class BankView extends Div implements HasUrlParameter<String> {

    private final HorizontalLayout topPanel = new HorizontalLayout();
    private final HorizontalLayout contentPanel = new HorizontalLayout();

    private final ClientService clientService;
    private final CreditService creditService;
    private final BankService bankService;

    private Long currentBankId;
    private String currentBankName;

    public BankView(ClientService clientService, CreditService creditService, BankService bankService) {
        this.clientService = clientService;
        this.creditService = creditService;
        this.bankService = bankService;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String parameter) {
        currentBankName = parameter;
        currentBankId = bankService.findByBankName(currentBankName).getId();

        addClassName("current-bank-view");

        configureTopPanel();
        configureContentPanel();

        add(topPanel, contentPanel);
    }

    private void configureContentPanel() {
        VerticalLayout clientLayout = new ClientLayout(clientService, bankService, currentBankName);
        VerticalLayout creditLayout = new CreditLayout(creditService, bankService, currentBankId);

        contentPanel.addAndExpand(clientLayout, creditLayout);
    }

    private void configureTopPanel() {
        Label greeting = new Label("Welcome on page of " + "\"" + currentBankName + "\"");
        String text = "In tables below you can see information about clients and credits of this bank. " +
                "If you want to edit or delete client, double-click on the line with this client. " +
                "You will not be able to delete a client if that client has an active loan.";
        Label info = new Label(text);

        Button returnButton = new Button("Bank selection page ", new Icon(VaadinIcon.HOME));
        returnButton.addClickListener(click -> returnButton.getUI().ifPresent(ui ->
                ui.navigate(MainView.class)));

        Button goToOfferButton = new Button("To credit offers ", new Icon(VaadinIcon.ARROW_RIGHT));
        goToOfferButton.setIconAfterText(true);
        goToOfferButton.addClickListener(click -> goToOfferButton.getUI().ifPresent(ui ->
                ui.navigate(OfferView.class, currentBankName)));

        VerticalLayout leftPanel = new VerticalLayout();
        VerticalLayout rightPanel = new VerticalLayout();
        leftPanel.setMaxWidth("300px");

        rightPanel.add(greeting, info);
        leftPanel.add(returnButton, goToOfferButton);

        topPanel.add(leftPanel, rightPanel);
    }
}

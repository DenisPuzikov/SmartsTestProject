package ru.puzikov.SmartsTestProject.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import ru.puzikov.SmartsTestProject.backend.service.*;
import ru.puzikov.SmartsTestProject.ui.view.layout.OfferLayout;

@Route("test")
public class OfferView extends VerticalLayout implements HasUrlParameter<String> {

    private HorizontalLayout topPanel = new HorizontalLayout();
    private VerticalLayout contentPanel;

    private final CreditOfferService creditOfferService;
    private final ClientService clientService;
    private final CreditService creditService;
    private final PaymentService paymentService;
    private final BankService bankService;

    private Long currentBankId;

    public OfferView(CreditOfferService creditOfferService, ClientService clientService,
                     CreditService creditService, PaymentService paymentService, BankService bankService) {
        this.creditOfferService = creditOfferService;
        this.clientService = clientService;
        this.creditService = creditService;
        this.paymentService = paymentService;
        this.bankService = bankService;
    }

    @Override
    public void setParameter(BeforeEvent event, String currentBankName) {
        currentBankId = bankService.findByBankName(currentBankName).getId();

        configureTopPanel(currentBankName);

        contentPanel = new OfferLayout(creditOfferService, clientService, creditService,
                paymentService, currentBankName, currentBankId);

        add(topPanel, contentPanel);
    }

    private void configureTopPanel(String currentBankName) {
        Label greeting = new Label("This page displays loan offers of "
                + "\"" + currentBankName + "\"");
        Label info = new Label("After selecting some offer you can see payment details. " +
                "Double-click to edit this offer.");

        Button returnToBankButton = new Button("Return to page of " + "\"" + currentBankName + "\"",
                new Icon(VaadinIcon.ARROW_LEFT));
        returnToBankButton.addClickListener(click ->
                returnToBankButton.getUI().ifPresent(ui -> ui.navigate(BankView.class, currentBankName)));

        Button returnToMainButton = new Button("Bank selection page ", new Icon(VaadinIcon.HOME));
        returnToMainButton.addClickListener(click ->
                returnToMainButton.getUI().ifPresent(ui -> ui.navigate(MainView.class)));

        VerticalLayout rightPanel = new VerticalLayout(greeting, info);
        VerticalLayout leftPanel = new VerticalLayout(returnToMainButton, returnToBankButton);
        leftPanel.setMaxWidth("300px");

        topPanel.add(leftPanel, rightPanel);
    }
}

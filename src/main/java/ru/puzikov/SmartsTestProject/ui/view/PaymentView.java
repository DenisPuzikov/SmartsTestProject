package ru.puzikov.SmartsTestProject.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import ru.puzikov.SmartsTestProject.backend.entity.*;
import ru.puzikov.SmartsTestProject.backend.service.CreditOfferService;
import ru.puzikov.SmartsTestProject.backend.service.PaymentService;

@Route("payment")
public class PaymentView extends VerticalLayout implements HasUrlParameter<Long> {

    private final PaymentService paymentService;
    private final CreditOfferService creditOfferService;
    private String currentBankName;

    private final Grid<Payment> paymentGrid = new Grid<>(Payment.class);

    public PaymentView(PaymentService paymentService, CreditOfferService creditOfferService) {
        this.paymentService = paymentService;
        this.creditOfferService = creditOfferService;
    }

    @Override
    public void setParameter(BeforeEvent event, Long currentOfferId) {
        CreditOffer currentOffer = creditOfferService.findById(currentOfferId).get();
        Client currentClient = currentOffer.getClient();

        Bank currentBank = currentClient.getBank();
        currentBankName = currentBank.getBankName();

        Credit currentCredit = currentOffer.getCredit();
        Long currentCreditSum = currentOffer.getCreditSum();


        Label offerInfo = new Label("Details for offer of client " + "\"" + currentClient + "\"" + " by credit "
                + currentCredit + " for " + currentCreditSum + " $");

        paymentGrid.setColumns("paymentDate", "paymentAmount", "paymentBody", "interestRepayment");

        paymentGrid.getColumnByKey("interestRepayment").
                setFooter(String.format("Total Repayment: %.2f",
                        paymentService.getCurrentRepaymentSum(currentOfferId)));

        paymentGrid.getColumnByKey("paymentAmount").
                setFooter(String.format("Credit sum: %d", currentCreditSum));

        paymentGrid.setWidthFull();
        paymentGrid.setHeight("500px");
        paymentGrid.setItems(paymentService.findAll(currentOfferId));

        add(offerInfo ,getToolBar(currentBankName), paymentGrid);
    }

    private HorizontalLayout getToolBar(String currentBankName) {
        Button returnToBankButton = new Button("Page of \"" + currentBankName + "\"",
                new Icon(VaadinIcon.ARROW_LEFT));
        Button returnToMainButton = new Button("Bank selection page", new Icon(VaadinIcon.HOME));
        Button returnToOffersButton = new Button("Page of offers of \"" + currentBankName + "\"",
                new Icon(VaadinIcon.ARROW_LEFT));

        returnToBankButton.addClickListener(click -> returnToBankButton
                .getUI().ifPresent(ui -> ui.navigate(BankView.class, currentBankName)));
        returnToMainButton.addClickListener(click -> returnToMainButton
                .getUI().ifPresent(ui -> ui.navigate(MainView.class)));
        returnToOffersButton.addClickListener((click -> returnToOffersButton
                .getUI().ifPresent(ui -> ui.navigate(OfferView.class, currentBankName))));

        HorizontalLayout toolBar = new HorizontalLayout(returnToMainButton, returnToBankButton, returnToOffersButton);

        return toolBar;
    }
}

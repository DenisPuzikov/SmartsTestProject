package ru.puzikov.SmartsTestProject.ui.form;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import ru.puzikov.SmartsTestProject.backend.entity.Bank;

import java.util.List;

public class BankForm extends FormLayout {

    TextField bankName = new TextField("Bank name");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Bank> binder = new Binder<>(Bank.class);
    private Bank bank;

    public BankForm(List<Bank> all) {
        addClassName("bank-form");

        binder.bindInstanceFields(this);


        add(
            bankName,
            createButtonsLayout()
        );
    }

    public void setBank(Bank bank) {
        this.bank = bank;
        binder.readBean(bank);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, bank)));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {

      try {
        binder.writeBean(bank);
        fireEvent(new SaveEvent(this, bank));
      } catch (ValidationException e) {
        e.printStackTrace();
      }
    }

    public static abstract class BankFormEvent extends ComponentEvent<BankForm> {
      private Bank bank;

      protected BankFormEvent(BankForm source, Bank bank) {
        super(source, false);
        this.bank = bank;
      }

      public Bank getBank() {
        return bank;
      }
    }

    public static class SaveEvent extends BankFormEvent {
      SaveEvent(BankForm source, Bank bank) {
        super(source, bank);
      }
    }

    public static class DeleteEvent extends BankFormEvent {
      DeleteEvent(BankForm source, Bank bank) {
        super(source, bank);
      }
    }

    public static class CloseEvent extends BankFormEvent {
      CloseEvent(BankForm source) {
        super(source, null);
      }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
      return getEventBus().addListener(eventType, listener);
    }
}

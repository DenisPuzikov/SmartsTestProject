package ru.puzikov.SmartsTestProject.ui.form;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import ru.puzikov.SmartsTestProject.backend.entity.Client;

import java.util.List;

public class ClientForm extends FormLayout {

    TextField firstName = new TextField("First Name");
    TextField lastName = new TextField("Last Name");
    TextField middleName = new TextField("Middle Name");
    TextField phoneNumber = new TextField("Phone number");
    TextField email = new TextField("Email");
    TextField passport = new TextField("Passport");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Client> binder = new Binder<>(Client.class);
    private Client client;

    public ClientForm(List<Client> all) {
        addClassName("client-form");

        binder.bindInstanceFields(this);

        firstName.setClearButtonVisible(true);
        firstName.setHelperText("Max 25 characters");
        firstName.setMaxLength(25);
        firstName.setSuffixComponent(new Span("Name"));
        lastName.setClearButtonVisible(true);
        lastName.setHelperText("Max 25 characters");
        lastName.setMaxLength(25);
        lastName.setSuffixComponent(new Span("Surname"));
        middleName.setClearButtonVisible(true);
        middleName.setHelperText("Max 25 characters");
        middleName.setMaxLength(25);
        middleName.setSuffixComponent(new Span("Fathers name"));

        phoneNumber.setAllowedCharPattern("[8\\d{10}]");
        phoneNumber.setHelperText("Max 11 digits starting with 8, example: 89995551100");
        phoneNumber.setMaxLength(11);
        phoneNumber.setClearButtonVisible(true);

        passport.setAllowedCharPattern("[\\d{10}]");
        passport.setHelperText("Max 10 digits, example: 3300998877");
        passport.setMaxLength(10);
        passport.setClearButtonVisible(true);

        email.setPattern("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
        email.setHelperText("Example: clientname@example.com");
        email.setClearButtonVisible(true);

        add(
                firstName,
                lastName,
                middleName,
                phoneNumber,
                email,
                passport,
                createButtonsLayout()
        );
    }

    public void setClient(Client client) {
        this.client = client;
        binder.readBean(client);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, client)));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(client);
            fireEvent(new SaveEvent(this, client));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class ClientFormEvent extends ComponentEvent<ClientForm> {
        private Client client;

        protected ClientFormEvent(ClientForm source, Client client) {
            super(source, false);
            this.client = client;
        }

        public Client getClient() {
            return client;
        }
    }

    public static class SaveEvent extends ClientFormEvent {
        SaveEvent(ClientForm source, Client client) {
            super(source, client);
        }
    }

    public static class DeleteEvent extends ClientFormEvent {
        DeleteEvent(ClientForm source, Client client) {
            super(source, client);
        }

    }

    public static class CloseEvent extends ClientFormEvent {
        CloseEvent(ClientForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}

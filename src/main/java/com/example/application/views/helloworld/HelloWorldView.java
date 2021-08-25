package com.example.application.views.helloworld;

import com.example.application.views.test.CustomDialog;
import com.example.application.views.test.PushyView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.MainLayout;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Hello World")
@Route(value = "hello", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@SpringComponent
@UIScope
public class HelloWorldView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;
    private Button editButton;

    //private PushyView pushyView;
    private CustomDialog customDialog;

    @Autowired
    public HelloWorldView(CustomDialog customDialog) {
    //public HelloWorldView(PushyView pushyView) {
        //this.pushyView = pushyView;
        this.customDialog = customDialog;
        addClassName("hello-world-view");
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        editButton = new Button("Navigate to");
        add(name, sayHello, editButton);
        setVerticalComponentAlignment(Alignment.END, name, sayHello, editButton);
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
        editButton.addClickListener(e ->
                //this.pushyView.open()
                this.customDialog.open()
                //editButton.getUI().ifPresent(ui -> ui.navigate(PushyView.class))
        );
    }

}

package com.example.application.views.test;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@SpringComponent
public class CustomDialog  extends Div {

    //private Button openPush;
    private PushyView pushyView;
    private Dialog dialog = new Dialog();

    @Autowired
    public CustomDialog(PushyView pushyView) {
        this.pushyView = pushyView;

        Button openPush = new Button("open push", e ->
        { this.pushyView.setBarSize(10);
            this.pushyView.setCallBack(new InputCallBack() {
                @Override
                public String callback(int i) {
                    return i + "data from custom  " + i;
                }
            });
            this.pushyView.open();});
        this.add(openPush);
        dialog.add(this);
    }

    public void open(){
        dialog.open();
    }
}

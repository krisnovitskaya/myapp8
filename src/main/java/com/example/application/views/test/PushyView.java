package com.example.application.views.test;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Push
@Route("push")
@SpringComponent
@UIScope
public class PushyView extends VerticalLayout {
    private FeederThread thread;
    private Dialog dialog = new Dialog();
    private Runnable task;
    private InputCallBack callBack;
    private ProgressBar progressBar;

    public PushyView() {
        progressBar = new ProgressBar();
        this.add(progressBar);
        dialog.add(this);
    }

    public void open(){
        dialog.open();
    }

    public void setTask(Runnable task){
        this.task = task;
    }

    public void setCallBack(InputCallBack callBack){
        this.callBack = callBack;
    }

    public void setBarSize(double size){
        progressBar.setMax(size);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        add(new Span("Waiting for updates"));

        // Start the data feed thread
        thread = new FeederThread(attachEvent.getUI(), this, progressBar, callBack);
        thread.start();
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        // Cleanup
        thread.interrupt();
        thread = null;
    }

    private static class FeederThread extends Thread {
        private final UI ui;
        private final PushyView view;
        private final ProgressBar progressBar;
        private final InputCallBack callBack;

        private int count = 0;

        public FeederThread(UI ui, PushyView view, ProgressBar progressBar, InputCallBack callBack) {
            this.ui = ui;
            this.view = view;
            this.progressBar = progressBar;
            this.callBack = callBack;
        }

        @Override
        public void run() {
            try {
                // Update the data for a while
                while (count < 10) {
                    // Sleep to emulate background work
                    System.out.println(LocalDateTime.now());
                    Thread.sleep(5000);
                    String message = "This is update " + count++;

                    System.out.println(LocalDateTime.now());
                    String returned = callBack.callback(count);
                    ui.access(() ->
                            {
                                view.add(new Span(message + returned));
                                progressBar.setValue(count);
                            });
                }

                // Inform that we are done
                ui.access(() -> {
                    view.add(new Span("Done updating"));
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
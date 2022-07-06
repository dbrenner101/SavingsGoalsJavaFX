package com.brenner.savingsgoals.view;

import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.text.Format;

public class TableColumnFormatter<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    private final Format format;
    
    public TableColumnFormatter(Format format) {
        super();
        this.format = format;
    }
    @Override
    public TableCell<S, T> call(TableColumn<S, T> arg0) {
        return new TableCell<S, T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new Label(format.format(item)));
                }
            }
        };
    }
}
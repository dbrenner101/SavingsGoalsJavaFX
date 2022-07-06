module com.brenner.savingsgoals {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    
    requires org.apache.commons.codec;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpasyncclient;
    requires org.apache.httpcomponents.httpcore.nio;
    requires org.apache.httpcomponents.httpmime;
    
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    
    
    opens com.brenner.savingsgoals;
    opens com.brenner.savingsgoals.controller;
    opens com.brenner.savingsgoals.view;
    
    exports com.brenner.savingsgoals.model;
    exports com.brenner.savingsgoals.controller.service.model;
    //opens com.brenner.savingsgoals.model;
    
    /*opens com.brenner.savingsgoals to javafx.fxml;
    exports com.brenner.savingsgoals;*/
}
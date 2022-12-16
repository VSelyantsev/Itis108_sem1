module ru.kpfu.itis.selyantsev.serverbot {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    opens ru.kpfu.itis.selyantsev.serverbot to javafx.fxml;
    exports ru.kpfu.itis.selyantsev.serverbot.client;
    exports ru.kpfu.itis.selyantsev.serverbot.server;
}
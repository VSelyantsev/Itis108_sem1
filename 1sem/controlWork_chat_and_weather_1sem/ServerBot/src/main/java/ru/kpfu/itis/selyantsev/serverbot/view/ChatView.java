package ru.kpfu.itis.selyantsev.serverbot.view;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import ru.kpfu.itis.selyantsev.serverbot.app.ChatApplication;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatView extends BaseView {

    private TextArea city;
    private Button findWeather;

    private TextArea input;
    private TextArea conversation;
    private AnchorPane pane = null;
    private final ChatApplication application = BaseView.getChatApplication();
    private final EventHandler onKeyEvent = new EventHandler<KeyEvent>() {

        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.ENTER) {
                String sender = application.getUserConfig().getName();
                String message = input.getText() + "\n";
                application.getChatClient().sendMessage(sender + " " + message);
                conversation.appendText("you: " + message);

                input.clear();
                event.consume();
            }
        }
    };

    public ChatView() throws Exception {
    }


    @Override
    public Parent getView() {
        if (pane == null) {
            this.createView();
        }

        return pane;
    }

    private String getTemperature(String city) {
        String apiKey = "6faded995f07a67bd6431a5176bb4640";
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" +
                    city + "&appid=" + apiKey);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            try (BufferedReader reader =
                         new BufferedReader(
                                 new InputStreamReader(connection.getInputStream())
                         )) {
                StringBuilder content = new StringBuilder();
                String input;
                while ((input = reader.readLine()) != null) {
                    content.append(input);
                }
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode node = objectMapper.readTree(String.valueOf(content));
                String res = String.valueOf(node.get("main").get("temp"));
                return String.valueOf(Double.parseDouble(res) - 273);
            }
        } catch (IOException e) {
            return "Could not find the city";
        }
    }

    private void createView() {
        pane = new AnchorPane();

        conversation = new TextArea();
        conversation.setEditable(false);
        conversation.setWrapText(true);


        AnchorPane.setTopAnchor(conversation, 10.0);
        AnchorPane.setLeftAnchor(conversation, 10.0);
        AnchorPane.setRightAnchor(conversation, 10.0);

        input = new TextArea();
        input.setMaxHeight(50);

        city = new TextArea();
        city.setMaxSize(60, 40);
        city.setMaxHeight(40);

        findWeather = new Button("Check Weather in your city");

        findWeather.setOnAction(actionEvent -> {
            conversation.appendText("Weather" + getTemperature(city.getText()));
        });

        AnchorPane.setBottomAnchor(findWeather, 10.0);
        AnchorPane.setLeftAnchor(input, 10.0);
        AnchorPane.setRightAnchor(findWeather, 10.0);

        AnchorPane.setBottomAnchor(city, 40.0);
        AnchorPane.setLeftAnchor(input, 10.0);
        AnchorPane.setRightAnchor(city, 10.0);

        input.addEventHandler(KeyEvent.KEY_PRESSED, onKeyEvent);
        pane.getChildren().addAll(input, conversation, findWeather, city);

        AnchorPane.setBottomAnchor(findWeather, 20.0);
        AnchorPane.setLeftAnchor(findWeather, 10.0);
        AnchorPane.setRightAnchor(findWeather, 20.0);


        AnchorPane.setBottomAnchor(input, 10.0);
        AnchorPane.setLeftAnchor(input, 10.0);
        AnchorPane.setRightAnchor(input, 10.0);

        input.addEventHandler(KeyEvent.KEY_PRESSED, onKeyEvent);
        pane.getChildren().addAll(input, conversation);
    }

    public void appendMessageToConversation(String message) {
        if (message != null) conversation.appendText(message);
    }
}
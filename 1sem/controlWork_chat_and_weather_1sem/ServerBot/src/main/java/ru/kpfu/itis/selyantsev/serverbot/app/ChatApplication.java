package ru.kpfu.itis.selyantsev.serverbot.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.kpfu.itis.selyantsev.serverbot.client.ChatClient;
import ru.kpfu.itis.selyantsev.serverbot.model.UserConfig;
import ru.kpfu.itis.selyantsev.serverbot.view.BaseView;
import ru.kpfu.itis.selyantsev.serverbot.view.ChatView;
import ru.kpfu.itis.selyantsev.serverbot.view.UserConfigView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatApplication extends Application {

    Stage primaryStage;

    private BorderPane rootLayout;

    private ChatView chatView;

    private UserConfigView userConfigView;

    public UserConfig getUserConfig() {
        return userConfig;
    }

    private UserConfig userConfig;

    public ChatClient getChatClient() {
        return chatClient;
    }

    private ChatClient chatClient;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        this.primaryStage.setTitle("Chat");
        this.primaryStage.setOnCloseRequest(e -> System.exit(0));

        this.chatClient = new ChatClient(this);

        BaseView.setChatApplication(this);

        this.userConfigView = new UserConfigView();
        this.chatView = new ChatView();

        this.initLayout();
    }

    private void initLayout() {
        rootLayout = new BorderPane();

        Scene scene = new Scene(rootLayout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        this.setView(userConfigView);
    }

    public BaseView getChatView() {
        return chatView;
    }

    public void setView(BaseView view) {
        rootLayout.setCenter(view.getView());
    }

    public void startChatClient() throws IOException {
        this.chatClient.start();
    }

    public void appendMessage(String message) {
        chatView.appendMessageToConversation(message);
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void setUserConfig(UserConfig userConfig) {
        this.userConfig = userConfig;
    }

}


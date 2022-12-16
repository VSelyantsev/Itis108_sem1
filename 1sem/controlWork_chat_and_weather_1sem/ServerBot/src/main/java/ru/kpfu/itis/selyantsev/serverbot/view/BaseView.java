package ru.kpfu.itis.selyantsev.serverbot.view;

import javafx.scene.Parent;
import ru.kpfu.itis.selyantsev.serverbot.app.ChatApplication;

public abstract class BaseView {

    private static ChatApplication chatApplication;

    public static ChatApplication getChatApplication() throws Exception {
        if (chatApplication != null) {
            return chatApplication;
        }
        throw new Exception("No app in base view");
    }

    public static void setChatApplication(ChatApplication chatApplication) {
        BaseView.chatApplication = chatApplication;
    }

    public abstract Parent getView();


}
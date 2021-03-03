package com.xiaoban.zimu.controller;

/**
 * @author xiaoban
 * @create 2021/03/03 - 17:56
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.awt.*;

public class Main1 extends Application {
    private static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    private static final String url = "https://www.youzanxuetang.com";
    private static final String urlStart = "http://";

    /**
     */
    public static void web() {
        JFrame frame = new JFrame("嵌入浏览器");
        final JFXPanel webBrowser = new JFXPanel();
        frame.setLayout(new BorderLayout());
        frame.add(webBrowser, BorderLayout.CENTER);
        Platform.runLater(() -> {
            Group root = new Group();
            Scene scene = new Scene(root, WIDTH, HEIGHT);
            webBrowser.setScene(scene);
            double widthDouble = new Integer(WIDTH).doubleValue();
            double heightDouble = new Integer(HEIGHT).doubleValue();

            VBox box = new VBox(10);
            HBox urlBox = new HBox(10);
            final TextField urlTextField = new TextField();
            urlTextField.setText(url);
            Button go = new Button("go");
            urlTextField.setPrefWidth(WIDTH - 80);
            urlBox.getChildren().addAll(urlTextField, go);

            WebView view = new WebView();
            view.setMinSize(widthDouble, heightDouble);
            view.setPrefSize(widthDouble, heightDouble);
            final WebEngine eng = view.getEngine();
            eng.load(url);
            root.getChildren().add(view);

            box.getChildren().add(urlBox);
            box.getChildren().add(view);
            root.getChildren().add(box);

            go.setOnAction(event -> {
                if (!urlTextField.getText().startsWith(urlStart)) {
                    eng.load(urlStart + urlTextField.getText());
                } else {
                    eng.load(urlTextField.getText());
                }
            });
        });

        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocation((screenWidth - WIDTH) / 2, (screenHeight - HEIGHT) / 2);
        frame.setVisible(true);
    }

    @Override
    public void start(final Stage stage) {

    }

    public static void main(String[] args) {
        launch(args);
    }

}

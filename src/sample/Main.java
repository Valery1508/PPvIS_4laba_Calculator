package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import view.MainFrame;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        MainFrame mainFrame = new MainFrame(primaryStage);
        mainFrame.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

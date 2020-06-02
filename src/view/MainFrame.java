package view;

import controller.TreeAndOutputController;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainFrame {
    final int MAIN_FRAME_HEIGHT = 400;
    final int MAIN_FRAME_WIDTH = 580;

    private Stage stage;
    private Scene scene;
    private TreeAndOutputController treeAndOutputController;
    private InputTextArea inputTextArea;
    private CalcButtons buttons;
    private TreeAndOutput treeAndOutput;
    private VBox rightSide;

    public MainFrame(Stage stage) {
        this.stage = stage;
        treeAndOutputController = new TreeAndOutputController();
        inputTextArea = new InputTextArea();
        treeAndOutput = new TreeAndOutput("", null, 0);
        buttons = new CalcButtons(inputTextArea, treeAndOutputController, this, treeAndOutput);
    }

    public void show(){
        VBox leftSide = new VBox();
        leftSide.getChildren().addAll(inputTextArea.getScrollPaneOfInput(), buttons.getButtons());

        rightSide = new VBox();
        rightSide.getChildren().addAll(treeAndOutput.getOutput(), treeAndOutput.getScrollForTree());

        HBox pane = new HBox();
        pane.getChildren().addAll(leftSide, rightSide);

        stage.setTitle("Calculator");
        stage.setMinWidth(MAIN_FRAME_WIDTH);
        stage.setMinHeight(MAIN_FRAME_HEIGHT);
        stage.setResizable(false);
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public VBox getRightSide() {
        return rightSide;
    }
}

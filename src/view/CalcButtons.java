package view;

import controller.CalculatorController;
import controller.TreeAndOutputController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

public class CalcButtons {
    private HBox buttons = new HBox();
    private TreeAndOutputController treeAndOutputController;
    private TextArea inputText;
    private CalculatorController calculatorController = new CalculatorController();
    private String resultOutput;
    private MainFrame mainFrame;
    private TreeAndOutput treeAndOutput;
    private int limit = 0;

    public CalcButtons(InputTextArea inputTextArea, TreeAndOutputController treeAndOutputController, MainFrame mainFrame, TreeAndOutput treeAndOutput) {
        this.treeAndOutputController = treeAndOutputController;
        this.inputText = inputTextArea.getInput();
        this.mainFrame = mainFrame;
        this.treeAndOutput = treeAndOutput;
        config();
    }

    public void config(){

        Button leftButton = new Button("<");
        leftButton.setPrefSize(55, 55); buttonText(leftButton);
        Button rightButton = new Button(">");
        rightButton.setPrefSize(55, 55);    buttonText(rightButton);
        Button clearButton = new Button("C");
        clearButton.setPrefSize(55, 55);    buttonText(clearButton);
        Button startBracketButton = new Button("(");
        startBracketButton.setPrefSize(55, 55);    buttonText(startBracketButton);
        Button endBracketButton = new Button(")");
        endBracketButton.setPrefSize(55, 55);    buttonText(endBracketButton);
        HBox firstRow = new HBox();
        firstRow.getChildren().addAll(leftButton, rightButton, clearButton, startBracketButton, endBracketButton);

        Button sevenButton = new Button("7");
        sevenButton.setPrefSize(55, 55); buttonText(sevenButton);
        Button eightButton = new Button("8");
        eightButton.setPrefSize(55, 55);    buttonText(eightButton);
        Button nineButton = new Button("9");
        nineButton.setPrefSize(55, 55);    buttonText(nineButton);
        Button plusButton = new Button("+");
        plusButton.setPrefSize(55, 55);    buttonText(plusButton);
        Button minusButton = new Button("-");
        minusButton.setPrefSize(55, 55);    buttonText(minusButton);
        HBox secondRow = new HBox();
        secondRow.getChildren().addAll(sevenButton, eightButton, nineButton, plusButton, minusButton);

        Button fourButton = new Button("4");
        fourButton.setPrefSize(55, 55);    buttonText(fourButton);
        Button fiveButton = new Button("5");
        fiveButton.setPrefSize(55, 55);    buttonText(fiveButton);
        Button sixButton = new Button("6");
        sixButton.setPrefSize(55, 55);    buttonText(sixButton);
        Button multiplyButton = new Button("*");
        multiplyButton.setPrefSize(55, 55);    buttonText(multiplyButton);
        Button divideButton = new Button("/");
        divideButton.setPrefSize(55, 55);    buttonText(divideButton);
        HBox thirdRow = new HBox();
        thirdRow.getChildren().addAll(fourButton, fiveButton, sixButton, multiplyButton, divideButton);

        Button firstButton = new Button("1");
        firstButton.setPrefSize(55, 55);    buttonText(firstButton);
        Button secondButton = new Button("2");
        secondButton.setPrefSize(55, 55);    buttonText(secondButton);
        Button thirdButton = new Button("3");
        thirdButton.setPrefSize(55, 55);    buttonText(thirdButton);
        Button percentButton = new Button("%");
        percentButton.setPrefSize(55, 55);    buttonText(percentButton);
        Button reverseButton = new Button("1/x");
        reverseButton.setPrefSize(55, 55);    buttonText(reverseButton);
        HBox fourthRow = new HBox();
        fourthRow.getChildren().addAll(firstButton, secondButton, thirdButton, percentButton, reverseButton);

        Button zeroButton = new Button("0");
        zeroButton.setPrefSize(55, 55);    buttonText(zeroButton);
        Button dotButton = new Button(".");
        dotButton.setPrefSize(55, 55);    buttonText(dotButton);
        Button sqrtButton = new Button("√");
        sqrtButton.setPrefSize(55, 55);    buttonText(sqrtButton);
        Button equalButton = new Button("=");
        equalButton.setPrefSize(110, 55);    buttonText(equalButton);
        HBox fifthRow = new HBox();
        fifthRow.getChildren().addAll(zeroButton, dotButton, sqrtButton, equalButton);

        firstButton.setOnAction(firstEventHandler);
        secondButton.setOnAction(secondEventHandler);
        thirdButton.setOnAction(thirdEventHandler);
        fourButton.setOnAction(fourthEventHandler);
        fiveButton.setOnAction(fifthEventHandler);
        sixButton.setOnAction(sixEventHandler);
        sevenButton.setOnAction(sevenEventHandler);
        eightButton.setOnAction(eightEventHandler);
        nineButton.setOnAction(nineEventHandler);
        zeroButton.setOnAction(zeroEventHandler);

        plusButton.setOnAction(plusEventHandler);
        minusButton.setOnAction(minusEventHandler);
        multiplyButton.setOnAction(multiplyEventHandler);
        divideButton.setOnAction(divideEventHandler);
        percentButton.setOnAction(percentEventHandler);
        reverseButton.setOnAction(reverseEventHandler);
        sqrtButton.setOnAction(sqrtEventHandler);
        startBracketButton.setOnAction(startBracketEventHandler);
        endBracketButton.setOnAction(endBracketEventHandler);
        clearButton.setOnAction(clearEventHandler);
        dotButton.setOnAction(dotEventHandler);
        equalButton.setOnAction(equalEventHandler);
        leftButton.setOnAction(leftEventHandler);
        rightButton.setOnAction(rightEventHandler);

        VBox usualButtons = new VBox();
        usualButtons.getChildren().addAll(firstRow, secondRow, thirdRow, fourthRow, fifthRow);

        Button powerTwoButton = new Button("x^2");
        powerTwoButton.setPrefSize(65, 65);    buttonText(powerTwoButton);
        Button powerThreeButton = new Button("x^3");
        powerThreeButton.setPrefSize(65, 65);    buttonText(powerThreeButton);
        Button powerButton = new Button("x^y");
        powerButton.setPrefSize(65, 65);    buttonText(powerButton);

        CheckBox additionalFuncs = new CheckBox("Extra");
        additionalFuncs.setSelected(true);
        additionalFuncs.setOnAction(event -> {
            powerTwoButton.setDisable(!additionalFuncs.isSelected());
            powerThreeButton.setDisable(!additionalFuncs.isSelected());
            powerButton.setDisable(!additionalFuncs.isSelected());
        });

        powerTwoButton.setOnAction(powerTwoEventHandler);
        powerThreeButton.setOnAction(powerThreeEventHandler);
        powerButton.setOnAction(powerEventHandler);

        VBox additionalButtons = new VBox();
        additionalButtons.getChildren().addAll(additionalFuncs, powerTwoButton, powerThreeButton, powerButton);
        additionalButtons.setSpacing(10);

        buttons.getChildren().addAll(usualButtons, additionalButtons);
        buttons.setSpacing(10);
    }

    public HBox getButtons(){
        return buttons;
    }

    public void buttonText(Button button){
        button.setFont(Font.font("Verdana", FontWeight.NORMAL, 18));
    }

    private EventHandler<ActionEvent> firstEventHandler = e -> {
        inputText.setText(inputText.getText() + "1");
    };

    private EventHandler<ActionEvent> secondEventHandler = e -> {
        inputText.setText(inputText.getText() + "2");
    };

    private EventHandler<ActionEvent> thirdEventHandler = e -> {
        inputText.setText(inputText.getText() + "3");
    };

    private EventHandler<ActionEvent> fourthEventHandler = e -> {
        inputText.setText(inputText.getText() + "4");
    };

    private EventHandler<ActionEvent> fifthEventHandler = e -> {
        inputText.setText(inputText.getText() + "5");
    };

    private EventHandler<ActionEvent> sixEventHandler = e -> {
        inputText.setText(inputText.getText() + "6");
    };

    private EventHandler<ActionEvent> sevenEventHandler = e -> {
        inputText.setText(inputText.getText() + "7");
    };

    private EventHandler<ActionEvent> eightEventHandler = e -> {
        inputText.setText(inputText.getText() + "8");
    };

    private EventHandler<ActionEvent> nineEventHandler = e -> {
        inputText.setText(inputText.getText() + "9");
    };

    private EventHandler<ActionEvent> zeroEventHandler = e -> {
        inputText.setText(inputText.getText() + "0");
    };

    private EventHandler<ActionEvent> plusEventHandler = e -> {
        inputText.setText(inputText.getText() + "+");
    };

    private EventHandler<ActionEvent> minusEventHandler = e -> {
        inputText.setText(inputText.getText() + "-");
    };

    private EventHandler<ActionEvent> multiplyEventHandler = e -> {
        inputText.setText(inputText.getText() + "*");
    };

    private EventHandler<ActionEvent> divideEventHandler = e -> {
        inputText.setText(inputText.getText() + "/");
    };

    private EventHandler<ActionEvent> percentEventHandler = e -> {
        inputText.setText(inputText.getText() + "%");
    };

    private EventHandler<ActionEvent> reverseEventHandler = e -> {
        inputText.setText(inputText.getText() + "()^-1");
    };

    private EventHandler<ActionEvent> startBracketEventHandler = e -> {
        inputText.setText(inputText.getText() + "(");
    };

    private EventHandler<ActionEvent> endBracketEventHandler = e -> {
        inputText.setText(inputText.getText() + ")");
    };

    private EventHandler<ActionEvent> clearEventHandler = e -> {
        inputText.setText("");
        treeAndOutput.updateTreeAndOutput("", null, 0, mainFrame);
        calculatorController.clear();
    };

    private EventHandler<ActionEvent> dotEventHandler = e -> {
        inputText.setText(inputText.getText() + ".");
    };

    private EventHandler<ActionEvent> sqrtEventHandler = e -> {
        inputText.setText(inputText.getText() + "()sqrt");
    };

    private EventHandler<ActionEvent> equalEventHandler = e -> {
        limit = 0;
        resultOutput = calculatorController.startParse(inputText.getText());
        treeAndOutput.updateTreeAndOutput(resultOutput, calculatorController.getTreeList(), limit, mainFrame);

    };

    private EventHandler<ActionEvent> leftEventHandler = e -> {
        if(!calculatorController.getList().isEmpty()){
            if(limit != 0) limit--;
            inputText.setText(calculatorController.getList().get(limit));
            treeAndOutput.updateTreeAndOutput(resultOutput, calculatorController.getTreeList(), limit, mainFrame);
        }
    };

    private EventHandler<ActionEvent> rightEventHandler = e -> {
        if(!calculatorController.getList().isEmpty()){
            if(calculatorController.getTreeList().size() != limit){
                limit++;
            }
            if(limit == calculatorController.getTreeList().size()){
                inputText.setText(resultOutput);
            } else inputText.setText(calculatorController.getList().get(limit));

            treeAndOutput.updateTreeAndOutput(resultOutput, calculatorController.getTreeList(), limit, mainFrame);
        }
    };

    private EventHandler<ActionEvent> powerTwoEventHandler = e -> {
        inputText.setText(inputText.getText() + "()^2");
    };

    private EventHandler<ActionEvent> powerThreeEventHandler = e -> {
        inputText.setText(inputText.getText() + "()^3");
    };

    private EventHandler<ActionEvent> powerEventHandler = e -> {
        inputText.setText(inputText.getText() + "()^()");
    };

}

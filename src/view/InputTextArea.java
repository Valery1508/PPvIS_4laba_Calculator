package view;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class InputTextArea {

    private TextArea input;
    private ScrollPane scrollForInput;

    public InputTextArea(){
        input = new TextArea();
        input.setPrefSize(360, 60);
        input.setFont(Font.font("Verdana", FontWeight.NORMAL, 25));

        scrollForInput = new ScrollPane();
        scrollForInput.setContent(input);
        scrollForInput.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollForInput.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollForInput.setPrefViewportHeight(60);
        scrollForInput.setPrefViewportWidth(360);
    }

    public TextArea getInput(){
        return input;
    }

    public ScrollPane getScrollPaneOfInput(){
        return scrollForInput;
    }

}

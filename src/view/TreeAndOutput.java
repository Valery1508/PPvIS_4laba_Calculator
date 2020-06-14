package view;

import controller.TreeAndOutputController;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

public class TreeAndOutput {
    private TextArea output;
    private ScrollPane scrollForTree;

public TreeAndOutput(String resultOutput, List<List<String>> listsOfTree, int digit) {

    output = new TextArea();
    output.setPrefSize(210, 80);
    output.setEditable(false);
    output.setFont(Font.font("Verdana", FontWeight.NORMAL, 25));
    output.setText(resultOutput);

    scrollForTree = new ScrollPane();
    scrollForTree.setContent(TreeAndOutputController.makeTree(listsOfTree, digit));
    scrollForTree.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrollForTree.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrollForTree.setPrefViewportHeight(250);
    scrollForTree.setPrefViewportWidth(210);
}

    public TextArea getOutput(){
        return output;
    }

    public void updateTreeAndOutput(String textForOutput, List<List<String>> treeList, int digit, MainFrame mainFrame){
        mainFrame.getRightSide().getChildren().clear();
        this.output.setText(textForOutput);
        this.scrollForTree.setContent(TreeAndOutputController.makeTree(treeList, digit));

        mainFrame.getRightSide().getChildren().addAll(this.output, this.scrollForTree);
    }

    public ScrollPane getScrollForTree(){
        return scrollForTree;
    }

}

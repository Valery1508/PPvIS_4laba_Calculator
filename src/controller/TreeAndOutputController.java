package controller;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.List;

public class TreeAndOutputController {

    public TreeAndOutputController(){

    }

    public static TreeView<String> makeTree(List<List<String>> listsOfTree, int digit){
        if(listsOfTree == null) {
            return null;
        }

        if(digit == listsOfTree.size()){
            TreeItem<String> headRoot;
            if(listsOfTree.get(digit-1).size() == 3){
                headRoot = new TreeItem<>(listsOfTree.get(digit-1).get(2));
                headRoot.setExpanded(true);
            } else{
                headRoot = new TreeItem<>(listsOfTree.get(digit-1).get(3));
                headRoot.setExpanded(true);
            }
            TreeView<String> tree = new TreeView<>(headRoot);
            return tree;
        }

        TreeItem<String> currentRoot = new TreeItem<>(listsOfTree.get(listsOfTree.size()-1).get(1));
        currentRoot.setExpanded(true);
        TreeItem<String> headRoot = currentRoot;
        headRoot.setExpanded(true);

        for(int i = listsOfTree.size()-1; i > digit; i--){
            TreeItem<String> sign = new TreeItem<>(listsOfTree.get(i-1).get(1));
            sign.setExpanded(true);
            currentRoot.getChildren().addAll(sign);
            if(listsOfTree.get(i).size() == 3){
                currentRoot.getChildren().add(new TreeItem<>(listsOfTree.get(i).get(0)));
            } else currentRoot.getChildren().add(new TreeItem<>(listsOfTree.get(i).get(2)));
            currentRoot = sign;
        }
        currentRoot.getChildren().add(new TreeItem<>(listsOfTree.get(digit).get(0)));
        //currentRoot.setExpanded(true);

        if(listsOfTree.get(digit).size() ==4){
            currentRoot.getChildren().add(new TreeItem<>(listsOfTree.get(digit).get(2)));
            currentRoot.setExpanded(true);
        }

        TreeView<String> tree = new TreeView<>(headRoot);
        return tree;
    }
}

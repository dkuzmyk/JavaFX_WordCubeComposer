package com.dkuzmyk.cube_scrambler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class Controller {

    private Logic logic;
    private TextField[] cubes;

    @FXML
    private CheckBox cc1;
    @FXML
    private CheckBox cc2;
    @FXML
    private CheckBox cc3;
    @FXML
    private CheckBox cc4;
    @FXML
    private CheckBox cc5;
    @FXML
    private CheckBox cc6;

    @FXML
    private TextField wordField;

    @FXML
    private TextField cube1;
    @FXML
    private TextField cube2;
    @FXML
    private TextField cube3;
    @FXML
    private TextField cube4;
    @FXML
    private TextField cube5;
    @FXML
    private TextField cube6;

    @FXML
    private Button cubeSubmit;
    @FXML
    private Button wordSubmit;
    @FXML
    private Button instructions;

    @FXML
    private ListView resultView;

    @FXML
    public void initialize() {
        logic = new Logic();
        cube1.setDisable(true);
        cube2.setDisable(true);
        cube3.setDisable(true);
        cube4.setDisable(true);
        cube5.setDisable(true);
        cube6.setDisable(true);
        cubes = new TextField[]{cube1, cube2, cube3, cube4, cube5, cube6};
    }

    public void wordSubmitAction(ActionEvent event){
        logic.setWord(wordField.getText());
    }

    public void cubeSubmitAction(ActionEvent event){
        int i = 0;
        for (TextField c : cubes ) {
            // if cube is active
            if(!c.getText().equals("")){
                // check validity of the cube
                if(!logic.checkValidCube(c.getText())){
                    Alert err = new Alert(Alert.AlertType.ERROR);
                    err.setHeaderText("ERROR");
                    err.setTitle("ERROR");
                    err.setContentText("Invalid cube: "+c.getText());
                    if(err.showAndWait().get() == ButtonType.OK) {
                        err.close();
                        break;
                    }
                }
                // if cube is not used -> just update
                // else -> add new cube
                if(!logic.getUsed()[i])
                    logic.updateCube(c.getText(), i);
                else
                    logic.addCube(c.getText(), i);
            }
            i++;
        }
        // DEBUG
        //logic.printCubes();
        resultView.getItems().clear();
        logic.solve();
        if(!logic.getResultPath().isEmpty())
            resultView.getItems().add("Possible! Word: "+wordField.getText());
        else
            resultView.getItems().add("Impossible for word: "+wordField.getText());

        ArrayList<String> path = logic.getResultPath();
        for (String e : path) {
            resultView.getItems().add(e);
        }
    }

    public void instructionsAction(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Instructions");
        alert.setHeaderText("Instructions");
        alert.setContentText("""
                Add a word, the cubes will find out if it's possible to compose such a word.

                Choose up to 6 cubes, 6 faces each and give each face a letter.
                
                eg:
                word: hello;\s
                cube 1: t,t,h,t,t,t;\s
                cube 2: t,t,t,l,t,t;\s
                cube 3: t,t,t,t,t,e;\s
                cube 4: o,t,t,l,t,t;\s
                cube 5: t,t,t,t,t,l""");
        if(alert.showAndWait().get() == ButtonType.OK) {
            alert.close();
        }
    }

    // checkmark settings
    public void cc1Action(ActionEvent event){
        if(cc1.isSelected()){
            cube1.setDisable(false);
        }
        else{
            cube1.setDisable(true);
            cube1.setText("");
            logic.resetCube(0);
        }
    }
    public void cc2Action(ActionEvent event){
        if(cc2.isSelected()){
            cube2.setDisable(false);
        }
        else{
            cube2.setDisable(true);
            cube2.setText("");
            logic.resetCube(1);
        }
    }
    public void cc3Action(ActionEvent event){
        if(cc3.isSelected()){
            cube3.setDisable(false);
        }
        else{
            cube3.setDisable(true);
            cube3.setText("");
            logic.resetCube(2);
        }
    }
    public void cc4Action(ActionEvent event){
        if(cc4.isSelected()){
            cube4.setDisable(false);
        }
        else{
            cube4.setDisable(true);
            cube4.setText("");
            logic.resetCube(3);
        }
    }
    public void cc5Action(ActionEvent event){
        if(cc5.isSelected()){
            cube5.setDisable(false);
        }
        else{
            cube5.setDisable(true);
            cube5.setText("");
            logic.resetCube(4);
        }
    }
    public void cc6Action(ActionEvent event){
        if(cc6.isSelected()){
            cube6.setDisable(false);
        }
        else{
            cube6.setDisable(true);
            cube6.setText("");
            logic.resetCube(5);
        }
    }
}
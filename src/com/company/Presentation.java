package com.company;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

public class Presentation implements EventHandler<ActionEvent> {
    private Model model;
    private Vew vew;
    private AnimationTimer animationTimer;
    private int timeValue;
    private int second;
    private int minute;

    public Presentation(Model model){
        second = 0;
        minute = 0;
        this.model = model;
    }

    public void handle(ActionEvent event){
        TextField textField = (TextField) event.getSource();

        if (!textField.getText().equalsIgnoreCase("")){
            if(model.validateEntry(textField.getText())){
                vew.actualizeScore();
                vew.changeImageView();
            }else{
                vew.actualizeScore();
            }
        }
    }

    public void timeStart(){
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                timeValue++;
                if(timeValue % 60 == 1) {
                    second++;
                    if (second == 60){
                        second = 0;
                        minute++;
                    }
                    vew.incrementTime(second, minute);
                }
            }
        };
        animationTimer.start();
    }

    public int getScore(){ return model.getScore(); }

    public void associateVue(Vew vew){ this.vew = vew; }

    public String randomImagePath() {
        String imagePath = model.randomImagePath();
        if (imagePath.equals("Game Over")){
            animationTimer.stop();
        }
        return imagePath;
    }

    public void initializeGame(){
        String imagePath = model.randomImagePath();
        timeStart();
        vew.setUpImageView(imagePath);
        vew.showAnswer(imagePath);
    }

    public void lostPoint() { model.decrementScore(); }

    public int returnScoreMax(){ return model.returnScoreMax(); }

    public int getMistakes() { return model.getMistakes(); }
}

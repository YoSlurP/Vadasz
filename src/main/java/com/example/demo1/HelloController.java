package com.example.demo1;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class HelloController {
    @FXML
    private Label lbRoka;
    @FXML
    private Label lbLoves;
    @FXML
    Pane pnJatek;

    private String[] iconNev={"dark","dead","fox","home","tree"};
    private Image[] icon = new Image[5];

    private ImageView[][] it= new ImageView[16][32];
    private int[][] t=new int[16][32];
    private int es=-1,eo=-1;

    private final int ROKA =2;
    private final int TREE =4;
    private final int DARK =0;
    private final int HOME =3;
    private final int DEAD =1;

    private int loves=0;
    private int maxloves=0;
    private int roka=0;
    private int rokaMax=0;

    private AnimationTimer timer =null;
    private long most=0;
    private long r=0;

    public void initialize(){
        for(int i=0;i<5;i++) icon[i]= new Image(getClass().getResourceAsStream("icons/"+iconNev[i]+".png"));
        for(int s=0;s<16;s++) for(int o=0; o<32;o++){
            int ss=s, oo=o;
            it[s][o]= new ImageView(icon[DARK]);
            it[s][o].setLayoutX(10+o*48);
            it[s][o].setLayoutY(10+s*48);
            it[s][o].setOnMouseEntered(e-> vilagit(ss,oo));
            it[s][o].setOnMousePressed(e-> katt(ss,oo));
            pnJatek.getChildren().add(it[s][o]);
        }

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                most=now;
                if(now > r) elbujik();
                if(roka==0){timer.stop();vege();}
            }
        };
        generalerdo();
        timer.start();
    }

    private void generalerdo(){
        roka=0;loves=0; maxloves=0;
        for(int s=0;s<16;s++)for(int o=0; o<32;o++){
            if(Math.random()<0.1) {
                t[s][o]=ROKA; roka++;
            }else{
                t[s][o]=TREE;
            }
            it[s][o].setImage(icon[DARK]);
        }
        rokaMax = roka;
        lbLoves.setText(loves +" löves / "+ maxloves+" talált");
        lbRoka.setText(roka+" / "+rokaMax +" róka");
    }
    private void vilagit(int s,int o){
        if(s!= es || o!=eo){
            for(int dS=-2; dS<=+2;dS++) for(int dO=-2;dO<=+2;dO++){
                int ss=es+dS,oo=eo+dO;
                if(ss>=0&& ss<=15 && oo>=0 && oo<=31){
                    it[ss][oo].setImage(icon[DARK]);
                }
            }
            for(int dS=-2; dS<=+2;dS++) for(int dO=-2;dO<=+2;dO++){
                int ss=s+dS,oo=o+dO;
                if(ss>=0&& ss<=15 && oo>=0 && oo<=31 && !(Math.abs(dS)==2 && Math.abs(dO)==2)){
                    it[ss][oo].setImage(icon[t[ss][oo]]);
                }
            }
            es=s;eo=o;
            r=most + 500000000;
        }

    }
    private void elbujik(){
        for(int dS=-2; dS<=+2;dS++) for(int dO=-2;dO<=+2;dO++){
            int ss=es+dS,oo=eo+dO;
            if(ss>=0&& ss<=15 && oo>=0 && oo<=31 &&!(Math.abs(dS)==2 && Math.abs(dO)==2)&& t[ss][oo]==ROKA){
                it[ss][oo].setImage(icon[HOME]); t[ss][oo]=HOME;roka--;
            }
        }
        lbRoka.setText(roka+" / "+rokaMax +" róka");
    }
    private void katt(int s,int o){
        loves++;
        if(t[s][o]==ROKA){
            it[s][o].setImage(icon[DEAD]);t[s][o]=DEAD;roka--;maxloves++;
            lbRoka.setText(roka+" / "+rokaMax +" róka");
        }
        lbLoves.setText(loves +" löves / "+ maxloves+" talált");

    }

    private void vege(){
        for(int s=0;s<16;s++)for(int o=0;o<32;o++) it[s][o].setImage(icon[t[s][o]]);
        Alert uzenet= new Alert(Alert.AlertType.NONE);
        uzenet.setTitle("Game Over!");
        uzenet.setHeaderText(null);
        String txt=String.format("%d lövésből %d talált, ami %d%%\n",loves,maxloves,maxloves*100/loves);
        txt+=String.format("%d rókábol %d lett lelőve, ami %d%%",rokaMax,maxloves, maxloves*100/rokaMax);
        uzenet.setContentText(txt);
        uzenet.getButtonTypes().removeAll();
        uzenet.getButtonTypes().add(new ButtonType("Újra"));
        uzenet.setOnCloseRequest(e->generalerdo());
        uzenet.show();
    }


}
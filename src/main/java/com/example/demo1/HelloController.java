package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class HelloController {
    @FXML
    private Label lbRoka;
    @FXML
    private Label LbLoves;
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

    private int roka=0;
    private int rokaMax=0;
    public void initialize(){
        for(int i=0;i<5;i++) icon[i]= new Image(getClass().getResourceAsStream("icons/"+iconNev[i]+".png"));
        for(int s=0;s<16;s++) for(int o=0; o<32;o++){
            int ss=s, oo=o;
            it[s][o]= new ImageView(icon[DARK]);
            it[s][o].setLayoutX(10+o*48);
            it[s][o].setLayoutY(10+s*48);
            it[s][o].setOnMouseEntered(e-> vilagit(ss,oo));
            pnJatek.getChildren().add(it[s][o]);
        }
        generalerdo();
    }

    private void generalerdo(){
        roka=0;
        for(int s=0;s<16;s++)for(int o=0; o<32;o++){
            if(Math.random()<0.1) {
                t[s][o]=ROKA; roka++;
            }else{
                t[s][o]=TREE;
            }
        }
        rokaMax = roka;
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
        }

    }
    private void sotetit(int s,int o){

    }


}
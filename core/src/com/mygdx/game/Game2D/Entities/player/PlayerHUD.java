package com.mygdx.game.Game2D.Entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Game2D.Manager.ResourceManager;
import com.mygdx.game.Game2D.status.PlayerPortrait;

public class PlayerHUD {

    private Stage stage;
    private PlayerPortrait playerPortrait;
    private Label nameLabel;
    private Label dayLabel;
    private Label mapLabel;
    private int defaultWidth;
    private int defaultHeight;


    public PlayerHUD(Player player) {
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        playerPortrait = new PlayerPortrait();
        defaultWidth = 350;
        defaultHeight = 100;


        Table table = new Table();
        table.pad(10);
        table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("test.png")))));

        table.left();

        Table playerTable = new Table();
        playerTable.add(playerPortrait).size(50).align(Align.left).padLeft(10);

        table.add(playerTable).align(Align.left);



        //Nested Table for rowspan
        Table table2 = new Table();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = ResourceManager.pixel10;
        labelStyle.fontColor = Color.BLACK;


        // Name label
        nameLabel = new Label(player.username, labelStyle);
        nameLabel.setFontScale(2.5f);
        table2.add(nameLabel).align(Align.left).size(10);
        table2.row();


        // Add day label TODO Dynamic Day
        dayLabel = new Label("Day: Monday" , labelStyle);
        dayLabel.setFontScale(2.5f);
        table2.add(dayLabel).align(Align.left).size(10).padTop(15);


        table2.padLeft(30);


        table.add(table2).align(Align.left);



//        //TODO Make HUD interactable
//        TextButton inventoryButton = new TextButton("Inventory", skin); // Assuming you have a skin
//        TextButton profileButton = new TextButton("Profile", skin);
//
//        Table table3 = new Table();
//        table3.add(inventoryButton).align(Align.left).size(10).padTop(10);
//        table3.add(profileButton).align(Align.left).size(10).padTop(10);
//
//        table.add(table3).align(Align.left);


        table.setSize(defaultWidth, defaultHeight);
        stage.addActor(table);


        // x = 10 for margin and - 10 for y margin
        table.setPosition(10, Gdx.graphics.getHeight() - table.getHeight() - 10);


    }



    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void setNameLabelText(String text) {
        nameLabel.setText(text);
    }

    public void setDayLabelText(String text) {
        dayLabel.setText(text);
    }


    public void dispose() {
        stage.dispose();
    }

    public  Stage  getStage(){
        return stage;
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

}

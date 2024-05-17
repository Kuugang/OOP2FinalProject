package com.mygdx.game.Game2D.Entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Game2D.Entities.Entity;
import com.mygdx.game.Game2D.Manager.ResourceManager;
import com.mygdx.game.Game2D.status.PlayerPortrait;

import static com.mygdx.game.Game2D.Manager.ResourceManager.skin;

public class PlayerHUD2 {

    private Stage stage;
    private PlayerPortrait playerPortrait;
    private Label nameLabel;
    private Label dayLabel;
    private Label mapLabel;


    public PlayerHUD2(Player player) {
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));


        playerPortrait = new PlayerPortrait();

        //Set position
        playerPortrait.setPosition(10, 10);

        Table table = new Table();
        table.pad(10);
        table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("test.png")))));

        table.left();

        Table playerTable = new Table();
        playerTable.add(playerPortrait).size(50).align(Align.left).padLeft(10);

        table.add(playerTable).align(Align.left);

        Table table2 = new Table();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = ResourceManager.pixel10;
        labelStyle.fontColor = Color.BLACK;


        // name label
        nameLabel = new Label(player.username, labelStyle);
        nameLabel.setFontScale(2.5f);
        table2.add(nameLabel).align(Align.left).size(10);
        table2.row();


        // Add day label
        dayLabel = new Label("Day: Monday", labelStyle);
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


        table.setSize(350, 100);
        stage.addActor(table);

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

    public void resize(int width, int height) {

        stage.getViewport().update(width, height, true);
    }

}
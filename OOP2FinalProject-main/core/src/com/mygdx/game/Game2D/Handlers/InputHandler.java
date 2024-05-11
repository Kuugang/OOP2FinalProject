package com.mygdx.game.Game2D.Handlers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class InputHandler implements InputProcessor {
    public static boolean W, A, S, D;
    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.A){
            A = !A;
            return true;
        }

        if(keycode == Input.Keys.S){
            S = !S;
            return true;
        }

        if(keycode == Input.Keys.W){
            W = !W;
            return true;
        }

        if(keycode == Input.Keys.D){
            D = !D;
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return keyDown(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}

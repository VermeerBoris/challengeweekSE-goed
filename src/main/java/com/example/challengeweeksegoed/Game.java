package com.example.challengeweeksegoed;// Tip voor groepsgenoten: verwijder module-info.jav als je de onderstaande foutmelding krijgt
// 'Caused by: java.lang.module.InvalidModuleDescriptorException: Game.class found in top-level directory (unnamed package not allowed in module)'

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.LevelLoader;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.view.KeyView;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Map;

public class Game extends GameApplication {

    private Entity shieldGuy;
    private Entity rangeGuy;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(60 * 18);
        gameSettings.setHeight(60 * 18);
        gameSettings.setTitle("Dinsdag 12:25");
        gameSettings.setVersion("1.3");
    }

    @Override
    protected void initGame() {

        FXGL.getGameWorld().addEntityFactory(new GameEntityFactory());
        FXGL.spawn("background");

        setLevel(1);

        shieldGuy = FXGL.spawn("shieldguy", FXGL.getAppWidth() / 2, FXGL.getAppHeight() / 2);
        rangeGuy = FXGL.spawn("rangeguy", FXGL.getAppWidth() / 2, FXGL.getAppHeight() / 2);

//        Viewport viewport = FXGL.getGameScene().getViewport();
//        viewport.setBounds(-1500, 0, 250 * 70, FXGL.getAppHeight());
//        viewport.bindToEntity(shieldGuy, FXGL.getAppWidth() / 2, FXGL.getAppHeight() / 2);
//        viewport.setLazy(true);

//        FXGL.run(() -> FXGL.spawn("zombie", 100, 100), Duration.seconds(1));

    }

    @Override
    protected void initInput() {
        FXGL.getInput().addAction(new UserAction("RangeLeft") {
            @Override
            protected void onAction() {
                rangeGuy.getComponent(RangeGuyComponent.class).moveLeft();
            }

            protected void onActionEnd(){
                rangeGuy.getComponent(RangeGuyComponent.class).stop();
            }
        }, KeyCode.A, VirtualButton.LEFT);

        FXGL.getInput().addAction(new UserAction("RangeRight") {
            @Override
            protected void onAction() {
                rangeGuy.getComponent(RangeGuyComponent.class).moveRight();
            }

            protected void onActionEnd(){
                rangeGuy.getComponent(RangeGuyComponent.class).stop();
            }
        }, KeyCode.D, VirtualButton.RIGHT);

        FXGL.getInput().addAction(new UserAction("RangeJump") {
            @Override
            protected void onActionBegin() {
                rangeGuy.getComponent(RangeGuyComponent.class).jump();
            }

        }, KeyCode.W, VirtualButton.A);


        FXGL.onKeyDown(KeyCode.C, "shoot", () -> rangeGuy.getComponent(RangeGuyComponent.class).shoot());

        FXGL.getInput().addAction(new UserAction("ShieldLeft") {
            @Override
            protected void onAction() {
                shieldGuy.getComponent(ShieldGuyComponent.class).moveLeft();
            }

            protected void onActionEnd(){
                shieldGuy.getComponent(ShieldGuyComponent.class).stop();
            }
        }, KeyCode.LEFT, VirtualButton.LEFT);

        FXGL.getInput().addAction(new UserAction("ShieldRight") {
            @Override
            protected void onAction() {
                shieldGuy.getComponent(ShieldGuyComponent.class).moveRight();
            }

            protected void onActionEnd(){
                shieldGuy.getComponent(ShieldGuyComponent.class).stop();
            }
        }, KeyCode.RIGHT, VirtualButton.RIGHT);

        FXGL.getInput().addAction(new UserAction("ShieldJump") {
            @Override
            protected void onActionBegin() {
                shieldGuy.getComponent(ShieldGuyComponent.class).jump();
            }

        }, KeyCode.UP, VirtualButton.A);

        FXGL.onKeyDown(KeyCode.SPACE, "shield", () -> shieldGuy.getComponent(ShieldGuyComponent.class).shield());
    }

    @Override
    protected void initPhysics(){
        FXGL.onCollisionBegin(EntityTypes.ZOMBIE, EntityTypes.ARROW, (zombie, arrow) -> {
            zombie.removeFromWorld();
            arrow.removeFromWorld();
        });
        FXGL.onCollision(EntityTypes.RANGEGUY, EntityTypes.ZOMBIE, (rangeGuy, zombie) -> {
            rangeGuy.getComponent(RangeGuyComponent.class).loseHealth();
        });
    }



    @Override
    protected void initUI(){

    }

    @Override
    protected void initGameVars(Map<String, Object> vars){

    }

    private void setLevel(int levelNum){
        Level level = FXGL.setLevelFromMap("tmx/lostvikings_test.tmx");
    }

    public static void main (String[]args){
        launch(args);
    }
}
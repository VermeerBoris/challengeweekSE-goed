package com.example.challengeweeksegoed;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;


public class RangeGuyComponent extends Component {

    private PhysicsComponent physics;
    private int healthCounter = 3;
    private int jumps = 2;

    public void onAdded(){
        physics.onGroundProperty().addListener((obs, old, isOnGround) -> {
            if (isOnGround){
                jumps = 2;
            }
        });
    }

    public void stop(){
        physics.setVelocityX(0);
    }
    public void moveLeft(){
        physics.setVelocityX(-170);
        entity.setScaleX(0.5);
    }

    public void moveRight(){
        physics.setVelocityX(170);
        entity.setScaleX(-0.5);
    }

    public void jump(){
        if (jumps == 0){
            return;
        }

        physics.setVelocityY(-300);
        jumps--;
    }

    public void moveDown(){
        entity.translate(0, 3);
    }

    public void loseHealth(){
//        entity.getComponent(HealthComponent.class).setHealthCounter(healthCounter--);
        if (healthCounter == 0){
            return;
        }
        FXGL.spawn("heart", new SpawnData().put("healthCounter", healthCounter--));
    }

    public void shoot(){
        Point2D position = entity.getPosition();
        Vec2 dir = Vec2.fromAngle(entity.getRotation());

        FXGL.spawn("arrow", new SpawnData(position.getX(), position.getY() + (entity.getHeight() / 3)).put("dir", dir.toPoint2D()));
    }
}

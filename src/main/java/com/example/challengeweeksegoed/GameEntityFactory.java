package com.example.challengeweeksegoed;

import com.almasb.fxgl.core.util.Platform;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.dsl.components.RandomMoveComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GameEntityFactory implements EntityFactory {

    @Spawns("")
    public Entity newNothing (SpawnData data){
        return FXGL.entityBuilder(data)
                .viewWithBBox(new Circle(data.<Integer>get("width") / 2, Color.GOLD))
                .type(EntityTypes.COIN)
                .build();
    }

    @Spawns("background")
    public Entity newBackground(SpawnData data){
        return FXGL.entityBuilder()
                .from(data)
                .view(new Rectangle(FXGL.getAppWidth(), FXGL.getAppHeight()))
                .build();
    }

    @Spawns("platform")
    public Entity newPlatform(SpawnData data){
        return FXGL.entityBuilder(data)
                .type(EntityTypes.PLATFORM)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("coin")
    public Entity newCoin(SpawnData data){
        return FXGL.entityBuilder(data)
                .viewWithBBox(new Circle(data.<Integer>get("width") / 2, Color.GOLD))
                .collidable()
                .type(EntityTypes.COIN)
                .build();
    }

    @Spawns("shieldguy")
    public Entity newShieldGuy(SpawnData data){
        PhysicsComponent physics = new PhysicsComponent();
        physics.addGroundSensor(new HitBox("GROUND_SENSOR", new Point2D(5, 95), BoundingShape.box(90, 10)));
        physics.setFixtureDef(new FixtureDef().friction(0.0f));
        physics.setBodyType(BodyType.DYNAMIC);
        return FXGL.entityBuilder()
                .from(data)
                .viewWithBBox("shieldguy.png")
                .type(EntityTypes.SHIELDGUY)
                .collidable()
                .scale(0.5,0.5)
                .with(physics)
                .with(new ShieldGuyComponent())
                .build();
    }

    @Spawns("rangeguy")
    public Entity newRangeGuy(SpawnData data){
        PhysicsComponent physics = new PhysicsComponent();
        physics.addGroundSensor(new HitBox("GROUND_SENSOR", new Point2D(5, 95), BoundingShape.box(90, 10)));
        physics.setFixtureDef(new FixtureDef().friction(0.0f));
        physics.setBodyType(BodyType.DYNAMIC);
        return FXGL.entityBuilder()
                .from(data)
                .viewWithBBox("rangeguy.png")
                .type(EntityTypes.RANGEGUY)
                .collidable()
                .with(physics)
                .scale(0.5, 0.5)
                .with(new RangeGuyComponent())
                .build();
    }

    @Spawns("arrow")
    public Entity newArrow(SpawnData data){
        Point2D dir = data.get("dir");
        return FXGL.entityBuilder()
                .from(data)
                .type(EntityTypes.ARROW)
                .viewWithBBox("arrow.png")
                .collidable()
                .scale(0.5, 0.5)
                .with(new ProjectileComponent(dir, 400))
                .build();
    }

    @Spawns("enemyZombie")
    public Entity newZombie(SpawnData data){
        int patrolEndX = data.get("patrolEndX");
        return FXGL.entityBuilder(data)
                .type(EntityTypes.ZOMBIE)
                .collidable()
                .viewWithBBox("zombie.png")
                .scale(0.5, 0.5)
                .with(new EnemyZombieComponent(patrolEndX))
                .build();
    }

    @Spawns("heart")
    public Entity newHeart(SpawnData data){
        int healthCounter = data.get("healthCounter");
        return FXGL.entityBuilder(data)
                .type(EntityTypes.HEART)
                .view("heart" + healthCounter + ".png")
                .build();
    }
}

package com.example.challengeweeksegoed;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;

import java.awt.*;

public class HealthComponent extends Component {
    private int healthCounter;

    public HealthComponent(int healthCounter){
        this.healthCounter = healthCounter;
    }

    public void setHealthCounter(int healthCounter) {
        this.healthCounter = healthCounter;
    }


}

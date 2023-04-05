package com.example.challengeweeksegoed;

import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.awt.*;

public class EnemyZombieComponent extends Component {
        private int patrolEndX;
        private LocalTimer timer;
        private Duration duration;
        private double speed;
        private double distance;
        private boolean goingRight = true;
        public EnemyZombieComponent(int patrolEndX){
                this.patrolEndX = patrolEndX;

                duration = Duration.seconds(1.5);
        }

        public void onAdded(){
                distance = patrolEndX - entity.getX();
                timer = FXGL.newLocalTimer();
                timer.capture();
                speed = distance / duration.toSeconds();
        }

        public void onUpdate(double tpf){
            if (timer.elapsed(duration)){
                    goingRight = !goingRight;
                    timer.capture();
            }

            if (goingRight){
                    entity.translateX(speed * tpf);
            }
            else{
                    entity.translateX(-speed * tpf);
            }
        }

}

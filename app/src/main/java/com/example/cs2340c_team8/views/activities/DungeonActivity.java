package com.example.cs2340c_team8.views.activities;

import static com.example.cs2340c_team8.views.enemies.GameView.isFireballThrown;
import static com.example.cs2340c_team8.views.enemies.GameView.setFireballPosition;
import static java.lang.System.currentTimeMillis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.databinding.DataBindingUtil;

import com.example.cs2340c_team8.R;
import com.example.cs2340c_team8.databinding.DungeonBinding;
import com.example.cs2340c_team8.models.Player;
import com.example.cs2340c_team8.models.GameConfig;
import com.example.cs2340c_team8.viewModels.DungeonViewModel;
import com.example.cs2340c_team8.viewModels.LeaderboardViewModel;
import com.example.cs2340c_team8.views.LevelIndicatorView;
import com.example.cs2340c_team8.views.Thumbstick;
import com.example.cs2340c_team8.views.enemies.GameView;

import java.util.Timer;
import java.util.TimerTask;

public class DungeonActivity extends AppCompatActivity {
    private long startTime;
    private int score;
    private long scoreSeconds;
    private TextView timeElapsedTextView;
    private TextView scoreTextView;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController == null) {
            return;
        }
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());

        DungeonBinding dungeonScreenBinding = DataBindingUtil
                .setContentView(this, R.layout.dungeon);

        GameView gameView = findViewById(R.id.gameView);

        Player player = Player.getInstance();

        ConstraintLayout layout = findViewById(R.id.dungeonLayout);
        Thumbstick thumbstick = new Thumbstick(this,
                GameConfig.getThumbstickX(), GameConfig.getThumbstickY());
        layout.addView(thumbstick);

        ImageView sprite = findViewById(R.id.level_indicator_sprite);
        LevelIndicatorView levelIndicatorView = new LevelIndicatorView(this, sprite);
        layout.addView(levelIndicatorView);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    runTimer();
                    if (gameView.isGameCompleted()) {
                        loadEndingScreen();
                    }
                    if (GameView.isLevelChanged()) {
                        levelIndicatorView.update();
                        GameView.setLevelChanged(false);
                    }
                });
            }
        }, 0, 500);

        DungeonViewModel dungeonViewModel = new DungeonViewModel(DungeonActivity.this, timer);
        dungeonScreenBinding.setViewModel(dungeonViewModel);
        dungeonScreenBinding.setLifecycleOwner(this);
        dungeonScreenBinding.executePendingBindings();

        player.addObserver(dungeonViewModel);

        startTime = currentTimeMillis();
        timeElapsedTextView = findViewById(R.id.time_elapsed);
        scoreTextView = findViewById(R.id.score_indicator);

        Button attackEnemy = findViewById(R.id.attackEnemy);
        attackEnemy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFireballPosition();
                isFireballThrown = true;
            }
        });
    }

    public void loadEndingScreen() {
        LeaderboardViewModel.addNewScore(GameConfig.getUsername(),
                score, currentTimeMillis() - startTime);
        timer.cancel();

        Intent end = new Intent(DungeonActivity.this, LeaderboardActivity.class);
        end.putExtra("score", Integer.parseInt(((String) scoreTextView.getText())
                .split(" ")[1]));
        end.putExtra("time", timeElapsedTextView.getText());
        end.putExtra("keys", "3 of 3");
        end.putExtra("success", true);
        startActivity(end);
        finish();
    }


    protected void runTimer() {
        long runTime = currentTimeMillis() - startTime;
        long hours = runTime / (1000 * 60 * 60);
        long minutes = (runTime / (1000 * 60)) % 60;
        long seconds = (runTime / 1000) % 60;
        scoreSeconds = (runTime / 1000);

        score = 999 - (int) scoreSeconds;
        if (score < 0) {
            score = 0;
        }

        timeElapsedTextView.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
        scoreTextView.setText(String.format("Score: %02d", score));
    }

    public long getScore() {
        return score;
    }
}

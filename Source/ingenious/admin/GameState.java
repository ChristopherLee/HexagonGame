package ingenious.admin;

import com.google.java.contract.Invariant;

@Invariant("ordinal() >= 0")
public enum GameState {
    REGISTRATION, RUNGAME, GAMEOVER
}

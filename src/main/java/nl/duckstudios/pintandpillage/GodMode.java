package nl.duckstudios.pintandpillage;

public class GodMode {
    static public boolean hasGodMode() {
        return  Boolean.parseBoolean(System.getenv("GOD_MODE"));
    }
}

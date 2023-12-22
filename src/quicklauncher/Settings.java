package quicklauncher;

public class Settings {
    private int preferredDirection;
    private int scrollSpeed;
    private int startUp;
    
    public Settings(int preferredDirection, int scrollSpeed, int startUp) {
        this.preferredDirection = preferredDirection;
        this.scrollSpeed = scrollSpeed;
        this.startUp = startUp;
    }

    public int getPreferredDirection() {
        return preferredDirection;
    }

    public void setPreferredDirection(int preferredDirection) {
        this.preferredDirection = preferredDirection;
    }

    public int getScrollSpeed() {
        return scrollSpeed;
    }

    public void setScrollSpeed(int scrollSpeed) {
        this.scrollSpeed = scrollSpeed;
    }

    public int isStartUp() {
        return startUp;
    }

    public void setStartUp(int startUp) {
        this.startUp = startUp;
    }
    
}

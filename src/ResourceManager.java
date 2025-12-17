import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private static final Map<String, ImageIcon> imageCache = new HashMap<>();
    private static Clip backgroundMusic;

    public static ImageIcon getImage(String name) {
        if (imageCache.containsKey(name)) {
            return imageCache.get(name);
        }
        try {
            URL url = ResourceManager.class.getResource("/resources/" + name);
            if (url == null) {
                File file = new File("src/resources/" + name);
                if (file.exists()) {
                    url = file.toURI().toURL();
                }
            }
            
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                imageCache.put(name, icon);
                return icon;
            } else {
                System.err.println("Could not find image: " + name);
            }
        } catch (Exception e) {
            System.err.println("Error loading image " + name + ": " + e.getMessage());
        }
        return null;
    }

    public static ImageIcon getScaledImage(String name, int width, int height) {
        ImageIcon icon = getImage(name);
        if (icon != null) {
            Image img = icon.getImage();
            Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(newImg);
        }
        return null;
    }

    public static void playSound(String name) {
        new Thread(() -> {
            try {
                URL url = ResourceManager.class.getResource("/resources/" + name);
                if (url == null) {
                    File file = new File("src/resources/" + name);
                    if (file.exists()) {
                        url = file.toURI().toURL();
                    }
                }

                if (url != null) {
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioIn);
                    clip.start();
                } else {
                    System.err.println("Could not find sound: " + name);
                }
            } catch (Exception e) {
                System.err.println("Error playing sound " + name + ": " + e.getMessage());
            }
        }).start();
    }

    public static void loopMusic(String name) {
        stopMusic();
        new Thread(() -> {
            try {
                URL url = ResourceManager.class.getResource("/resources/" + name);
                if (url == null) {
                    File file = new File("src/resources/" + name);
                    if (file.exists()) {
                        url = file.toURI().toURL();
                    }
                }

                if (url != null) {
                    backgroundMusic = AudioSystem.getClip();
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                    backgroundMusic.open(audioIn);
                    backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
                    backgroundMusic.start();
                }
            } catch (Exception e) {
                System.err.println("Error playing music " + name + ": " + e.getMessage());
            }
        }).start();
    }

    public static void stopMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
            backgroundMusic.close();
        }
    }
}

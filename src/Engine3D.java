import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Engine3D {
    static Camera camera = new Camera(200, 100, -200);
    static Angle3D camAngle = camera.getAngle();
    static World world = new World();
    static JPanel renderPanel = new JPanel() {
        public void paintComponent(Graphics g) {
            if(w) moveForwards();
            if(a) moveLeft();
            if(s) moveBackwards();
            if(d) moveRight();
            if(space) moveUp();
            if(shift) moveDown();
            camAngle.x = MouseInfo.getPointerInfo().getLocation().x;
            camAngle.y = -MouseInfo.getPointerInfo().getLocation().y;
            cube.rotate(1, 1, 0);
            // if(camAngle.y > 360 / 4) {
            //     camAngle.y = 360/4;
            // } else if(camAngle.y < -360/4) {
            //     camAngle.y = -360/4;
            // }

            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.drawImage(camera.render(world), 0, 0, null);
        }
    };
    static Model cube = newCube(0, 0, 0, new Color(255, 255, 255));
    
    static boolean w = false;
    static boolean a = false;
    static boolean s = false;
    static boolean d = false;
    static boolean space = false;
    static boolean shift = false;
    static int mod = 0;
    public static void main(String[] args) {
        camera.setWorld(world);

        camAngle.x = -45;
        camAngle.y = -30;

        // Model sphere = new Model(0, 0, 0);
        // Sphere sph = new Sphere(new Point3D(0, 0, 0), 100);
        // sph.setColor(new Color(255, 255, 255));
        // sphere.addFace(sph);
        // world.addModel(sphere);

        // newCube(0, 25, 0, Color.BLUE);
        
        for(int i = 0; i < 10; i++) {
            double x = Math.random() * 1000 - 500;
            double y = Math.random() * 1000 - 500;
            double z = Math.random() * 1000 - 500;
            int r = (int) (Math.random() * 256);
            int g = (int) (Math.random() * 256);
            int b = (int) (Math.random() * 256);
            newCube(x, y, z, new Color(r, g, b));
        } 

        Light light = new Light(100, 100, 100);
        world.addLight(light);


        JFrame frame = new JFrame();
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());
        pane.add(renderPanel, BorderLayout.CENTER);

        Timer timer = new Timer();
        timer.schedule(new Painter(), 0, 1000 / 60);
        
        renderPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "moveForwards");
        renderPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.SHIFT_DOWN_MASK, false), "moveForwards");
        renderPanel.getActionMap().put("moveForwards", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                w = true;
            }
        });
        renderPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "moveForwards2");
        renderPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.SHIFT_DOWN_MASK, true), "moveForwards2");
        renderPanel.getActionMap().put("moveForwards2", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                w = false;
            }
        });

        renderPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "moveLeft");
        renderPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.SHIFT_DOWN_MASK), "moveLeft");
        renderPanel.getActionMap().put("moveLeft", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                a = true;
            }
        });
        renderPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "moveLeft2");
        renderPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.SHIFT_DOWN_MASK, true), "moveLeft2");
        renderPanel.getActionMap().put("moveLeft2", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                a = false;
            }
        });

        renderPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "moveBackwards");
        renderPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.SHIFT_DOWN_MASK), "moveBackwards");
        renderPanel.getActionMap().put("moveBackwards", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                s = true;
            }
        });
        renderPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "moveBackwards2");
        renderPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.SHIFT_DOWN_MASK, true), "moveBackwards2");
        renderPanel.getActionMap().put("moveBackwards2", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                s = false;
            }
        });

        renderPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "moveRight");
        renderPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.SHIFT_DOWN_MASK), "moveRight");
        renderPanel.getActionMap().put("moveRight", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                d = true;
            }
        });
        renderPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "moveRight2");
        renderPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.SHIFT_DOWN_MASK, true), "moveRight2");
        renderPanel.getActionMap().put("moveRight2", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                d = false;
            }
        });

        renderPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "moveUp");
        renderPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, KeyEvent.SHIFT_DOWN_MASK), "moveUp");
        renderPanel.getActionMap().put("moveUp", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                space = true;
            }
        });
        renderPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "moveUp2");
        renderPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, KeyEvent.SHIFT_DOWN_MASK, true), "moveUp2");
        renderPanel.getActionMap().put("moveUp2", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                space = false;
            }
        });
        
        renderPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, KeyEvent.SHIFT_DOWN_MASK, false), "moveDown");
        renderPanel.getActionMap().put("moveDown", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                shift = true;
            }
        });
        renderPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, 0, true), "moveDown2");
        renderPanel.getActionMap().put("moveDown2", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                shift = false;
            }
        });

        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    static void moveForwards() {
        double xRad = camAngle.getXRadians();
        camera.move(10 * Math.sin(xRad), 0, 10 * Math.cos(xRad));
    }

    static void moveBackwards() {
        double xRad = camAngle.getXRadians();
        camera.move(-10 * Math.sin(xRad), 0, -10 * Math.cos(xRad));
    }

    static void moveLeft() {
        double xRad = camAngle.getXRadians();
        camera.move(-10 * Math.cos(xRad), 0, 10 * Math.sin(xRad));
    }

    static void moveRight() {
        double xRad = camAngle.getXRadians();
        camera.move(10 * Math.cos(xRad), 0, -10 * Math.sin(xRad));
    }

    static void moveUp() {
        camera.move(0, 10, 0);
    }

    static void moveDown() {
        camera.move(0, -10, 0);
    }

    static void lookUp() {
        camAngle.y += 5;
        if(camAngle.y > 360 / 4) camAngle.y = 360/4;
    }

    static void lookDown() {
        camAngle.y -= 5;
        if(camAngle.y < -360/4) camAngle.y = -360/4;
    }

    static void lookLeft() {
        camAngle.x -= 5;
    }

    static void lookRight() {
        camAngle.x += 5;
    }

    static Model newCube(double x, double y, double z, Color color) {
        Model cube = new Model(x, y, z);
        Point3D[] cubePoints = {new Point3D(-50, -50, -50),
                                new Point3D( 50, -50, -50), // 1
                                new Point3D( 50, -50,  50), // 2
                                new Point3D(-50, -50,  50), // 3
                                new Point3D(-50,  50, -50), // 4
                                new Point3D( 50,  50, -50), // 5
                                new Point3D( 50,  50,  50), // 6
                                new Point3D(-50,  50,  50)};
        cube.addTriangle(cubePoints[0], cubePoints[1], cubePoints[3], color);
        cube.addTriangle(cubePoints[2], cubePoints[3], cubePoints[1], color);
        
        cube.addTriangle(cubePoints[4], cubePoints[7], cubePoints[5], color);
        cube.addTriangle(cubePoints[6], cubePoints[5], cubePoints[7], color);
        
        cube.addTriangle(cubePoints[0], cubePoints[4], cubePoints[1], color);
        cube.addTriangle(cubePoints[5], cubePoints[1], cubePoints[4], color);
        
        cube.addTriangle(cubePoints[1], cubePoints[5], cubePoints[2], color);
        cube.addTriangle(cubePoints[6], cubePoints[2], cubePoints[5], color);
        
        cube.addTriangle(cubePoints[2], cubePoints[6], cubePoints[3], color);
        cube.addTriangle(cubePoints[7], cubePoints[3], cubePoints[6], color);
        
        cube.addTriangle(cubePoints[3], cubePoints[7], cubePoints[0], color);
        cube.addTriangle(cubePoints[4], cubePoints[0], cubePoints[7], color);

        world.addModel(cube);
        return cube;
    }

    static class Painter extends TimerTask {
        public void run() {
            renderPanel.repaint();
        }
    }
}
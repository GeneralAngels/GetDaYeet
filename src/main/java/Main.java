import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;

public class Main {

    private static final int SIZE = 500;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Get Da Yeet");
//        frame.setUndecorated(true);
        frame.setSize(SIZE * 2, SIZE + frame.getHeight());
        JYeet yeet = new JYeet();
        frame.setContentPane(yeet);
        Communicator.Topic topic = new Communicator.Topic();
        topic.setCommand("follower get_da_yeet");
        topic.getBroadcast().listen(new Broadcast.Listener<String>() {
            @Override
            public void update(String thing) {
                yeet.array = new JSONArray(thing);
                yeet.repaint();
            }
        });
        topic.begin(5);
        frame.setVisible(true);
        frame.pack();
    }

    private static class JYeet extends JPanel {

        private JSONArray array;

        public JYeet() {
            setMinimumSize(new Dimension(SIZE * 2, SIZE));
            setMaximumSize(new Dimension(SIZE * 2, SIZE));
            setPreferredSize(new Dimension(SIZE * 2, SIZE));
        }

        @Override
        public void paint(Graphics g) {
            g.clearRect(0, 0, SIZE * 2, SIZE);
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, SIZE * 2, SIZE);
            g.setColor(Color.BLUE);
            if (array != null) {
                for (Object object : array) {
                    if (object instanceof JSONObject) {
                        JSONObject myObject = (JSONObject) object;
                        double x, y;
                        x = myObject.getDouble("x");
                        y = myObject.getDouble("y");
                        x *= 100;
                        y *= 100;
                        x += SIZE / 4; // Fucking offset
                        y += SIZE / 4;
                        g.fillRect((int) x, SIZE - (int) y, 4, 4);
                    }
                }
            }
        }
    }
}

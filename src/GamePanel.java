import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                g.setColor((i+j)%2==0 ? new Color(0xCCE792) : new Color(0x204204));
                g.fillRect(10+j*80, 10+i*80, 80, 80);
            }
        }
    }
}

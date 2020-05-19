package device;

import java.awt.Color;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import serial.DataController;

public class Processing extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2229516915657622986L;
	DataController ct = new DataController();	
	StringBuffer mainsbf;
	public Processing(String portNum, boolean envMode){
	 	setTitle("SSD v1.0");
        setSize(320, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setIconImage(new ImageIcon(getClass().getResource("images/myIcon.png")).getImage());
        JLabel myIcon = new JLabel();
        URL myIcon_url = this.getClass().getResource("images/processing.gif");
        myIcon.setIcon(new ImageIcon(myIcon_url));
        myIcon.setVerticalAlignment(JLabel.CENTER);
        myIcon.setBounds(16,14,288,346); 
        
        //[메인텍스트]	 
		mainsbf = new StringBuffer();	  
		 
        JPanel myPanel = new JPanel();
        myPanel.setLayout(null);
        myPanel.setBackground(Color.WHITE);
        myPanel.add(myIcon);
        add(myPanel);
        
        
        //[포트 연결]
        
  	     try {
  			
  			if(envMode){
  				ct.connectSensor(portNum, mainsbf,"57600");
  	        }else{
  	        	ct.connectSensor(portNum, mainsbf,"9600");
  	        }
  		} catch (Exception ee) {
  			System.out.println("Connection error");
  		}
        setVisible(true);
	}
}

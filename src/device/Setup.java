package device;

import jssc.SerialPortList;
import serial.DataController;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class Setup extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5505713683171148076L;
	private SSDMainProcess main;
	DataController ct = new DataController();	
	public Setup(){
		String ports[] = SerialPortList.getPortNames();
	 	setTitle("SSD v1.0");
        setSize(300, 380);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setIconImage(new ImageIcon(getClass().getResource("images/myIcon.png")).getImage());
	    /*
	     * 
	     * END SETUP
	     * 
	     */
	    
        JLabel msol = new JLabel();
        URL msol_logo = this.getClass().getResource("images/logo.png");
        msol.setIcon(new ImageIcon(msol_logo));
        msol.setVerticalAlignment(JLabel.CENTER);
        msol.setBounds(25,20,250,100); 
        
        JCheckBox mode = new JCheckBox("ENV",true);
        mode.setFont(new Font("나눔바른고딕",Font.BOLD,16));
        mode.setBounds(120,130,100,20);
        mode.setBackground(Color.white);
        mode.setFocusPainted(false);
        
        @SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cb = new JComboBox(ports);
        cb.setFont(new Font("나눔바른고딕",Font.BOLD,35));
        cb.setBackground(Color.white);	        
        cb.setBorder(new LineBorder(Color.DARK_GRAY, 1));
        ((JLabel) cb.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        cb.setOpaque(true);
        cb.setBounds(30,160,240,55);
        
        JButton link = new JButton();
        URL imgUrl2 = this.getClass().getResource("images/btn_link.png");
        link.setIcon(new ImageIcon(imgUrl2));	
        link.setBounds(25,235,250,80); 
        link.setBackground(null);
        link.setBorder(null);
        link.setFocusPainted(false);
        
        JPanel panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.add(mode);
        panel1.add(msol);
        panel1.add(cb);
        panel1.add(link);
        panel1.setBackground(Color.white);
        add(panel1);	        
        setVisible(true);
        
        link.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String portNum = cb.getSelectedItem().toString();
            	if(mode.isSelected()){
            		main.showMainFrame1(portNum, true);
            	}else{
            		main.showMainFrame1(portNum, false);
            	}
            }
        });
	}
	public void setMain(SSDMainProcess main) {
		this.main = main;
    }
}

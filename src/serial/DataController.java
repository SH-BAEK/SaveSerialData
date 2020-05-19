package serial;

import jssc.SerialPort;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import jssc.SerialPortException;
import jssc.SerialPortList;

public class DataController{	
	public DataController(){
	}
	ReadThread1 rt;
	ReadThread2 rt2;
	WriteThread1 wt;
	SerialPort serialPort;
	//연결 가능한 포트 목록 검색
	public String getPortList(){	
		StringBuilder portList = new StringBuilder();
		 String[] portNames = SerialPortList.getPortNames();
		    for(int i = 0; i < portNames.length; i++){
		    	portList.append(portNames[i]+",");
		    }		    
		    return portList.toString();
	}
	//포트연결
	public void connectSensor(String port,StringBuffer sbf,String buad){
		  serialPort = new SerialPort(port);	
		  rt = new ReadThread1(serialPort,sbf);
		  try{
			  serialPort.openPort();//Open serial port	
			  if(buad == "9600"){
				  serialPort.setParams(SerialPort.BAUDRATE_9600,
				                   SerialPort.DATABITS_8,
				                   SerialPort.STOPBITS_1,
				                   SerialPort.PARITY_NONE);		
				  rt2.start();
			  }else if(buad == "57600"){
				  serialPort.setParams(SerialPort.BAUDRATE_57600,
		                   SerialPort.DATABITS_8,
		                   SerialPort.STOPBITS_1,
		                   SerialPort.PARITY_NONE);
				  rt.start();
			  }
		  }catch(SerialPortException e){
		  }
	}
	//포트연결 해제
	@SuppressWarnings("deprecation")
	public void disconnectSensor() throws Exception{
		rt.stop();
		serialPort.closePort();
	}
	public SerialPort getSerialPort() {
		return serialPort;
	}
	public void setSerialPort(SerialPort serialPort) {
		this.serialPort = serialPort;
	}
}
// Serial에서 data를 읽어오는 쓰레드
class ReadThread1 extends Thread{
	SerialPort serial;
	StringBuffer sbf;
	ReadThread1(SerialPort serial,StringBuffer sbf){
		this.serial = serial;
		this.sbf = sbf;
	}
	public void run() {
		try {
			while (true) {				
				byte[] read = serial.readBytes();
				if(read != null && read.length > 0){					
					sbf.append((new String(read)));
					if(sbf.toString().indexOf("\n") > 0){
						if(sbf.toString().indexOf("[END]") > 0){
							Date myTime = new Date();				
							SimpleDateFormat  parseDate = new SimpleDateFormat("MMdd_yy");	
							SimpleDateFormat  inputTime = new SimpleDateFormat("YYYY-MM-dd HH:mm ");	
							String FilePath ="/home/Envdata/"+parseDate.format(myTime)+".txt";
						    FileWriter fileWriter = new FileWriter(FilePath, true);
						    fileWriter.write(inputTime.format(myTime));
						    fileWriter.write(sbf.toString());
							fileWriter.close();
							sbf = new StringBuffer();
						}else{
							sbf = new StringBuffer();
						}
					}
				}												
			}			
		} catch (Exception e) {
		}
	}
}

//Serial에서 data를 읽어오는 쓰레드
class ReadThread2 extends Thread{
	SerialPort serial;
	StringBuffer sbf;
	String before_Time  = "";
	ReadThread2(SerialPort serial,StringBuffer sbf){
		this.serial = serial;
		this.sbf = sbf;
	}
	public void run() {
		try {
			while (true) {				
				byte[] read = serial.readBytes();
				if(read != null && read.length > 0){					
					sbf.append((new String(read)));
					if(sbf.toString().indexOf("\n") > 0){ 
						Date myTime = new Date();				
						SimpleDateFormat  parseDate = new SimpleDateFormat("MMdd_yy");	
						SimpleDateFormat  inputTime = new SimpleDateFormat("YYYY-MM-dd HH:mm ");	
						String FilePath ="/home/data/"+parseDate.format(myTime)+".txt";
						if(before_Time != inputTime.format(myTime)){
							before_Time = inputTime.format(myTime);
						    FileWriter fileWriter = new FileWriter(FilePath, true);
						    fileWriter.write(inputTime.format(myTime));
						    fileWriter.write(sbf.toString());
							fileWriter.close();
						}
						sbf = new StringBuffer();
					}
				}												
			}			
		} catch (Exception e) {
		}
	}
}
//Serial에  data를 전송하는 쓰레드
class WriteThread1 extends Thread{
	SerialPort serial;
	WriteThread1(SerialPort serial){
		this.serial = serial;
	}
	public void run() {
		try {
			int c = 0;		
			while (true) {
				c = System.in.read();
				serial.writeInt(c);
			}
		} catch (Exception e) {
		}
	}
}


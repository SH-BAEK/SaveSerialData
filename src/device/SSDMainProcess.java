package device;

public class SSDMainProcess {
	Setup set;
	Processing ps;
	String portNum;
	public static void main(String[] args) {
		SSDMainProcess myMain = new SSDMainProcess();
		myMain.set = new Setup();
		myMain.set.setMain(myMain);
	}
	public void showMainFrame1(String portNum, boolean envMode) {
		this.portNum = portNum;
		set.dispose();
    	this.ps = new Processing(portNum, envMode);
    }
}

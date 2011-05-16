package com.yazo;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;




public class KetrisMIDlet extends MIDlet{

	KetrisCanvas ketris;

	public KetrisMIDlet(){
		ketris = new KetrisCanvas(this);
	}

	public void startApp(){
		Display.getDisplay(this).setCurrent(ketris);
	}

	public void pauseApp(){
	}

	public void destroyApp(boolean unc){
	}
}

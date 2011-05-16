package com.yazo;

import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class KetrisCanvas extends Canvas implements CommandListener, Runnable {

	// Midlet
	protected KetrisMIDlet ketris;
	protected Command exitCmd;
	protected Command startCmd;
	protected Command puseCmd;

	protected int game; /* ��Ϸ����״̬ */

	protected KetrisBlock block; /* ��ǰ��׹�� */
	protected KetrisMap map; /* ��Ϸ��ͼ */

	protected Thread thread; /* �ػ��߳�,���߳�ʵ����Ϸ�����ػ����� */

	/* counter��maxCount��2������������������Ϸ�ٶ� */
	protected int counter;
	protected int maxCount;

	protected final int GAME_INIT = 0; /* ��Ϸ��ʼ״̬ */
	protected final int GAME_RUN = 1; /* ��Ϸ����״̬ */
	protected final int GAME_OVER = 4; /* ��Ϸ����״̬ */
	protected final int GAME_START_DEMO = 9; /* demo״̬����ʾdemo�Ļ��� */
	protected final int GAME_SUSPEND = 9; /* ����״̬��ֵ��demo״̬ͬ */

	protected boolean startDemoFlag; /* �Ƿ��Ѿ���ʾ����ʼ���� */

	private static int mainWidth; /* ��Ļ���,��sun gray emulator��=180 */
	private static int mainHeight; /* ��Ļ�߶�,��sun gray emulator��=177 */

	public static int GAMEAREA_X; // ��Ϸ�������Ͻ�x����,��Ϸ����Ϊ��ߵ���Ϸ����������ұߵ���һ����׹����ʾ�������
	public static int GAMEAREA_Y; // ��Ϸ�������Ͻ�y����
	public static int BRICK_WIDTH; // Сש��ı߳�

	public static final int BACKGROUD = 0x00000000; // ������ɫ

	public KetrisCanvas(KetrisMIDlet ketris) {

		this.ketris = ketris;

		init();

		map = new KetrisMap();
		block = new KetrisBlock(map);

		startCmd = new Command("��ʼ", Command.OK, 0);
		exitCmd = new Command("�˳�", Command.EXIT, 0);
		puseCmd = new Command("��ͣ", Command.OK, 0);
		addCommand(startCmd);
		addCommand(exitCmd);
		setCommandListener(this);

		thread = new Thread(this);
		thread.start();

	}

	/* ��ʼ������ʾdemo������������� */
	protected void init() {

		this.mainHeight = getHeight();
		this.mainWidth = getWidth();

		// ����Сש����
		int min = mainWidth;
		if (mainHeight < min) {
			min = mainHeight;
		}

		// ��Ϸ����Ӧ���ܱ�16����
		for (; min >= 0; min--) {
			if (min % 16 == 0) {
				break;
			}
		}

		// ��Ϸ����Ϊmin�ķ���,��minΪ16�ı���
		BRICK_WIDTH = min / 16; // ש����
		GAMEAREA_X = (mainWidth - min) / 2;
		GAMEAREA_Y = (mainHeight - min) / 2;

		startDemoFlag = false; // ��û����ʾ��ʼ����
		game = GAME_START_DEMO; // ��Ϸ����demo����״̬

	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				break;
			}
			repaint();

		}
	}

	public void commandAction(Command c, Displayable d) {
		if (c == exitCmd) {
			ketris.destroyApp(false);
			ketris.notifyDestroyed();
		}
		if (c == startCmd) {
			// ��û�п�ʼ��Ϸ������Ϸ�ѽ���
			if (startDemoFlag == false || game == GAME_OVER) {
				startDemoFlag = true;
				game = GAME_INIT;
				removeCommand(startCmd);
				addCommand(puseCmd);
			} else {

				// ������Ϸ��
				game = GAME_RUN;
				removeCommand(startCmd);
				addCommand(puseCmd);
			}
		}
		if (c == puseCmd) {
			game = GAME_SUSPEND;
			removeCommand(puseCmd);
			addCommand(startCmd);
		}
	}

	protected synchronized void keyPressed(int keyCode) {
		int action = getGameAction(keyCode);

		if (action == Canvas.LEFT && game == GAME_RUN) { /* ���� */
			if (block.checkMove(1)) {
				block.move(1);
			}
		} else if (action == Canvas.RIGHT && game == GAME_RUN) { /* ���� */
			if (block.checkMove(2)) {
				block.move(2);
			}
		} else if (action == Canvas.UP && game == GAME_RUN) { /* ��׹��仯 */
			if (block.checkRot()) {
				block.rotBlock();
			}
		} else if (action == Canvas.DOWN && game == GAME_RUN) { /* ���� */
			if (block.checkDown(false)) {
				block.down();
			}
		} else if (action == Canvas.FIRE && game == GAME_RUN) { /* ��׹��仯 */
			if (block.checkRot()) {
				block.rotBlock();
			}
		}
	}

	public void paint(Graphics g) {
		Image img = null;
		if (game == GAME_START_DEMO) { /* ��Ϸ����demo����״̬ */
			if (!startDemoFlag) {
				try {
					img = Image.createImage("/logo.png");
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.drawImage(
						ImageUtil.effect_resizeImage(img, this.getWidth(),
								this.getHeight()), 0, 0, Graphics.TOP
								| Graphics.LEFT);
			}

			/* ��Ϸ��һ������ */
		} else if (game == GAME_INIT) {
			// ������Ϸ��ͼ(��������)
			map.init();
			map.paint(g);

			block.init();
			block.drawBlock(g);
			block.drawNextBlock(g);

			counter = 0;
			maxCount = 8;

			game = GAME_RUN;
		} else if (game == GAME_RUN) {

			counter++;
			if (counter >= maxCount) {
				if (block.checkDown(true)) {
					block.down();
					block.paint(g);

				} else {

					int y = block.getY();
					block.paint(g);
					block.fixBlock();
					if (map.check(g, y)) {
						map.repaintMap(g);
					}

					block.init();
					y = block.getY();
					if (y < 0) {
						game = GAME_OVER;
					}
					block.drawBlock(g);
					block.drawNextBlock(g);

				}
				counter = 0;
			} else {

				block.paint(g);
			}
		} else if (game == GAME_OVER) {

			g.setColor(BACKGROUD);
			g.fillRect(KetrisCanvas.GAMEAREA_X, KetrisCanvas.GAMEAREA_Y,
					16 * KetrisCanvas.BRICK_WIDTH,
					16 * KetrisCanvas.BRICK_WIDTH);

			g.setColor(255, 0, 0);
			g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD,
					Font.SIZE_LARGE));
			g.drawString("Game Over", KetrisCanvas.GAMEAREA_X + 8
					* KetrisCanvas.BRICK_WIDTH, KetrisCanvas.GAMEAREA_Y + 4
					* KetrisCanvas.BRICK_WIDTH, Graphics.BASELINE | Graphics.HCENTER);
			this.removeCommand(puseCmd);
			this.addCommand(startCmd);
		}
	}

	public static void clear(Graphics g) {
		g.setColor(0xffffff);
		g.fillRect(0, 0, mainWidth, mainHeight);
		g.setColor(BACKGROUD);
		g.fillRect(KetrisCanvas.GAMEAREA_X, KetrisCanvas.GAMEAREA_Y,
				16 * KetrisCanvas.BRICK_WIDTH, 16 * KetrisCanvas.BRICK_WIDTH);
	}

}

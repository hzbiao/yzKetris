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

	protected int game; /* 游戏运行状态 */

	protected KetrisBlock block; /* 当前下坠物 */
	protected KetrisMap map; /* 游戏地图 */

	protected Thread thread; /* 重画线程,该线程实现游戏画布重画机制 */

	/* counter，maxCount这2个变量是用来控制游戏速度 */
	protected int counter;
	protected int maxCount;

	protected final int GAME_INIT = 0; /* 游戏初始状态 */
	protected final int GAME_RUN = 1; /* 游戏运行状态 */
	protected final int GAME_OVER = 4; /* 游戏结束状态 */
	protected final int GAME_START_DEMO = 9; /* demo状态，显示demo的画面 */
	protected final int GAME_SUSPEND = 9; /* 挂起状态，值与demo状态同 */

	protected boolean startDemoFlag; /* 是否已经显示过开始画面 */

	private static int mainWidth; /* 屏幕宽度,在sun gray emulator上=180 */
	private static int mainHeight; /* 屏幕高度,在sun gray emulator上=177 */

	public static int GAMEAREA_X; // 游戏区域左上角x坐标,游戏区域为左边的游戏容器区域和右边的下一个下坠物显示区域组成
	public static int GAMEAREA_Y; // 游戏区域左上角y坐标
	public static int BRICK_WIDTH; // 小砖块的边长

	public static final int BACKGROUD = 0x00000000; // 背景颜色

	public KetrisCanvas(KetrisMIDlet ketris) {

		this.ketris = ketris;

		init();

		map = new KetrisMap();
		block = new KetrisBlock(map);

		startCmd = new Command("开始", Command.OK, 0);
		exitCmd = new Command("退出", Command.EXIT, 0);
		puseCmd = new Command("暂停", Command.OK, 0);
		addCommand(startCmd);
		addCommand(exitCmd);
		setCommandListener(this);

		thread = new Thread(this);
		thread.start();

	}

	/* 初始化，显示demo画面所需的设置 */
	protected void init() {

		this.mainHeight = getHeight();
		this.mainWidth = getWidth();

		// 计算小砖块宽度
		int min = mainWidth;
		if (mainHeight < min) {
			min = mainHeight;
		}

		// 游戏区域应该能被16整除
		for (; min >= 0; min--) {
			if (min % 16 == 0) {
				break;
			}
		}

		// 游戏区域为min的方形,且min为16的倍数
		BRICK_WIDTH = min / 16; // 砖块厚度
		GAMEAREA_X = (mainWidth - min) / 2;
		GAMEAREA_Y = (mainHeight - min) / 2;

		startDemoFlag = false; // 还没有显示开始画面
		game = GAME_START_DEMO; // 游戏处于demo画面状态

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
			// 还没有开始游戏或者游戏已结束
			if (startDemoFlag == false || game == GAME_OVER) {
				startDemoFlag = true;
				game = GAME_INIT;
				removeCommand(startCmd);
				addCommand(puseCmd);
			} else {

				// 处于游戏中
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

		if (action == Canvas.LEFT && game == GAME_RUN) { /* 左移 */
			if (block.checkMove(1)) {
				block.move(1);
			}
		} else if (action == Canvas.RIGHT && game == GAME_RUN) { /* 右移 */
			if (block.checkMove(2)) {
				block.move(2);
			}
		} else if (action == Canvas.UP && game == GAME_RUN) { /* 下坠块变化 */
			if (block.checkRot()) {
				block.rotBlock();
			}
		} else if (action == Canvas.DOWN && game == GAME_RUN) { /* 下移 */
			if (block.checkDown(false)) {
				block.down();
			}
		} else if (action == Canvas.FIRE && game == GAME_RUN) { /* 下坠块变化 */
			if (block.checkRot()) {
				block.rotBlock();
			}
		}
	}

	public void paint(Graphics g) {
		Image img = null;
		if (game == GAME_START_DEMO) { /* 游戏处于demo画面状态 */
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

			/* 游戏第一次启动 */
		} else if (game == GAME_INIT) {
			// 画出游戏地图(容器部分)
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

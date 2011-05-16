package com.yazo;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

/**
 * 游戏地图,地图高16个小砖块,宽16小砖块,但是游戏容器高16，宽12（包括左右2堵墙） 所以容器的内直径为10
 */

public class KetrisMap {

	// 地图数据
	protected int mapdata[][];
	protected boolean mapBlockExist[]; // 长度为16的boolean数组，如果mapBlockExist[i]=true,则第i+1行有砖块
	private int score; // 分数
	public static final Font SCOREFONT = Font.getFont(Font.FACE_SYSTEM,
			Font.STYLE_PLAIN, Font.SIZE_LARGE);

	public KetrisMap() {
		mapdata = new int[16][12];
		mapBlockExist = new boolean[16];

	}

	public void init() {
		// 清除计分
		score = 0;
		// 先把全部元素清0
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 12; j++) {
				mapdata[i][j] = 0;
			}
			mapBlockExist[i] = false;
		}

		// 设置2堵墙
		for (int i = 0; i < 16; i++) {
			mapdata[i][0] = 8;
			mapdata[i][11] = 8;
		}

		// 设置容器底
		for (int i = 0; i < 12; i++) {
			mapdata[15][i] = 8;
		}

		mapBlockExist[15] = true;
	}

	/**
	 * 获取地图某行某列的数据
	 * 
	 * @param x
	 *            int 行号
	 * @param y
	 *            int 列号
	 * @return int 地图数据，非0表示有砖块
	 */
	public int get(int x, int y) {
		int data = mapdata[y][x];

		return data;
	}

	/* 设置地图数据 */
	public void set(int x, int y, int val) {
		if (x >= 0 && y >= 0) {
			mapdata[y][x] = val;
			mapBlockExist[y] = true;
		}

	}

	/**
	 * 该方法其实只负责非运动砖块
	 * 
	 * @param g
	 *            Graphics
	 */
	public void paint(Graphics g) {
		// 清屏
		KetrisCanvas.clear(g);

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 12; j++) {
				if (mapdata[i][j] == 8) {
					KetrisBlock.drawBrick(KetrisCanvas.GAMEAREA_X + j
							* KetrisCanvas.BRICK_WIDTH, KetrisCanvas.GAMEAREA_Y
							+ i * KetrisCanvas.BRICK_WIDTH, g, 7);
				}
			}
		}
	}

	public boolean check(Graphics g, int row) {
		boolean deleteFlag = false;

		// 最多可以连消4行
		int tmpRowNo;
		if (row + 4 >= 15) {
			tmpRowNo = 15;
		} else {
			tmpRowNo = row + 4;
		}

		for (int y = row; y < tmpRowNo; y++) {
			boolean flag = true;

			for (int x = 1; x < 11; x++) {
				if (mapdata[y][x] == 0) { /* 空白区 */
					flag = false;
				}
			}

			/* 需要消行 */
			if (flag) {
				mapBlockExist[y] = false;
				for (int x = 1; x < 11; x++) {
					mapdata[y][x] = 0;
				}

				deleteRow(g, y);

				deleteFlag = true;
				// 加分
				score += 10;
				paintScore(g);
				// 发声
				// try {
				// if (player != null) {
				// player.start();
				// }
				//
				// // }
				// catch (MediaException me) {
				// System.out.println("sound not availible");
				// }

			}
		}

		return deleteFlag;
	}

	// 删除行，只是简单的把该行置黑
	protected void deleteRow(Graphics g, int y) {
		g.setColor(KetrisCanvas.BACKGROUD);
		g.fillRect(KetrisCanvas.GAMEAREA_X + KetrisCanvas.BRICK_WIDTH,
				KetrisCanvas.GAMEAREA_Y + y * KetrisCanvas.BRICK_WIDTH,
				10 * KetrisCanvas.BRICK_WIDTH, KetrisCanvas.BRICK_WIDTH);

	}

	/* 该方法在有消去行为后调用 */
	public void repaintMap(Graphics g) {
		// 从容启底开始
		for (int i = 14; i > 0; i--) {
			int tmp;

			// 有砖块的行才移动
			if (mapBlockExist[i]) {
				// 只有下一行为空白行才进行移动
				if (!mapBlockExist[i + 1]) {
					tmp = i + 1;

					if (!mapBlockExist[i + 2]) {
						tmp = i + 2;

						if (!mapBlockExist[i + 3]) {
							tmp = i + 3;
						}
					}
					deleteRow(g, i);
					// 行复制
					for (int j = 1; j < 11; j++) {
						mapdata[tmp][j] = mapdata[i][j];
						mapdata[i][j] = 0;
					}
					mapBlockExist[i] = false;
					mapBlockExist[tmp] = true;

					drawBlock(g, tmp);
				}
			}
		}
	}

	public void drawBlock(Graphics g, int y) {
		for (int x = 1; x < 11; x++) {
			if (mapdata[y][x] != 0) {
				KetrisBlock.drawBrick(KetrisCanvas.GAMEAREA_X + x
						* KetrisCanvas.BRICK_WIDTH, KetrisCanvas.GAMEAREA_Y + y
						* KetrisCanvas.BRICK_WIDTH, g, mapdata[y][x] - 1);

			}
		}
	}

	private void paintScore(Graphics g) {
		// 清除记分牌
		g.setColor(KetrisCanvas.BACKGROUD);
		g.fillRect(KetrisCanvas.GAMEAREA_X + 12 * KetrisCanvas.BRICK_WIDTH,
				KetrisCanvas.GAMEAREA_Y + 6 * KetrisCanvas.BRICK_WIDTH,
				KetrisCanvas.BRICK_WIDTH * 4, KetrisCanvas.BRICK_WIDTH * 4);
		// 计分
		g.setColor(0, 255, 0);
		g.setFont(SCOREFONT);
		g.drawString("" + score, KetrisCanvas.GAMEAREA_X + 14
				* KetrisCanvas.BRICK_WIDTH, KetrisCanvas.GAMEAREA_Y + 8
				* KetrisCanvas.BRICK_WIDTH, Graphics.BASELINE | Graphics.HCENTER);
	}

}

package com.yazo;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

/**
 * ��Ϸ��ͼ,��ͼ��16��Сש��,��16Сש��,������Ϸ������16����12����������2��ǽ�� ������������ֱ��Ϊ10
 */

public class KetrisMap {

	// ��ͼ����
	protected int mapdata[][];
	protected boolean mapBlockExist[]; // ����Ϊ16��boolean���飬���mapBlockExist[i]=true,���i+1����ש��
	private int score; // ����
	public static final Font SCOREFONT = Font.getFont(Font.FACE_SYSTEM,
			Font.STYLE_PLAIN, Font.SIZE_LARGE);

	public KetrisMap() {
		mapdata = new int[16][12];
		mapBlockExist = new boolean[16];

	}

	public void init() {
		// ����Ʒ�
		score = 0;
		// �Ȱ�ȫ��Ԫ����0
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 12; j++) {
				mapdata[i][j] = 0;
			}
			mapBlockExist[i] = false;
		}

		// ����2��ǽ
		for (int i = 0; i < 16; i++) {
			mapdata[i][0] = 8;
			mapdata[i][11] = 8;
		}

		// ����������
		for (int i = 0; i < 12; i++) {
			mapdata[15][i] = 8;
		}

		mapBlockExist[15] = true;
	}

	/**
	 * ��ȡ��ͼĳ��ĳ�е�����
	 * 
	 * @param x
	 *            int �к�
	 * @param y
	 *            int �к�
	 * @return int ��ͼ���ݣ���0��ʾ��ש��
	 */
	public int get(int x, int y) {
		int data = mapdata[y][x];

		return data;
	}

	/* ���õ�ͼ���� */
	public void set(int x, int y, int val) {
		if (x >= 0 && y >= 0) {
			mapdata[y][x] = val;
			mapBlockExist[y] = true;
		}

	}

	/**
	 * �÷�����ʵֻ������˶�ש��
	 * 
	 * @param g
	 *            Graphics
	 */
	public void paint(Graphics g) {
		// ����
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

		// ����������4��
		int tmpRowNo;
		if (row + 4 >= 15) {
			tmpRowNo = 15;
		} else {
			tmpRowNo = row + 4;
		}

		for (int y = row; y < tmpRowNo; y++) {
			boolean flag = true;

			for (int x = 1; x < 11; x++) {
				if (mapdata[y][x] == 0) { /* �հ��� */
					flag = false;
				}
			}

			/* ��Ҫ���� */
			if (flag) {
				mapBlockExist[y] = false;
				for (int x = 1; x < 11; x++) {
					mapdata[y][x] = 0;
				}

				deleteRow(g, y);

				deleteFlag = true;
				// �ӷ�
				score += 10;
				paintScore(g);
				// ����
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

	// ɾ���У�ֻ�Ǽ򵥵İѸ����ú�
	protected void deleteRow(Graphics g, int y) {
		g.setColor(KetrisCanvas.BACKGROUD);
		g.fillRect(KetrisCanvas.GAMEAREA_X + KetrisCanvas.BRICK_WIDTH,
				KetrisCanvas.GAMEAREA_Y + y * KetrisCanvas.BRICK_WIDTH,
				10 * KetrisCanvas.BRICK_WIDTH, KetrisCanvas.BRICK_WIDTH);

	}

	/* �÷���������ȥ��Ϊ����� */
	public void repaintMap(Graphics g) {
		// �������׿�ʼ
		for (int i = 14; i > 0; i--) {
			int tmp;

			// ��ש����в��ƶ�
			if (mapBlockExist[i]) {
				// ֻ����һ��Ϊ�հ��вŽ����ƶ�
				if (!mapBlockExist[i + 1]) {
					tmp = i + 1;

					if (!mapBlockExist[i + 2]) {
						tmp = i + 2;

						if (!mapBlockExist[i + 3]) {
							tmp = i + 3;
						}
					}
					deleteRow(g, i);
					// �и���
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
		// ����Ƿ���
		g.setColor(KetrisCanvas.BACKGROUD);
		g.fillRect(KetrisCanvas.GAMEAREA_X + 12 * KetrisCanvas.BRICK_WIDTH,
				KetrisCanvas.GAMEAREA_Y + 6 * KetrisCanvas.BRICK_WIDTH,
				KetrisCanvas.BRICK_WIDTH * 4, KetrisCanvas.BRICK_WIDTH * 4);
		// �Ʒ�
		g.setColor(0, 255, 0);
		g.setFont(SCOREFONT);
		g.drawString("" + score, KetrisCanvas.GAMEAREA_X + 14
				* KetrisCanvas.BRICK_WIDTH, KetrisCanvas.GAMEAREA_Y + 8
				* KetrisCanvas.BRICK_WIDTH, Graphics.BASELINE | Graphics.HCENTER);
	}

}

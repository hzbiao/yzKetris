package com.yazo;

import javax.microedition.lcdui.*;

import java.util.Random;



public class KetrisBlock {



  //����ש��,1-7Ϊ�ש����ɫ��8Ϊǽש��ɫ
  public static final int[] BRICK_COLORS = {
      0x00FF0000, 0x0000FF00, 0x00FFFF00, 0x000000FF, 0x00FF00FF, 0x0000FFFF,
      0x00C0DCC0, 0x00808080};

  /**
   * blockpattern�ı������:blockpattern��ʾһ����׹�������״,һ����׹�����ɫ�ǹ̶��ġ�
   * ����һ����׹���һ����ά�����ʾ����һά��rot��ʾ����תֵ�����ڶ�ά��x��Ҳ�����У�,����ά��y��ʾ��Ҳ�����У���
   * ���� blockpattern1�����ּ�������ת��״
   * blockpattern2����L�ּ�������ת��״
   * blockpattern3��L�ּ�������ת��״
   * blockpattern4��1�ּ�������ת��״
   * ........................
   */

  protected int blockpattern1[][][] = {
      {
      {
      0, 0, 0, 0}
      , {
      0, 1, 1, 0}
      , {
      0, 1, 1, 0}
      , {
      0, 0, 0, 0}
  }
      , {
      {
      0, 0, 0, 0}
      , {
      0, 1, 1, 0}
      , {
      0, 1, 1, 0}
      , {
      0, 0, 0, 0}
  }
      , {
      {
      0, 0, 0, 0}
      , {
      0, 1, 1, 0}
      , {
      0, 1, 1, 0}
      , {
      0, 0, 0, 0}
  }
      , {
      {
      0, 0, 0, 0}
      , {
      0, 1, 1, 0}
      , {
      0, 1, 1, 0}
      , {
      0, 0, 0, 0}
  }
  };

  protected int blockpattern2[][][] = {
      {
      {
      0, 0, 1, 0}
      , {
      0, 0, 1, 0}
      , {
      0, 1, 1, 0}
      , {
      0, 0, 0, 0}
  }
      , {
      {
      0, 0, 0, 0}
      , {
      0, 1, 0, 0}
      , {
      0, 1, 1, 1}
      , {
      0, 0, 0, 0}
  }
      , {
      {
      0, 0, 0, 0}
      , {
      0, 1, 1, 0}
      , {
      0, 1, 0, 0}
      , {
      0, 1, 0, 0}
  }
      , {
      {
      0, 0, 0, 0}
      , {
      1, 1, 1, 0}
      , {
      0, 0, 1, 0}
      , {
      0, 0, 0, 0}
  }
  };

  protected int blockpattern3[][][] = {
      {
      {
      0, 1, 0, 0}
      , {
      0, 1, 0, 0}
      , {
      0, 1, 1, 0}
      , {
      0, 0, 0, 0}
  }
      , {
      {
      0, 0, 0, 0}
      , {
      0, 1, 1, 1}
      , {
      0, 1, 0, 0}
      , {
      0, 0, 0, 0}
  }
      , {
      {
      0, 0, 0, 0}
      , {
      0, 1, 1, 0}
      , {
      0, 0, 1, 0}
      , {
      0, 0, 1, 0}
  }
      , {
      {
      0, 0, 0, 0}
      , {
      0, 0, 1, 0}
      , {
      1, 1, 1, 0}
      , {
      0, 0, 0, 0}
  }
  };

  protected int blockpattern4[][][] = {
      {
      {
      0, 0, 1, 0}
      , {
      0, 0, 1, 0}
      , {
      0, 0, 1, 0}
      , {
      0, 0, 1, 0}
  }
      , {
      {
      0, 0, 0, 0}
      , {
      0, 0, 0, 0}
      , {
      1, 1, 1, 1}
      , {
      0, 0, 0, 0}
  }
      , {
      {
      0, 0, 1, 0}
      , {
      0, 0, 1, 0}
      , {
      0, 0, 1, 0}
      , {
      0, 0, 1, 0}
  }
      , {
      {
      0, 0, 0, 0}
      , {
      0, 0, 0, 0}
      , {
      1, 1, 1, 1}
      , {
      0, 0, 0, 0}
  }
  };

  protected int blockpattern5[][][] = {
      {
      {
      0, 0, 0, 0}
      , {
      1, 1, 0, 0}
      , {
      0, 1, 1, 0}
      , {
      0, 0, 0, 0}
  }
      , {
      {
      0, 0, 1, 0}
      , {
      0, 1, 1, 0}
      , {
      0, 1, 0, 0}
      , {
      0, 0, 0, 0}
  }
      , {
      {
      0, 0, 0, 0}
      , {
      1, 1, 0, 0}
      , {
      0, 1, 1, 0}
      , {
      0, 0, 0, 0}
  }
      , {
      {
      0, 0, 1, 0}
      , {
      0, 1, 1, 0}
      , {
      0, 1, 0, 0}
      , {
      0, 0, 0, 0}
  }
  };

  protected int blockpattern6[][][] = {
      {
      {
      0, 0, 0, 0}
      , {
      0, 1, 1, 0}
      , {
      1, 1, 0, 0}
      , {
      0, 0, 0, 0}
  }
      , {
      {
      0, 1, 0, 0}
      , {
      0, 1, 1, 0}
      , {
      0, 0, 1, 0}
      , {
      0, 0, 0, 0}
  }
      , {
      {
      0, 0, 0, 0}
      , {
      0, 1, 1, 0}
      , {
      1, 1, 0, 0}
      , {
      0, 0, 0, 0}
  }
      , {
      {
      0, 1, 0, 0}
      , {
      0, 1, 1, 0}
      , {
      0, 0, 1, 0}
      , {
      0, 0, 0, 0}
  }
  };

  protected int blockpattern7[][][] = {
      {
      {
      0, 0, 0, 0}
      , {
      1, 1, 1, 0}
      , {
      0, 1, 0, 0}
      , {
      0, 0, 0, 0}
  }
      , {
      {
      0, 1, 0, 0}
      , {
      1, 1, 0, 0}
      , {
      0, 1, 0, 0}
      , {
      0, 0, 0, 0}
  }
      , {
      {
      0, 1, 0, 0}
      , {
      1, 1, 1, 0}
      , {
      0, 0, 0, 0}
      , {
      0, 0, 0, 0}
  }
      , {
      {
      0, 1, 0, 0}
      , {
      0, 1, 1, 0}
      , {
      0, 1, 0, 0}
      , {
      0, 0, 0, 0}
  }
  };

  private int blockpattern[][][]; /* ��ǰ׹����״��Ϊ���϶�����߸���׹��֮һ */
  private int blockNextpattern[][]; /* ��һ��׹����״����ʾ����Ϸ�������ұ� */

  private int x; //blockpattern���Ͻ�x����,x=i��ʾ���ϽǾ�����Ϸ�������Ͻ�x����i��Сש�鵥λ
  private int y; //blockpattern���Ͻ�y����,y=i��ʾ���ϽǾ�����Ϸ�������Ͻ�y����i��Сש�鵥λ
  private int oldx; //x�ľ�ֵ
  private int oldy; //y�ľ�ֵ
  private int rot; //��תֵ��0-3
  private int oldrot; //��ת��ֵ
  private int pattern; /* ��ɵ�ǰ׹������Сש��id(1-7),ͬʱҲ��ʾһ����׹����״ */
  private int next; /* �����һ��׹������Сש��id(1-7),ͬʱҲ��ʾһ����׹����״ */

  private KetrisMap map;
  protected Random rand;

  /* ���죬����map,��ʼ��blockimage,rand,next */
  public KetrisBlock(KetrisMap map) {
    this.map = map;
    rand = new Random();
    next =Math.abs(rand.nextInt()) % 7 + 1;
  }

  /* ��ʼ�� */
  protected void init() {

    pattern = next;
    next = Math.abs(rand.nextInt()) % 7 + 1;

    /* �õ���ǰ��׹�� */
    switch (pattern) {
      case 1:
        readPattern(blockpattern1);
        break;
      case 2:
        readPattern(blockpattern2);
        break;
      case 3:
        readPattern(blockpattern3);
        break;
      case 4:
        readPattern(blockpattern4);
        break;
      case 5:
        readPattern(blockpattern5);
        break;
      case 6:
        readPattern(blockpattern6);
        break;
      case 7:
        readPattern(blockpattern7);
        break;
    }

    /* �õ���һ����׹�� */
    switch (next) {
      case 1:
        readNextPattern(blockpattern1);
        break;
      case 2:
        readNextPattern(blockpattern2);
        break;
      case 3:
        readNextPattern(blockpattern3);
        break;
      case 4:
        readNextPattern(blockpattern4);
        break;
      case 5:
        readNextPattern(blockpattern5);
        break;
      case 6:
        readNextPattern(blockpattern6);
        break;
      case 7:
        readNextPattern(blockpattern7);
        break;
    }

    x = 5; /* ��Ϸ�����ھ���һ�� */
    y = 0; /*     y���� */
    rot = 0;




    //�ж�map���ݣ�����y������ֵ,֮������ô��������Ϊ��game over��ʱ�����һ����׹�����ֻ�ܻ���һ����
    //Ϊ�˴ﵽ���Ч��,������y��Ϊһ��ǡ���ĸ�ֵ

    while (isCrashAtBegin()) {
      y--;
      if(y<-4){
        break;
      }
    }

    oldx = x;
    oldy = y;

    oldrot = rot;

  }

  /**
   * ���õ�ǰ��׹�����������
   * @param nowblock int[][][] 7����׹�ﳣ��֮һ
   */

  private void readPattern(int[][][] nowblock) {
    blockpattern = new int[4][4][4];

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 4; k++) {
          blockpattern[i][j][k] = nowblock[i][j][k];
        }
      }
    }
  }

  /**
   * ������һ����׹����������ݡ�ֻ��Ҫ����4����ת�仯�еĵ�һ�ּ��ɣ�����rotάֵ=0
   * @param nowblock int[][][] 7����׹�ﳣ��֮һ
   */

  private void readNextPattern(int[][][] nowblock) {
    blockNextpattern = new int[4][4];

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        blockNextpattern[i][j] = nowblock[0][i][j];
      }
    }
  }

  /* ��ת��׹�� */
  protected void rotBlock() {
    rot++;

    if (rot == 4) {
      rot = 0;
    }
  }

  /**
   * ������׹����������׹��ľ�ͼ�񣬵��û�����׹����ͼ��ĺ���
   * @param g Graphics
   */

  public synchronized void paint(Graphics g) {

    //���3ά��û�б仯���������ػ�
    if ( (oldrot != rot) || (oldx != x) || (oldy != y)) {

      //�����ͼ��
      g.setColor(KetrisCanvas.BACKGROUD);
      for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
          if (blockpattern[oldrot][i][j] == 1) {
            g.fillRect(KetrisCanvas.GAMEAREA_X +
                       (oldx + j) * KetrisCanvas.BRICK_WIDTH,
                       KetrisCanvas.GAMEAREA_Y +
                       (oldy + i) * KetrisCanvas.BRICK_WIDTH,
                       KetrisCanvas.BRICK_WIDTH, KetrisCanvas.BRICK_WIDTH);
          }
        }
      }

      drawBlock(g);
      oldrot = rot;
      oldx = x;
      oldy = y;
    }

  }

  /**
   * ������׹��
   * @param g Graphics
   */

  public void drawBlock(Graphics g) {
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        if (blockpattern[rot][i][j] == 1) {
          drawBrick(KetrisCanvas.GAMEAREA_X +
                    (x + j) * KetrisCanvas.BRICK_WIDTH,
                    KetrisCanvas.GAMEAREA_Y +
                    (y + i) * KetrisCanvas.BRICK_WIDTH, g, pattern - 1);
        }
      }
    }
  }

  /**
   * �ж���׹���ǲ��Ǻ�map�����е�ש���ص�,Ϊ�˴���gameover��ʱ��ֻ�軭��������׹������
    @return true:���ص�,false:���ص�
   */

  public boolean isCrashAtBegin() {
    //��
    for (int i = 3; i >= 0; i--) {
      //��
      for (int j = 0; j < 4; j++) {
        int mx = x + j;

        int my = y + i;
        if (my < 0) {
          my = 0;
        }

        if (blockpattern[rot][i][j] == 1 && map.get(mx, my) != 8 &&
            map.get(mx, my) != 0) {
          return true;

        }
      }
    }
    return false;
  }

  /**
   * ��Сש��
   * @param px x����
   * @param py y����
   * @param g Graphics
   * @param colorIndex ��ɫ����ֵ
   */

  public static void drawBrick(int px, int py, Graphics g, int colorIndex) {
    //���ױ�
    g.setColor(255, 255, 255);
    g.fillRect(px, py, 1, KetrisCanvas.BRICK_WIDTH);
    g.fillRect(px, py, KetrisCanvas.BRICK_WIDTH, 1);
    //������
    int color = BRICK_COLORS[colorIndex];
    g.setColor(color);
    g.fillRect(px + 1, py + 1, KetrisCanvas.BRICK_WIDTH - 1,
               KetrisCanvas.BRICK_WIDTH - 1);
    //���ұ�
    g.setColor(0x00c0c0c0);
    g.fillRect(px + KetrisCanvas.BRICK_WIDTH - 1, py + 1, 1,
               KetrisCanvas.BRICK_WIDTH - 1);
    g.fillRect(px + 1, py + KetrisCanvas.BRICK_WIDTH - 1,
               KetrisCanvas.BRICK_WIDTH - 2, 1);
  }

  /**
   * ����Ϸ�������ұ߻����һ����׹��
   * @param g Graphics
   */

  public void drawNextBlock(Graphics g) {
    //�����������
    g.setColor(KetrisCanvas.BACKGROUD);
    int px = KetrisCanvas.GAMEAREA_X + 12 * KetrisCanvas.BRICK_WIDTH;
    int py = KetrisCanvas.GAMEAREA_Y + 2 * KetrisCanvas.BRICK_WIDTH;
    int width = KetrisCanvas.BRICK_WIDTH * 4;
    g.fillRect(px, py, width, width);

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        if (blockNextpattern[i][j] == 1) {
          drawBrick(px + j * KetrisCanvas.BRICK_WIDTH,
                    py + i * KetrisCanvas.BRICK_WIDTH, g, next - 1);
        }
      }
    }
  }

  /**
   * �ж���׹���Ƿ�������
   * @param kyouseiflag boolean true:�Զ����� false:�˹���������
   * @return boolean
   */
  public boolean checkDown(boolean kyouseiflag) {

    boolean check = true;

    /* �ֱ�ɨ����׹���4�У�������������п�ʼ */
    for (int i = 0; i < 4; i++) {
      int row = 3;
      while (row >= 0) {
        if (blockpattern[rot][row][i] == 1) {
          if (map.get(x + i, y + row + 1) != 0) {
            check = false;
          }

          row = -1; /* ��ֹѭ�� */
        }
        else {
          row--;
        }
      }
    }

    return check;
  }

  /* ��׹������1�� */
  public void down() {
    y = y + 1;
  }

  /* �ж��Ƿ�����ת */
  public boolean checkRot() {
    boolean check = true;

    int tmpRot = rot + 1;
    if (tmpRot == 4) {
      tmpRot = 0;
    }

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        if (blockpattern[tmpRot][i][j] == 1) {
          if (map.get(x + j, y + i) != 0) {
            check = false;
          }
        }
      }
    }

    return check;
  }

  /* �ж���׹���Ƿ�����ƶ� */
  public boolean checkMove(int direct) {

    boolean check = true;

    /* �ֱ�ɨ����׹���4�� */
    for (int i = 0; i < 4; i++) {
      if (direct == 1) { /* ���� */
        int row = 0;
        while (row <= 3) {
          if (blockpattern[rot][i][row] == 1) {
            if (map.get(x + row - 1, y + i) != 0) {
              check = false;
            }

            row = 4; /* ��ֹѭ�� */
          }
          else {
            row++;
          }
        }
      }
      else { /* ���� */
        int row = 3;
        while (row >= 0) {
          if (blockpattern[rot][i][row] == 1) {
            if (map.get(x + row + 1, y + i) != 0) {
              check = false;
            }

            row = -1; /* ��ֹѭ�� */
          }
          else {
            row--;
          }
        }
      }
    }

    return check;
  }

  /* �����ƶ� */
  public void move(int direct) {
    if (direct == 1) {
      x = x - 1;
    }
    else {
      x = x + 1;
    }
  }

  public int getY() {
    return y;
  }

  /* ������׹��ĵ�ǰλ�����õ�ͼ���� */
  public void fixBlock() {
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        if (blockpattern[rot][i][j] == 1) {
          map.set(x + j, y + i, pattern);

        }
      }
    }
  }
}

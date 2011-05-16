package com.yazo;

import javax.microedition.lcdui.*;

import java.util.Random;



public class KetrisBlock {



  //各种砖块,1-7为活动砖块颜色，8为墙砖颜色
  public static final int[] BRICK_COLORS = {
      0x00FF0000, 0x0000FF00, 0x00FFFF00, 0x000000FF, 0x00FF00FF, 0x0000FFFF,
      0x00C0DCC0, 0x00808080};

  /**
   * blockpattern的编码规则:blockpattern表示一个下坠物体的形状,一种下坠物的颜色是固定的。
   * 对于一个下坠物，用一个三维数祖表示，第一维用rot表示（旋转值），第二维用x（也就是行）,第三维用y表示（也就是列）。
   * 所以 blockpattern1：田字及四种旋转形状
   * blockpattern2：反L字及四种旋转形状
   * blockpattern3：L字及四种旋转形状
   * blockpattern4：1字及四种旋转形状
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

  private int blockpattern[][][]; /* 当前坠物形状，为以上定义的七个下坠物之一 */
  private int blockNextpattern[][]; /* 下一个坠物形状，显示在游戏容器的右边 */

  private int x; //blockpattern左上角x坐标,x=i表示左上角距离游戏容器左上角x轴上i个小砖块单位
  private int y; //blockpattern左上角y坐标,y=i表示左上角距离游戏容器左上角y轴上i个小砖块单位
  private int oldx; //x的旧值
  private int oldy; //y的旧值
  private int rot; //旋转值，0-3
  private int oldrot; //旋转旧值
  private int pattern; /* 组成当前坠物所用小砖块id(1-7),同时也表示一种下坠物形状 */
  private int next; /* 组成下一个坠物所用小砖块id(1-7),同时也表示一种下坠物形状 */

  private KetrisMap map;
  protected Random rand;

  /* 构造，保存map,初始化blockimage,rand,next */
  public KetrisBlock(KetrisMap map) {
    this.map = map;
    rand = new Random();
    next =Math.abs(rand.nextInt()) % 7 + 1;
  }

  /* 初始化 */
  protected void init() {

    pattern = next;
    next = Math.abs(rand.nextInt()) % 7 + 1;

    /* 得到当前下坠物 */
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

    /* 得到下一个下坠物 */
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

    x = 5; /* 游戏容器内径的一半 */
    y = 0; /*     y坐标 */
    rot = 0;




    //判断map数据，决定y的真正值,之所以这么处理，是因为当game over的时候，最后一个下坠物，可能只能画出一部分
    //为了达到这个效果,必须让y成为一个恰当的负值

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
   * 设置当前下坠物变量的内容
   * @param nowblock int[][][] 7种下坠物常量之一
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
   * 设置下一个下坠物变量的内容。只需要包存4中旋转变化中的第一种即可，所以rot维值=0
   * @param nowblock int[][][] 7种下坠物常量之一
   */

  private void readNextPattern(int[][][] nowblock) {
    blockNextpattern = new int[4][4];

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        blockNextpattern[i][j] = nowblock[0][i][j];
      }
    }
  }

  /* 旋转下坠物 */
  protected void rotBlock() {
    rot++;

    if (rot == 4) {
      rot = 0;
    }
  }

  /**
   * 绘制下坠物，包括清除下坠物的旧图像，调用绘制下坠物新图像的函数
   * @param g Graphics
   */

  public synchronized void paint(Graphics g) {

    //如果3维都没有变化，则无需重画
    if ( (oldrot != rot) || (oldx != x) || (oldy != y)) {

      //清除旧图形
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
   * 绘制下坠物
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
   * 判断下坠物是不是和map中已有的砖块重叠,为了处理gameover的时候，只需画出部分下坠物的情况
    @return true:有重叠,false:无重叠
   */

  public boolean isCrashAtBegin() {
    //行
    for (int i = 3; i >= 0; i--) {
      //列
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
   * 画小砖块
   * @param px x坐标
   * @param py y坐标
   * @param g Graphics
   * @param colorIndex 颜色索引值
   */

  public static void drawBrick(int px, int py, Graphics g, int colorIndex) {
    //画白边
    g.setColor(255, 255, 255);
    g.fillRect(px, py, 1, KetrisCanvas.BRICK_WIDTH);
    g.fillRect(px, py, KetrisCanvas.BRICK_WIDTH, 1);
    //画中心
    int color = BRICK_COLORS[colorIndex];
    g.setColor(color);
    g.fillRect(px + 1, py + 1, KetrisCanvas.BRICK_WIDTH - 1,
               KetrisCanvas.BRICK_WIDTH - 1);
    //画灰边
    g.setColor(0x00c0c0c0);
    g.fillRect(px + KetrisCanvas.BRICK_WIDTH - 1, py + 1, 1,
               KetrisCanvas.BRICK_WIDTH - 1);
    g.fillRect(px + 1, py + KetrisCanvas.BRICK_WIDTH - 1,
               KetrisCanvas.BRICK_WIDTH - 2, 1);
  }

  /**
   * 在游戏容器的右边绘出下一个下坠物
   * @param g Graphics
   */

  public void drawNextBlock(Graphics g) {
    //清除绘制区域
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
   * 判断下坠物是否能下移
   * @param kyouseiflag boolean true:自动下移 false:人工按键下移
   * @return boolean
   */
  public boolean checkDown(boolean kyouseiflag) {

    boolean check = true;

    /* 分别扫描下坠物的4行，从最下面的那行开始 */
    for (int i = 0; i < 4; i++) {
      int row = 3;
      while (row >= 0) {
        if (blockpattern[rot][row][i] == 1) {
          if (map.get(x + i, y + row + 1) != 0) {
            check = false;
          }

          row = -1; /* 终止循环 */
        }
        else {
          row--;
        }
      }
    }

    return check;
  }

  /* 下坠物下移1行 */
  public void down() {
    y = y + 1;
  }

  /* 判断是否能旋转 */
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

  /* 判断下坠物是否可以移动 */
  public boolean checkMove(int direct) {

    boolean check = true;

    /* 分别扫描下坠物的4行 */
    for (int i = 0; i < 4; i++) {
      if (direct == 1) { /* 左移 */
        int row = 0;
        while (row <= 3) {
          if (blockpattern[rot][i][row] == 1) {
            if (map.get(x + row - 1, y + i) != 0) {
              check = false;
            }

            row = 4; /* 终止循环 */
          }
          else {
            row++;
          }
        }
      }
      else { /* 右移 */
        int row = 3;
        while (row >= 0) {
          if (blockpattern[rot][i][row] == 1) {
            if (map.get(x + row + 1, y + i) != 0) {
              check = false;
            }

            row = -1; /* 终止循环 */
          }
          else {
            row--;
          }
        }
      }
    }

    return check;
  }

  /* 左右移动 */
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

  /* 根据下坠物的当前位置设置地图数据 */
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

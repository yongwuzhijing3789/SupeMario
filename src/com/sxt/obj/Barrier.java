package com.sxt.obj;
import com.sxt.util.BackGround;
import com.sxt.util.GameAssets;
import java.awt.*;
import java.awt.image.BufferedImage;
// 表示游戏世界中的障碍物对象。
public class Barrier implements Runnable {
    private int x; // 障碍物的 x 坐标
    private int y; // 障碍物的 y 坐标
    private int type; // 障碍物类型
    private BufferedImage show; // 显示障碍物的图像
    private BackGround bg; // 背景对象的引用
    private Thread thread; // 用于移动障碍物的线程
    //构造障碍物对象。
    public Barrier(int x, int y, int type, BackGround bg) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.bg = bg;
        this.show = GameAssets.barrier.get(type);
        // 如果是旗子类型的障碍物，启动一个线程进行动画
        if (type == 8) {
            thread = new Thread(this);
            thread.start();
        }
    }
    //获取障碍物的 x 坐标。
    public int getX() {return x;}
    //获取障碍物的 y 坐标。
    public int getY() {return y;}
    //获取障碍物的类型。
    public int getType() {return type;}
    //设置障碍物的类型。
    public void setType(int type) {this.type = type;}
    //获取显示障碍物的图像。
    public BufferedImage getShow() {return show;}
    //线程运行方法，用于实现障碍物的动画效果。
    @Override
    public void run() {
        while (true) {
            if (bg.isReach()) {
                if (y < 374) {
                    y += 5; // 向下移动障碍物
                } else {
                    bg.setBase(true); // 设置背景的基础条件
                }
            }
            try {
                Thread.sleep(50); // 等待下一次更新
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //获取表示障碍物边界的矩形。
    public Rectangle getRec() {return new Rectangle(x, y, 30, 30);
// 假设障碍物大小为 30x30 像素
    }
}

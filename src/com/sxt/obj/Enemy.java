package com.sxt.obj;
import com.sxt.util.BackGround;
import com.sxt.util.GameAssets;
import java.awt.*;
import java.awt.image.BufferedImage;
//表示游戏世界中的敌人对象。
public class Enemy implements Runnable {
    private int x; // 敌人的 x 坐标
    private int y; // 敌人的 y 坐标
    private int type; // 敌人类型
    public boolean face_to = true; // 敌人的移动方向，默认向右
    private BufferedImage show; // 显示敌人的当前图像
    private BackGround bg; // 背景对象的引用
    private int max_up = 0; // 食人花向上移动的极限位置
    private int max_down = 0; // 食人花向下移动的极限位置
    private Thread thread = new Thread(this); // 定义一个线程对象
    private int image_type = 0; // 当前图像的状态
    public Enemy() {
    }
    //蘑菇敌人的构造函数。
    public Enemy(int x, int y, boolean face_to, int type, BackGround bg) {
        this.x = x;
        this.y = y;
        this.face_to = face_to;
        this.type = type;
        this.bg = bg;
        show = GameAssets.mogu.get(0); // 设置蘑菇敌人的初始图像
        thread.start(); // 启动敌人线程
    }
    //食人花敌人的构造函数。
    public Enemy(int x, int y, boolean face_to, int type, int max_up, int max_down, BackGround bg) {
        this.x = x;
        this.y = y;
        this.face_to = face_to;
        this.type = type;
        this.max_up = max_up;
        this.max_down = max_down;
        this.bg = bg;
        show = GameAssets.flower.get(0); // 设置食人花敌人的初始图像
        thread.start(); // 启动敌人线程
    }
    //敌人死亡方法，根据不同的敌人类型执行不同的死亡动作。
    public void death() {
        if (type == 1) { // 蘑菇敌人
            show = GameAssets.mogu.get(1); // 显示蘑菇敌人死亡图像
            bg.getEnemyList().remove(this); // 从背景的敌人列表中移除
        } else if (type == 3) { // 乌龟敌人
            type = 4; // 改变为壳子类型
        } else if (type == 4) { // 壳子
            show = GameAssets.shell.get(0); // 显示壳子图像
            bg.getEnemyList().remove(this); // 从背景的敌人列表中移除
        } else if (type == 2) { // 食人花敌人
            bg.getEnemyList().remove(this); // 从背景的敌人列表中移除
        }
    }
    // 获取敌人的 x 坐标。
    public int getX(){return x;}
    //获取敌人的 y 坐标。
    public int getY() {return y;}
    //设置敌人的 y 坐标。
    public void setY(int y) { this.y = y;}
    //获取当前显示的敌人图像。
    public BufferedImage getShow() {return show;}
    //获取敌人的类型。
    public int getType() {return type;}
    //获取表示敌人边界的矩形。
    public Rectangle getRec() {return new Rectangle(x, y, 35, 35); }
    //敌人线程的运行方法，用于实现敌人的移动和动画效果。
    @Override
    public void run() {
        while (true) {
            if (type == 1) { // 蘑菇敌人
                if (face_to) {
                    x -= 2; // 向左移动
                } else {
                    x += 2; // 向右移动
                }
                image_type = image_type == 1 ? 0 : 1; // 切换图像状态
                show = GameAssets.mogu.get(image_type); // 更新显示的图像
            } else if (type == 3) { // 乌龟敌人
                if (face_to) {
                    x -= 2; // 向左移动
                    show = GameAssets.toise_L.get(image_type); // 左移动态图像
                } else {
                    x += 2; // 向右移动
                    show = GameAssets.toise_R.get(image_type); // 右移动态图像
                }
                image_type = image_type == 1 ? 0 : 1; // 切换图像状态
            } else if (type == 4) { // 壳子
                if (face_to) {
                    x -= 5; // 向左快速移动
                } else {
                    x += 5; // 向右快速移动
                }
                image_type = image_type == 1 ? 0 : 1; // 切换图像状态
                show = GameAssets.shell.get(image_type); // 更新显示的图像
            }
            boolean canLeft = true; // 是否可以向左移动
            boolean canRight = true; // 是否可以向右移动
            // 遍历背景中的障碍物
            for (int i = 0; i < bg.getbarrierList().size(); i++) {
                Barrier ob1 = bg.getbarrierList().get(i);
                // 判断是否可以向右移动
                if (ob1.getX() == x + 36 && (ob1.getY() + 50 > y && ob1.getY() - 26 < y)) {
                    canRight = false;
                }
                // 判断是否可以向左移动
                if (ob1.getX() == x - 36 && (ob1.getY() + 50 > y && ob1.getY() - 26 < y)) {
                    canLeft = false;
                }
            }
            // 根据移动方向更新 face_to
            if ((face_to && !canLeft) || x == 20) {
                face_to = false;
            } else if ((!face_to && !canRight) || x == 754) {
                face_to = true;
            }
            // 食人花敌人特有的移动逻辑
            if (type == 2) {
                if (face_to) {
                    y -= 2; // 向上移动
                } else {
                    y += 2; // 向下移动
                }
                image_type = image_type == 1 ? 0 : 1; // 切换图像状态
                // 达到极限位置时改变移动方向
                if (face_to && y == max_up) {
                    face_to = false;
                }
                if (!face_to && y == max_down) {
                    face_to = true;
                }
                show = GameAssets.flower.get(image_type); // 更新显示的图像
            }
            try {
                Thread.sleep(50); // 等待下一次更新
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

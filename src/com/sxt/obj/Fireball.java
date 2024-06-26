package com.sxt.obj;
import com.sxt.util.BackGround;
import com.sxt.util.GameAssets;
import java.awt.*;
import java.awt.image.BufferedImage;
// 表示游戏中的火球对象。
public class Fireball implements Runnable {
    private int x; // 火球的 x 坐标
    private int y; // 火球的 y 坐标
    private boolean face_to = true; // 火球的移动方向，默认向左
    private BufferedImage show; // 显示火球的当前图像
    private BackGround bg; // 背景对象的引用
    private int image_type = 0; // 当前图像的状态
    private Thread thread = new Thread(this); // 线程对象
    // 构造函数，初始化火球对象。
    public Fireball(int x, int y, boolean face_to, BackGround bg) {
        this.x = x;
        this.y = y;
        this.face_to = face_to;
        this.bg = bg;
        show = face_to ? GameAssets.fireball_L.get(0) : GameAssets.fireball_R.get(0); // 根据移动方向设置初始图像
        thread.start(); // 启动火球线程
    }
    // 火球线程的运行方法，实现火球的移动和碰撞检测。
    @Override
    public void run() {
        while (true) {
            if (face_to) { // 向左移动
                x -= 10;
                show = GameAssets.fireball_L.get(image_type); // 更新显示的图像
            } else { // 向右移动
                x += 10;
                show = GameAssets.fireball_R.get(image_type); // 更新显示的图像
            }
            image_type = image_type == 1 ? 0 : 1; // 切换图像状态
            // 检测火球与障碍物的碰撞
            for (int i = 0; i < bg.getbarrierList().size(); i++) {
                Barrier ob1 = bg.getbarrierList().get(i);
                if (getRec().intersects(ob1.getRec())) { // 碰撞检测
                    y = 900; // 将火球移出屏幕
                    bg.getfireballList().remove(this); // 从背景的火球列表中移除
                }
            }
            // 检测火球与敌人的碰撞
            for (int i = 0; i < bg.getEnemyList().size(); i++) {
                Enemy enemy = bg.getEnemyList().get(i);
                if (getRec().intersects(enemy.getRec())) { // 碰撞检测
                    y = 900; // 将火球移出屏幕
                    bg.getfireballList().remove(this); // 从背景的火球列表中移除
                    enemy.death(); // 敌人死亡
                }
            }
            try {
                Thread.sleep(50); // 等待下一次更新
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    // 获取火球的 x 坐标。
    public int getX() {return x;}
    // 设置火球的 x 坐标。
    public void setX(int x) {this.x = x;}
    // 获取火球的 y 坐标。
    public int getY() {return y;}
    // 设置火球的 y 坐标。
    public void setY(int y) {this.y = y;}
    // 获取火球的移动方向。
    public boolean isFace_to() {return face_to;}
    // 设置火球的移动方向。
    public void setFace_to(boolean face_to) {this.face_to = face_to;}
    // 获取当前显示的火球图像。
    public BufferedImage getShow() {return show;}
    // 设置当前显示的火球图像。
    public void setShow(BufferedImage show) {this.show = show;}
    // 获取背景对象。
    public BackGround getBg() {return bg;}
    // 设置背景对象。
    public void setBg(BackGround bg) {this.bg = bg;}
    // 获取火球的边界矩形。
    public Rectangle getRec() {return new Rectangle(x, y, 15, 15); }
}

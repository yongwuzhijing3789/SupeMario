package com.sxt.obj;
import com.sxt.util.BackGround;
import com.sxt.util.GameAssets;
import java.awt.image.BufferedImage;
// 表示游戏中的道具对象。
public class GameItem implements Runnable {
    private int x; // 道具的 x 坐标
    private int y; // 道具的 y 坐标
    private int type; // 道具的类型
    private BufferedImage show; // 显示道具当前图像
    private BackGround bg; // 背景对象的引用
    private Thread thread = new Thread(this); // 线程对象
    private int image_type = 0; // 当前图像的状态
    // 默认构造函数
    public GameItem() {}
    // 蘑菇道具的构造函数
    public GameItem(int x, int y, int type, BackGround bg) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.bg = bg;
        show = GameAssets.mogu.get(0); // 初始化显示为蘑菇图像
        thread.start(); // 启动道具线程
    }
    // 吃道具方法，从背景的道具列表中移除自身
    public void eat() {
        this.bg.getgameitemList().remove(this);
    }
    // 获取道具的 x 坐标
    public int getX() {return x;}
    // 获取道具的 y 坐标
    public int getY() {return y;}
    // 获取道具的类型
    public int getType() {return type;}
    // 获取显示道具当前图像
    public BufferedImage getShow() {return show;}
    // 道具线程的运行方法，实现道具的动画效果
    @Override
    public void run() {
        while (true) {
            // 根据道具类型设置不同的显示图像
            // 如果是蘑菇道具
            if (type == 0) {
                image_type = image_type == 1 ? 0 : 1; // 切换图像状态
                show = GameAssets.mg.get(image_type); // 更新显示的蘑菇图像
            }
            // 如果是小花道具
            if (type == 1) {show = GameAssets.xh; // 设置显示的小花图像}
                // 如果是金币道具
                if (type == 2) {
                    image_type = image_type == 1 ? 0 : 1; // 切换图像状态
                    show = GameAssets.jinBi.get(image_type); // 更新显示的金币图像
                }
                try {
                    Thread.sleep(50); // 等待下一次更新
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

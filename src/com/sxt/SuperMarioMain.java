package com.sxt;
import com.sxt.obj.*;
import com.sxt.util.BackGround;
import com.sxt.util.Music;
import com.sxt.util.GameAssets;
import javazoom.jl.decoder.JavaLayerException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
public class SuperMarioMain extends JFrame implements KeyListener, Runnable {
    // 用于存储所有的背景
    private List<BackGround> allBg = new ArrayList<>();
    // 当前背景
    private BackGround nowBg = new BackGround();
    // 双缓冲图像
    private Image offScreenImage = null;
    // 马里奥对象
    private Mario mario = new Mario();
    // 线程对象，用于马里奥的运动
    private Thread thread = new Thread(this);
    public SuperMarioMain() {
        // 设置窗口大小为800 * 600
        this.setSize(800, 600);
        // 窗口居中显示
        this.setLocationRelativeTo(null);
        // 设置窗口可见
        this.setVisible(true);
        // 点击关闭窗口结束程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 窗口大小不可变
        this.setResizable(false);
        // 添加键盘监听器
        this.addKeyListener(this);
        // 窗口标题
        this.setTitle("超级玛丽奥");
        // 初始化图片资源
        GameAssets.init();
        // 初始化马里奥
        mario = new Mario(10, 355, this.getWidth(), this.getHeight());
        // 创建所有场景
        for (int i = 1; i <= 3; i++) {
            allBg.add(new BackGround(i, i == 3));
        }
        // 将第一个场景设置为当前场景
        nowBg = allBg.get(0);
        mario.setBackGround(nowBg);
        // 绘制图像
        repaint();
        // 启动线程
        thread.start();
        try {
            new Music();
        } catch (FileNotFoundException | JavaLayerException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void paint(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = createImage(800, 600);
        }
        Graphics graphics = offScreenImage.getGraphics();
        graphics.fillRect(0, 0, 800, 600);
        // 绘制背景
        graphics.drawImage(nowBg.getBgImage(), 0, 0, this);
        // 绘制敌人
        for (Enemy e : nowBg.getEnemyList()) {
            graphics.drawImage(e.getShow(), e.getX(), e.getY(), this);
        }
        // 绘制道具
        for (GameItem d : nowBg.getgameitemList()) {
            graphics.drawImage(d.getShow(), d.getX(), d.getY(), this);
        }
        // 绘制障碍物
        for (Barrier ob : nowBg.getbarrierList()) {
            graphics.drawImage(ob.getShow(), ob.getX(), ob.getY(), this);
        }
        // 绘制城堡
        graphics.drawImage(nowBg.getTower(), 620, 270, this);
        // 绘制旗杆
        graphics.drawImage(nowBg.getGan(), 500, 220, this);
        // 绘制火球
        for (Fireball hq : nowBg.getfireballList()) {
            graphics.drawImage(hq.getShow(), hq.getX(), hq.getY(), this);
        }
        // 绘制马里奥
        if (!mario.isBig()) {
            graphics.drawImage(mario.getShow(), mario.getX(), mario.getY(), 25, 25, this);
        } else {
            graphics.drawImage(mario.getShow(), mario.getX(), mario.getY() - 15, 28, 40, this);
        }
        // 添加分数显示
        Color c = graphics.getColor();
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("黑体", Font.BOLD, 25));
        graphics.drawString("当前的分数为: " + mario.getScore(), 300, 100);
        graphics.setColor(c);
        // 将图像绘制到窗口中
        g.drawImage(offScreenImage, 0, 0, this);
    }
    public static void main(String[] args) {
        SuperMarioMain myFrame = new SuperMarioMain();
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        // 键盘按下事件处理
        switch (e.getKeyCode()) {
            case KeyEvent.VK_D: // 右移键为D键
                mario.rightMove();
                break;
            case KeyEvent.VK_A: // 左移键为A键
                mario.leftMove();
                break;
            case KeyEvent.VK_K: // 跳跃键为K键
                mario.jump();
                break;
            case KeyEvent.VK_J: // 攻击键为J键
                mario.fire();
                break;
            default:
                break;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        // 键盘松开事件处理
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A: // 左移键为A键
                mario.leftStop();
                break;
            case KeyEvent.VK_D: // 右移键为D键
                mario.rightStop();
                break;
            default:
                break;
        }
    }
    @Override
    public void run() {
        // 游戏运行逻辑
        while (true) {
            repaint();
            try {
                Thread.sleep(50);
                // 检测是否到达边界
                if (mario.getX() >= 775) {
                    nowBg = allBg.get(nowBg.getSort());
                    mario.setBackGround(nowBg);
                    mario.setX(10);
                    mario.setY(355);
                }
                // 检测马里奥状态
                if (mario.isDeath()) {
                    JOptionPane.showMessageDialog(this, "游戏结束！");
                    System.exit(0);
                }
                if (mario.isOK()) {
                    JOptionPane.showMessageDialog(this, "通关成功！");
                    System.exit(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


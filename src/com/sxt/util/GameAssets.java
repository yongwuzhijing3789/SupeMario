package com.sxt.util;
import com.sxt.obj.Enemy;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//游戏资源管理类，用于加载游戏中使用的所有图片资源
public class GameAssets {
    // 背景图片
    public static BufferedImage bg = null;
    public static BufferedImage bg2 = null;
    // 马里奥向左跳跃
    public static BufferedImage jump_L = null;
    // 马里奥向右跳跃
    public static BufferedImage jump_R = null;
    // 马里奥向左站立
    public static BufferedImage stand_L = null;
    // 马里奥向右站立
    public static BufferedImage stand_R = null;
    // 城堡
    public static BufferedImage tower = null;
    // 蘑菇道具
    public static List<BufferedImage> mg = new ArrayList<>();
    // 小花道具
    public static BufferedImage xh = null;
    // 旗杆
    public static BufferedImage gan = null;
    // 火球
    public static List<BufferedImage> fireball_L = new ArrayList<>();
    public static List<BufferedImage> fireball_R = new ArrayList<>();
    // 障碍物
    public static List<BufferedImage> barrier = new ArrayList<>();
    // 马里奥向左跑
    public static List<BufferedImage> run_L = new ArrayList<>();
    // 马里奥向右跑
    public static List<BufferedImage> run_R = new ArrayList<>();
    // 蘑菇敌人
    public static List<BufferedImage> mogu = new ArrayList<>();
    // 食人花敌人
    public static List<BufferedImage> flower = new ArrayList<>();
    // 乌龟敌人向左
    public static List<BufferedImage> toise_L = new ArrayList<>();
    // 乌龟敌人向右
    public static List<BufferedImage> toise_R = new ArrayList<>();
    // 龟壳
    public static List<BufferedImage> shell = new ArrayList<>();
    // 金币
    public static List<BufferedImage> jinBi = new ArrayList<>();
    // 图片路径前缀
    public static String path = System.getProperty("user.dir") + "/src/images/";
    static Enemy enemy = new Enemy();
    // 初始化游戏资源方法，加载所有图片资源
    public static void init() {
        try {
            // 加载背景图片
            bg = ImageIO.read(new File(path + "bg.png"));
            bg2 = ImageIO.read(new File(path + "bg2.png"));
            // 加载马里奥站立图片
            stand_L = ImageIO.read(new File(path + "s_mario_stand_L.png"));
            stand_R = ImageIO.read(new File(path + "s_mario_stand_R.png"));
            // 加载城堡图片
            tower = ImageIO.read(new File(path + "tower.png"));
            // 加载旗杆图片
            gan = ImageIO.read(new File(path + "gan.png"));
            // 加载马里奥跳跃图片
            jump_L = ImageIO.read(new File(path + "s_mario_jump1_L.png"));
            jump_R = ImageIO.read(new File(path + "s_mario_jump1_R.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加载道具蘑菇
        for (int i = 1; i <= 3; i++) {
            try {
                mg.add(ImageIO.read(new File(path + "mushroom" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 加载道具小花
        try {
            xh = ImageIO.read(new File(path + "xiaohua.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加载火球图片
        try {
            fireball_L.add(ImageIO.read(new File(path + "huoqiu2.png")));
            fireball_R.add(ImageIO.read(new File(path + "huoqiu1.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加载金币图片
        for (int i = 1; i <= 4; i++) {
            try {
                jinBi.add(ImageIO.read(new File(path + "jinbi" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 加载马里奥跑步图片
        for (int i = 1; i <= 2; i++) {
            try {
                run_L.add(ImageIO.read(new File(path + "s_mario_run" + i + "_L.png")));
                run_R.add(ImageIO.read(new File(path + "s_mario_run" + i + "_R.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            // 加载障碍物图片
            barrier.add(ImageIO.read(new File(path + "brick.png")));
            barrier.add(ImageIO.read(new File(path + "soil_up.png")));
            barrier.add(ImageIO.read(new File(path + "soil_base.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加载水管图片
        for (int i = 1; i <= 4; i++) {
            try {
                barrier.add(ImageIO.read(new File(path + "pipe" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            // 加载不可破坏的砖块和旗子图片
            barrier.add(ImageIO.read(new File(path + "brick2.png")));
            barrier.add(ImageIO.read(new File(path + "flag.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加载乌龟敌人向左图片
        for (int i = 1; i <= 2; i++) {
            try {
                toise_L.add(ImageIO.read(new File(path + "Ltortoise" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 加载乌龟敌人向右图片
        for (int i = 1; i <= 2; i++) {
            try {
                toise_R.add(ImageIO.read(new File(path + "Rtortoise" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 加载龟壳图片
        for (int i = 1; i <= 4; i++) {
            try {
                shell.add(ImageIO.read(new File(path + "shell" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 加载蘑菇敌人图片
        for (int i = 1; i <= 3; i++) {
            try {
                mogu.add(ImageIO.read(new File(path + "fungus" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 加载食人花敌人图片
        for (int i = 1; i <= 2; i++) {
            try {
                flower.add(ImageIO.read(new File(path + "flower1." + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 加载道具方块图片
        for (int i = 1; i <= 4; i++) {
            try {
                barrier.add(ImageIO.read(new File(path + "box1." + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 加载火球图片
        try {
            fireball_R.add(ImageIO.read(new File(path + "huoqiu1.png")));
            fireball_L.add(ImageIO.read(new File(path + "huoqiu2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

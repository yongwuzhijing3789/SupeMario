package com.sxt.obj;
import com.sxt.util.BackGround;
import com.sxt.util.GameAssets;
import java.awt.image.BufferedImage;
// 表示游戏中的马里奥角色对象。
public class Mario implements Runnable {
    private int x; // 马里奥的 x 坐标
    private int y; // 马里奥的 y 坐标
    private int width = 25; // 马里奥的宽度
    private int height = 25; // 马里奥的高度
    private String status = null; // 马里奥当前状态
    private boolean big = false; // 是否吃掉蘑菇道具
    private boolean eatHua = false; // 是否吃掉小花道具
    private BufferedImage show = null; // 显示马里奥当前状态对应的图像
    private BackGround backGround = new BackGround(); // 背景对象，用于获取障碍物信息
    private Thread thread = null; // 线程对象
    private int xSpeed; // 马里奥的水平移动速度
    private int ySpeed; // 马里奥的垂直移动速度
    private int index; // 图像序列索引
    private int upTime = 0; // 马里奥上升的时间
    private boolean isOK; // 是否到达城堡门口
    private boolean isDeath = false; // 是否死亡
    private int score = 0; // 分数
    private boolean face_to = true; // 马里奥的朝向，默认向右
    // 默认构造函数
    public Mario() {}
    // 带参数的构造函数，初始化马里奥的位置和大小
    public Mario(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        show = GameAssets.stand_R; // 初始显示为站立状态的右侧图像
        this.status = "stand--right"; // 初始状态为站立向右
        thread = new Thread(this); // 创建线程对象
        thread.start(); // 启动马里奥线程
    }
    // 马里奥的死亡方法
    public void death() {isDeath = true;}
    // 马里奥向左移动
    public void leftMove() {
        xSpeed = -5; // 设置向左移动的速度
        if (backGround.isReach()) { // 如果到达旗子位置
            xSpeed = 0; // 停止水平移动
        }
        if (status.indexOf("jump") != -1) { // 如果正在跳跃
            status = "jump--left"; // 设置为跳跃向左
        } else { // 否则为普通移动向左
            status = "move--left";
        }
        this.face_to = true; // 设置朝向为左侧
    }
    // 马里奥向右移动
    public void rightMove() {
        xSpeed = 5; // 设置向右移动的速度
        if (backGround.isReach()) { // 如果到达旗子位置
            xSpeed = 0; // 停止水平移动
        }
        if (status.indexOf("jump") != -1) { // 如果正在跳跃
            status = "jump--right"; // 设置为跳跃向右
        } else { // 否则为普通移动向右
            status = "move--right";
        }
        this.face_to = false; // 设置朝向为右侧
    }
    // 马里奥向左停止
    public void leftStop() {
        xSpeed = 0; // 停止水平移动
        if (status.indexOf("jump") != -1) { // 如果正在跳跃
            status = "jump--left"; // 设置为跳跃向左
        } else { // 否则为停止向左
            status = "stop--left";
        }
        this.face_to = true; // 设置朝向为左侧
    }
    // 马里奥向右停止
    public void rightStop() {
        xSpeed = 0; // 停止水平移动
        if (status.indexOf("jump") != -1) { // 如果正在跳跃
            status = "jump--right"; // 设置为跳跃向右
        } else { // 否则为停止向右
            status = "stop--right";
        }
        this.face_to = false; // 设置朝向为右侧
    }
    // 马里奥发射火球
    public void fire() {
        if (this.eatHua && this.big) { // 如果吃了小花和蘑菇
            backGround.getfireballList().add(new Fireball(this.x, this.y, this.face_to, backGround)); // 发射火球
        }
    }
    // 马里奥跳跃
    public void jump() {
        if (status.indexOf("jump") == -1) { // 如果不在跳跃状态
            if (status.indexOf("left") != -1) { // 如果正在向左移动
                status = "jump--left"; // 设置为跳跃向左
                this.face_to = true; // 设置朝向为左侧
            } else { // 否则为跳跃向右
                status = "jump--right";
                this.face_to = false; // 设置朝向为右侧
            }
            if (!this.isEatHua()) { // 如果没吃小花
                ySpeed = -10; // 设置跳跃速度
                upTime = 7; // 跳跃时间
            } else { // 否则为吃了小花
                ySpeed = -20; // 设置跳跃速度更快
                upTime = 5; // 跳跃时间更短
            }
        }
        if (backGround.isReach()) { // 如果到达旗子位置
            ySpeed = 0; // 停止垂直移动
        }
    }
    // 马里奥下落
    public void fall() {
        if (status.indexOf("left") != -1) { // 如果正在向左移动
            status = "jump--left"; // 设置为跳跃向左
            this.face_to = true; // 设置朝向为左侧
        } else { // 否则为跳跃向右
            status = "jump--right";
            this.face_to = false; // 设置朝向为右侧
        }
        ySpeed = 10; // 下落速度
    }
    @Override
    public void run() {
        while (true) {
            boolean onObstacle = false; // 是否在障碍物上
            boolean canRight = true; // 是否可以向右移动
            boolean canLeft = true; // 是否可以向左移动
            if (backGround.isFlag() && this.x >= 500) { // 如果到达旗子位置
                this.backGround.setReach(true); // 设置到达旗子位置为真
                if (this.backGround.isBase()) { // 如果旗杆下降完成
                    status = "move--right"; // 移动向右
                    if (x < 690) { // 如果 x 坐标小于 690
                        x += 5; // 向右移动
                    } else {
                        isOK = true; // 到达城堡门口
                    }
                } else { // 否则
                    if (y < 395) { // 如果 y 坐标小于 395
                        xSpeed = 0; // 停止水平移动
                        this.y += 5; // 增加 y 坐标
                        status = "jump--right"; // 跳跃向右
                    }
                    if (y > 395) { // 如果 y 坐标大于 395
                        this.y = 395; // 设置 y 坐标为 395
                        status = "stop--right"; // 停止向右
                    }
                }
            } else { // 否则
                for (int i = 0; i < backGround.getbarrierList().size(); i++) { // 遍历障碍物列表
                    Barrier ob = backGround.getbarrierList().get(i); // 获取障碍物对象
                    if (ob.getY() == this.y + 25 && (ob.getX() > this.x - 30 && ob.getX() < this.x + 25)) { // 如果在障碍物上
                        onObstacle = true; // 设置在障碍物上为真
                    }
                    if ((ob.getY() >= this.y - 30 && ob.getY() <= this.y - 20) && (ob.getX() > this.x - 30 && ob.getX() < this.x + 25)) { // 如果顶到砖块
                        if (ob.getType() == 0) { // 如果是类型为0
                            backGround.getbarrierList().remove(ob); // 移除障碍物
                            score += 1; // 分数加1
                        }
                        if (ob.getType() == 9) { // 如果是类型为9
                            if (this.big) { // 如果吃了蘑菇
                                backGround.getgameitemList().add(new GameItem(ob.getX(), this.y - 55, 1, backGround)); // 添加道具蘑菇
                            } else {
                                backGround.getgameitemList().add(new GameItem(ob.getX(), this.y - 55, 0, backGround)); // 添加普通道具
                            }
                            ob.setType(11); // 设置类型为11
                        }
                        if (ob.getType() == 11) { // 如果是类型为11
                            score += 1; // 分数加1
                            backGround.getgameitemList().add(new GameItem(ob.getX() + 5, this.y - 53, 2, backGround)); // 添加道具金币
                            ob.setType(12); // 设置类型为12
                        }
                        if (ob.getType() == 12) { // 如果是类型为12
                            score += 5; // 分数加5
                            ob.setType(7); // 设置类型为7
                        }
                        upTime = 0; // 上升时间清零
                    }
                    if (ob.getX() == this.x + 25 && (ob.getY() > this.y - 30 && ob.getY() < this.y + 25)) { // 如果可以向右移动
                        canRight = false; // 设置可以向右移动为假
                    }
                    if (ob.getX() == this.x - 30 && (ob.getY() > this.y - 30 && ob.getY() < this.y + 25)) { // 如果可以向左移动
                        canLeft = false; // 设置可以向左移动为假
                    }
                }
                for (int i = 0; i < backGround.getgameitemList().size(); i++) { // 遍历道具列表
                    GameItem d = backGround.getgameitemList().get(i); // 获取道具对象
                    if ((d.getX() + 35 > this.x && d.getX() - 25 < this.x) && (d.getY() + 35 > this.y && d.getY() - 20 < this.y)) { // 如果吃到道具
                        if (d.getType() == 0) { // 如果是类型为0
                            d.eat(); // 吃掉道具
                            this.setBig(true); // 设置为吃了蘑菇
                            System.out.println("吃蘑菇"); // 输出信息
                        }
                        if (d.getType() == 1) { // 如果是类型为1
                            d.eat(); // 吃掉道具
                            this.setEatHua(true); // 设置为吃了小花
                            System.out.println("吃小花"); // 输出信息
                        }
                    }
                    if ((d.getY() + 35 > this.y && d.getY() - 25 < this.y) && (d.getX() + 35 > this.x && d.getX() - 20 < this.y)) { // 如果吃到金币
                        if (d.getType() == 2) { // 如果是类型为2
                            this.score += 5; // 分数加5
                            d.eat(); // 吃掉道具
                            System.out.println("吃金币"); // 输出信息
                        }
                    }
                }
                for (int i = 0; i < backGround.getEnemyList().size(); i++) { // 遍历敌人列表
                    Enemy e = backGround.getEnemyList().get(i); // 获取敌人对象
                    if (e.getY() == this.y + 20 && (e.getX() - 25 <= this.x && e.getX() + 35 >= this.x)) { // 如果碰到敌人
                        if (e.getType() == 1) { // 如果是类型为1
                            e.death(); // 敌人死亡
                            score += 2; // 分数加2
                            upTime = 3; // 上升时间为3
                            ySpeed = -10; // 上升速度为-10
                        } else if (e.getType() == 2) { // 如果是类型为2
                            death(); // 马里奥死亡
                        } else if (e.getType() == 3) { // 如果是类型为3
                            score += 2; // 分数加2
                            upTime = 3; // 上升时间为3
                            ySpeed = -10; // 上升速度为-10
                            e.death(); // 敌人死亡
                            System.out.println(this.x); // 输出信息
                            e.setY(e.getY() + 10); // 设置敌人 y 坐标增加10
                        } else if (e.getType() == 4) { // 如果是类型为4
                            score += 2; // 分数加2
                            upTime = 3; // 上升时间为3
                            ySpeed = -10; // 上升速度为-10
                            e.death(); // 敌人死亡
                        }
                    }
                    if ((e.getX() + 35 > this.x && e.getX() - 25 < this.x) && (e.getY() + 35 > this.y && e.getY() - 20 < this.y)) { // 如果碰到敌人
                        death(); // 马里奥死亡
                    }
                }
                if (onObstacle && upTime == 0) { // 如果在障碍物上且跳跃时间为0
                    if (status.indexOf("left") != -1) { // 如果向左移动
                        if (xSpeed != 0) { // 如果有水平速度
                            status = "move--left"; // 移动向左
                        } else { // 否则停止向左
                            status = "stop--left";
                        }
                    } else { // 否则向右移动
                        if (xSpeed != 0) { // 如果有水平速度
                            status = "move--right"; // 移动向右
                        } else { // 否则停止向右
                            status = "stop--right";
                        }
                    }
                } else { // 否则
                    if (upTime != 0) { // 如果跳跃时间不为0
                        upTime--; // 跳跃时间减少
                    } else { // 否则下落
                        fall(); // 下落方法
                    }
                    y += ySpeed; // 增加 y 坐标
                }
            }
            if ((canLeft && xSpeed < 0) || (canRight && xSpeed > 0)) { // 如果可以向左或向右移动
                x += xSpeed; // 增加 x 坐标
                if (x < 0) { // 如果 x 坐标小于0
                    x = 0; // 设置 x 坐标为0
                }
            }
            if (status.contains("move")) { // 如果是移动状态
                index = index == 0 ? 1 : 0; // 索引切换
            }
            if ("move--left".equals(status)) { // 如果是向左移动
                show = GameAssets.run_L.get(index); // 显示向左移动图像
            }
            if ("move--right".equals(status)) { // 如果是向右移动
                show = GameAssets.run_R.get(index); // 显示向右移动图像
            }
            if ("stop--left".equals(status)) { // 如果是向左停止
                show = GameAssets.stand_L; // 显示向左停止图像
            }
            if ("stop--right".equals(status)) { // 如果是向右停止
                show = GameAssets.stand_R; // 显示向右停止图像
            }
            if ("jump--left".equals(status)) { // 如果是向左跳跃
                show = GameAssets.jump_L; // 显示向左跳跃图像
            }
            if ("jump--right".equals(status)) { // 如果是向右跳跃
                show = GameAssets.jump_R; // 显示向右跳跃图像
            }
            try { // 尝试
                Thread.sleep(50); // 睡眠50毫秒
            } catch (InterruptedException e) { // 捕获中断异常
                e.printStackTrace(); // 打印异常
            }
        }
    }
    public int getX() { return x;}
    public void setX(int x) {this.x = x;}
    public int getY() {return y;}
    public void setY(int y) {this.y = y;}
    public BufferedImage getShow() {return show; }
    public void setShow(BufferedImage show) {this.show = show;}
    public void setBackGround(BackGround backGround) {this.backGround = backGround;}
    public boolean isOK() {return isOK;}
    public boolean isDeath() {return isDeath;}
    public int getScore() {return score;}
    public int getWidth() {return width;}
    public void setWidth(int width) {this.width = width;}
    public int getHeight() {return height;}
    public void setHeight(int height) {this.height = height;}
    public boolean isBig() {return big;}
    public void setBig(boolean big) {this.big = big;}
    public boolean isEatHua() {return eatHua;}
    public void setEatHua(boolean eatHua) {this.eatHua = eatHua;}
    public boolean isFace_to() {return face_to;}
    public void setFace_to(boolean face_to) {this.face_to = face_to;}
}

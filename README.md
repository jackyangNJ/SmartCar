SmartCar
========
## Introduction ##
###1. Aim ###
**SmartCar ** is a project aims to navigate readers in the library to help them find the book they want. The car can autonomously drive  to the right bookshelf after you tell it the name of the book. To some extent the major work of the project is to solve the problem of indoor localization. 

###2. Platform ###
Our project is based on the platform [**ZRobot**][1]. 
![ZRobot][2]

ZRobot is an all programmable robot with motors, camera, ultrasonic sensor and wireless AP. And it is capable of finishing tasks requiring fast movement, obstruction avoidance, computer vision and remote control. 
The car consists of two layer boards, one is for controlling and the other for power supply. We adopted Zynq®-7000 cpu as the heart of the car. Zynq®-7000 is the newest FPGA platform which combines FPGA and ARM-A9 hard core. We run the software on the hard core and the FPGA resources are used as middleware between software and exterior sensors and devices.
The picture above is the original form of Zrobot. We have made some changes to the car.

![New ZRobot][3]



## System Architecture ##

## Algorithm ##

a project based on Zrobot
本工程依赖Opencv 2.4.8，请下载安装正确的版本，如果使用eclipse可能需要重新添加javacv的jar包，包路径是smartcar--libs。

[1]:http://zrobot.org/
[2]:http://zrobot.org/wp-content/uploads/2013/08/zrobot-600x400.jpg
[3]:https://github.com/jackyang74/SmartCar/document/picture/SmartCar.jpg

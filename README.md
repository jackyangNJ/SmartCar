SmartCar
========
## Introduction ##
###1. Aim ###
**SmartCar ** is a project aims to navigate readers in the library to help them find the book they want. The car can autonomously drive  to the right bookshelf after you tell it the name of the book. To some extent the major work of the project is to solve the problem of indoor localization. 

###2. Platform ###
![ZRobot][2]

Our project is based on the platform [**ZRobot**][1]. 
ZRobot is an all programmable robot with motors, camera, ultrasonic sensor and wireless AP. And it is capable of finishing tasks requiring fast movement, obstruction avoidance, computer vision and remote control. 
The car consists of two layer boards, one is for controlling and the other for power supply. We adopted Zynq®-7000 cpu as the heart of the car. Zynq®-7000 is the newest FPGA platform which combines FPGA and ARM-A9 hard core. We run the software on the hard core and take advantage of FPGA resources as middleware between the software and exterior sensors or devices.
The picture above is the original ZRobot. We have made some changes to the car, as shown in image below.

![New ZRobot][3]

## System Architecture ##
###1. Hardware ###
The chart below shows the sensors or devices we add to the car and corresponding usage.

|device|measured data|usage|
|:--|:--|:--|
|accelerator sensor|acceleration   |get the displacement by double integral of speed|
|gyroscope         |angular speed  |get the angle by integral of angular speed |
|Hall sensor       |number of rotations of the wheel|get the distance |
|ultrasonic sensors|distance       |detecting and avoiding obstruction|
|digital Pan-Tilt  |               |providing accurate positioning of cameras and ultrasonic sensor|
|USB Camera        |image |localization by image recognition|
|Wireless AP       |  |communication between the car and the computer or smart phone|

###2. Software ###
We have used C, Java and Verilog in our project. Verilog code is to implement the controllers of various sensors on FPGA. And the these controllers are connected to CPU via AXI4 bus and their Linux drivers are written in C. Java is used in the top layer of software hierarchy, achieving the functions of autonomous driving, planing a path and obstruction avoidance.

## Algorithm ##
###1. Kalman filter ###
For the inevitable noises in the measured data, we cannot carry out the integral of original data directly. And thus we adopt the Kalman filter to smooth the data and get relatively stable results.
###2. QR Code ###
The paper that contains QR Code image is stuck on the wall. The car could recognize the QR Code image from the camera by using image processing algorithm. After decoding the image we could get the location of the car.

[1]:http://zrobot.org/
[2]:http://zrobot.org/wp-content/uploads/2013/08/zrobot-600x400.jpg
[3]:https://raw.githubusercontent.com/jackyang74/SmartCar/master/document/picture/SmartCar.jpg

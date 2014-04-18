#!/usr/bin/python
'''
@author: jack
'''
import os
import time

def configPort(port_num, direction):
    cmd = "echo " + str(port_num) + "> " + gpio_export_file 
    result = os.system(cmd) 
    if result != 0:
        print("port" + str(port_num) + "has been exported")
            
    #write direction    
    gpio_direction_path=os.path.join("/sys/class/gpio","gpio"+str(port_num),"direction")
    cmd = "echo " + direction + " > "+gpio_direction_path;
    result = os.system(cmd)
    if result != 0:
        print("There may be some error in writing gpio's vdirection")
        
def setPortValue(port_num,value):
    gpio_value_path=os.path.join("/sys/class/gpio","gpio"+str(port_num),"value")
    cmd="echo "+str(value)+" > "+ gpio_value_path;
    result = os.system(cmd)
    if result != 0:
        print("There may be some error in writing gpio's value")
  
# control port
SDATA_OUT_NUM = 247
LOAD_NUM = 248
SCLK_NUM = 249
SDATA_IN_NUM = 250

# control file
gpio_export_file = '/sys/class/gpio/export'
# main
configPort(SDATA_OUT_NUM,"out")
configPort(LOAD_NUM,"out")
configPort(SCLK_NUM,"out")
configPort(SDATA_IN_NUM,"out")


setPortValue(SDATA_OUT_NUM, 0)
for i in range(10):
    setPortValue(LOAD_NUM, 0)
    setPortValue(SCLK_NUM, 0)
    time.sleep(0.1)
    setPortValue(LOAD_NUM, 1)
    setPortValue(SCLK_NUM, 1)
    time.sleep(0.1)
    
print("successfully config the Ultrasonic port")
    
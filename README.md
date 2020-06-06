## Tasmota (Connect)
Tasmota (Connect) is a SmartApp & Device Type for [Samsung SmartThings](https://www.smartthings.com/), that allows you to add your [Tasmota](https://github.com/arendst/Tasmota) devices as SmartThings devices.

## Features
* Full support for [SmartThings (new) App](https://play.google.com/store/apps/details?id=com.samsung.android.oneconnect) & [SmartThings (Classic) App](https://play.google.com/store/apps/details?id=com.smartthings.android) 
* Real-time device status
* Pure Tasmota & SmartThings integration (No need for additional MQTT bridge)
* Virtual Device (for RF / IR devices that can be controlled by a RF / IR bridge) 

## Requirement
* Samsung SmartThings Hub
* Official [Tasmota](https://github.com/arendst/Tasmota/releases) Firmware >=8.10

#### Tasmota Firmware

Firmware | Description
---- | ----
**tasmota.bin** | The Tasmota version with most drivers. **RECOMMENDED RELEASE BINARY**
**tasmota-sensors.bin** | The Sensors version adds more useful sensors.
**tasmota-ir** | The InfraRed Receiver and transmitter version allowing all available protocols provided by library IRremoteESP8266 but without most other features.

## Installation

#### GitHub Install
1. If you haven’t enabled GitHub integration, please visit here for [instructions](https://docs.smartthings.com/en/latest/tools-and-ide/github-integration.html).
2. Sign in to SmartThings IDE: https://account.smartthings.com/login
3. Under `My SmartApps` > click `Settings` > `Add new repository` > enter the following:
   * Owner: **hongtat**
   * Name: **tasmota-connect**
   * Branch: **master**
4. Under `Update from Repo` > click `tasmota-connect` > Select all files > Tick `Publish` > then `Execute Update`
5. Under `My Device Handlers` > click `Settings` > `Add new repository` > enter the following:
   * Owner: **hongtat**
   * Name: **tasmota-connect**
   * Branch: **master**
6. Under `Update from Repo` > click `tasmota-connect` > Select all files > Tick `Publish` > then `Execute Update`
7. Open your SmartThings mobile app
8. Add Tasmota (Connect) SmartApp
   * (New) Go to: `SmartApps` > `+` > `My SmartApps` > `Tasmota (Connect)`
   * (Classic) Go to: `Automation` > `SmartApps` > `Add a SmartApp` > `My Apps` > `Tasmota (Connect)`
9. For SmartApp and Device Handler updates,
   1. Sign in to SmartThings IDE: https://account.smartthings.com/login
   2. Under `My SmartApps` > `Update from Repo` > click `tasmota-connect` > Select all files > Tick `Publish` > then `Execute Update`
   3. Under `My Device Handlers` > `Update from Repo` > click `tasmota-connect` > Select all files > Tick `Publish` > then `Execute Update`

#### Manual Installation
For Tasmota (Connect) to function correctly, please make sure you install the SmartApp and all Device Handlers.

1. Sign in to SmartThings IDE: https://account.smartthings.com/login
2. SmartApp Installation
   1. Under `My SmartApps` > choose `New SmartApp` > `From Code`
   2. Paste the SmartApp code [[link](https://github.com/hongtat/tasmota-connect/blob/master/smartapps/hongtat/tasmota-connect.src/tasmota-connect.groovy)] into the text box
   3. Click "***Save***" and "***Publish***" it to yourself.
3. Device Handler Installation
   1. Click on this [[link](https://github.com/hongtat/tasmota-connect/tree/master/devicetypes/hongtat)] to obtain the device handlers. For each of the device handlers, do the following:
   2. Under `My Device Handlers` > choose `Create New Device Handler` > `From Code`
   3. Paste the device code into the text box
   4. Click "***Save***" and "***Publish***" it to yourself.
4. For SmartApp and Device Handler updates, perform the same steps in manual installation.   

## Adding your Tasmota devices
1. Open your SmartThings mobile app
2. Under `SmartApps`, select `Tasmota (Connect)`
3. Tap `New Tasmota Device`, and select the Tasmota device you want to add
4. Fill in the `IP address`, `username` (optional), `password` (optional) of the Tasmota device


## Supported Tasmota Devices

It should work for most switches, lights (CCT, RGB, RGBW), dimmers, relays, plugs, power strips, sockets, wall outlets, fan controllers, RF motion/contact sensors, RF remote controllers, IR bridges and RF bridges listed in the [Tasmota Device Templates Repository](https://templates.blakadder.com/).

If your Tasmota device is not listed below, choose a **Generic** device that is similar to your Tasmota device.

* **Generic Switch** (1,2,3,4,5,6CH) - No Power Monitoring
* **Generic Metering Switch** (1,2CH) - Power Monitoring
* **Generic Dimmer Switch**
* **Generic IR Bridge**
* **Generic Light (CT)**
* **Generic Light (RGB)**
* **Generic Light (RGBW)**
* Sonoff Basic
* Sonoff RF
* Sonoff TH
* Sonoff Dual & Dual R2
* Sonoff Pow & Pow R2
* Sonoff 4CH & 4CH Pro
* Sonoff S20, S26
* Sonoff S31
* Sonoff Touch, T1 (1,2,3CH)
* Sonoff RF Bridge
* Sonoff iFan02, iFan03

## Virtual Device
A Virtual Device uses a RF or IR bridge to control or read your dumb RF / IR devices.

Virtual Device | Usage
---- | ----
Virtual Switch          | Add a SmartThings generic switch that can be controlled by a RF / IR bridge (e.g. Sonoff RF Bridge).
Virtual Shade           | Add a SmartThings shade/blind that can be controlled by a RF / IR bridge (e.g. Sonoff RF Bridge).
Virtual Button          | Add RF/IR remote controller 1/2/4/6-button as SmartThings remote controller button.
Virtual Contact Sensor  | Add a RF contact sensor
Virtual Motion Sensor   | Add a RF motion sensor
  
#### Configuring a Virtual Switch
1. Choose a RF or IR Bridge
2. Enter the Tasmota command to send for "ON" and "OFF"
      
      For RF, it has to be one of these formats
      
      * `Backlog RfSync <value>; RfLow <value>; RfHigh <value>; RfCode <value>`      
      * `Backlog RfRaw <value>; RfRaw 0`
         > Note: RFRaw requires [Portisch Firmware](https://github.com/Portisch/RF-Bridge-EFM8BB1)
     
3. Optionally, enable `State tracking` to listen for RF codes (e.g. from RF remote) to simulate a Stateful device
4. If you enable `State tracking`, enter the code that represents the "ON" and "OFF" state
       
   In the example below, the code is **70C70F**
   
   * "RfReceived":{"Sync":7110,"Low":210,"High":660,"Data":"**70C70F**","RfKey":"None"}

#### Configuring a Virtual Contact / Motion Sensor / Button
1. Choose a RF Bridge
2. Enter the RFReceived Data that represents "OPEN", "CLOSE", "ACTIVE", "INACTIVE" or button state
             
   In the example below, the code is **70C70F**
   
   * "RfReceived":{"Sync":7110,"Low":210,"High":660,"Data":"**70C70F**","RfKey":"None"}


## FAQ

#### My Tasmota device is not responding?
* Verify the IP address is correct and you are able to access your device via the IP address.
* If you have added this device using other developer device type handler (DTH), please delete the device.
* If the device is still not responding, look at the Tasmota Console so that you can see exactly what is happening. e.g. The actions (on/off) that you've called in SmartThings App didn't appear in the Console, it's likely the SmartThings hub is unable to access the device.
* Ensure your Tasmota devices and SmartThings hub are assigned with static IP addresses.

####  Where can I find out more about Tasmota command for sending/receiving IR / RF codes?
Please see under RF Bridge & IR Remote - https://tasmota.github.io/docs/#/Commands

#### I have feedback, questions, suggestion..
Please use [SmartThings Community](https://community.smartthings.com/t/release-tasmota-connect-pure-tasmota-st-integration-real-time-status-for-sonoff-tuya-smartlife-other-esp8266-devices/187553) for feedback and questions.

## License
GPL-3.0

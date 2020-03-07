/**
 *  Tasmota - Virtual 1-button
 *
 *  Copyright 2020 AwfullySmart.com - HongTat Tan
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
private getButtonLabels() {[
    "button_1",
]}

import groovy.json.JsonOutput
metadata {
    definition (name: "Tasmota Virtual 1 Button", namespace: "hongtat", author: "HongTat Tan", ocfDeviceType: "x.com.st.d.remotecontroller") {
        capability "Button"
        capability "Sensor"
        capability "Health Check"
        capability "Configuration"

        attribute "lastSeen", "string"
    }

    preferences {
        section {
            input(title: "Device Settings",
                    description: "To view/update this settings, go to the Tasmota (Connect) SmartApp and select this device.",
                    displayDuringSetup: false,
                    type: "paragraph",
                    element: "paragraph")
        }
    }

    tiles(scale: 2) {
        multiAttributeTile(name: "button", type: "generic", width: 6, height: 4, canChangeIcon: true) {
            tileAttribute("device.button", key: "PRIMARY_CONTROL") {
                attributeState "default", label: ' ', icon: "st.unknown.zwave.remote-controller", backgroundColor: "#ffffff"
            }
        }

        main "button"
        details(["button"])
    }
}

def installed() {
    sendEvent(name: "checkInterval", value: 30 * 60 + 2 * 60, displayed: false, data: [protocol: "lan", hubHardwareId: device.hub.hardwareID])
    sendEvent(name: "button", value: "pushed", isStateChange: true, displayed: false)
    sendEvent(name: "supportedButtonValues", value: supportedButtonValues.encodeAsJSON(), displayed: false)
    log.debug "Installed"
    initialize()
}

def updated() {
}

def initialize() {
    def numberOfButtons = buttonLabels.size()
    log.debug "initialize(); numberOfButtons: ${numberOfButtons}"
    sendEvent(name: "numberOfButtons", value: numberOfButtons, displayed: false)
    sendEvent(name: "button", value: "pushed", displayed: false)
}

def configure() {
}

def parse(description) {
}

def parseEvents(status, json) {
    def events = []
    if (status as Integer == 200) {
        // rfData
        if (json?.rfData) {
            def numberOfButtons = buttonLabels.size()
            def data = parent.childSetting(device.id, buttonLabels)
            def found = data.find{ it?.value?.toUpperCase() == json?.rfData?.toUpperCase() }?.key
            def buttonNumber = 1
            if (found && found == "button_${buttonNumber}") {
                def description = "Button ${buttonNumber} was pushed"
                events << sendEvent(name: "button", value: "pushed", descriptionText: description, data: [buttonNumber: buttonNumber], isStateChange: true)
            }
        }
        // irData
        if (json?.irData) {
            def numberOfButtons = buttonLabels.size()
            def data = parent.childSetting(device.id, buttonLabels)
            def found = data.find{ it?.value?.toUpperCase() == json?.irData?.toUpperCase() }?.key
            def buttonNumber = 1
            if (found && found == "button_${buttonNumber}") {
                def description = "Button ${buttonNumber} was pushed"
                events << sendEvent(name: "button", value: "pushed", descriptionText: description, data: [buttonNumber: buttonNumber], isStateChange: true)
            }
        }
        // Bridge's Signal Strength
        if (json?.Wifi) {
            events << sendEvent(name: "lqi", value: json?.Wifi.RSSI, displayed: false)
            events << sendEvent(name: "rssi", value: json?.Wifi.Signal, displayed: false)
        }
        // Bridge's Last seen
        if (json?.lastSeen) {
            events << sendEvent(name: "lastSeen", value: json?.lastSeen, displayed: false)
        }
    }
    return events
}

private getSupportedButtonValues() {[
    "pushed"
]}


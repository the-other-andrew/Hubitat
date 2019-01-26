/**
 *
 *   File: Virtual_Contact_Sensor_Switch_Driver.groovy
 *   Platform: Hubitat
 *   Modification History:
 *       Date       Who               What
 *       2019-01-24 the-other-andrew  Virtual Switch that can show as a contact sensor
 *
 *  Copyright 2019 Andrew Barkley
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
def version() {"v1.0.20190124"}

preferences {
}

metadata {
    definition (name: "Virtual Contact Sensor and Switch", namespace: "the-other-andrew", author: "Andrew Barkley") {
        capability "Switch"
        capability "Contact Sensor"
    }
}

def on() {
    sendEvent(name: "switch", value: "on")
    sendEvent(name: "contact", value: "open")
}

def off() {
    sendEvent(name: "switch", value: "off")
    sendEvent(name: "contact", value: "closed")
}
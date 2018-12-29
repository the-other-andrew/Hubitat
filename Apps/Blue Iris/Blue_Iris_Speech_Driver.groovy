/**
 *
 *   File: Blue_Iris_Speech_Driver.groovy
 *   Platform: Hubitat
 *   Modification History:
 *       Date       Who               What
 *       2018-12-29 the-other-andrew  Speech driver for Profile control of Blue Iris
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
def version() {"v1.0.20181229"}

preferences {
    input("biHost", "text", title: "Host", description: "The internet address for the Blue Iris server")
    input("biPort", "number", title: "Port", description: "The TCP port used by the Blue Iris web server")
    input("biUser", "text", title: "User", description: "The Blue Iris user name that will execute administrative commands")
    input("biPass", "password", title: "Password", description: "The Blue Iris password associated with the user")
}

metadata {
    definition (name: "Blue Iris Schedule and Profile Control", namespace: "the-other-andrew", author: "Andrew Barkley") {
        capability "Notification"
        capability "Actuator"
        capability "Speech Synthesis"
    }
}

def installed() {
    initialize()
}

def updated() {
    initialize()
}

def initialize() {
    state.version = version()
}

def speak(message) {
    def command
    switch (message) {
        case ~/^[~1234567]$/:
            command = "http://${biHost}:${biPort}/admin?profile=${message}&user=${biUser}&pw=${biPass}"
            break;
        default:
            command = "http://${biHost}:${biPort}/admin?schedule=${message}&user=${biUser}&pw=${biPass}"
    }
    httpGet(uri: command){response ->
        if(response.status != 200) {
            log.error "Received HTTP error ${response.status}."
        }
        else {
            log.debug "Command succeeded"
        }
    }
}
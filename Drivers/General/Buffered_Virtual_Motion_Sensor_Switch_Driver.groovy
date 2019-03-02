/**
 *
 *   File: Buffered_Virtual_Motion_Sensor_Switch_Driver.groovy
 *   Platform: Hubitat
 *   Modification History:
 *       Date       Who               What
 *       2019-03-02 the-other-andrew  Virtual Switch that can show as a contact sensor
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
def version() {"v1.0.20190302"}

preferences {
    input("window", "number", title: "Window", description: "The maximum time in seconds between push events before motion is detected")
    input("duration", "number", title: "Duration", description: "The duration that motion will be reported")
}

metadata {
    definition (name: "Buffered Virtual Motion Sensor", namespace: "the-other-andrew", author: "Andrew Barkley") {
        capability "Switch"
        capability "MotionSensor"
    }
}

def time() {
    return new GregorianCalendar().time.time
}

def on() {
    log.info "Push"
    long current = time()
    log.info "current $current"
    log.info "last $state.last"
    if (state.last != null && state.last > 0) {
        long delay = current - state.last
        long seconds = delay / 1000

        if (seconds < window) {
            sendEvent(name: "switch", value: "on")
            sendEvent(name: "motion", value: "active")
            runIn(duration, off)
        }
    }
    state.last = current;
}

def off() {
    log.warn "Off"
    sendEvent(name: "switch", value: "off")
    sendEvent(name: "motion", value: "inactive")
}
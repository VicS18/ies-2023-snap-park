# Applicational Protocol for Event Messaging


## Typical event structure

### Park entry/exit traffic

| type | ts | park | sensor | entering | vehicle | veh_type |
| ----------- | ----------- | ----------- | ----------- | ----------- | ----------- | ----------- | 
|"TRF"| UNIX Milliseconds | Integer | Integer | Boolean | Integer | Char |

### Light level 

Note: Sent when the light level changes, as defined by an internal margin in the Sensor Box.

|type| ts | park | sensor | intensity (Lux) |
| ----------- | ----------- | ----------- | ----------- | ----------- |
| "LGT" | UNIX Milliseconds | Integer | Integer | Float |

### Temperature change

Note: Sent when the temperature changes, as defined by an internal margin in the Sensor Box.

|type| ts | park | sensor | temperature (Celsius) |
| ----------- | ----------- | ----------- | ----------- | ----------- |
| "TMP" | UNIX Milliseconds | Integer | Integer | Float |

### Air quality change

Note: Sent when the AQI (Air Quality Index) changes, calculated in the Sensor Box.

|type| ts | park | sensor | aqi |
| ----------- | ----------- | ----------- | ----------- | ----------- |
| "AQ" | UNIX Milliseconds | Integer | Integer | Integer |
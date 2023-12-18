import time
import numpy
import pika
import json

def randNumEvents( window , rate ) -> int:
    '''Returns a random number based a time window and rate according to a Poisson distribution'''
    window = window / ( 1000 * 60 )
    rate = rate * window
    return numpy.random.poisson( rate )

_stayingTimeOverDay = [ 8    , 10  , 10.5 , 10    , 9     , 8     , 6.5  , 5    , 4   , 2 , 2   , 2   , 2.5 , 2.3 , 2    , 2.5 , 3    , 3.5  , 3    , 4    , 5   , 5.5 , 6   , 7   , 8    ]
_stayingVarOverDay =  [ 4.5  , 3.5 , 2.5  , 2     , 1.5   , 1     , 3    , 2.5  , 1   , 1 , 1.5 , 0.5 , 0.5 , 0.5 , 1.25 , 1   , 0.75 , 0.75 , 1    , 2    , 3   , 3   , 3.5 , 4.5 , 5    ]
_entryRateOverDay =   [ 0.10,0.07,0.05,0.04,0.03,0.035,0.08,0.12,0.3,1,0.6,0.5,0.7,1.2,0.9,0.85,0.9,1.2,1.3,0.85,1.1,0.9,0.6,0.4,0.2]
_luxOverDay =         [ 10e-3,10e-3,10e-3,10e-3,10e-3,10**-2.8,10e-1,10e3,10e4,10**4.5,10e5,10**5.1,10**5.3,10**5.3,10**5.1,10e5,10**4.7,10**4.5,10e4,10**2.5,10e-1,10**-2.9,10e-3,10e-3,10e-3 ]
_celsiusOverDay =     [-3.5208, -5.020833, -6.520833, -7.520833, -9.520832, -10.520832, -11.520832, -10.520832, -8.520832, -5.520833, -2.520833, 2.479167, 4.4791667, 7.4791667, 7.9791667, 8.4791668, 10.4791668, 10.4791668, 9.4791668, 8.4791668, 7.4791667, 4.4791667, 0.9791667, -1.520833]
def _overDay(values,ts):
    seconds = int(ts/1000) % 86400
    hour = seconds // 3600
    minute = ( seconds - hour*3600 ) // 60
    percent_minute = minute/60
    return values[hour%24]*(1-percent_minute)+values[(hour+1)%24]*percent_minute

def entryRateOverDay( ts ):
    return _overDay(_entryRateOverDay,ts) * 10 / 60

def stayingTimeOverDay(ts):
    return _overDay(_stayingTimeOverDay,ts) * 60

def stayingVarOverDay(ts):
    return ( _overDay(_stayingVarOverDay,ts) * 60 ) ** 0.5

def luxOverDay(ts):
    return _overDay( _luxOverDay,ts )

def celsiusOverDay(ts):
    return _overDay(_celsiusOverDay,ts)

class eventGen:

    def __init__(self, park , timescale , generators ,states) -> None:
        self.park = park 
        self.timescale = timescale
        self.initial_time = int(time.time() * 1000)
        self.prevtime = self.initial_time
        self.genarators = generators
        self.states = states

        self.curtime = None

    def start(self):
        for state in self.states:
            state.start(self.initial_time)

    def getEvents(self) -> list:
        ret = []
        if self.curtime :
            curtime = self.curtime
        else:
            curtime = ( int(time.time() * 1000) - self.initial_time ) * self.timescale + self.initial_time

        for gen in self.genarators:
            events = gen.getEvents( self.prevtime , curtime )
            for event in events:
                event["park"]=self.park
                ret.append(event)
            
        self.prevtime = curtime
        self.curtime = None

        return ret

class parkingState:

    def __init__(self,avg_staying_time = stayingTimeOverDay , leaving_var = stayingVarOverDay, initial_capacity = 0 , max_capacity = 100) -> None:
        self.avg_staying_time = avg_staying_time
        self.leaving_var = leaving_var
        self.max_capacity = max_capacity
        self.initial_capacity = initial_capacity

        self.capacity = 0
        self.cur_car_id = 2
        self.total_weigth = 0
        self.leavingOptions = []

    def start(self,ts):
        self.inPark = []
        self.capacity = 0
        for i in range(self.initial_capacity):
            self.enter( ts )

    def newCarId(self):
        self.cur_car_id += 1
        return self.cur_car_id

    def enter(self,entering_time):
        if self.capacity >= self.max_capacity:
            return (False,-1)
        
        self.capacity += 1
        leaving_time = int(numpy.random.normal(self.avg_staying_time(entering_time)*60*1000,self.leaving_var(entering_time)*60*1000))
        while leaving_time < 0:
            leaving_time = int(numpy.random.normal(self.avg_staying_time(entering_time)*60*1000,self.leaving_var(entering_time)*60*1000))
        leaving_time += entering_time 
        car_id = self.newCarId()

        leaving_weigth = numpy.random.uniform(0,self.total_weigth)
        prev_weigth = 0
        for leaving_option in self.leavingOptions:
            leaving_gen , weigth = leaving_option
            if prev_weigth <= leaving_weigth <= weigth:
                break
            prev_weigth = weigth

        self.inPark.append( (car_id,leaving_time,leaving_gen) )

        return (True,car_id)
    
    def leave(self,car_id):
        self.inPark = [ car for car in self.inPark if car[0]!=car_id ]
        self.capacity -= 1

    def register(self, gen , weigth ):
        self.leavingOptions.append((gen,self.total_weigth+weigth))
        self.total_weigth += weigth

    def toLeave(self,gen,time_upper):
        return [ (car_id,l_ts) for car_id,l_ts,l_gen in self.inPark if l_ts <= time_upper and l_gen == gen]

class movementsGen:

    def __init__(self , sensor_id , state: parkingState , entering_rate = entryRateOverDay , leaving_weigth = 1 ) -> None:
        self.id = sensor_id
        self.entering_rate = entering_rate
        self.state = state
        self.state.register(self,leaving_weigth)

        self.type = "TRF"
        self.veh_type = 'B'
        
    def getEvents(self, time_lower , time_upper):
        time_window = time_upper - time_lower

        time_middle = (time_upper + time_lower) /2
               
        num_of_entries = randNumEvents( time_window , self.entering_rate(time_middle) )

        entering_times = [ int(time_upper - time_window / num_of_entries * e)
                          for e in range(num_of_entries) ]
        
        events = []
        for enter_time in entering_times:
            entered , car_id = self.state.enter(enter_time)
            if entered:
                events.append({
                                "type"      :   self.type,
                                "ts"        :   enter_time,
                                "sensor"    :   self.id,
                                "entering"  :   True,
                                "vehicle"   :   car_id,
                                "veh_type"  :   self.veh_type,
                            })

        for leaving_car in self.state.toLeave(self,time_upper):
            car_id , ts = leaving_car
            events.append({
                                "type"      :   self.type,
                                "ts"        :   ts,
                                "sensor"    :   self.id,
                                "entering"  :   False,
                                "vehicle"   :   car_id,
                                "veh_type"  :   self.veh_type,
                            })
            self.state.leave(car_id)
  
        return events

class ligthState:

    def __init__(self,luxOverDay=luxOverDay) -> None:
        self.luxOverDay = luxOverDay

    def luxAt(self,ts):
        return self.luxOverDay(ts)
    
    def start(self,ts):
        pass
    
class ligthGen:

    def __init__(self, sensor_id , state: ligthState , sampling_rate = 10 , intensity = 1 , medium = 0 , accuracy = 0.01 ) -> None:
        self.id = sensor_id
        self.state = state
        self.intensity = intensity
        self.accuracy = accuracy
        self.medium = medium
        self.sampling_rate = sampling_rate
        self.type = "LGT"
        
    def getEvents(self, time_lower , time_upper):   

        delta_seconds = ( time_upper - time_lower ) // 1000
        samples = delta_seconds // self.sampling_rate

        start_ts = ( time_lower // ( 1000 * self.sampling_rate ) ) *  ( 1000 * self.sampling_rate )

        sampling_times = [ start_ts + e * self.sampling_rate
                          for e in range(int(samples)) ]
        
        events = []
        for ts in sampling_times:
            intensity = self.state.luxAt(ts)*self.intensity + numpy.random.uniform(0,self.accuracy) + self.medium
            events.append({
                                "type"      :   self.type,
                                "ts"        :   ts,
                                "sensor"    :   self.id,
                                "intensity" :   intensity
                            })
        return events

class temperatureState:

    def __init__(self,celsiusOverDay=celsiusOverDay) -> None:
        self.celsiusOverDay = celsiusOverDay

    def celsiusAt(self,ts):
        return self.celsiusOverDay(ts)
    
    def start(self,ts):
        pass
    
class temperatureGen:

    def __init__(self, sensor_id , state: temperatureState , sampling_rate = 10 , intensity = 1 , medium = 14 , accuracy = 0.01 ) -> None:
        self.id = sensor_id
        self.state = state
        self.intensity = intensity
        self.accuracy = accuracy
        self.medium = medium
        self.sampling_rate = sampling_rate
        self.type = "TMP"
        
    def getEvents(self, time_lower , time_upper):   

        delta_seconds = ( time_upper - time_lower ) // 1000
        samples = delta_seconds // self.sampling_rate

        start_ts = ( time_lower // ( 1000 * self.sampling_rate ) ) *  ( 1000 * self.sampling_rate )

        sampling_times = [ start_ts + e * self.sampling_rate
                          for e in range(samples) ]
        
        events = []
        for ts in sampling_times:
            temperature = self.state.celsiusAt(ts)*self.intensity + numpy.random.uniform(0,self.accuracy) + self.medium
            events.append({
                                "type"      :   self.type,
                                "ts"        :   ts,
                                "sensor"    :   self.id,
                                "temperature" : temperature
                            })
        return events
    

class airState:

    def __init__(self,aqiOverDay=lambda ts:25,aqiVarOverDay=lambda ts: 3) -> None:
        # assume is constant over day
        self.aqiOverDay = aqiOverDay
        self.aqiVarOverDay = aqiVarOverDay

    def aqiAt(self,ts):
        aqi = int(numpy.random.normal(self.aqiOverDay(ts),self.aqiVarOverDay(ts)))
        while aqi < 0:
            aqi = int(numpy.random.normal(self.aqiOverDay(ts),self.aqiVarOverDay(ts)))
        return aqi

    def start(self,ts):
        pass
    
class airGen:

    def __init__(self, sensor_id , state: airState , sampling_rate = 10 , intensity = 1 , medium = 0 , var = 0 , accuracy = 0.01 ) -> None:
        self.id = sensor_id
        self.state = state
        self.intensity = intensity
        self.accuracy = accuracy
        self.medium = medium
        self.var = var
        self.sampling_rate = sampling_rate
        self.type = "AQ"
        
    def getEvents(self, time_lower , time_upper):   

        delta_seconds = ( time_upper - time_lower ) // 1000
        samples = delta_seconds // self.sampling_rate

        start_ts = ( time_lower // ( 1000 * self.sampling_rate ) ) *  ( 1000 * self.sampling_rate )

        sampling_times = [ start_ts + e * self.sampling_rate
                          for e in range(samples) ]
        
        events = []
        for ts in sampling_times:
            aqi = int(self.state.aqiAt(ts)*self.intensity + numpy.random.uniform(0,self.accuracy) + 
                      numpy.random.normal(self.medium,self.var))
            while aqi < 0:
                aqi = int(self.state.aqiAt(ts)*self.intensity + numpy.random.uniform(0,self.accuracy) + 
                      numpy.random.normal(self.medium,self.var))
            events.append({
                                "type"      :   self.type,
                                "ts"        :   ts,
                                "sensor"    :   self.id,
                                "aqi"       :   aqi
                            })
        return events

p = parkingState(initial_capacity=45)    
l = ligthState()
t = temperatureState()
a = airState()
samplingRate=1000
m_gens = [ movementsGen(i,p) for i in range(1,3) ]
l_gens = [ ligthGen(100,l,samplingRate,1),ligthGen(101,l,samplingRate,0.2),ligthGen(102,l,samplingRate,10e2,0)  ]
t_gens = [ temperatureGen(200,t,samplingRate,1,14),temperatureGen(201,t,samplingRate,0.1,18) ]
a_gens = [ airGen(300,a,samplingRate,1,0,0),airGen(301,a,samplingRate,0.5,15,6),airGen(302,a,samplingRate,0.1,30,2) ]
gens = m_gens + l_gens + t_gens + a_gens
gen = eventGen(1,10,gens,[p,l,t,a]) # timescale around sampling rate is good ( 10 )
gen.start()

QUEUE_NAME = 'snap_park'
HOST = 'localhost'

def main():
    global gen
    connection = pika.BlockingConnection(
    pika.ConnectionParameters(host=HOST))
    gen.initial_time = 1702813480000 #1700316280000 #start sim time
    gen.prevtime=gen.initial_time
    gen.curtime=gen.prevtime + 600 * 1000
    endTime=1702908280000 #time to finish simulation
    try:
        while gen.curtime<endTime:
            update(QUEUE_NAME, connection)
    finally:
        connection.close()


def update(queue_name, conn):   
    global gen 
    
    #time.sleep(1)
    print("\033c", end='')
    print("Time now:" , gen.prevtime )
    events = gen.getEvents()
    print("Park State: ")
    print(" Num of cars: ",len(p.inPark))
    print( [ car[0] for car in p.inPark] )
    l_events = [ event.get("intensity") for event in events if event.get("intensity") ]
    #print(" Ligth avg: " , sum(l_events)/len(l_events) )
    print(l_events)
    t_events = [ event.get("temperature") for event in events if event.get("temperature") ]
    #print(" Temperature avg: " , sum(t_events)/len(t_events) )
    print(t_events)
    print("Events: ")
    print( "    Leaving: " , [ event.get("vehicle") for event in events if event.get("entering") == False] )
    print( "    Entering: " , [ event.get("vehicle") for event in events if event.get("entering") == True] )
    print( "    All: " , events )

    channel = conn.channel()
    channel.queue_declare(queue=queue_name, durable=True)
    step = 3600 #1 hour at a time
    gen.curtime = gen.prevtime + step * 1000
    for event in events:
        channel.basic_publish(exchange='', routing_key=queue_name, body=json.dumps(event))


# FOLLOWING CODE TO MAKE GRAPHS 

# import matplotlib.pyplot as plt
# from matplotlib.animation import FuncAnimation

# step = 600
# gen.initial_time = 0
# gen.prevtime = 0
# gen.curtime = 0 + step
# gen.start() # Comment above

# def update(frame):
#     plt.clf()
#     Ts.append(frame * step / 3600)
#     Capacity.append( len(p.inPark) )
#     plt.xlabel('TimeStamp')
#     plt.ylabel('Occupation')
#     plt.title('Park lotation overtime')
#     plt.legend()
#     plt.ylim(0, p.max_capacity)
#     plt.plot(Ts,Capacity)
#     events = gen.getEvents()
#     gen.curtime = gen.prevtime + step * 1000
#     print("\033c", end='')
#     print("Frame: ",frame)
#     print("Time now:" , int(frame * step / 36)/100)

# fig, ax = plt.subplots()
# animation = FuncAnimation(fig, update, frames=300, interval=10)
# animation.save('animated_plot.gif', writer='ffmpeg')


if __name__ == "__main__":
    main()
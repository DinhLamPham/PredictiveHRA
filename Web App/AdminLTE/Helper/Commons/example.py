trace = 'START!!START!@#A1!!P1!@#A2!!P2!@#A3!!P3!@#END!!END'

event = trace.split('!@#')

for e in event:
    temp = e.split('!!')
    print('activity: %s, performer: %s' %(temp[0], temp[1]))

import xml.etree.ElementTree as ET
import pandas as pd

from AdminLTE.Helper.Commons.Variables import activity_key


events = ("start", "end")
key_connect_node = '!@#'
key_connect_inside = '!!'

activity_key = 'concept:name'
performer_key = 'org:resource'
timestamp_key = 'time:timestamp'



def Save_XES_Log_list_To_CSV(_log_list, output_file):

    result = []
    for trace in _log_list:
        total_event = len(trace)
        row = [total_event]
        for event in trace:
            eventInfo = event.split(key_connect_inside)
            activity, performer, timestamp = eventInfo[0], eventInfo[1], eventInfo[2]
            row.append(activity), row.append(performer), row.append(timestamp)
        result.append(row)
        print(result)
    df = pd.DataFrame(result)
    df.to_csv(output_file)




def XESLogProcess(_input_log):
    log_list_string = []

    startTrace, endTrace, startEvent, endEvent = False, False, False, False
    trace_list, event_list = [], []
    this_event = ''
    for event, elem in ET.iterparse(_input_log, events=events):
        this_tag = elem.tag.replace(r'{http://www.xes-standard.org/}', '')
        # this_tag = elem.tag.replace(r'{http://www.xes-standard.org}', '')
        if event == "start":
            if 'trace' in this_tag:
                startTrace, endTrace, startEvent, endEvent = True, False, False, False
                event_list = ['START' + key_connect_inside + 'START' + key_connect_inside + 'START']
            elif 'event' in this_tag:
                startEvent = True
                this_act, this_per, this_time = "NULL", "NULL", "NULL"


            if 'key' in elem.attrib:
                if startEvent:
                    this_val = elem.attrib['value']
                    if elem.attrib['key'] == activity_key:
                        this_act = this_val
                    elif elem.attrib['key'] == performer_key:
                        this_per = this_val
                    elif elem.attrib['key'] == timestamp_key:
                        this_time = this_val

                elif startTrace and (not endTrace) and (elem.attrib['key'] == activity_key):
                    trace_list.append(elem.attrib['value'])
        elif event == "end":
            if 'event' in this_tag:
                startEvent = False
                this_event = this_act + key_connect_inside + this_per + key_connect_inside + this_time
                event_list.append(this_event)

            elif 'trace' in this_tag:
                startTrace = False
                event_list.append('END'+key_connect_inside+'END'+key_connect_inside+'END')
                log_list_string.append(event_list)

    Save_XES_Log_list_To_CSV(log_list_string, input_file.replace('xes', 'csv'))


input_file = 'ETM_Configuration1.xes'
XESLogProcess(input_file)


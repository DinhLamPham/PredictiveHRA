import xml.etree.ElementTree as ET
import pandas as pd

from AdminLTE.Helper.Commons.Variables import activity_key


events = ("start", "end")
key_connect_node = '!@#'
key_connect_inside = '!!'

activity_key = 'concept:name'
performer_key = 'org:resource'
timestamp_key = 'time:timestamp'


def Save_XES_Log_list_To_TXT(_log_list, output_file):

    with open(output_file, 'w') as fp:
        for trace in _log_list:
            fp.write(f'{trace}\n')
        print('write file done!!!')





def XESLogProcess(_input_log):
    log_list_string = []

    count = 0

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
                this_trace = key_connect_node.join(event_list)
                log_list_string.append(this_trace)
                print(f'----------------append number trace: {len(log_list_string)}')

    Save_XES_Log_list_To_TXT(log_list_string, input_file.replace('xes', 'txt'))


input_file = 'bpi2012.xes'
XESLogProcess(input_file)


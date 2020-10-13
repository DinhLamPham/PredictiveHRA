from AdminLTE.Helper.Commons.Variables import *
from AdminLTE.Helper.MySQL.Query import SelectColsWithConditions, SelectColWithCondition, InsertRowToTable
from AdminLTE.Helper.MySQL.mySQLSettings import *
from datetime import datetime
import dateutil.parser


def GetDurationBetween(_startTime, _endTime):
    if _startTime == "NULL" or _endTime == "NULL" or _endTime == "END":
        return 0

    _startTime = dateutil.parser.parse(_startTime)
    _endTime = dateutil.parser.parse(_endTime)
    # result = duration.total_seconds() / (60 * 60)
    return _endTime - _startTime

def xes_to_trace_list_info(_logid, _loglist):
    spec_set = ['START', 'END', 'NULL']
    for idx, events in enumerate(_loglist):
        count_act, count_per = dict(), dict()
        try:
            trace_duration = GetDurationBetween(events[1].split(key_connect_inside)[-1],
                                                events[-2].split(key_connect_inside)[-1])
        except:
            trace_duration = 0

        for event in events:
            e = event.split(key_connect_inside)
            if e[0] not in spec_set:
                if e[0] in count_act:
                    count_act[e[0]] += 1
                else:
                    count_act[e[0]] = 1
            if e[1] not in spec_set:
                if e[1] in count_per:
                    count_per[e[1]] += 1
                else:
                    count_per[e[1]] = 1

        print(idx, '/', len(_loglist), ': acts: ', len(count_act), ' pers: ', len(count_per))

        _rowsTuple = (_logid, idx, len(count_act), len(count_per), str(trace_duration).split('.')[0])
        InsertRowToTable(tbl_trace_info, tbl_trace_info_header, _rowsTuple)



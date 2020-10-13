from AdminLTE.Helper.Commons.Variables import *
from AdminLTE.Helper.MySQL.Query import SelectColWithCondition, SelectColsWithConditions, InsertRowToTable
from AdminLTE.Helper.MySQL.mySQLSettings import *
from AdminLTE.Helper.NetworkX.CytoscapeHelper import GraphX_to_cytoscape
from AdminLTE.Helper.NetworkX.GraphHelper import *
from myproject.settings import MEDIA_ROOT, JS_ROOT
import networkx as nx
import json


def GetSingleTraceInfo(_logid, _traceId):
    _file = MEDIA_ROOT + path_char + _logid
    count = -1
    with open(_file, encoding='utf-8') as f:
        f.readline().strip()
        f.readline().strip()
        while True:
            currentLine = f.readline()
            if not currentLine:
                break
            count += 1
            if count == int(_traceId):
                return currentLine.strip()
    return None


def GetMultipleTraceInfo(_logid, _fromTrace, _toTrace):
    _file = MEDIA_ROOT + path_char + _logid
    count = -1
    trace_list = []
    with open(_file, encoding='utf-8') as f:
        f.readline().strip()
        f.readline().strip()
        while True:
            currentLine = f.readline()
            if not currentLine:
                break
            count += 1
            if int(_fromTrace) <= count <= int(_toTrace):
                trace_list.append(currentLine.strip())
    return trace_list


def GetValueFromTrace(_trace, _pos):  # 0: activity, 1: performer, 2: timestamp
    result = []
    _trace = _trace.split(key_connect_node)
    for event in _trace:
        val = event.split(key_connect_inside)[_pos]
        if _pos == 2:
            val = val.split('.')[0]
        result.append(val)

    return result


def Trace2NetworkX(_graphX, _trace, _posType):
    GraphX_AddNode(_graphX, _trace[0].split(key_connect_inside)[_posType])
    for i in range(1, len(_trace)):
        currentNode = _trace[i].split(key_connect_inside)[_posType]
        prevNode = _trace[i - 1].split(key_connect_inside)[_posType]

        GraphX_AddNode(_graphX, currentNode)
        GraphX_AddEdge(_graphX, prevNode, currentNode)


def LoadJsonFromDB(_filename, _traces):
    _columnCondition = ['logid', 'traces']
    _valueCondition = [_filename, _traces]
    df_cache = SelectColsWithConditions(tbl_json_cache_header, tbl_json_cache, _columnCondition, _valueCondition)
    row = df_cache.values.tolist()
    jsonData = row[0][-1]
    return jsonData


def GenerateJsonFromInputTraces(_filename, _fromid, _toid, _posType):
    _fromid, _toid = str(_fromid), str(_toid)
    multi_trace = GetMultipleTraceInfo(_filename, _fromid, _toid)
    graph = nx.DiGraph()
    for trace in multi_trace:
        trace = trace.split(key_connect_node)
        Trace2NetworkX(graph, trace, _posType)

    jsondata = GraphX_to_cytoscape(graph)
    graph_jsfile = JS_ROOT + path_char + 'graph.json'
    with open(graph_jsfile, 'w') as json_file:
        json.dump(jsondata, json_file)
        print('-----------finished generate json file--FromID: %s-----ToID: %s-------' % (_fromid, _toid))
    return jsondata


if __name__ == "__main__":
    GenerateJsonFromInputTraces('ETM_Configuration1.txt', 1, 2)

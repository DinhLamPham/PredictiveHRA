import networkx as nx
import matplotlib.pyplot as plt

from AdminLTE.Helper.Commons.Variables import key_connect_inside


def DisplayGraphWithWeight(_graph):
    edge_labels = dict([((u, v,), d['weight'])
                        for u, v, d in _graph.edges(data=True)])
    pos = nx.spring_layout(_graph)
    nx.draw(_graph, pos, edge_color='black', width=1, linewidths=1, node_size=500, node_color='pink', alpha=0.9,
            labels={node: node for node in _graph.nodes()})
    nx.draw_networkx_edge_labels(_graph, pos, edge_labels=edge_labels, font_color='red')
    plt.show()


def DisplayGraph(_graph):
    nx.draw(_graph)
    plt.show()


def GraphX_AddNode(_graphX, _node):
    if not _graphX.has_node(_node):
        _graphX.add_node(_node, occurence=1)
    else:
        _graphX.nodes[_node]['occurence'] += 1


def GraphX_AddEdge(_graphX, _fromNode, _toNode):
    if _graphX.has_edge(_fromNode, _toNode):
        _graphX[_fromNode][_toNode]['weight'] += 1
    else:
        _graphX.add_edge(_fromNode, _toNode, weight=1, id=_fromNode+key_connect_inside+_toNode)

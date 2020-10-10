
import networkx as nx

__all__ = ['GraphX_to_cytoscape', 'cytoscape_to_graphX']

_attrs = dict(name='name', ident='id')


def GraphX_to_cytoscape(G, attrs=None):
    """Returns data in Cytoscape JSON format (cyjs).
    """
    if not attrs:
        attrs = _attrs
    else:
        attrs.update({k: v for (k, v) in _attrs.items() if k not in attrs})

    name = attrs["name"]
    ident = attrs["ident"]

    if len(set([name, ident])) < 2:
        raise nx.NetworkXError('Attribute names are not unique.')

    jsondata = {}
    jsondata = {"nodes": [], "edges": []}
    nodes = jsondata["nodes"]
    edges = jsondata["edges"]

    for i, j in G.nodes.items():
        n = {"data": j.copy()}
        n["data"]["id"] = j.get(ident) or str(i)
        n["data"]["value"] = i
        n["data"]["name"] = j.get(name) or str(i)
        nodes.append(n)

    if G.is_multigraph():
        for e in G.edges(keys=True):
            n = {"data": G.adj[e[0]][e[1]][e[2]].copy()}
            n["data"]["source"] = e[0]
            n["data"]["target"] = e[1]
            n["data"]["key"] = e[2]
            edges.append(n)
    else:
        for e in G.edges():
            n = {"data": G.adj[e[0]][e[1]].copy()}
            n["data"]["source"] = e[0]
            n["data"]["target"] = e[1]
            edges.append(n)
    return jsondata



def cytoscape_to_graphX(data, attrs=None):
    if not attrs:
        attrs = _attrs
    else:
        attrs.update({k: v for (k, v) in _attrs.items() if k not in attrs})

    name = attrs["name"]
    ident = attrs["ident"]

    if len(set([ident, name])) < 2:
        raise nx.NetworkXError('Attribute names are not unique.')

    multigraph = data.get('multigraph')
    directed = data.get('directed')
    if multigraph:
        graph = nx.MultiGraph()
    else:
        graph = nx.Graph()
    if directed:
        graph = graph.to_directed()
    graph.graph = dict(data.get('data'))
    for d in data["elements"]["nodes"]:
        node_data = d["data"].copy()
        node = d["data"]["value"]

        if d["data"].get(name):
            node_data[name] = d["data"].get(name)
        if d["data"].get(ident):
            node_data[ident] = d["data"].get(ident)

        graph.add_node(node)
        graph.nodes[node].update(node_data)

    for d in data["elements"]["edges"]:
        edge_data = d["data"].copy()
        sour = d["data"].pop("source")
        targ = d["data"].pop("target")
        if multigraph:
            key = d["data"].get("key", 0)
            graph.add_edge(sour, targ, key=key)
            graph.edges[sour, targ, key].update(edge_data)
        else:
            graph.add_edge(sour, targ)
            graph.edges[sour, targ].update(edge_data)
    return graph

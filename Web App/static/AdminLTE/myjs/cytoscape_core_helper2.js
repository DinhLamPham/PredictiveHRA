function UpdateNodeOccurence(nodeEle, newValue){
    let currentNode = cy.$id(nodeEle['id'])
    nodeEle._private.data['occurence'] = newValue
}

function GetSelfLoopValue(node)
{
    let edgeId = node['id']+keyConnectInside+node['id']
    let selfLoopEdge = cy.$id(edgeId)
    if (selfLoopEdge['length'] == 0)
        return 0

    let edgeWeight = selfLoopEdge._private.data['weight']
    return edgeWeight
}

// Check occurrence of every node in group. Input list of node. ouput: 1: And. 2: Or. 3: Complex
function CheckGateTypeForList(captain_node, list_of_nodes){
    let sum_occurrence = 0
    let equal_all = true

    if (list_of_nodes.length <= 1)
        return 'No_Gate'

    if(captain_node._private.data['id'].includes('Gate_'))
        return 'No_Gate'

    for(let i = 0; i < list_of_nodes.length; i++){

        sum_occurrence += list_of_nodes[i]._private.data['occurence']
        if(i>0){
            if(list_of_nodes[i-1]._private.data['occurence'] != list_of_nodes[i]._private.data['occurence'])
                equal_all = false
        }
    }
    if (sum_occurrence == captain_node._private.data['occurence'])
        return 'OR'
    if (equal_all)
        return 'AND'
    return 'COMPLEX'
}


// Create a list of node to process gate. The order of list follows the BFS order and the begin node is 'START'
function ListOfNodeToVisit(){
    let waiting_list = new Array()
    waiting_list.push('START')
    let result_list = new Array()

    while(waiting_list.length > 0){
        let first_val = waiting_list.shift()
        if(result_list.indexOf(first_val) == -1)
            result_list.push(first_val)

        let list_of_son = GetEleOutgoing(cy.getElementById(first_val), 'nodes')

        list_of_son.forEach(sonEle =>{
            if(result_list.indexOf(sonEle._private.data['id']) == -1)
                waiting_list.push(sonEle._private.data['id'])
        })
    }
    return result_list

}


//Create Virtual Open Gate to conect list_of_son (Son_list, Type). _typeClass=orGateClass/ andGateClass in Variables list
function CreateVirtualOpenGate(_son_list_data, _typeClass){
    // Create new Gate and Add to Cy
    let openGateName = _typeClass + '_Open_' + (gateArr.length + 1)
    gateArr.push(openGateName)
    let gateNodeData = {id: openGateName, name: openGateName, occurence: '-'}
    AddNodeToCy(gateNodeData, _typeClass)

    //Create edges from gate => _son_list
    _son_list_data.forEach(son =>{
        let edgeData = {id: gateNodeData['id'] + keyConnectInside + son['id'],
                    weight: son['occurence'],
                    source: gateNodeData['id'],
                    target: son['id']}
        AddEdge(edgeData, 'NewEdge')
    })
    return openGateName

}

//Create Open Gate in ICN model:  (Node, Son_list, Type)
function CreateOpenGate(_node, _son_list, _typeClass){

    // Create new Gate and Add to Cy
    let openGateName = _typeClass + '_Open_' + (gateArr.length + 1)
    let gateOccurence = _node._private.data['occurence']
    gateArr.push(openGateName)
    let gateNodeData = {id: openGateName, name: openGateName, occurence: gateOccurence}
    AddNodeToCy(gateNodeData, _typeClass)

    //Remove all edges from _node to _son_list
    _son_list.forEach(sonEle =>{
        let edgeToRemoveId = _node._private.data['id'] + keyConnectInside + sonEle._private.data['id']
        RemoveElement(edgeToRemoveId)
    })

    //Create edges from _node => gate
    let edgeData = {id: _node._private.data['id'] + keyConnectInside + gateNodeData['id'],
                    weight: _node._private.data['occurence'],
                    source: _node._private.data['id'],
                    target: gateNodeData['id']}
    AddEdge(edgeData, 'NewEdge')

    //Create edges from gate => _son_list
    _son_list.forEach(sonEle =>{
        let edgeData = {id: gateNodeData['id'] + keyConnectInside + sonEle._private.data['id'],
                    // weight: sonEle._private.data['occurence'],
                    weight: '-', //---------------- // WILL BE CALCULATED LATER
                    source: gateNodeData['id'],
                    target: sonEle._private.data['id']}
        AddEdge(edgeData, 'NewEdge')
    })

}


// Create Virtual close Gate to connect list of father
function CreateVirtualCloseGate(_father_list_data, _typeClass){

    // Create new Gate and Add to Cy
    let closeGateName = _typeClass + '_Close_' + (gateArr.length + 1)

    gateArr.push(closeGateName)
    let gateNodeData = {id: closeGateName, name: closeGateName, occurence: '-'}
    AddNodeToCy(gateNodeData, _typeClass)

    //Create edges from _father_list => virtual gate
    _father_list_data.forEach(father =>{
        let edgeData = {id: father['id'] + keyConnectInside + gateNodeData['id'],
                    weight: father['occurence'],
                    source: father['id'],
                    target: gateNodeData['id']}
        AddEdge(edgeData, 'NewEdge')
    })
    return closeGateName
}



// Create Close Gate
function CreateCloseGate(_node, _father_list, _typeClass){

    // Create new Gate and Add to Cy
    let closeGateName = _typeClass + '_Close_' + (gateArr.length + 1)
    let gateOccurence = _node._private.data['occurence']
    gateArr.push(closeGateName)
    let gateNodeData = {id: closeGateName, name: closeGateName, occurence: gateOccurence}
    AddNodeToCy(gateNodeData, _typeClass)

    //Remove all edges from _father_list to _node
    _father_list.forEach(fatherEle =>{
        let edgeToRemoveId = fatherEle._private.data['id'] + keyConnectInside + _node._private.data['id']
        RemoveElement(edgeToRemoveId)
    })

    //Create edges from gate  => _node
    let edgeData = {id: gateNodeData['id'] + keyConnectInside + _node._private.data['id'],
                    weight: _node._private.data['occurence'],
                    source: gateNodeData['id'],
                    target: _node._private.data['id']}
    AddEdge(edgeData, 'NewEdge')

    //Create edges from _father_list => gate
    _father_list.forEach(fatherEle =>{
        let edgeData = {id: fatherEle._private.data['id'] + keyConnectInside + gateNodeData['id'],
                    // weight: fatherEle._private.data['occurence'],
                    weight: '-',  //---------------- // WILL BE CALCULATED LATER
                    source: fatherEle._private.data['id'],
                    target: gateNodeData['id']}
        AddEdge(edgeData, 'NewEdge')
    })

}
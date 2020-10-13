let example_nodes = ['A', 'G']
function GetSampleData(){
    let exampleData = document.getElementById('txtSample').value
    if(exampleData.length>0)
        example_nodes = exampleData.split(',')
    else{
        let node_list = cy.elements('node')
        node_list.forEach(node=>{
            example_nodes.push(node._private.data['id'])
        })
    }

}

function BuildICNModel(){
    GetSampleData()
    let node_visit_list = ListOfNodeToVisit()
    node_visit_list.forEach(nodeId =>{
        if (example_nodes.indexOf(nodeId)!=-1){
            // Get list of Son and list of father
            let _captain_node = cy.getElementById(nodeId)
            let list_of_son = GetEleOutgoing(_captain_node, 'nodes')
            let list_of_father = GetEleIncoming(_captain_node, 'nodes')

            // Check gate type to create
            let openGateType = CheckGateTypeForList(_captain_node, list_of_son)
            let closeGateType = CheckGateTypeForList(_captain_node, list_of_father)

            // Processing for Open gate gate
            switch (openGateType){
                case 'AND':
                    CreateOpenGate(cy.getElementById(nodeId), list_of_son, andGateClass)
                    break
                case 'OR':
                    CreateOpenGate(cy.getElementById(nodeId), list_of_son, orGateClass)
                    break
                case 'COMPLEX':
                    let _list_of_son_data = list_of_ele_to_list_of_data(list_of_son),
                        _captain_node_data = _captain_node._private.data, _gateType = 'Open'
                    ProcessForComplexCases(_captain_node_data, _list_of_son_data, _gateType)
                    break
            }

            // Processing for close gate
            switch (closeGateType){
                case 'AND':
                    CreateCloseGate(cy.getElementById(nodeId), list_of_father, andGateClass)
                    break
                case 'OR':
                    CreateCloseGate(cy.getElementById(nodeId), list_of_father, orGateClass)
                    break
                case 'COMPLEX':
                    let _list_of_father_data = list_of_ele_to_list_of_data(list_of_father),
                        _captain_node_data = _captain_node._private.data, _gateType = 'Close'
                    ProcessForComplexCases(_captain_node_data, _list_of_father_data, _gateType)
                    break
            }
        }
    })

    // Processing for Loop gate. OrGate <-> Orgate => Loop Gate.
    ProcessForLoopGate()

    //Remove colored edges
    RemoeEdgeNewClasses()

    //Refresh Graph
    VisualizeGraph()
}

// Process for complicated cases.
function ProcessForComplexCases(_captain_node_data, _list_of_nodes_data, _gateType){
    // let A = {id: 'A', 'occurence': 11}, B = {id: 'B', 'occurence': 12}, C = {id: 'C', 'occurence': 10},
    //     D = {id: 'D', 'occurence': 20}, E = {id: 'E', 'occurence': 12}
    // let _list_of_node = [A, B, C, D, E]
    let roomArray = NodeAndRooms(_list_of_nodes_data)

    let new_node_list = new Array()
    console.log('Room of nodes: ')
    console.log(roomArray)

    //Connect memebers in the same room by a virtual gate.
    roomArray.forEach(room => {
        let new_gate_name = room
        let room_member = room.split(keyConnectInside)
        if(room_member.length>1){
            // new_gate_name = _gateType + '_' + room  // Se thay bang tao AND gate moi cho Group nay. Goi ham.
            let list_of_members = new Array()
            room_member.forEach(memberId=>{
                list_of_members.push(cy.getElementById(memberId)._private.data)
            })

            switch(_gateType){
                case 'Open':
                    new_gate_name = CreateVirtualOpenGate(list_of_members, andGateClass)
                    console.log('newGateName: ' + new_gate_name)
                    break;
                case 'Close':
                    new_gate_name = CreateVirtualCloseGate(list_of_members, andGateClass)
                    break;
            }
        }
        new_node_list.push(new_gate_name)
    })

    // Da co new_node_list => Ket noi voi Captain bang OrGate.
    // new_node_list: [A!!B, C!!E, D]
    let captain_ele = cy.getElementById(_captain_node_data['id'])
    let new_node_list_ele = new Array()
    new_node_list.forEach(nodeId =>{
        new_node_list_ele.push(cy.getElementById(nodeId))
    })

    //Create the main Gate between captain and list
    switch(_gateType)
    {
        case 'Open':
            CreateOpenGate(captain_ele, new_node_list_ele, orGateClass)
            break;
        case 'Close':
            CreateCloseGate(captain_ele, new_node_list_ele, orGateClass)
            break;
    }

    //Remove the old relationship between captain with _list_of_node
    switch(_gateType){
        case 'Open':
            Remove_Edge_Between_Captain_And_List(_captain_node_data, _list_of_nodes_data, 'Open')
            break;
        case 'Close':
            Remove_Edge_Between_Captain_And_List(_captain_node_data, _list_of_nodes_data, 'Close')
            break;
    }
}

// Input: array of nodes and its number. Output: Rooms contain nodes which has the same occurrence number.
function NodeAndRooms(_list_of_node){
    let flagArray = new Array(_list_of_node.length)
    flagArray.fill(0)

    let roomArray = new Array()
    for(let i=0; i<_list_of_node.length; i++){
        if(flagArray[i] == 0){
            roomArray.push(_list_of_node[i]['id'])
            flagArray[i] = 1
            let label = _list_of_node[i]['occurence']

            if(i<_list_of_node.length-1)
                for(let j=i+1; j<_list_of_node.length; j++){
                    if(flagArray[j] == 0 && _list_of_node[j]['occurence']==label){
                        roomArray[roomArray.length-1] = roomArray[roomArray.length-1] + keyConnectInside + _list_of_node[j]['id']
                        flagArray[j] = 1
                    }
                }
        }
    }
    return roomArray
}

// Process for Loop Gate. OrGate <-> OrGate => LoopGate
function ProcessForLoopGate(){
    let count=0
    for(let i=0; i<gateArr.length-1; i++){
        let gateA = gateArr[i]
        if(gateA.indexOf('OrGate')!=-1)
            for(let j=i+1; j<gateArr.length; j++){
                let gateB = gateArr[j]
                if(gateB.indexOf('OrGate')!=-1){
                    let gateA_type = gateA.substring(7, 12)
                    let gateB_type = gateB.substring(7, 12)

                    if(gateA_type!=gateB_type){
                        //Check this Pair.
                        let checkA2B = DFS(gateA, gateB)['length']==1?true:false, checkB2A = DFS(gateB, gateA)['length']==1?true:false
                        if(checkA2B && checkB2A){
                            // Change OrOpen -> LoopClose; OrClose -> LoopOpen
                            ConvertOrGateToLoopGate(gateA)
                            ConvertOrGateToLoopGate(gateB)
                        }
                    }
                }
            }
    }

}






















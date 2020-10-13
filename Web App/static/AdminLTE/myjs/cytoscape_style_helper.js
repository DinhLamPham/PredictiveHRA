let showNodeOcc = false
let showEdgeWeight = false
function SetStartEndStyle(){
    cy.style()

    .selector('.ENDclass')
        .style({
        'background-color': 'green',
        'shape': 'triangle',
         "color": "black",
        "font-size": 15,
            "size": 30


    })
    .selector('.AndGate')
        .style({
        'background-color': 'black',
        'shape': 'ellipse',
         "color": "black",
        'height': 10,
        'width': 10,
    })

    .selector('.OrGate')
        .style({
        'background-color': 'white',
        'shape': 'ellipse',
         "color": "black",
        'height': 10,
        'width': 10,
    })

    .selector('.LoopGate')
        .style({
        'background-color': 'red',
        'shape': 'ellipse',
         "color": "black",
        'height': 10,
        'width': 10,
    })

    .selector('.Recoverclass')
        .style({
         'line-color': 'green',
         'target-arrow-color': 'green'
    })

    .selector('.NewEdge')
        .style({
         'line-color': 'blue',
         'target-arrow-color': 'blue'
    })


      .update()
    cy.$('#START').classes('ENDclass');
    cy.$('#END').classes('ENDclass')

}

// function SetNodeSize(){
//     cy.style()
//         .selector('node')
//         .style(({
//             height: 60,
//             width: 60
//         }))
//         .update()
//     // console.log('hello world! I am settting node size()')
// }

function SetEdgeSize(){
    cy.style()
        .selector('edge')
        .style(({
            width:3
        }))
        .update()

}

function SetNodeColor(_nodeEle, _color){
    _nodeEle.style({
        'background-color': _color
    })
}


function SetNodeOccurence(currentNode, flag){

    let occurence = currentNode._private.data['occurence']
    let name = currentNode._private.data['name']
    if (flag) {
        currentNode.style({"label": name +  ":" + occurence})
    }
    else{currentNode.style({"label": name})}
}

function ShowHideNodeOccurence(){
    showNodeOcc = !showNodeOcc

    let node_list = cy.elements('node');

    for (let i=0; i<node_list.size(); i++){
        SetNodeOccurence(node_list[i], showNodeOcc)
    }
}

function SetEdgeWeight(edge, flag){
    let weight = edge._private.data['weight']
    if (flag) {
        edge.style({"label": weight, 'font-size': 10})
    }
    else{edge.style({"label": ""})}
}

function ShowHideEdgeWeight(){
    showEdgeWeight = !showEdgeWeight;

    let edge_list = cy.elements('edge');

    for (let i=0; i<edge_list.size(); i++){
        let edge = edge_list[i]
        SetEdgeWeight(edge, showEdgeWeight)

    }
}


// OrGate_Close_x -> LoopGate_Open_x                 OrGate_Open_x -> LoopGate_Close_x          change format.
function ConvertOrGateToLoopGate(_orGateId){
    let temp = _orGateId.split('_')
    let idx = temp.pop(), gateType = temp.pop()
    let newGateName = 'LoopGate' + '_Open_' + idx
    if(gateType == 'Open')
        newGateName = 'LoopGate' + '_Close_' + idx

    let node = cy.$id(_orGateId)
    if(node!=null){
        node._private.data['name'] = newGateName
        node.removeClass('OrGate')
        node.classes('LoopGate')
//    cy.$id(edgeData['id']).classes(edgeClass);

    console.log('old Name: ', _orGateId, 'new Name: ', newGateName)
    }
}

function RemoeEdgeNewClasses(){
    let edgeList = cy.elements('')

    edgeList.forEach(edge=>{
        edge.removeClass('NewEdge')
        edge.removeClass('Recoverclass')
    })
}

function AdjAllNodeSize(wValue, hValue){
    // console.log(nodeEle.style())
    let currentHeight = cy.$id('START').style()['height'].replace('px', ''),
        currentWidth = cy.$id('START').style()['width'].replace('px', '')
    let newHValue = (parseInt(currentHeight) + hValue), newWvalue = (parseInt(currentWidth) + wValue)

    if (newHValue<0 || newWvalue<0) return

    cy.style()
    .selector('node')
        .style({
         'height':  newHValue + 'px',
        'width':    newWvalue +'px',
    }).update()
}

function SetNodeSize(_nodeEle, _value){

    _nodeEle.style({
        'height': _value +'px',
        'width': _value +'px'
    })
}

function HighlightNode(_nodeEle){
    let showIncomingEdge = document.getElementById('showIncomingEdge').checked
    let showOutgoingEdge = document.getElementById('showOutgoingEdge').checked

    cy.edges().style({
        'line-color': '#f7f8fa',
         'target-arrow-color': '#f7f8fa'
    })
    if(showIncomingEdge){
        let inComingEdgeList = GetEleIncoming(_nodeEle, 'edges')
        inComingEdgeList.forEach(edge => {
            edge.style({
                 'line-color': '#ffaaaa',
                 'target-arrow-color': '#ffaaaa'
            })
        })
    }

    if(showOutgoingEdge)
    {
        let outGoingEdgeList = GetEleOutgoing(_nodeEle, 'edges')
        outGoingEdgeList.forEach(edge => {
            edge.style({
                 'line-color': '#7bc783',
                 'target-arrow-color': '#7bc783'
            })
        })
    }

}
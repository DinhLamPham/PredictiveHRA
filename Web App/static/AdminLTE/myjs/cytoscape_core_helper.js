// _type: Open => Captain -> List.    Close => list -> Captain
function Remove_Edge_Between_Captain_And_List(_captain_data, _list_of_nodes_data, _type){
    _list_of_nodes_data.forEach(nodeData=>{
        let edgeId = _captain_data['id'] + keyConnectInside + nodeData['id']
        if(_type == 'Close')
            edgeId = nodeData['id'] + keyConnectInside + _captain_data['id']
        RemoveElement(edgeId)
    })
}

function list_of_ele_to_list_of_data(list_of_ele){
    let list_of_data = new Array()
    list_of_ele.forEach(ele =>{
        list_of_data.push(ele._private.data)
    })
    return list_of_data
}

function GetCyNode(id){
    let currentNode = cy.nodes().filter(function( ele ){
      return ele.data('id') == id;
    });
    return currentNode;
}

function GetNodeValue(node, id, field){
    return node._private.map.get(id).ele._private.data[field]
}



function GetCyEdge(id){
    let currentEdge = cy.edges().filter(function( ele ){
      return ele.data('id') == id;
    });
    return currentEdge;
}

function getEdgeValue(edge, id, field){
    return edge._private.map.get(id).ele._private.data[field]
}



function AddNodeToCy(nodeData, nodeClass){

    let ele = [
           {group: 'nodes', data: {id: nodeData['id'], name: nodeData['name'], occurence: nodeData['occurence']}},]
    cy.add(ele);
    cy.$id(nodeData['id']).classes(nodeClass)

}
function AddEdge(edgeData, edgeClass){
    let edge = [{group: 'edges', data: {id: edgeData['id'], weight: edgeData['weight'],
            source: edgeData['source'], target: edgeData['target']}}]
    cy.add(edge)

    if (edgeClass!=null)
        cy.$id(edgeData['id']).classes(edgeClass);
    // cy.style().update()

}

function RemoveElement(eleId){
    let ele = cy.$id(eleId)
    if (ele['length'] == 1)
        cy.remove(ele)
}


function GetEleIncoming(nodeEle, type)
{
    let resultArray= new Array()
    let inComingSet = nodeEle.incomers()
    inComingSet.forEach(element => {
                if(element._private.group == type)
                    resultArray.push(element)
            })
    return resultArray
}

// Type: 'nodes'/ 'edges'. Return type: Set
function GetEleOutgoing(nodeEle, type)
{
    let resultArray = new Array()
    let outGoingSet = nodeEle.outgoers()
    outGoingSet.forEach(element => {
                if(element._private.group == type)
                    resultArray.push(element)
            })
    return resultArray
}


function ClickNodeListener(){
    let collection = cy.collection();
    cy.nodes().on('click', function(e){
  let clickedNode = e.target;

  collection = collection.union(clickedNode);
        alert(clickedNode);
});

}


// REMOVE DELEBERATE NOISES FUNCTION---------------------------------------------
function RemoveDeleberateNoises(){
    let edge_list = cy.elements('edge');
    // Finding and marking as will be remove edges
    for (let i=0; i<edge_list.size(); i++){
        let edgeData = edge_list[i]._private.data

        let node0Id = edgeData['source']
        let node1Id = edgeData['target']

        let reverseEdgeId = node1Id + keyConnectInside + node0Id
        let virtualEdge = cy.$id(reverseEdgeId)

        if(virtualEdge['length'] == 1) // Has reverse edge
            removedEdgeSet.add(edgeData)
    }

    // Remove edges
    removedEdgeSet.forEach(ele =>{
        let eleId = ele['id']
        RemoveElement(eleId)

        // Minus weight for selfloop node
        if (ele['source'] == ele['target']){
            let selfLoopValue = ele['weight']
            let nodeEle = cy.$id(ele['source'])
            UpdateNodeOccurence(nodeEle, nodeEle._private.data['occurence'] - selfLoopValue)
        }
    })

    // Refresh Graph
    VisualizeGraph();


}

//-------------------Recover stand alone node--------------------------
function RecoverEdgeForStandAloneNode(){
    let node_list = cy.elements('node')

    node_list.forEach(node =>{
        let nodeId = node._private.data['id']
        if(nodeId != 'START' && nodeId != 'END')
            if(node.indegree() == 0 || node.outdegree() == 0){ // Gia vo => chuyen thanh 0, ket hop voi outdegree()
                removedEdgeSet.forEach(currentEdge =>{
                    if((currentEdge['source'] == nodeId || currentEdge['tartget'] == nodeId)
                        && (currentEdge['source'] != (currentEdge['target']))){
                        console.log('Recover this Edge: ' + currentEdge['id'])
                        recoverEdgeSet.add(currentEdge)
                        removedEdgeSet.delete(currentEdge)

                        // Find and add the reverse edge to recover set:
                        removedEdgeSet.forEach( reversedEdge => {
                            if(reversedEdge['source'] == currentEdge['target'] && reversedEdge['target'] == currentEdge['source']){
                                recoverEdgeSet.add(reversedEdge)
                                removedEdgeSet.delete(reversedEdge)
                            }

                        })
                    }
                })
            }
    })

    recoverEdgeSet.forEach(edge => {
        console.log(edge)
        AddEdge(edge, 'Recoverclass')
    })

    // Refresh Graph
    VisualizeGraph();
}

function CheckPathIsExist(){
    let startNode = 'END', endNode = 'START'
    let result = DFS(startNode, endNode)
    console.log(result)
}


//BFS
function BFS(startNode, endNode){
    // here is the important part of the code
    let idList = [];            // list for id storage
    let edgeList = []
    let bfs = cy.elements().bfs({
      roots: '#'+startNode,
      visit: function (v, e, u, i, depth) {
        idList[i] = v.id();
        if(e!=null)
            edgeList[i] = e._private.data['id']

        if( v.data('id') == endNode ){
            return true;
        }
      },
      directed: true
    });

}

//DFS
function DFS(startNode, endNode){
    let dfs = cy.elements().dfs({
      roots: '#'+startNode,
      visit: function(v, e, u, i, depth){

        if( v.data('id') == endNode){
          return true;
        }

        // example of exiting search early

      },
      directed: true
    });

    let path = dfs.path; // path to found node
    let found = dfs.found; // found node

    return found
}

function AdjustAllNodeSize(){
    AdjAllNodeSize(3,3)
}

function GetBetweennessCentrality(ele){
    let bc = cy.$().bc();
    return bc.betweenness(cy.$id(ele._private.data['id']))
}

function GetClosenessCentrality(ele){
    let cc = cy.$().ccn();
    return cc.closeness(cy.$id(ele._private.data['id']))
}

function GetDegreeCentrality(ele){
    let dcn = cy.$().dcn();
    console.log( 'degree centrality of ' + ele._private.data['name'] + ': ' + dcn.degree(cy.$id(ele._private.data['id'])) );
    return dcn.degree(cy.$id(ele._private.data['id']))
}
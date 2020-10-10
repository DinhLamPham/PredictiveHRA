//let node_list = cy.elements('node'); let edge_list = cy.elements('edge');
// let ele = cy.$id(eleId); let captain_ele = cy.getElementById(_captain_node_data['id'])
//cy.nodes.forEach(node =>{
//
//     })

let cy;
let check_cy = document.getElementById('cy');
if(check_cy !== null)
{
  cy = cytoscape({
      container: document.getElementById('cy'),
      boxSelectionEnabled: true,
      autounselectify: true,
      wheelSensitivity: 0.05,

      style: [
                {
                    selector: 'node',
                    style: {
                        'height': 20,
                        'width': 20,
                        'background-fit': 'cover',
                        'border-color': '#3344ac',
                        "background-color": "yellow",
                        'border-width': 1,
                        'border-opacity': 0.5,
                        "label": "data(name)",
                        "font-size": "14px",
                    }
                },
                 {
                    selector: '.ENDclass',
                    style: {
                    'background-color': 'red',
                    'shape': 'triangle',
                     "color": "black",
                    "font-size": 15,
                        }
                },

                {
                    selector: 'edge',
                    style: {
                        'curve-style': 'bezier',
                        'width': 1,
                        'target-arrow-shape': 'triangle',
                        'line-color': '#ffaaaa',
                        'target-arrow-color': '#ffaaaa'
                    }
                },

                {
                    selector: 'node.highlight',
                        style: {
                            'label': 'data(name)',
                            'text-valign': 'center',
                            'color':"white",
                            'text-outline-color': 'red',
                            'text-outline-width': 2,
                            'background-color': 'red',
                        }
                },

                {
                    selector: 'node.semitransp',
                    style:{ 'opacity': '0.5' }
                },

                ],


    }); // cy init
    cy.on('tap', 'node', function(e){  // Node on click event...............
            let ele = e.target;
            document.getElementById('txtSample').value = document.getElementById('txtSample').value
                + ',' +  ele._private.data['id']
        HighlightNode(ele)
            // SetNodeColor(ele, '#22ab56')
        // GetDegreeCentrality(ele)
            // SetNodeSize(ele, 40)
            // console.log(ele.closenessCentrality())
            // console.log(ele.degreeCentrality())

            // BFS('START', ele._private.data['id'])
            // console.log(ele._private.data)
            // let son_list = GetEleOutgoing(ele, "nodes")
            // CreateOpenGate(ele, son_list, orGateClass)

            // let father_list = GetEleIncoming(ele, "nodes")
            // CreateCloseGate(ele, father_list, orGateClass)
            // let selfLoopValue = GetSelfLoopValue(ele._private.data)
            // UpdateNodeOccurence(ele, ele._private.data['occurence'] - selfLoopValue)
            // UpdateNodeOccurence(ele,45464545)
            // console.log(GetEleIncoming(ele, 'nodes'))
            // console.log(GetEleOutgoing(ele, 'edges'))

        });

    cy.on('tap', 'edge', function(e){
            let ele = e.target;
            console.log(ele._private.data);
            // console.log(ele._private.data.id);
            // console.log('Edge weight: ' + ele._private.data['weight']);
        });
}

if (check_cy!= null)
{
  //---------------------------------------- Load data from json file
    let myObject = {};
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
          myObject = JSON.parse(this.responseText);
          initCytoscape();
      }
    };
    xhttp.open("GET", "/static/AdminLTE/myjs/graph.json", true);
    xhttp.send();

    function initCytoscape() {
      cy.add(myObject)
    }




// ------------------------------------------End loading data--------------
}





function VisualizeGraph(){
    let e = document.getElementById("visual_type");
    let input_layout = e.options[e.selectedIndex].text;
    let options = Cose_Layout(input_layout);
    let layout = cy.layout( options );
    SetStartEndStyle();
    showNodeOcc = false;
    ShowHideNodeOccurence();

    showEdgeWeight = true;
    ShowHideEdgeWeight();
    layout.run();

}

function AnimateGraph(){
cy
  .animate({
    fit: { eles: '#START' },
      zoom: 2,
      duration: 5000
  })

  .delay(1000)

  .animate({
    fit: { eles: '#END' },
      zoom: 2
  })
;
}


// ==============================New function---------------------------------

